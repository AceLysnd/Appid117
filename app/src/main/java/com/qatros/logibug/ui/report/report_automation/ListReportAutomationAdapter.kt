package com.qatros.logibug.ui.report.report_automation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qatros.logibug.core.data.response.report.ReportRequest
import com.qatros.logibug.core.data.response.report.VersionAutomation
import com.qatros.logibug.databinding.CvReportDetailAutomationBinding

class ListReportAutomationAdapter :
    RecyclerView.Adapter<ListReportAutomationAdapter.ReportViewHolder>()
 {

    private val versionAutomation: MutableList<VersionAutomation> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val binding = CvReportDetailAutomationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val version = versionAutomation[position]
        holder.bind(version)
    }

    override fun getItemCount(): Int {
        return versionAutomation.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(versionAutomation : List<VersionAutomation>) {
        this.versionAutomation.clear()
        this.versionAutomation.addAll(versionAutomation)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(report: ReportRequest)
    }

    inner class ReportViewHolder(private val binding: CvReportDetailAutomationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(versionAutomation: VersionAutomation) {
            binding.apply {
                tvNameTestingReportDetailAutomatic.text = versionAutomation.versionName
                tvAmountRequestReportDetailAutomatic.text = versionAutomation.reqTotal.toString()
            }
        }
    }
}