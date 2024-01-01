package com.qatros.logibug.ui.account

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.qatros.logibug.databinding.FragmentHelpBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HelpFragment : Fragment() {

    private var _binding: FragmentHelpBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHelpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ibNavigateBackLevelCriteria.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.tvDownload.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(driveLink))
            startActivity(browserIntent)
        }
    }

    companion object {
        const val driveLink = "https://drive.google.com/file/d/1IRolVA3j6hMSEkeCT5rKahf8teT1wf0V/view "
    }

}