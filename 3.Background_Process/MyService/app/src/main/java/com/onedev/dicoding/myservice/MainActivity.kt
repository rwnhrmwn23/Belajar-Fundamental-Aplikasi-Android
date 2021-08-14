package com.onedev.dicoding.myservice

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import com.onedev.dicoding.myservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var mServiceBound = false
    private lateinit var mBoundService: MyBoundService
    private lateinit var binding: ActivityMainBinding

    private val mServiceConnection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val myBinder = service as MyBoundService.MyBinder
            mBoundService = myBinder.getService
            mServiceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mServiceBound = false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStartService.setOnClickListener(this)
        binding.btnStartJobIntentService.setOnClickListener(this)
        binding.btnStartBoundService.setOnClickListener(this)
        binding.btnStopBoundService.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnStartService -> {
                val mStartServiceIntent = Intent(this, MyService::class.java)
                startService(mStartServiceIntent)
            }
            binding.btnStartJobIntentService -> {
                val mStartIntentService = Intent(this, MyJobIntentService::class.java)
                mStartIntentService.putExtra(MyJobIntentService.EXTRA_DURATION, 5000L)
                MyJobIntentService.enqueueWork(this, mStartIntentService)
            }
            binding.btnStartBoundService -> {
                val mBoundServiceIntent = Intent(this, MyBoundService::class.java)
                bindService(mBoundServiceIntent, mServiceConnection, BIND_AUTO_CREATE)
            }
            binding.btnStopBoundService -> {
                unbindService(mServiceConnection)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mServiceBound) {
            unbindService(mServiceConnection)
        }
    }
}