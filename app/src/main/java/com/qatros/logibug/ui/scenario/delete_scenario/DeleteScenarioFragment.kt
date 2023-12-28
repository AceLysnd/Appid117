package com.qatros.logibug.ui.scenario.delete_scenario

import android.os.Bundle
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
import com.qatros.logibug.databinding.FragmentDeleteScenarioBinding
import com.qatros.logibug.ui.scenario.list_scenario.ListScenarioViewModel

@AndroidEntryPoint
class DeleteScenarioFragment : DialogFragment() {

    private var _binding: FragmentDeleteScenarioBinding? = null
    private val binding get() = _binding!!

    private var token = ""

    private val deleteViewModel: ListScenarioViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()

    private val args: DeleteScenarioFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeleteScenarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val projectId = args.projectId
        val versionId = args.versionId
        val scenarioId = args.scenarioId
        val scenarioName = args.scenarioName

        binding.tvDescriptionDeleteScenario.text = "Are you sure to delete $scenarioName"

        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
            token = it.token
        }

        binding.apply {
            btnDeleteAlertDialogDeleteScenario.setOnClickListener {
                deleteViewModel.deleteScenario(
                    token, scenarioId
                )
                deleteViewModel.message.observe(viewLifecycleOwner) {
                    Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                    if (it == "Berhasil mengahapus scenario") {
                        val action =
                            DeleteScenarioFragmentDirections.actionDeleteScenarioFragmentToListScenarioFragment(
                                projectId, versionId
                            )
                        findNavController().navigate(action)
                    } else {
                        dismiss()
                    }
                }
            }

            btnCancelAlertDialogDeleteScenario.setOnClickListener {
                dismiss()
            }

        }

    }

}