package com.example.isekai.writeNewDiary

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.isekai.HomePage
import com.example.isekai.R
import kotlinx.android.synthetic.main.activity_new_diary1.*

class NewDiary1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_diary1)


        findViewById<EditText>(R.id.location1).setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEND -> {
                    sendMessage()
                    true
                }
                else -> false
            }
        }


        nextButton.setOnClickListener {
            sendMessage()
        }

        cancelButton.setOnClickListener {
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }

    }

    private fun sendMessage() {
        val send_text: EditText = findViewById(R.id.location1)
        val intent = Intent(this, NewDiary2::class.java)
        intent.putExtra("sent",send_text.text.toString())
        startActivity(intent)
    }
}