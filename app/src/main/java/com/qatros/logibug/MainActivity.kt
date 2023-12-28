package com.qatros.logibug

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import com.qatros.logibug.core.data.utils.ConnectionLiveData
import com.qatros.logibug.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var connectionLiveData: ConnectionLiveData
    private lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this, Observer { isNetworkAvailable ->
            isNetworkAvailable?.let {
                if (it) {
                    dismissAlertDialog()
                } else {
                    showNoInternetAlertDialog()
                }
                updateUI(it)
            }
        })

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment

        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.splashScreenFragment ||
                destination.id == R.id.viewPagerFragment ||
                destination.id == R.id.loginFragment ||
                destination.id == R.id.registerFragment ||
                destination.id == R.id.listAllVersionFragment ||
                destination.id == R.id.createTestingVersionFragment ||
                destination.id == R.id.createProjectFragment ||
                destination.id == R.id.createTestCaseFragment ||
                destination.id == R.id.listTestCaseFragment ||
                destination.id == R.id.listScenarioFragment ||
                destination.id == R.id.editProjectFragment ||
                destination.id == R.id.editTestCaseFragment ||
                destination.id == R.id.addResultFragment ||
                destination.id == R.id.detailTestCaseFragment ||
                destination.id == R.id.detailTestCaseResultFragment ||
                destination.id == R.id.editResultFragment ||
                destination.id == R.id.listReportManualFragment ||
                destination.id == R.id.listFilterTestCaseFragment ||
                destination.id == R.id.accountSettingFragment ||
                destination.id == R.id.aboutFragment ||
                destination.id == R.id.listMemberFragment ||
                destination.id == R.id.rateUseFragment ||
                destination.id == R.id.listFilterTestCaseFragment ||
                destination.id == R.id.uploadFileApiTestingFragment ||
                destination.id == R.id.pencapaianFragment ||
                destination.id == R.id.achievementFragment
            ) {
                binding.bottomNavigationView.visibility = View.GONE
            } else {
                binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)
    }

    private fun updateUI(isConnected: Boolean) {
        if (isConnected) {
        } else {
        }
    }

    private fun showNoInternetAlertDialog() {
        alertDialog = AlertDialog.Builder(this)
            .setTitle("Tidak Ada Koneksi Internet")
            .setMessage("Mohon aktifkan koneksi internet Anda.")
            .setPositiveButton("Aktifkan") { _, _ ->
                openNetworkSettings()
            }
            .setCancelable(false)
            .create()
        alertDialog.show()
    }

    private fun dismissAlertDialog() {
        if (::alertDialog.isInitialized && alertDialog.isShowing) {
            alertDialog.dismiss()
        }
    }

    private fun openNetworkSettings() {
        val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
        startActivity(intent)
    }

    override fun onBackPressed() {
        if (::alertDialog.isInitialized && alertDialog.isShowing) {
            return
        }
        super.onBackPressed()
    }
}