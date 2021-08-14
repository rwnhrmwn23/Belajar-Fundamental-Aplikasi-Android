package com.onedev.dicoding.myservice

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

class MyJobIntentService : JobIntentService() {

    companion object {
        private const val JOB_ID = 1000
        private const val TAG = "MyJobIntentService"
        internal const val EXTRA_DURATION = "extra_duration"

        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, MyJobIntentService::class.java, JOB_ID, intent)
        }
    }

    override fun onHandleWork(intent: Intent) {
        Log.d(TAG, "onHandleWork: Mulai...")
        val duration = intent.getLongExtra(EXTRA_DURATION, 0)
        try {
            Thread.sleep(duration)
            Log.d(TAG, "onHandleWork: Selesai...")
        } catch (e: InterruptedException) {
            e.printStackTrace()
            Thread.currentThread().interrupt()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy()")
    }

}