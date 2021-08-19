package com.example.isekai.SignIn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.isekai.HomePage
import com.example.isekai.MapsActivity
import com.example.isekai.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login_page.*
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
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
                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                Toast.makeText(this,"You are logged in successful", Toast.LENGTH_SHORT).show()

                                val intent = Intent(this, HomePage::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id",FirebaseAuth.getInstance().currentUser!!.uid)
                                intent.putExtra("email_id",email)
                                startActivity(intent)
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
}