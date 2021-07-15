package com.example.isekai.writeNewDiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.isekai.R
import kotlinx.android.synthetic.main.activity_new_diary1.*
import kotlinx.android.synthetic.main.activity_new_diary1.nextButton
import kotlinx.android.synthetic.main.activity_new_diary2.*

class NewDiary2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_diary2)
        supportActionBar!!.title = "Diary"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        backButton.setOnClickListener {
            val intent = Intent(this, NewDiary1::class.java)
            startActivity(intent)
        }
    }
}