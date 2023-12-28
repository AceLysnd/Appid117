package com.qatros.logibug.ui.testcase.filter_test_case

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.core.data.response.test_case.DetailFilterTestCase
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentListFilterTestCaseBinding

@AndroidEntryPoint
class ListFilterTestCaseFragment : Fragment(), FilterTestCaseListListener {

    private var _binding: FragmentListFilterTestCaseBinding? = null
    private val binding get() = _binding!!

    private val filterTestCaseViewModel: FilterTestCaseViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()
    private lateinit var filterTestCaseAdapter: FilterTestCaseAdapter

    private val args: ListFilterTestCaseFragmentArgs by navArgs()
    private var token = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListFilterTestCaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val projectId = args.projectId
        val versionId = args.versionId
        val scenarioId = args.scenarioId

        binding.ibNavigateBackFilterTestCase.setOnClickListener {
            val action =
                ListFilterTestCaseFragmentDirections.actionListFilterTestCaseFragmentToListTestCaseFragment(
                    projectId,
                    versionId
                )
            findNavController().navigate(action)
        }

        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
            token = it.token
            filterTestCaseViewModel.getListFilterTestCase(token, versionId, scenarioId)
        }

        filterTestCaseViewModel.listFilterTestCase.observe(viewLifecycleOwner) {
            setupRecyclerView(it.data)
            val countTestCase = it.count.toString()
            binding.tvCountTestCase.text = "$countTestCase Test Case"
        }
        loadingState()

    }

    private fun setupRecyclerView(list: List<DetailFilterTestCase>) {
        filterTestCaseAdapter = FilterTestCaseAdapter(list)
        FilterTestCaseAdapter.listenerFilterTestCase = this@ListFilterTestCaseFragment
        binding.rvFilterTestCase.layoutManager = LinearLayoutManager(activity)
        binding.rvFilterTestCase.adapter = filterTestCaseAdapter

    }

    override fun deleteTestCase(projectId: Int, versionId: Int, testCaseId: Int) {
        val action =
            ListFilterTestCaseFragmentDirections.actionListFilterTestCaseFragmentToDialogDeleteTestCaseFragment(
                projectId,
                versionId,
                testCaseId
            )
        findNavController().navigate(action)
    }

    override fun editTestCase(projectId: Int, versionId: Int, testCaseId: Int) {
        val action =
            ListFilterTestCaseFragmentDirections.actionListFilterTestCaseFragmentToEditTestCaseFragment(
                projectId,
                versionId,
                testCaseId,
                true
            )
        findNavController().navigate(action)
    }

    override fun onCardVersionClicked(projectId: Int, versionId: Int, testCaseId: Int) {
        val action =
            ListFilterTestCaseFragmentDirections.actionListFilterTestCaseFragmentToDetailTestCaseFragment(
                projectId,
                versionId,
                testCaseId,
                true
            )
        findNavController().navigate(action)
    }

    private fun loadingState() {
        filterTestCaseViewModel.loading.observe(viewLifecycleOwner) {
            binding.rvFilterTestCase.isVisible = !it
            binding.progressBar.isVisible = it
        }
    }

}