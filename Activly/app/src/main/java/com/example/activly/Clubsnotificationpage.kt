package com.example.activly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.activly.MyListener
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast

import androidx.core.content.ContextCompat.getSystemService

class Clubsnotificationpage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clubsnotificationpage)

        Log.i(this.javaClass.simpleName, "***********************~~~~~~~~")
    }
}

//class Notifunc(override val default_notification_channel_id: Any) : MyListener {
////    val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//    override fun setValue(notifmessage: String?) {
//    Log.i(this.javaClass.simpleName, "sdsfacndandad*******************************************************~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
//        Log.i("", notifmessage!!)
//    }
//}
