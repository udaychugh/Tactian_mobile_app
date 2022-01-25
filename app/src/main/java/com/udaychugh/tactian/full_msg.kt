package com.udaychugh.tactian

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class full_msg : AppCompatActivity() {
    
    lateinit var reportbtn : RelativeLayout
    lateinit var deletebtn : RelativeLayout
    lateinit var numberhere : TextView
    lateinit var msghere : TextView
    lateinit var risk : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_msg)
        
        reportbtn=findViewById(R.id.reportbtn)
        deletebtn=findViewById(R.id.deletebtn)
        numberhere=findViewById(R.id.numberHere)
        msghere=findViewById(R.id.msgHere)
        risk=findViewById(R.id.riskscore)


        val sharedPreferences : SharedPreferences = getSharedPreferences("myshare", MODE_PRIVATE)
        val senderNumber = sharedPreferences.getString("PHONE", "")
        val senderSMS = sharedPreferences.getString("MESSAGE", "")
        val randomrisk = (61..100).random()


        numberhere.text = senderNumber
        msghere.text = senderSMS
        risk.text = randomrisk.toString()

        reportbtn.setOnClickListener {
            Toast.makeText(applicationContext, "Suspicious Message Reported", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@full_msg, report::class.java)
            startActivity(intent)
        }

        deletebtn.setOnClickListener {
            Toast.makeText(applicationContext, "Suspicious Message Deleted", Toast.LENGTH_SHORT).show()
        }
        
    }
}

