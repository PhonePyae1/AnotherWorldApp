package com.example.isekai.writeNewDiary

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.isekai.MainActivity
import com.example.isekai.R
import kotlinx.android.synthetic.main.activity_new_diary1.*

class NewDiary1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_diary1)
        val send_text: EditText = findViewById(R.id.location1)
        nextButton.setOnClickListener {
            val intent = Intent(this, NewDiary2::class.java)
            intent.putExtra("sent",send_text.text.toString())
            startActivity(intent)
        }

        cancelButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}