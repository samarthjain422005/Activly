package com.example.activly

import android.app.Notification
import android.content.Context
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi

interface MyListener {
    val default_notification_channel_id: Any
    fun setValue(notifmessage: String?)
}

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
class NotificationService : NotificationListenerService() {
    private val TAG = this.javaClass.simpleName
    var context: Context? = null
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        Log.i(TAG, "Notification posted.")
//        Log.i(TAG, sbn.notification.extras.)
        myListener!!.setValue(sbn?.notification?.extras?.getString(Notification.EXTRA_TEXT))
//        Toast.makeText(this, sbn?.notification?.extras?.getString(Notification.EXTRA_TEXT),Toast.LENGTH_LONG).show()
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        Log.i(TAG, "Notification removed.")
    }

    fun setListener(myListener: MyListener?) {
        Companion.myListener = myListener
    }

    companion object {
        var myListener: MyListener? = null
    }
}