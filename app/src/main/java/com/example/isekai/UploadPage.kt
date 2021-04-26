package com.example.isekai

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class UploadPage : AppCompatActivity() {
    private val PickImageReq = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_page)

        val btn_choosefile = findViewById<Button>(R.id.button_chooseFiles)
        val btn_upload = findViewById<Button>(R.id.uploadButton)
        val input_filename = findViewById<EditText>(R.id.edit_text_name)
        val mprogressBar = findViewById<ProgressBar>(R.id.progress_bar)


        btn_choosefile.setOnClickListener{
            openFileChooser()
        }
        btn_upload.setOnClickListener{}
    }

    private fun openFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent, PickImageReq)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val imageViewer = findViewById<ImageView>(R.id.imagesView1)

        if (requestCode == PickImageReq && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            var mImageUri: Uri? = data.getData()

            Picasso.with(this).load(mImageUri).into(imageViewer)
        }
    }
}