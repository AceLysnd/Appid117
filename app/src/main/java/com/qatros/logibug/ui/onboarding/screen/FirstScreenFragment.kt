package com.qatros.logibug.ui.onboarding.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.qatros.logibug.R
import com.qatros.logibug.databinding.FragmentFirstScreenBinding

class FirstScreenFragment : Fragment() {

    private var _binding : FragmentFirstScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<ImageView>(R.id.img_back).visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        if (requireActivity().findViewById<ImageView>(R.id.img_back) == null) {
            Log.e("FirstScreenFragment", "onResume: Failed")
        } else {
            Log.e("FirstScreenFragment", "onResume: Sukses")
            requireActivity().findViewById<ImageView>(R.id.img_back).visibility = View.GONE
        }
    }
}