package com.qatros.logibug.ui.report.list_report

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qatros.logibug.R
import com.qatros.logibug.core.data.response.project.ProjectResponse
import com.qatros.logibug.databinding.ItemListProjectNameBinding

class ListAllReportAdapter(private val listProjectsReport: List<ProjectResponse>) :
    RecyclerView.Adapter<ListAllReportAdapter.ProjectListReportViewHolder>() {

    inner class ProjectListReportViewHolder(private val binding: ItemListProjectNameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(project: ProjectResponse) {
            with(binding) {
                tvProjectName.text = project.projectName
                tvTypeTest.text = project.typeTest

                when (project.platform) {
                    "mobile" -> {
                        ivPatform.setImageResource(R.drawable.ic_mobile_color)
                    }
                    "web" -> {
                        ivPatform.setImageResource(R.drawable.ic_web_color)
                    }
                    "api" -> {
                        ivPatform.setImageResource(R.drawable.ic_api_color)
                    }
                }

                ibEditItemListMobileHomepage.visibility = View.GONE

                cvItemProject.setOnClickListener {
                    listProjectsReportListener?.onCardProjectReportClicked(project.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectListReportViewHolder {
        val binding = ItemListProjectNameBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProjectListReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectListReportViewHolder, position: Int) {
        holder.bind(listProjectsReport[position])
    }

    override fun getItemCount(): Int {
        return listProjectsReport.size
    }

    companion object {
        var listProjectsReportListener: ProjectListReportListener? = null
    }

    interface ProjectListReportListener {
        fun onCardProjectReportClicked(projectId: Int)
    }
}