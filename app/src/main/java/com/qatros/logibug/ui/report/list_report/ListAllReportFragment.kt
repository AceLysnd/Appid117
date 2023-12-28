package com.qatros.logibug.ui.report.list_report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.qatros.logibug.core.data.response.project.ProjectResponse
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentListAllReportBinding
import com.qatros.logibug.ui.version.list_version.ListAllVersionFragment.Companion.typeTest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListAllReportFragment : Fragment(), ListAllReportAdapter.ProjectListReportListener {

    private var _binding: FragmentListAllReportBinding? = null
    private val binding get() = _binding!!

    private val listAllReportViewModel: ListAllReportViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()
    private lateinit var listAllReportAdapter: ListAllReportAdapter

    private lateinit var progressBar: ProgressBar

    private var token = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListAllReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = binding.progressBarListAllReport

        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) { state ->
            state?.let { loginState ->
                token = loginState.token
                listAllReportViewModel.getReportAllProject(loginState.token, "0")
            }
        }

        listAllReportViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.rvItemListAllReport.visibility = if (isLoading) View.GONE else View.VISIBLE
        }

        listAllReportViewModel.listProject.observe(viewLifecycleOwner) { projectList ->
            projectList?.let { list ->
                setupRecyclerView(list.data)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        listAllReportViewModel.getReportAllProject(token, typeTest = "")
    }

    private fun setupRecyclerView(list: List<ProjectResponse>) {
        listAllReportAdapter = ListAllReportAdapter(list)
        ListAllReportAdapter.listProjectsReportListener = this@ListAllReportFragment
        binding.rvItemListAllReport.layoutManager = LinearLayoutManager(activity)
        binding.rvItemListAllReport.adapter = listAllReportAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCardProjectReportClicked(projectId: Int) {
        val selectedProject = listAllReportViewModel.listProject.value?.data?.find { it.id == projectId }
        if (selectedProject?.typeTest == "automatic") {
            val action =
                ListAllReportFragmentDirections.actionListAllReportFragmentToListReportAutomationFragment(
                    typeTest,
                    projectId
                )
            findNavController().navigate(action)
        } else {
            val action =
                ListAllReportFragmentDirections.actionListAllReportFragmentToListReportManualFragment(
                    projectId
                )
            findNavController().navigate(action)
        }
    }
}
