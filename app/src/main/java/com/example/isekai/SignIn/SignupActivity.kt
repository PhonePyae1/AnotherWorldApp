package com.example.isekai.signIn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.isekai.MainActivity
import com.example.isekai.R

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

    }

    fun signIn_btn_onClick(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun signUp_Click(view: View) {
        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)
    }


}