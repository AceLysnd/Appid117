package com.qatros.logibug.ui.splashscreen

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.R
import com.qatros.logibug.core.datastore.PreferenceViewModel

@AndroidEntryPoint
class SplashScreenFragment : Fragment() {

    private val preferenceViewModel: PreferenceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Handler().postDelayed({

//            preferenceViewModel.getLoginState().observe(viewLifecycleOwner){
//                if (it.isLogin){
//                    findNavController().navigate(R.id.action_splashScreenFragment_to_homePageFragment)
//                    Toast.makeText(requireContext(), "You have Logged in", Toast.LENGTH_SHORT).show()
//                }
//            }
            findNavController().navigate(R.id.action_splashScreenFragment_to_viewPagerFragment)
        }, 1000)

        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

}