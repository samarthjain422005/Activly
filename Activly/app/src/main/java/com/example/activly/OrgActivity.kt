package com.example.activly

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.content.Intent

class OrgActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_org)
        var t4=findViewById<Button>(R.id.button3)
        t4.setOnClickListener {
            val intent7=Intent(this,MainUpdatesActivity::class.java)
            startActivity(intent7)
        }
    }
}