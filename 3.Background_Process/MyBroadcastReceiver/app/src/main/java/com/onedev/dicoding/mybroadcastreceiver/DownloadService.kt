package com.onedev.dicoding.mybroadcastreceiver

import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

class DownloadService: JobIntentService() {

    companion object {
        private const val TAG = "DownloadService"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            enqueueWork(this, this::class.java, 101, intent)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleWork(intent: Intent) {
        Log.d(TAG, "onHandleWork: Service Download Running")
        try {
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        val notifyFinishIntent = Intent(MainActivity.ACTION_DOWNLOAD_STATUS)
        sendBroadcast(notifyFinishIntent)
    }
}