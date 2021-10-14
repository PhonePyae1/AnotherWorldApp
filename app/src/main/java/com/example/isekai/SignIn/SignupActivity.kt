package com.example.isekai.SignIn

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import com.example.isekai.HomePage
import com.example.isekai.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login_page.*
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val auth:FirebaseAuth = FirebaseAuth.getInstance()

        val preferences: SharedPreferences = getSharedPreferences("checkbox",MODE_PRIVATE)
        val checkbox: String? = preferences.getString("remember","")
        if(checkbox.equals("true")) {
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }else if(checkbox.equals("false")) {
            Toast.makeText(this,"Check Remember me.",Toast.LENGTH_SHORT).show()
        }

        rememberMe.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(compoundButton: CompoundButton?, b: Boolean) {
                if (compoundButton != null) {
                    if (compoundButton.isChecked) {
                        val preferences = getSharedPreferences("checkbox", MODE_PRIVATE)
                        val editor: SharedPreferences.Editor = preferences.edit()
                        editor.putString("remember","true")
                        editor.apply()

                    } else if (!compoundButton.isChecked) {
                        val preferences = getSharedPreferences("checkbox", MODE_PRIVATE)
                        val editor: SharedPreferences.Editor = preferences.edit()
                        editor.putString("remember","false")
                        editor.apply()
                    }
                }
            }
        })

        btn_login.setOnClickListener {
            when {
                TextUtils.isEmpty(gmailLoginInput.text.toString().trim{it<= ' '}) -> {
                    Toast.makeText(
                        this,
                        "Please Enter Email",
                        Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(passwordLoginInput.text.toString().trim{it<= ' '}) -> {
                    Toast.makeText(
                        this,
                        "Please Enter Password",
                        Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val email: String = gmailLoginInput.text.toString().trim { it <= ' '}
                    val password: String = passwordLoginInput.text.toString().trim { it <= ' '}

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(
                        OnCompleteListener<AuthResult> {
                                task ->
                            if (task.isSuccessful) {
                                val firebaseUser = auth.currentUser
                                updateUI(firebaseUser)

                            }else {
                                Toast.makeText(this,task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            }
        }
    }


    fun signUp_Click(view: View) {
        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                Toast.makeText(this,"You are logged in successful", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, HomePage::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            else{
                Toast.makeText(this,"Your email is not verified", Toast.LENGTH_SHORT).show()

            }
        }
    }

    fun ForgotPass_Click(view: View) {
        val intent = Intent(this, ForgotPassword::class.java)
        startActivity(intent)
    }
}