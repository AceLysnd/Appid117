package com.qatros.logibug.ui.ai

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
import com.qatros.logibug.R
import com.qatros.logibug.core.data.request.ai.AICommandRequest
import com.qatros.logibug.core.data.response.ai.AICommandResponse
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentAIBinding
import com.qatros.logibug.ui.scenario.create_scenario.CreateScenarioViewModel
import com.qatros.logibug.ui.testcase.create_test_case.CreateTestCaseFragmentArgs
import com.qatros.logibug.ui.testcase.create_test_case.CreateTestCaseFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AIFragment : DialogFragment() {
    private var _binding: FragmentAIBinding? = null
    private val binding get() = _binding!!

    private val createScenarioViewModel: CreateScenarioViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()

    private val args: CreateTestCaseFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAIBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGenerateCommand.isEnabled = false

        var token = ""

        val projectId = args.projectId
        val versionId = args.versionId

        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
            token = it.token
        }

        binding.etCommand.addTextChangedListener(object : TextWatcher {
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

        createScenarioViewModel.message.observe(viewLifecycleOwner) { message ->
            if (message == "Successfully generating results") {
                createScenarioViewModel.aiResponse.observe(viewLifecycleOwner) {
                    aiCommandResponse = it
                    fromGenerate = true
                    val action =
                        AIFragmentDirections.actionAIFragmentToCreateTestCaseFragment2(
                            projectId, versionId
                        )
                    findNavController().navigate(action)
                }
            }
        }

        binding.ibCloseGenerate.setOnClickListener {
            dismiss()
        }

        binding.btnGenerateCommand.setOnClickListener {
            val aiCommand = AICommandRequest (
                data = binding.etCommand.text.toString()
            )
            createScenarioViewModel.generateAi(aiCommand)
        }

        binding.ibCloseGenerate.setOnClickListener {
            dismiss()
        }

    }

    private fun updateStatusCreateButton() {
        val command = binding.etCommand.text.toString()

        binding.btnGenerateCommand.isEnabled = command.isNotEmpty()

        if (binding.btnGenerateCommand.isEnabled) {
            val colorButtonPrimary = ContextCompat.getColor(requireActivity(), R.color.Primary)
            val colorTextWhite = ContextCompat.getColor(requireActivity(), R.color.white)

            binding.btnGenerateCommand.setBackgroundColor(colorButtonPrimary)
            binding.btnGenerateCommand.setTextColor(colorTextWhite)
        } else {
            val colorButtonFalse = ContextCompat.getColor(requireActivity(), R.color.neutral_second)
            val colorTextNeutral = ContextCompat.getColor(requireActivity(), R.color.neutral)

            binding.btnGenerateCommand.setBackgroundColor(colorButtonFalse)
            binding.btnGenerateCommand.setTextColor(colorTextNeutral)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        var aiCommandResponse : AICommandResponse? = null
        var fromGenerate = false
    }

}