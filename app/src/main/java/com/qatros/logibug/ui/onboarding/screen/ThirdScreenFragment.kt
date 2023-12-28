package com.qatros.logibug.ui.onboarding.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.R
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentThirdScreenBinding

@AndroidEntryPoint
class ThirdScreenFragment : Fragment() {

    private var _binding : FragmentThirdScreenBinding? = null
    private val binding get() = _binding!!

    private val preferenceViewModel: PreferenceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<ImageView>(R.id.img_back).visibility = View.VISIBLE
        requireActivity().findViewById<DotsIndicator>(R.id.dot_indicator).visibility = View.GONE
        requireActivity().findViewById<TextView>(R.id.tv_skip).visibility = View.INVISIBLE
        requireActivity().findViewById<TextView>(R.id.tv_next).visibility = View.GONE

        binding.btnStart.setOnClickListener {
            preferenceViewModel.saveOnboardingState()
            it.findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<ImageView>(R.id.img_back).visibility = View.VISIBLE
        requireActivity().findViewById<DotsIndicator>(R.id.dot_indicator).visibility = View.GONE
        requireActivity().findViewById<TextView>(R.id.tv_skip).visibility = View.INVISIBLE
        requireActivity().findViewById<TextView>(R.id.tv_next).visibility = View.GONE
    }
}