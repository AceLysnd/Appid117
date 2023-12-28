package com.qatros.logibug.ui.testcase.list_test_case

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.qatros.logibug.R
import com.qatros.logibug.core.data.response.test_case.DetailTestCase
import com.qatros.logibug.databinding.ItemTestCaseBinding
import com.qatros.logibug.ui.version.list_version.ListAllVersionFragment

class ListTestCaseAdapter(private val listTestCase: List<DetailTestCase>) :
    RecyclerView.Adapter<ListTestCaseAdapter.TestCaseViewHolder>() {
    class TestCaseViewHolder(private val binding: ItemTestCaseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(testCase: DetailTestCase){
            with(binding){
                tvDescriptionItemTestCase.text = testCase.testCaseName
                tvStatusScenarioInItemTestCase.text = testCase.scenarioName
                tvStatusTestInItemTestCase.text = testCase.status
                tvStatusTestInItemTestCaseResult.text = testCase.priority
                tvStatusTestInItemTestCaseSeverity.text = testCase.severity

                if (testCase.status.isNullOrEmpty() && testCase.severity.isNullOrEmpty() && testCase.priority.isNullOrEmpty()) {
                    tvStatusTestInItemTestCaseResult.visibility = View.GONE
                    tvStatusTestInItemTestCase.visibility = View.GONE
                    tvStatusTestInItemTestCaseSeverity.visibility = View.GONE
                } else if (testCase.status == "pass") {
                    tvStatusTestInItemTestCase.setBackgroundResource(R.drawable.corner_radius_status_green)
                    ivIndicatorVerticalItemTestCase.setBackgroundResource(R.drawable.indicator_pass)
                    tvStatusTestInItemTestCaseResult.visibility = View.GONE
                    tvStatusTestInItemTestCaseSeverity.visibility = View.GONE
                } else {
                    tvStatusTestInItemTestCaseSeverity.setBackgroundResource(R.drawable.indicator_severity)
                    tvStatusTestInItemTestCaseResult.setBackgroundResource(R.drawable.indicator_priority)
                    ivIndicatorVerticalItemTestCase.setBackgroundResource(R.drawable.indicator_fail)
                    tvStatusTestInItemTestCaseResult.visibility = View.VISIBLE
                    tvStatusTestInItemTestCase.visibility = View.VISIBLE
                    tvStatusTestInItemTestCaseSeverity.visibility = View.VISIBLE
                }

                val role = ListAllVersionFragment.role

                if (role == "dev"){
                    ibNavigateOpsiItemTestCase.visibility = View.GONE
                }else{
                    ibNavigateOpsiItemTestCase.visibility = View.VISIBLE
                }

                ibNavigateOpsiItemTestCase.setOnClickListener {
                    showItemMenu(it, testCase)
                }

                cvItemTestCase.setOnClickListener {
                    listenerTestCase?.onCardVersionClicked(testCase.projectId, testCase.versionId, testCase.testCaseId)
                }

            }
        }

        private fun showItemMenu(view: View, testCase: DetailTestCase) {
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.menuInflater.inflate(R.menu.menu_test_case, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.edit_test_case -> {
                        listenerTestCase?.editTestCase(testCase.projectId, testCase.versionId, testCase.testCaseId)
                        true
                    }
                    R.id.delete_test_case -> {
                        listenerTestCase?.deleteTestCase(testCase.projectId, testCase.versionId, testCase.testCaseId)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestCaseViewHolder {
        val binding = ItemTestCaseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TestCaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TestCaseViewHolder, position: Int) {
        holder.bind(listTestCase[position])
    }

    override fun getItemCount(): Int {
        return listTestCase.size
    }

    companion object{
        var listenerTestCase: TestCaseListListener? = null
    }
}

interface TestCaseListListener {
    fun deleteTestCase(projectId: Int, versionId: Int, testCaseId: Int)
    fun editTestCase(projectId: Int, versionId: Int, testCaseId: Int)
    fun onCardVersionClicked(projectId: Int, versionId: Int, testCaseId: Int)
}