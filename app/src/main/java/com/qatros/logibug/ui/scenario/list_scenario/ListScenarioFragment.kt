package com.qatros.logibug.ui.scenario.list_scenario

import android.os.Bundle
import android.util.Log
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
import com.qatros.logibug.core.data.response.scenario.ScenarioDetail
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentListScenarioBinding

@AndroidEntryPoint
class ListScenarioFragment : Fragment(), ScenarioListListener {

    private var _binding: FragmentListScenarioBinding? = null
    private val binding get() = _binding!!

    private val listScenarioViewModel: ListScenarioViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()
    private lateinit var listScenarioAdapter: ListScenarioAdapter

    private val args: ListScenarioFragmentArgs by navArgs()

    var token = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListScenarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val projectId = args.projectId
        val versionId = args.versionId

        binding.ibNavigateBackScenario.setOnClickListener {
            val action =
                ListScenarioFragmentDirections.actionListScenarioFragmentToListTestCaseFragment(
                    projectId,
                    versionId
                )
            findNavController().navigate(action)
        }

        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
            token = it.token
            listScenarioViewModel.getAllScenario(it.token, projectId)
        }

        listScenarioViewModel.listScenario.observe(viewLifecycleOwner) {
            setupRecyclerView(it.data)
        }
        loadingState()

    }

    private fun setupRecyclerView(list: List<ScenarioDetail>) {
        listScenarioAdapter = ListScenarioAdapter(list)
        ListScenarioAdapter.listenerScenario = this@ListScenarioFragment
        binding.rvItemScenario.layoutManager = LinearLayoutManager(activity)
        binding.rvItemScenario.adapter = listScenarioAdapter
    }

    override fun deleteScenario(projectId: Int, versionId: Int, scenarioId: Int, scenarioName: String) {
        val versionId = args.versionId
        val action =
            ListScenarioFragmentDirections.actionListScenarioFragmentToDeleteScenarioFragment(
                projectId,
                versionId,
                scenarioId,
                scenarioName
            )
        findNavController().navigate(action)
    }

    override fun editScenario(projectId: Int, versionId: Int, scenarioId: Int) {
        val versionId = args.versionId
        Log.d("scenarioId", "editScenario: $scenarioId")
        val action =
            ListScenarioFragmentDirections.actionListScenarioFragmentToEditScenarioFragment(
                projectId,
                versionId,
                scenarioId,
                true
            )
        findNavController().navigate(action)
    }

    private fun loadingState() {
        listScenarioViewModel.loading.observe(viewLifecycleOwner) {
            binding.rvItemScenario.isVisible = !it
            binding.progressBar.isVisible = it
        }
    }

}