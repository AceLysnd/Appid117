package com.qatros.logibug.ui.member.list_member

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qatros.logibug.core.data.response.member.DetailMember
import com.qatros.logibug.databinding.ItemMemberBinding
import java.util.Random

class ListMemberAdapter(private val listMember: List<DetailMember>) :
    RecyclerView.Adapter<ListMemberAdapter.MemberListViewHolder>() {

    class MemberListViewHolder(private val binding: ItemMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(member: DetailMember){

                with(binding){
                    tvUsernameMember.text = member.email
                    tvRoleMember.text = member.role

                    when (member.role) {
                        "po" -> tvRoleMember.text = "Owner"
                        "dev" -> tvRoleMember.text = "Programmer"
                        "qa" -> tvRoleMember.text = "QA"
                    }

                    val firstLetters = member.email.split("")
                        .filter { it.isNotBlank() }
                        .take(1)
                        .map { it.take(1) }
                    tvIconUsername.text = firstLetters[0]

                    val random = Random()
                    val randomNumber = random.nextInt(4) + 1
                    when (randomNumber) {
                        1 -> tvIconUsername.backgroundTintList = ColorStateList.valueOf(
                            Color.parseColor("#554AF0"))
                        2 -> tvIconUsername.backgroundTintList = ColorStateList.valueOf(
                            Color.parseColor("#1A8A0F"))
                        3 -> tvIconUsername.backgroundTintList = ColorStateList.valueOf(
                            Color.parseColor("#F99B13"))
                        4 -> tvIconUsername.backgroundTintList = ColorStateList.valueOf(
                            Color.parseColor("#DD00D2"))
                    }
                }
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberListViewHolder {
        val binding = ItemMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberListViewHolder, position: Int) {
        holder.bind(listMember[position])
    }

    override fun getItemCount(): Int {
        return listMember.size
    }

    companion object{
        var listenerMember: MemberListListener? = null
    }

}

interface MemberListListener{
    fun editMember(projectId: Int)
}