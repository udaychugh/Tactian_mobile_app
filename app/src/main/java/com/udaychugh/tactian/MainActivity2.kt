package com.udaychugh.tactian

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat


class MainActivity2 : AppCompatActivity() {



    private val requestRecieveSms = 2

    lateinit var scanner : RelativeLayout
    lateinit var susmsg : RelativeLayout
    lateinit var contactName : TextView
    lateinit var contactMsg : TextView
    lateinit var fullmsg : RelativeLayout

    lateinit var report : ImageView
    lateinit var delete : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)



        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_SMS),requestRecieveSms)
        }

        scanner=findViewById(R.id.thiscanbehidden)
        susmsg=findViewById(R.id.showingMsg)
        contactName=findViewById(R.id.contactname)
        contactMsg=findViewById(R.id.contactmessage)
        fullmsg=findViewById(R.id.gotofullmsg)

        report=findViewById(R.id.reportimage)
        delete=findViewById(R.id.deleteimage)

        val sharedPreferences : SharedPreferences = getSharedPreferences("myshare", MODE_PRIVATE)
        val phonenum = sharedPreferences.getString("PHONE", "")
        val msgtext = sharedPreferences.getString("MESSAGE", "")

        //val phonenum = intent.getStringExtra("phone")
        //val msgtext = intent.getStringArrayExtra("message")


        if(msgtext?.contains("http://446bdf227fc4.ngrok.io/xxxbank") == true)
        {
            scanner.visibility = View.INVISIBLE
            susmsg.visibility = View.VISIBLE
            contactName.text = phonenum.toString()
            contactMsg.text = msgtext.toString()
            sendNotification()

        }

        report.setOnClickListener {
            Toast.makeText(applicationContext, "Suspicious Message Reported", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity2, report::class.java)
            startActivity(intent)
        }

        delete.setOnClickListener {
            scanner.visibility = View.VISIBLE
            susmsg.visibility = View.INVISIBLE
            Toast.makeText(applicationContext, "Suspicious Message Deleted", Toast.LENGTH_SHORT).show()
        }

        fullmsg.setOnClickListener {
            val intent = Intent(this@MainActivity2, full_msg::class.java)
            startActivity(intent)
        }



    }

    private fun sendNotification() {
        lateinit var notificationManager : NotificationManager
        lateinit var notificationChannel: NotificationChannel
        lateinit var builder : Notification.Builder
        val channelId = "i.apps.notification"
        val description = "Suspicious Message Found"

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(this, full_msg::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val contentView = RemoteViews(packageName, R.layout.activity_full_msg)
        contentView.setTextViewText(R.id.msgHere, "Suspicious Message Found. Please Check Tactian")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channelId)
                    .setContent(contentView)
                    .setSmallIcon(R.drawable.thisisit)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.thisisit))
                    .setContentIntent(pendingIntent)
        } else{
            builder = Notification.Builder(this)
                    .setContent(contentView)
                    .setSmallIcon(R.drawable.thisisit)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.thisisit))
                    .setContentIntent(pendingIntent)
        }
        notificationManager.notify(1234, builder.build())






    }
}