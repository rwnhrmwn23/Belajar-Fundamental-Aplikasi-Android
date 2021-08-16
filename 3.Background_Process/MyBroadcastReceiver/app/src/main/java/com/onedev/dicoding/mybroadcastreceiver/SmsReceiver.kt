package com.onedev.dicoding.mybroadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import java.lang.Exception
import kotlin.math.log

class SmsReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "SmsReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.extras
        try {
            if (bundle != null) {
                val pdusObject = bundle.get("pdus") as Array<*>
                for (aPdusObject in pdusObject) {
                    val currentMessage = getIncomingMessage(aPdusObject as Any, bundle)
                    val senderNumber = currentMessage.displayOriginatingAddress
                    val senderMessage = currentMessage.displayMessageBody
                    Log.d(
                        TAG,
                        "onReceive: senderNumber $senderNumber, senderMessage $senderMessage"
                    )

                    val showSmsIntent = Intent(context, SmsReceiverActivity::class.java)
                    showSmsIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    showSmsIntent.putExtra(SmsReceiverActivity.EXTRA_SMS_NO, senderNumber)
                    showSmsIntent.putExtra(SmsReceiverActivity.EXTRA_SMS_MESSAGE, senderMessage)
                    context.startActivity(showSmsIntent)
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "onReceive: Exception $e")
        }
    }

    private fun getIncomingMessage(aObject: Any, bundle: Bundle): SmsMessage {
        val currentSms: SmsMessage
        val format = bundle.getString("format")
        currentSms = if (Build.VERSION.SDK_INT >= 23) {
            SmsMessage.createFromPdu(aObject as ByteArray, format)
        } else {
            SmsMessage.createFromPdu(aObject as ByteArray)
        }
        return currentSms
    }
}