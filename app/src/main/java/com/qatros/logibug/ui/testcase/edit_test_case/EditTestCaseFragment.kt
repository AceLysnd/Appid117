package com.qatros.logibug.ui.testcase.edit_test_case

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentEditTestCaseBinding

@AndroidEntryPoint
class EditTestCaseFragment : Fragment() {

    private var _binding: FragmentEditTestCaseBinding? = null
    private val binding get() = _binding!!

    private val editTestCaseViewModel: EditTestCaseViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()

    private val args: EditTestCaseFragmentArgs by navArgs()
    private var token = ""

    private var scenarioId = 0
    private var scenarios = ""
    private var category = "Pilih Category"

    private var listCategory = mutableListOf<String>()
    private var listScenario = mutableListOf<String>()
    private var listScenarioId = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEditTestCaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var projectId = args.projectId
        var versionId = args.versionId
        var testCaseId = args.testCaseId
        var isEdit = args.isEdited

        binding.ibNavigateBackEditTestCase.setOnClickListener {
            findNavController().popBackStack()
        }

        if (isEdit){
            preferenceViewModel.getLoginState().observe(viewLifecycleOwner){

                token = it.token

                editTestCaseViewModel.getTestCaseById(token, testCaseId)

                getDropDownScenario(token, projectId)

                editTestCaseViewModel.testCase.observe(viewLifecycleOwner){get ->
                    binding.etTestcaseEditTestCase.setText(get.data.testCaseName.replace("\"", ""))
                    binding.etPreconditionEditTestCase.setText(get.data.preCondition.replace("\"", ""))
                    binding.etTeststepsEditTestCase.setText(get.data.testStep.replace("\"", ""))
                    binding.etExpectationEditTestCase.setText(get.data.expectation.replace("\"", ""))
                }

                editTestCaseViewModel.message.observe(viewLifecycleOwner){
                    Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                    if (it == "Berhasil mengubah test case") {
                        val action = EditTestCaseFragmentDirections.actionEditTestCaseFragmentToListTestCaseFragment(projectId, versionId)
                        findNavController().navigate(action)
                    }
                }

                listCategory = mutableListOf("Category", "positif", "negatif")
                val typeTestDropDown = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    listCategory
                )
                binding.actTestCategoryEditTestCase.setAdapter(typeTestDropDown)
                binding.actTestCategoryEditTestCase.setText(listCategory[0], false)
                binding.actTestCategoryEditTestCase.setOnItemClickListener { _, _, position, _ ->
                    category = listCategory[position]
                }

                editTestCaseViewModel.scenario.observe(viewLifecycleOwner){
                    for (i in it.data){
                        if (!listScenarioId.contains(i.scenarioId)) {
                            if (!i.name.isNullOrEmpty()) {
                                listScenario.add(i.name)
                                listScenarioId.add(i.scenarioId)
                            }
                        }
                    }
                    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, listScenario)
                    binding.actScenarioEditTestCase.setAdapter(adapter)
                    binding.actScenarioEditTestCase.setOnItemClickListener{
                            _, _, position, _ ->
                        scenarioId = listScenarioId[position]
                        scenarios = listScenario[position]
                    }
                }

                binding.btnEditTestCase.setOnClickListener {
                    val nameTestCase = binding.etTestcaseEditTestCase.text.toString()
                    val scenarioId = scenarioId
                    val preCondition = binding.etPreconditionEditTestCase.text.toString()
                    val testStep = binding.etTeststepsEditTestCase.text.toString()
                    val expectation = binding.etExpectationEditTestCase.text.toString()
                    val category = category

                    if (nameTestCase.isEmpty()) {
                        binding.etTestcaseEditTestCase.error = "Harus diisi"
                    } else if (scenarioId == null) {
                        Toast.makeText(
                            requireContext(),
                            "Pilih scenario",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (category == "Category") {
                        Toast.makeText(
                            requireContext(),
                            "Pilih category",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        editTestCaseViewModel.updateTestCase(
                            token,
                            testCaseId,
                            category,
                            preCondition,
                            nameTestCase,
                            testStep,
                            expectation
                        )
                    }
                }

            }
        }

    }

    private fun getDropDownScenario(token: String, projectId: Int){
        editTestCaseViewModel.getScenarioDropdown(token, projectId)
    }

}