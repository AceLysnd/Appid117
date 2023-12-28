package com.qatros.logibug.ui.report.report_automation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.qatros.logibug.core.data.response.report.*
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentListReportAutomationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListReportAutomationFragment : Fragment() {

    private var _binding: FragmentListReportAutomationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListReportAutomationViewModel by viewModels()
    private lateinit var listReportAutomationAdapter: ListReportAutomationAdapter
    private lateinit var rvAmountTestingApi: RvAmountTestingApi
    private val preferenceViewModel: PreferenceViewModel by viewModels()

    private val args: ListReportAutomationFragmentArgs by navArgs()

    private lateinit var progressBar: ProgressBar

    private var token = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListReportAutomationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = binding.progressBarReportDetailAutomation

        val projectId = args.projectId

        binding.apply {
            ibNavigateBackReportDetailAutomation.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        setupRecyclerView()
        observeReportDataAutomation()
//        observeMessage()

        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
            token = it.token
            viewModel.loadReportData(it.token, projectId)
        }
    }

    private fun setupRecyclerView() {
        listReportAutomationAdapter = ListReportAutomationAdapter()

        binding.rvReportDetailAutomation.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listReportAutomationAdapter
        }
    }


    private fun observeReportDataAutomation() {
        viewModel.reportData.observe(viewLifecycleOwner) { reportAutomationResponse ->
            reportAutomationResponse?.let {
                showReportDataAutomation(it)
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.rvReportDetailAutomation.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }

//    private fun observeMessage() {
//        viewModel.message.observe(viewLifecycleOwner) { message ->
//            if (message.isNotEmpty()) {
//                val toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
//                toast.show()
//            }
//        }
//    }

    private fun showReportDataAutomation(reportAutomationResponse: ReportAutomationResponse) {
        val reportRequest = reportAutomationResponse.dataAutomation.versionAutomation
        listReportAutomationAdapter.setData(reportRequest)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}