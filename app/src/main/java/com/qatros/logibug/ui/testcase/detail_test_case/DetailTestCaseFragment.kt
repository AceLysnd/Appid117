package com.qatros.logibug.ui.testcase.detail_test_case

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.R
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentDetailTestCaseBinding
import com.qatros.logibug.ui.result.detailresult.DetailTestCaseViewModel
import com.qatros.logibug.ui.testcase.edit_test_case.EditTestCaseViewModel

@AndroidEntryPoint
class DetailTestCaseFragment : Fragment() {
    private var _binding: FragmentDetailTestCaseBinding? = null
    private val binding get() = _binding!!

    private val detailTestCaseViewModel: EditTestCaseViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()
    private val detailResult: DetailTestCaseViewModel by viewModels()
    private var token = ""

    private val args: DetailTestCaseFragmentArgs by navArgs()
    companion object{
        var role = ""
        var status = ""
        var priority = ""
        var severity = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailTestCaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val projectId = args.projectId
        val versionId = args.versionId
        val testCaseId = args.testCaseId
        var resultId = 0

        detailResult.detailTestCaseResult.observe(viewLifecycleOwner) {
            status = it.data.status
        }

        binding.topAppBar.setOnClickListener {
            val action =
                DetailTestCaseFragmentDirections.actionDetailTestCaseFragmentToListTestCaseFragment(
                    projectId,
                    versionId,
                )
            findNavController().navigate(action)
        }
        binding.btnAddResult.setOnClickListener {
            val action =
                DetailTestCaseFragmentDirections.actionDetailTestCaseFragmentToAddResultFragment(
                    projectId,
                    versionId,
                    testCaseId
                )
            findNavController().navigate(action)
        }

        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
            token = it.token

            binding.tvNameProfileUserInDetailTestCaseResult.text = it.name

            detailTestCaseViewModel.getTestCaseById(token, testCaseId)
            detailResult.getDetailTestCase(it.token, testCaseId)


            detailTestCaseViewModel.testCase.observe(viewLifecycleOwner) {
                binding.apply {
                    tvFillTestCaseName.text = it.data.testCaseName
                    tvFillCategory.text = it.data.testCategory
                    tvFillPreCondition.text = it.data.preCondition
                    tvFillExpectation.text = it.data.expectation
                    tvFillTestStep.text = it.data.testStep
                    tvFillScenario.text = it.data.scenarioName
                }
            }

            detailResult.detailTestCaseResult.observe(viewLifecycleOwner) {
                binding.apply {
                    tvDescriptionActuallyInDetailTestCaseResult.text = it.data.actual
                    tvStatusFailInDetailTestCaseResult.text = it.data.status
                    tvStatusMediumInDetailTestCaseResult.text = it.data.priority
                    tvStatusCriticalInDetailTestCaseResult.text = it.data.severity
                    tvDescriptionNoteInDetailTestCaseResult.text = it.data.note
                    Log.d("halo", "${it.data.imgUrl}")
                    resultId = it.data.id
                    Glide.with(requireContext())
                        .load("${it.data.imgUrl.replace("http:","https:")}")
                        .centerCrop()
                        .error(R.drawable.ic_upload)
                        .fitCenter()
                        .into(ivDescriptionAttachmentInDetailTestCaseResult)
                    if (resultId == 0) {
                        binding.emptyState.visibility = View.GONE
                    }else{
                        binding.emptyState.visibility = View.VISIBLE
                        binding.btnAddResult.visibility = View.GONE
                        binding.tvResult.visibility = View.GONE
                        binding.tvFillResult.visibility = View.GONE
                    }
                }
            }



            binding.apply {

                ibEditDetailTestCaseResult.setOnClickListener {
                    val action =
                        DetailTestCaseFragmentDirections.actionDetailTestCaseFragmentToEditResultFragment(
                            projectId,
                            versionId,
                            testCaseId,
                            resultId,
                            true
                        )
                    findNavController().navigate(action)
                }
            }

        }
    }
}