package com.qatros.logibug.ui.report.report_manual

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.qatros.logibug.R
import com.qatros.logibug.core.data.response.report.Version
import com.qatros.logibug.databinding.CvReportDetailManualBinding

class ListReportManualAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<ListReportManualAdapter.ReportViewHolder>() {

    private val versions: MutableList<Version> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val binding = CvReportDetailManualBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val version = versions[position]
        holder.bind(version)
    }

    override fun getItemCount(): Int {
        return versions.size
    }

    fun setData(versions: List<Version>) {
        this.versions.clear()
        this.versions.addAll(versions)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(version: Version)
    }

    inner class ReportViewHolder(private val binding: CvReportDetailManualBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val progressBar: ProgressBar = binding.progressIndicatorLevel
        private var progressAnimator: ObjectAnimator? = null

        fun bind(version: Version) {
            binding.apply {
                tvNameTestingReportDetailManual.text = version.versionName
                tvAmountIndicatorLevel.text = version.percentage.toString()
                tvAmountTestCaseReportDetailManual.text = version.testCaseCount.toString()
                tvAmountFailReportDetailManual.text = version.testCaseFailCount.toString()
                tvAmountPassReportDetailManual.text = version.testCasePassCount.toString()

                val progress = (version.percentage).toInt()
                val maxProgress = 100

                if (progressAnimator != null && progressAnimator!!.isRunning) {
                    progressAnimator!!.cancel()
                }

                progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, progress)
                    .apply {
                        progressBar.progress = 0
                        ObjectAnimator.ofInt(progressBar, "progress", progress)
                            .apply {
                                progressBar.max = maxProgress
                                duration = PROGRESS_BAR_ANIMATION_DURATION.toLong()
                                interpolator =
                                    DecelerateInterpolator()
                                start()
                            }
                    }
            }
        }
    }
    companion object {
        const val PROGRESS_BAR_ANIMATION_DURATION = 400L
    }
}