package com.qatros.logibug.ui.report.report_automation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qatros.logibug.core.data.response.report.ReportRequest
import com.qatros.logibug.databinding.RvAmountTestingApiBinding

class RvAmountTestingApi() :
    RecyclerView.Adapter<RvAmountTestingApi.ReportViewHolder>() {

    private val reportRequest: MutableList<ReportRequest> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val binding = RvAmountTestingApiBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val report = reportRequest[position]
        holder.bind(report)
    }

    override fun getItemCount(): Int {
        return reportRequest.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(report: List<ReportRequest>) {
        this.reportRequest.clear()
        this.reportRequest.addAll(report)
        notifyDataSetChanged()
    }

    inner class ReportViewHolder(private val binding: RvAmountTestingApiBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(report: ReportRequest) {
            binding.apply {
                tvLeftAmount.text = report.resCode
                tvRightAmount.text = report.resCount.toString()
            }
        }
    }
}