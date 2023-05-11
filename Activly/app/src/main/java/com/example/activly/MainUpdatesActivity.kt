package com.example.activly

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainUpdatesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainupdates)
        var clubs=findViewById<Button>(R.id.Clubs)
        var organisation=findViewById<Button>(R.id.Organisation)
        var events=findViewById<Button>(R.id.Events)
        clubs.setOnClickListener {
            val intent3= Intent(this,Clubsnotificationpage::class.java)
            startActivity(intent3)
        }

        organisation.setOnClickListener {
            val intent4= Intent(this,Orgnotificationspage::class.java)
            startActivity(intent4)
        }

        events.setOnClickListener {
            val intent5= Intent(this,Eventsnotificationpage::class.java)
            startActivity(intent5)
        }
    }
}