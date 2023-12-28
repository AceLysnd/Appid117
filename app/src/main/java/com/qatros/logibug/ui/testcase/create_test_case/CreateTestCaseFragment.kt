package com.qatros.logibug.ui.testcase.create_test_case

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.R
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentCreateTestCaseBinding

@AndroidEntryPoint
class CreateTestCaseFragment : Fragment() {

    private var _binding: FragmentCreateTestCaseBinding? = null
    private val binding get() = _binding!!

    private val createTestCaseViewModel: CreateTestCaseViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()

    private var scenarioId = 0
    private var scenarios = ""
    private var category = "Pilih Category"
    private var token = ""

    private var listCategory = mutableListOf<String>()
    private var listScenario = mutableListOf<String>()
    private var listScenarioId = mutableListOf<Int>()

    private val args: CreateTestCaseFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCreateTestCaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCreateTestCase.isEnabled = false

        val projectId = args.projectId
        val versionId = args.versionId

        binding.etTestcaseCreateTestCase.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Before
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                updateStatusCreateButton()
            }

            override fun afterTextChanged(p0: Editable?) {
                //After
            }

        })
        binding.etExpectationCreateTestCase.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Before
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                updateStatusCreateButton()
            }

            override fun afterTextChanged(p0: Editable?) {
                //After
            }

        })
        binding.etPreconditionCreateTestCase.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Before
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                updateStatusCreateButton()
            }

            override fun afterTextChanged(p0: Editable?) {
                //After
            }

        })
        binding.etTeststepsCreateTestCase.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Before
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                updateStatusCreateButton()
            }

            override fun afterTextChanged(p0: Editable?) {
                //After
            }

        })

        binding.ivCreateScenario.setOnClickListener {
            val action =
                CreateTestCaseFragmentDirections.actionCreateTestCaseFragmentToCreateScenarioFragment(
                    projectId,
                    versionId
                )
            findNavController().navigate(action)
        }

        binding.ibNavigateBackCreateTestCase.setOnClickListener {
            val action =
                CreateTestCaseFragmentDirections.actionCreateTestCaseFragmentToListTestCaseFragment(
                    projectId,
                    versionId
                )
            findNavController().navigate(action)
        }

        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
            token = it.token
            getDropDownScenario(token, projectId)
        }

        listCategory = mutableListOf("Category", "positif", "negatif")
        val typeTestDropDown = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            listCategory
        )
        binding.actTestCategoryCreateTestCase.setAdapter(typeTestDropDown)
        binding.actTestCategoryCreateTestCase.setText(listCategory[0], false)
        binding.actTestCategoryCreateTestCase.setOnItemClickListener { _, _, position, _ ->
            category = listCategory[position]
        }

        createTestCaseViewModel.scenario.observe(viewLifecycleOwner) {
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
            binding.actScenarioCreateTestCase.setAdapter(adapter)
            binding.actScenarioCreateTestCase.setOnItemClickListener { _, _, position, _ ->
                scenarioId = listScenarioId[position]
                scenarios = listScenario[position]
            }
        }

        createTestCaseViewModel.message.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            if (it == "Berhasil Menambahkan Test Case") {
                val action =
                    CreateTestCaseFragmentDirections.actionCreateTestCaseFragmentToListTestCaseFragment(
                        projectId,
                        versionId
                    )
                findNavController().navigate(action)
            }
        }

        binding.btnCreateTestCase.setOnClickListener {
            val nameTestCase = binding.etTestcaseCreateTestCase.text.toString()
            val scenarioId = scenarioId
            val versionId = versionId
            val preCondition = binding.etPreconditionCreateTestCase.text.toString()
            val testStep = binding.etTeststepsCreateTestCase.text.toString()
            val expectation = binding.etExpectationCreateTestCase.text.toString()
            val category = category

            if (scenarioId == null) {
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
                createTestCaseViewModel.createTestCase(
                    token,
                    category,
                    scenarioId,
                    versionId,
                    preCondition,
                    nameTestCase,
                    testStep,
                    expectation
                )
            }
        }

    }

    private fun getDropDownScenario(token: String, projectId: Int) {
        createTestCaseViewModel.getScenarioDropdown(token, projectId)
    }

    private fun updateStatusCreateButton() {
        val testCaseName = binding.etTestcaseCreateTestCase.text.toString()
        val preCondition = binding.etPreconditionCreateTestCase.text.toString()
        val testStep = binding.etTeststepsCreateTestCase.text.toString()
        val expectation = binding.etExpectationCreateTestCase.text.toString()

        binding.btnCreateTestCase.isEnabled =
            testCaseName.isNotEmpty() && preCondition.isNotEmpty() && testStep.isNotEmpty() && expectation.isNotEmpty()

        if (binding.btnCreateTestCase.isEnabled) {
            val colorButtonPrimary = ContextCompat.getColor(requireActivity(), R.color.Primary)
            val colorTextWhite = ContextCompat.getColor(requireActivity(), R.color.white)

            binding.btnCreateTestCase.setBackgroundColor(colorButtonPrimary)
            binding.btnCreateTestCase.setTextColor(colorTextWhite)
        } else {
            val colorButtonFalse = ContextCompat.getColor(requireActivity(), R.color.neutral_second)
            val colorTextNeutral = ContextCompat.getColor(requireActivity(), R.color.neutral)

            binding.btnCreateTestCase.setBackgroundColor(colorButtonFalse)
            binding.btnCreateTestCase.setTextColor(colorTextNeutral)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}