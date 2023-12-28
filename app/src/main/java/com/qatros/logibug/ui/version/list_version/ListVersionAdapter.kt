package com.qatros.logibug.ui.version.list_version

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.qatros.logibug.R
import com.qatros.logibug.core.data.response.version.AllVersionResponse
import com.qatros.logibug.databinding.ItemTestingVersionBinding

class ListVersionAdapter(private val listVersions: List<AllVersionResponse>) :
    RecyclerView.Adapter<ListVersionAdapter.VersionListViewHolder>() {

    class VersionListViewHolder(private val binding: ItemTestingVersionBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(version: AllVersionResponse){
                with(binding){
                    tvVersionName.text = version.name
                    val role = ListAllVersionFragment.role

                    if (role == "dev"){
                        ibMenuVersionTesting.visibility = View.GONE
                    }else{
                        ibMenuVersionTesting.visibility = View.VISIBLE
                    }

                    ibMenuVersionTesting.setOnClickListener {
                        showItemMenu(it, version)
                    }

                    cvItemVersion.setOnClickListener {
                        listenerVersion?.onCardVersionClicked(version.projectId, version.id, version.name)
                    }

                }
            }

        private fun showItemMenu(view: View, version: AllVersionResponse) {
            val typeTest = ListAllVersionFragment.typeTest
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.menuInflater.inflate(R.menu.menu_version, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.edit_version -> {
                        listenerVersion?.editVersion(version.projectId, version.id, typeTest)
                        true
                    }

                    R.id.duplicate_version -> {
                        listenerVersion?.cloneVersion(version.projectId, version.id, version.name, typeTest)
                        true
                    }

                    R.id.delete_version -> {
                        listenerVersion?.deleteVersion(version.projectId, version.id, version.name, typeTest)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VersionListViewHolder {
        val binding = ItemTestingVersionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VersionListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VersionListViewHolder, position: Int) {
        holder.bind(listVersions[position])
    }

    override fun getItemCount(): Int {
        return listVersions.size
    }

    companion object {
        var listenerVersion: VersionListListener? = null
    }

}

interface VersionListListener {
    fun deleteVersion(projectId: Int, versionId: Int, versionName: String, typeTest: String)
    fun editVersion(projectId: Int, versionId: Int, typeTest: String)
    fun onCardVersionClicked(projectId: Int, versionId: Int, nameVersion: String)
    fun addVersion(id: Int, typeTest: String)
    fun cloneVersion(projectId: Int, versionId: Int, versionName: String, typeTest: String)
}