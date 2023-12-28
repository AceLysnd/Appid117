package com.qatros.logibug.ui.testcase.filter_test_case

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentFilterTestCaseBinding

@AndroidEntryPoint
class FilterTestCaseFragment : DialogFragment() {

    private var _binding: FragmentFilterTestCaseBinding? = null
    private val binding get() = _binding!!

    private val filterTestCaseViewModel: FilterTestCaseViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()

    private val args: FilterTestCaseFragmentArgs by navArgs()

    private var scenarioId = 0
    private var scenarios = ""

    private var token = ""

    private var listScenario = mutableListOf<String>()
    private var listScenarioId = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFilterTestCaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var projectId = args.projectId
        var versionId = args.versionId

        binding.ibClosedFilterTestCase.setOnClickListener {
            dismiss()
        }

        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {

            token = it.token

            getDropDownScenario(token, projectId)

            filterTestCaseViewModel.scenario.observe(viewLifecycleOwner) {
                for (i in it.data) {
                    if (!listScenarioId.contains(i.scenarioId)) {
                        if (!i.name.isNullOrEmpty()) {
                            listScenario.add(i.name)
                            listScenarioId.add(i.scenarioId)
                        }
                    }
                }
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    listScenario
                )
                binding.actScenario.setAdapter(adapter)
                binding.actScenario.setOnItemClickListener { _, _, position, _ ->
                    scenarioId = listScenarioId[position]
                    scenarios = listScenario[position]
                }
            }

            binding.btnFindTestCaseByScenario.setOnClickListener {
                val action =
                    FilterTestCaseFragmentDirections.actionFilterTestCaseFragmentToListFilterTestCaseFragment(
                        projectId,
                        versionId,
                        scenarioId
                    )
                findNavController().navigate(action)
            }

        }

    }

    private fun getDropDownScenario(token: String, projectId: Int) {
        filterTestCaseViewModel.getScenarioDropdown(token, projectId)
    }

}