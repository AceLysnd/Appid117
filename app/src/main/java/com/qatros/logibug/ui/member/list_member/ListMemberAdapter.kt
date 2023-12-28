package com.qatros.logibug.ui.member.list_member

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qatros.logibug.core.data.response.member.DetailMember
import com.qatros.logibug.databinding.ItemMemberBinding

class ListMemberAdapter(private val listMember: List<DetailMember>) :
    RecyclerView.Adapter<ListMemberAdapter.MemberListViewHolder>() {

    class MemberListViewHolder(private val binding: ItemMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(member: DetailMember){
                with(binding){
                    tvUsernameMember.text = member.email
                    tvRoleMember.text = member.role
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