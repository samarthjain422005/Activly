package com.example.activly

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.example.activly.databinding.ActivityMainBinding
//import com.google.android.gms.common.api.Response
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.activly.NotificationService as Notif
//import com.example.activly.ServiceBuilder as Servicebuilder

class MainActivity : AppCompatActivity(), MyListener{
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var auth = Firebase.auth
    override var default_notification_channel_id: Any = ""
    private var NOTIFICATION_CHANNEL_ID = "10001"
    override fun setValue(notifmessage: String?) {
        Log.i("", "setval")
        if (notifmessage != null) {
            Log.i("MESSAGE>>>>>", notifmessage)
        }
    }

    private val client = OkHttpClient.Builder().build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://sammyboy4205.pythonanywhere.com/") //
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }

    var createRetro = buildService(APIinterface::class.java)
    val obj=PostRequest("Hello Riya")
    val call: Call<PostRequest> =createRetro.sendReq(obj)

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        call.enqueue(object : Callback<PostRequest?> {
            override fun onResponse(call: Call<PostRequest?>?, response: Response<PostRequest?>) {
                val model: PostRequest? = response.body()
                println(model)
                println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
//                val resp = model!!.message
//                Log.i("response%%%%%%",)
            }

            override fun onFailure(call: Call<PostRequest?>, t: Throwable) {
                Log.i("Failed", t.toString())
            }
        }
        )

        var bt1 = findViewById<Button>(R.id.loginbtn)
        bt1.setOnClickListener {
            val username: String = findViewById<EditText>(R.id.login_username).text.toString()
            val password: String = findViewById<EditText>(R.id.login_password).text.toString()
            var username2: EditText? = findViewById(R.id.login_username)

            if (username.isNotEmpty() && password.isNotEmpty()) {
                if (isEmail(username2) == false) {
                    Toast.makeText(this, "You must enter valid email.", Toast.LENGTH_SHORT).show()
                } else {
                    firebaseAuth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Log.i("USER", auth.currentUser.toString())
                                Notif().setListener(this)
                                val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    Log.i("","hi****************")
                                    val importance = NotificationManager.IMPORTANCE_HIGH
                                    val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance)
                                    mNotificationManager.createNotificationChannel(notificationChannel)
                                }
                                Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show()
                                val intent2 = Intent(this, PreferenceActivity::class.java)
                                startActivity(intent2)
                            } else {
                                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }

        var bt2 =findViewById<Button>(R.id.signupredirect)
        bt2.setOnClickListener {
            val intent3=Intent(this,Loginpage::class.java )
            startActivity(intent3)
        }
    }
    fun isEmail(text: EditText?): Boolean {
        val email: CharSequence = text!!.text.toString()
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
