package com.qatros.logibug.ui.onboarding.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import com.qatros.logibug.R
import com.qatros.logibug.databinding.FragmentSecondScreenBinding

class SecondScreenFragment : Fragment() {

    private var bind : FragmentSecondScreenBinding? = null
    private val binding get() = bind!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentSecondScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<ImageView>(R.id.img_back).visibility = View.VISIBLE
        requireActivity().findViewById<DotsIndicator>(R.id.dot_indicator).visibility = View.VISIBLE
        requireActivity().findViewById<TextView>(R.id.tv_skip).visibility = View.VISIBLE
        requireActivity().findViewById<TextView>(R.id.tv_next).visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind = null
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<ImageView>(R.id.img_back).visibility = View.VISIBLE
        requireActivity().findViewById<DotsIndicator>(R.id.dot_indicator).visibility = View.VISIBLE
        requireActivity().findViewById<TextView>(R.id.tv_skip).visibility = View.VISIBLE
        requireActivity().findViewById<TextView>(R.id.tv_next).visibility = View.VISIBLE
    }
}