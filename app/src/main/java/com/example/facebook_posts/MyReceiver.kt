package com.example.facebook_posts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.TelephonyManager
import android.widget.Toast


class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        when (intent?.action) {
            //add receiver to manifest
            //add permission , and uses feature in manifest
            // ask for run time permission  in Activity : ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_SMS), 23)
            Telephony.Sms.Intents.SMS_RECEIVED_ACTION -> {
                Toast.makeText(context, "received an sms", Toast.LENGTH_LONG).show()
            }

            TelephonyManager.ACTION_PHONE_STATE_CHANGED -> {
                val phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
                when (phoneState) {
                    TelephonyManager.EXTRA_STATE_RINGING ->
                        Toast.makeText(context, "phone is ringing...", Toast.LENGTH_LONG).show()

                    TelephonyManager.EXTRA_STATE_OFFHOOK ->
                        Toast.makeText(context, "phone call started ...", Toast.LENGTH_LONG).show()

                    TelephonyManager.EXTRA_STATE_IDLE ->
                        Toast.makeText(context, "phone is idle...", Toast.LENGTH_LONG).show()

                }

            }

            MainActivity.CUSTOM_ACTION -> {
                Toast.makeText(context, "broadcast received", Toast.LENGTH_LONG).show()
            }

        }


    }

}
