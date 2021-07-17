package com.example.isekai.writeNewDiary

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.isekai.HomePage
import com.example.isekai.R

class NewDiary1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_diary1)
        val send_text: EditText = findViewById(R.id.location1)
        val nextButton: Button = findViewById(R.id.nextButton)
        val cancelButton : Button = findViewById(R.id.cancelButton)
        nextButton.setOnClickListener {
            val intent = Intent(this, NewDiary2::class.java)
            intent.putExtra("sent",send_text.text.toString())
            startActivity(intent)
        }

        cancelButton.setOnClickListener {
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }

    }

    fun write_next_onclick(view: View) {}
}