package com.qatros.logibug.ui.testcase.list_test_case

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
import com.qatros.logibug.core.data.response.test_case.DetailTestCase
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentListTestCaseBinding
import com.qatros.logibug.ui.version.list_version.ListAllVersionFragment

@AndroidEntryPoint
class ListTestCaseFragment : Fragment(), TestCaseListListener {

    private var _binding: FragmentListTestCaseBinding? = null
    private val binding get() = _binding!!

    private val listTestCaseViewModel: ListTestCaseViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()
    private lateinit var listTestCaseAdapter: ListTestCaseAdapter

    private val args: ListTestCaseFragmentArgs by navArgs()
    private var token = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListTestCaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val projectId = args.projectId
        val versionId = args.versionId
        val typeTest = ListAllVersionFragment.typeTest

        binding.tvListScenario.setOnClickListener {
            val action = ListTestCaseFragmentDirections.actionListTestCaseFragmentToListScenarioFragment(projectId, versionId)
            findNavController().navigate(action)
        }

        binding.ibFilterMyTestingVersion.setOnClickListener {
            val action = ListTestCaseFragmentDirections.actionListTestCaseFragmentToFilterTestCaseFragment(projectId, versionId)
            findNavController().navigate(action)
        }

        binding.fabAddTestCase.setOnClickListener {
            val action =
                ListTestCaseFragmentDirections.actionListTestCaseFragmentToCreateTestCaseFragment(
                    projectId,
                    versionId
                )
            findNavController().navigate(action)
        }

        binding.ibNavigateBackMyTestingVersion.setOnClickListener {
            val action =
                ListTestCaseFragmentDirections.actionListTestCaseFragmentToListAllVersionFragment(
                    typeTest, projectId
                )
            findNavController().navigate(action)
        }

        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
            token = it.token
            listTestCaseViewModel.getAllTestCase(it.token, versionId)
        }

        listTestCaseViewModel.listTestCase.observe(viewLifecycleOwner) {
            setupRecyclerView(it.data)
        }
        loadingState()

    }

    private fun setupRecyclerView(list: List<DetailTestCase>) {
        listTestCaseAdapter = ListTestCaseAdapter(list)
        ListTestCaseAdapter.listenerTestCase = this@ListTestCaseFragment
        binding.rvTestCase.layoutManager = LinearLayoutManager(activity)
        binding.rvTestCase.adapter = listTestCaseAdapter
    }

    override fun deleteTestCase(projectId: Int, versionId: Int, testCaseId: Int) {
        val action =
            ListTestCaseFragmentDirections.actionListTestCaseFragmentToDialogDeleteTestCaseFragment(
                projectId,
                versionId,
                testCaseId
            )
        findNavController().navigate(action)
    }

    override fun editTestCase(projectId: Int, versioId: Int, testCaseId: Int) {
        val action =
            ListTestCaseFragmentDirections.actionListTestCaseFragmentToEditTestCaseFragment(
                projectId,
                versioId,
                testCaseId,
                true
            )
        findNavController().navigate(action)
    }

    override fun onCardVersionClicked(projectId: Int, versionId: Int, testCaseId: Int) {
        val action = ListTestCaseFragmentDirections.actionListTestCaseFragmentToDetailTestCaseFragment(
            projectId,
            versionId,
            testCaseId,
            true
        )
        findNavController().navigate(action)
    }

    private fun loadingState(){
        listTestCaseViewModel.loading.observe(viewLifecycleOwner){
            binding.rvTestCase.isVisible = !it
            binding.progressBar.isVisible = it
        }
    }

}