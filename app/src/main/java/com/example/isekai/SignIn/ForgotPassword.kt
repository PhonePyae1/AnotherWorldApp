package com.example.isekai.SignIn

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.isekai.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        resetPasswordbtn.setOnClickListener {
            //making virtual keyboard disappear
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken,0)

            if (resetPasswordEmail.text.toString().isNullOrEmpty()) {
                Toast.makeText(
                    this,
                    "Please Enter Email",
                    Toast.LENGTH_SHORT).show()
            }else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(
                    resetPasswordEmail.text.toString()).addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Your reset password has been sent to the email.",
                                Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(
                                this,
                                "Something wrong.",
                                Toast.LENGTH_SHORT).show()
                        }
                }
            }

        }

    }

    fun goBack(view: View) {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }
}