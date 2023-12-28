package com.qatros.logibug.ui.report.report_manual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.R
import com.qatros.logibug.core.data.response.report.ReportResponse
import com.qatros.logibug.core.data.response.report.Version
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentListReportManualBinding

@AndroidEntryPoint
class ListReportManualFragment : Fragment(), ListReportManualAdapter.OnItemClickListener {

    private var _binding: FragmentListReportManualBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListReportManualViewModel by viewModels()
    private lateinit var listReportManualAdapter: ListReportManualAdapter
    private val preferenceViewModel: PreferenceViewModel by viewModels()

    private val args: ListReportManualFragmentArgs by navArgs()

    private lateinit var progressBar: ProgressBar

    private var token = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListReportManualBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val projectId = args.projectId

        progressBar = view.findViewById(R.id.progressBar_report_detail_manual)

        binding.apply {
            ibNavigateBackReportDetailManual.setOnClickListener {
                findNavController().navigate(R.id.action_listReportManualFragment_to_listAllReportFragment)
            }
        }

        setupRecyclerView()
        observeReportData()
        observeMessage()

        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
            token = it.token
            viewModel.loadReportData(it.token, projectId)
        }
    }

    private fun setupRecyclerView() {
        listReportManualAdapter = ListReportManualAdapter(this)
        binding.rvReportDetailManual.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listReportManualAdapter
        }
    }

    private fun observeReportData() {
        viewModel.reportData.observe(viewLifecycleOwner) { reportResponse ->
            reportResponse?.let {
                showReportData(it)
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.rvReportDetailManual.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }

    private fun observeMessage() {
        viewModel.message.observe(viewLifecycleOwner) { message ->
            if (message.isNotEmpty()) {
                val toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }

    private fun showReportData(reportResponse: ReportResponse) {
        val versions = reportResponse.data.versions

        listReportManualAdapter.setData(versions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(version: Version) {

    }
}
