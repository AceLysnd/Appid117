package com.qatros.logibug.ui.scenario.edit_scenario

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentEditScenarioBinding

@AndroidEntryPoint
class EditScenarioFragment : DialogFragment() {

    private var _binding: FragmentEditScenarioBinding? = null
    private val binding get() = _binding!!

    private val editScenarioViewModel: EditScenarioViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()

    private val args: EditScenarioFragmentArgs by navArgs()
    private var token = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditScenarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var projectId = args.projectId
        var versionId = args.versionId
        var scenarioId = args.scenarioId
        var isEdit = args.isEdited

        binding.ibClosedEditScenario.setOnClickListener {
            dismiss()
        }

        Log.d("scenarioId", "scenarioId = $scenarioId")

        if (isEdit) {
            preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
                token = it.token
                editScenarioViewModel.getScenarioById(token, scenarioId)
                editScenarioViewModel.scenario.observe(viewLifecycleOwner){get ->
                    binding.etScenario.setText(get.data.scenarioName.replace("\"", ""))
                }
                editScenarioViewModel.message.observe(viewLifecycleOwner) {
                    Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                    if (it == "Berhasil mengubah scenario") {
                        val action =
                            EditScenarioFragmentDirections.actionEditScenarioFragmentToListScenarioFragment(
                                projectId,
                                versionId
                            )
                        findNavController().navigate(action)
                    }
                }

                binding.btnSaveEditScenario.setOnClickListener {
                    val scenarioName = binding.etScenario.text.toString()

                    if (scenarioName.isEmpty()) {
                        binding.etScenario.error = "Tidak boleh kosong"
                    } else {
                        Log.d("scenarioId", "scenarioId = $scenarioId")
                        editScenarioViewModel.updateScenario(
                            token,
                            scenarioId,
                            scenarioName
                        )
                    }
                }

            }
        }
    }

}