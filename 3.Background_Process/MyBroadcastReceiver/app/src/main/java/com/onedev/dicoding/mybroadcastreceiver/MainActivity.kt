package com.onedev.dicoding.mybroadcastreceiver

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.onedev.dicoding.mybroadcastreceiver.databinding.ActivityMainBinding
import kotlin.math.log

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val ACTION_DOWNLOAD_STATUS = "action_download_status"
        private const val TAG = "MainActivity"
        private const val SMS_REQUEST_CODE = 101
    }

    private lateinit var downloadReceiver: BroadcastReceiver
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        downloadReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.d(TAG, "onReceive: Download Completed")
                Toast.makeText(context, "Download Completed", Toast.LENGTH_SHORT).show()
            }
        }
        val downloadIntentFilter = IntentFilter(ACTION_DOWNLOAD_STATUS)
        registerReceiver(downloadReceiver, downloadIntentFilter)

        binding?.btnPermission?.setOnClickListener(this)
        binding?.btnDownload?.setOnClickListener(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SMS_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Sms Receiver Permission Granted", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "Sms Receiver Permission Rejected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding?.btnPermission -> {
                PermissionManager.check(this, Manifest.permission.RECEIVE_SMS, SMS_REQUEST_CODE)
            }
            binding?.btnDownload -> {
                val downloadServiceIntent = Intent(this, DownloadService::class.java)
                startService(downloadServiceIntent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(downloadReceiver)
        binding = null
    }
}