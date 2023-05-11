package com.example.activly


//import android.R
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.activly.databinding.ActivityLoginpageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class Loginpage : AppCompatActivity() {
    private lateinit var binding: ActivityLoginpageBinding
    private lateinit var firebaseAuth: FirebaseAuth
    var name: EditText? = null
    var branch: EditText? = null
    var yr: EditText? = null
    var num: EditText? = null
    var email: EditText? = null
    var pass: EditText? =null
    private var auth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setContentView(R.layout.activity_loginpage)
        name = findViewById<EditText>(R.id.editTextTextPersonName)
        branch = findViewById<EditText>(R.id.editTextTextPersonName2)
        yr = findViewById<EditText>(R.id.editTextNumber)
        num=findViewById<EditText>(R.id.editTextNumber2)
        email = findViewById<EditText>(R.id.editTextTextEmailAddress)
        pass = findViewById<EditText>(R.id.editTextTextPassword)
        var btn = findViewById<Button>(R.id.button)

        firebaseAuth = FirebaseAuth.getInstance()


//        binding.textView.setOnClickListener {
//            val intent = Intent(this, SignInActivity::class.java)
//            startActivity(intent)
//        }

        btn.setOnClickListener(View.OnClickListener {
            var ans : Boolean = checkDataEntered()
            if(ans==true){
                val email = findViewById<EditText>(R.id.editTextTextEmailAddress).text.toString()
                val pass = findViewById<EditText>(R.id.editTextTextPassword).text.toString()
                if (email.isNotEmpty() && pass.isNotEmpty()) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Log.i("USER", auth.currentUser.toString())
                            // insert code to write user object to firestore
                            // also send user obj to preference class
                            val intent = Intent(this,PreferenceActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }
                } else {
                    Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    fun isEmail(text: EditText?): Boolean {
        val email: CharSequence = text!!.text.toString()
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isEmpty(text: EditText?): Boolean {
        val str: CharSequence = text!!.text.toString()
        return TextUtils.isEmpty(str)
    }

    fun checkDataEntered() : Boolean {
        var flag : Boolean=true;
        var nu = findViewById<EditText>(R.id.editTextNumber2)
        var a = nu.text.toString().length
        var b = (a==10)
        if (isEmpty(name)) {
            name!!.error = "Name is required!"
            val t = Toast.makeText(this, "You must enter your name to register!", Toast.LENGTH_SHORT)
            t.show()
            flag=false
        }
        if (isEmpty(branch)) {
            branch!!.error = "Branch is required!"
            flag=false
        }
        if (isEmpty(yr)) {
            yr!!.error = "Year is required!"
            flag=false
        }
        if (isEmail(email) == false) {
            email!!.error = "Enter valid email!"
            flag=false
        }
        if (isEmpty(num)) {
            num!!.error = "Number is required!"
            flag=false
        }
        if(b==false){
            Toast.makeText(this, "Number should be of 10 digit.", Toast.LENGTH_SHORT).show()
            flag=false
        }
        return flag
    }
}