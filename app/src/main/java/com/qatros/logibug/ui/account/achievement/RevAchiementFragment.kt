package com.qatros.logibug.ui.account.achievement

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.qatros.logibug.R
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentRevAchiementBinding
import com.qatros.logibug.ui.report.report_manual.ListReportManualAdapter

class RevAchiementFragment : Fragment() {

    private var _binding: FragmentRevAchiementBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AchievementViewModel by viewModels()
    private val preferenceViewModel: PreferenceViewModel by viewModels()

    private var token = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRevAchiementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val progressBar: ProgressBar = binding.progressLevelTestCaseAchievement
//        var progressAnimator: ObjectAnimator? = null
//
//        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) {
//            token = it.token
//            viewModel.getAchievement(token, it.idUser)
//
//            viewModel.achievementData.observe(viewLifecycleOwner) {
//                binding.apply {
//                    binding.tvNameUserAchievement.text = it.user
//                    binding.tvRankLevelAchievement.text = it.rank.nameRank
//                    binding.tvFinishedAmountTestCaseInAchievement.text = it.testcaseCount.toString()
//                    binding.tvDescriptionFinishedAmountTestCaseInAchievement.text = it.rank.rankDifference.toString()
//
//                    val progress = (it.testcaseCount)
//                    val maxProgress = 200
//
//                    if (progressAnimator != null && progressAnimator!!.isRunning) {
//                        progressAnimator!!.cancel()
//                    }
//
//                    progressAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, progress)
//                        .apply {
//                            progressBar.progress = 0
//                            ObjectAnimator.ofInt(progressBar, "progress", progress)
//                                .apply {
//                                    progressBar.max = maxProgress
//                                    duration =
//                                        ListReportManualAdapter.PROGRESS_BAR_ANIMATION_DURATION
//                                    interpolator =
//                                        DecelerateInterpolator()
//                                    start()
//                                }
//                        }
//                }
//            }
//            binding.apply {
//                binding.ibNavigateBackLevelCriteria.setOnClickListener {
//                    findNavController().popBackStack()
//                }
//
//            }
//        }
    }
}