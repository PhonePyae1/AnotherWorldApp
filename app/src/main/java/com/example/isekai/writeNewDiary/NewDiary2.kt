package com.example.isekai.writeNewDiary

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.isekai.HomePage
import com.example.isekai.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_new_diary2.*
import java.util.*


class NewDiary2 : AppCompatActivity() {

    private lateinit var database : DatabaseReference
    private lateinit var progressbar : ProgressBar
    private var downloadUri: Uri? = null
//    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_diary2)
        supportActionBar!!.title = "Diary"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

//        var progressBar = ProgressBar(this)

        backButton.setOnClickListener {
            val intent = Intent(this, NewDiary1::class.java)
            startActivity(intent)
        }

        val intent = intent
        val str: String? = intent.getStringExtra("sent")

        submitButton.setOnClickListener {

            val location = str.toString()
            val title = title1.text.toString()
            val story = story1.text.toString()
            val image = downloadUri.toString()

            database = FirebaseDatabase.getInstance().getReference("Diary")
//            val Diary = Diary(location, title, story)
            val Diary = Diary(location, title, story, image)
            database.child(location).setValue(Diary)

//            val intent = Intent(this, HomePage::class.java)
//            startActivity(intent)
            val intent = Intent(this, HomePage::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("fragmentToLoad", 1);
            startActivity(intent)
        }

//        progressbar.visibility = View.GONE

        fab.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(intent,100)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val imageURI = data?.data!!
            preview.setImageURI(imageURI)
//            preview.scaleType = ImageView.ScaleType.FIT_CENTER


            val id : UUID = UUID.randomUUID()
            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.reference

            val ref = storageRef.child("images/$id")
            var uploadTask = ref.putFile(imageURI)

            val urlTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                ref.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    downloadUri = task.result
                } else {
                    // Handle failures
                    // ...
                }
            }
        }
    }


}