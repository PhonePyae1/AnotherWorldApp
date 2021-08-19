package com.example.isekai.SignIn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.isekai.HomePage
import com.example.isekai.R
import kotlinx.android.synthetic.main.activity_login_page.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        registerButton.setOnClickListener {
            when {
                TextUtils.isEmpty(gmailInput.text.toString().trim{it<= ' '}) -> {
                    Toast.makeText(
                        this,
                        "Please Enter Email",
                        Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(passwordInput.text.toString().trim{it<= ' '}) -> {
                    Toast.makeText(
                        this,
                        "Please Enter Password",
                        Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val email: String = gmailInput.text.toString().trim { it <= ' '}
                    val password: String = passwordInput.text.toString().trim { it <= ' '}

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                        OnCompleteListener<AuthResult> {
                            task ->
                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                Toast.makeText(this,"You are registered successful",Toast.LENGTH_SHORT).show()

                                val intent = Intent(this, HomePage::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id",firebaseUser.uid)
                                intent.putExtra("email_id",email)
                                startActivity(intent)
                            }else {
                                Toast.makeText(this,task.exception!!.message.toString(),Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            }
        }
    }
    fun AlreadyHaveAccount(view: View) {
        onBackPressed()
    }

}