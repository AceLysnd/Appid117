package com.qatros.logibug.ui.homepage.list_project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.qatros.logibug.R
import com.qatros.logibug.core.data.response.project.ProjectResponse
import com.qatros.logibug.databinding.ItemListProjectNameBinding

class ListProjectAdapter(private val listProjects: List<ProjectResponse>) :
    RecyclerView.Adapter<ListProjectAdapter.ProjectListViewHolder>() {

    class ProjectListViewHolder(private val binding: ItemListProjectNameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(project: ProjectResponse) {
            with(binding) {
                tvProjectName.text = project.projectName
                tvTypeTest.text = project.typeTest
                if (project.platform == "mobile") {
                    ivPatform.setImageResource(R.drawable.ic_mobile_color)
                }
                if (project.platform == "web") {
                    ivPatform.setImageResource(R.drawable.ic_web_color)
                }
                ibEditItemListMobileHomepage.setOnClickListener {
                    showItemMenu(it, project)
                }
                cvItemProject.setOnClickListener{
                    listenerProject?.onCardProjectClicked(project.id, project.typeTest)
                }
            }
        }

        private fun showItemMenu(view: View, project: ProjectResponse) {
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.menuInflater.inflate(R.menu.menu_project, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.edit_project -> {
                        listenerProject?.editProject(project.id)
                        true
                    }
                    R.id.delete_project -> {
                        listenerProject?.deleteProject(project.id, project.projectName)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectListViewHolder {
        val binding =
            ItemListProjectNameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectListViewHolder, position: Int) {
        holder.bind(listProjects[position])
    }

    override fun getItemCount(): Int {
        return listProjects.size
    }

    companion object {
        var listenerProject: ProjectListListener? = null
    }

}


interface ProjectListListener {
    fun deleteProject(id: Int, projectName: String)
    fun editProject(id: Int)
    fun onCardProjectClicked(projectId: Int, typeTest: String)
}