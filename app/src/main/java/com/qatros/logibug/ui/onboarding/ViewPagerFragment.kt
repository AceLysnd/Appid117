package com.qatros.logibug.ui.onboarding

import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.qatros.logibug.R
import com.qatros.logibug.core.datastore.PreferenceViewModel
import com.qatros.logibug.databinding.FragmentViewPagerBinding
import com.qatros.logibug.ui.onboarding.screen.FirstScreenFragment
import com.qatros.logibug.ui.onboarding.screen.SecondScreenFragment
import com.qatros.logibug.ui.onboarding.screen.ThirdScreenFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!
    private val preferenceViewModel: PreferenceViewModel by viewModels()
    private lateinit var alertDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkInternetConnection()

        preferenceViewModel.getLoginState().observe(viewLifecycleOwner) { loginState ->
            if (loginState.isLogin) {
                if (isConnectedToInternet()) {
                    findNavController().navigate(R.id.action_viewPagerFragment_to_homePageFragment)
                    Toast.makeText(requireContext(), "You have Logged in", Toast.LENGTH_SHORT).show()
                } else {
                    showNoInternetAlertDialog()
                }
            } else if (loginState.isPassOnboarding) {
                findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
            }
        }

        val fragmentList = arrayListOf(
            FirstScreenFragment(),
            SecondScreenFragment(),
            ThirdScreenFragment()
        )

        val adapter = ViewPagerAdapater(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        val viewPager = binding.viewPager
        viewPager.adapter = adapter

        val indicator = binding.dotIndicator
        indicator.attachTo(viewPager)

        binding.apply {
            tvNext.setOnClickListener {
                when (viewPager.currentItem) {
                    0 -> viewPager.currentItem = 1
                    1 -> viewPager.currentItem = 2
                }
            }

            tvSkip.setOnClickListener {
                viewPager.currentItem = 2
            }

            imgBack.setOnClickListener {
                when (viewPager.currentItem) {
                    1 -> viewPager.currentItem = 0
                    2 -> viewPager.currentItem = 1
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkInternetConnection() {
        val connectivityManager =
            requireContext().getSystemService(ConnectivityManager::class.java)
        val networkCapabilities =
            connectivityManager?.activeNetwork ?: return

        val activeNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return

        if (!activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
            showNoInternetAlertDialog()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isConnectedToInternet(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(ConnectivityManager::class.java)
        val networkCapabilities =
            connectivityManager?.activeNetwork ?: return false

        val activeNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun showNoInternetAlertDialog() {
        alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Tidak Ada Koneksi Internet")
            .setMessage("Mohon aktifkan koneksi internet Anda.")
            .setPositiveButton("Aktifkan") { _, _ ->
                openNetworkSettings()
            }
            .setCancelable(false)
            .create()
        alertDialog.show()
    }

    private fun openNetworkSettings() {
        val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        if (::alertDialog.isInitialized && alertDialog.isShowing) {
            alertDialog.dismiss()
        }
    }

}
