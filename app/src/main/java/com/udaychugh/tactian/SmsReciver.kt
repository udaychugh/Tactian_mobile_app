package com.udaychugh.tactian

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.telephony.SmsMessage
import android.widget.Toast

class SmsReciver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val extras = intent.extras

        if(extras != null){
            val sms= extras.get("pdus") as Array<Any>

            for (i in sms.indices){
                val format = extras.getString("format")
                val smsMessage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    SmsMessage.createFromPdu(sms[i] as ByteArray, format)
                } else{
                    SmsMessage.createFromPdu(sms[i] as ByteArray)
                }

                val phoneNumber = smsMessage.originatingAddress
                val messageText = smsMessage.messageBody.toString()

                savedatatosharedpreference(context, phoneNumber.toString(), messageText)

                //val newintent = Intent(context, MainActivity2::class.java)
                //newintent.putExtra("phone", phoneNumber)
                //newintent.putExtra("message", messageText)
                //startActivity(context, newintent)



                Toast.makeText(context, "Phone Number : $phoneNumber \n Message : $messageText", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun savedatatosharedpreference(context: Context, phoneNumber: String, messageText: String) {
        val sharedPreferences : SharedPreferences = context.getSharedPreferences("myshare", MODE_PRIVATE)
        val myedit : SharedPreferences.Editor = sharedPreferences.edit()
        myedit.putString("PHONE", phoneNumber)
        myedit.putString("MESSAGE", messageText)
        myedit.commit()
    }

}