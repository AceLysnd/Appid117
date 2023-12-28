package com.qatros.logibug.ui.scenario.create_scenario

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.R
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentCreateScenarioBinding

@AndroidEntryPoint
class CreateScenarioFragment : DialogFragment() {

    private var _binding: FragmentCreateScenarioBinding? = null
    private val binding get() = _binding!!

    private val createScenarioViewModel: CreateScenarioViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()

    private val args: CreateScenarioFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateScenarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSaveScenario.isEnabled = false

        var token = ""
        val projectId = args.projectId
        val versionId = args.versionId

        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
            token = it.token
        }

        binding.etScenarioName.addTextChangedListener(object : TextWatcher {
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

        createScenarioViewModel.message.observe(viewLifecycleOwner) {
            if (it == "Successfully added scenario") {
                val action =
                    CreateScenarioFragmentDirections.actionCreateScenarioFragmentToCreateTestCaseFragment(
                        projectId,
                        versionId
                    )
                findNavController().navigate(action)
            }
        }

        binding.ibClosedScenario.setOnClickListener {
            dismiss()
        }

        binding.btnSaveScenario.setOnClickListener {
            val scenarioName = binding.etScenarioName.text.toString()
            createScenarioViewModel.createScenario(
                token,
                scenarioName,
                projectId
            )
        }

        binding.ibClosedScenario.setOnClickListener {
            dismiss()
        }

        binding.tvShowAllScenario.setOnClickListener {
            val action =
                CreateScenarioFragmentDirections.actionCreateScenarioFragmentToListScenarioFragment(
                    projectId,
                    versionId
                )
            findNavController().navigate(action)
        }

    }

    private fun updateStatusCreateButton() {
        val scenarioName = binding.etScenarioName.text.toString()

        binding.btnSaveScenario.isEnabled = scenarioName.isNotEmpty()

        if (binding.btnSaveScenario.isEnabled) {
            val colorButtonPrimary = ContextCompat.getColor(requireActivity(), R.color.Primary)
            val colorTextWhite = ContextCompat.getColor(requireActivity(), R.color.white)

            binding.btnSaveScenario.setBackgroundColor(colorButtonPrimary)
            binding.btnSaveScenario.setTextColor(colorTextWhite)
        } else {
            val colorButtonFalse = ContextCompat.getColor(requireActivity(), R.color.neutral_second)
            val colorTextNeutral = ContextCompat.getColor(requireActivity(), R.color.neutral)

            binding.btnSaveScenario.setBackgroundColor(colorButtonFalse)
            binding.btnSaveScenario.setTextColor(colorTextNeutral)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}