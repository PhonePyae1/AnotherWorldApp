package com.example.isekai.writeNewDiary

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.isekai.listDiaries.Diary
import com.example.isekai.HomePage
import com.example.isekai.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_new_diary2.*
import java.util.*
import java.text.DateFormat
import java.text.SimpleDateFormat


class NewDiary2 : AppCompatActivity() {

    private lateinit var database : DatabaseReference
    private lateinit var progressbar : ProgressBar
    private var downloadUri: Uri? = null
//    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_diary2)
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

//        var progressBar = ProgressBar(this)

        backButton.setOnClickListener {
            finish()
        }

        val intent = intent
        val str: String? = intent.getStringExtra("sent")

        submitButton.setOnClickListener {

            val city = "Bangkok, Thailand"
            val title = title1.text.toString()
            val image = downloadUri.toString()
            val attraction = str.toString()
            val context = story1.text.toString()

            val dateFormat: DateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
            val date = Date()
            val timeStamp: String = dateFormat.format(date).toString()

//            val timeStamp = strDate

            val ref = FirebaseDatabase.getInstance().reference
//            val map: MutableMap<*, *> = HashMap()
//            map["timestamp"] = ServerValue.TIMESTAMP
//            ref.child("yourNode").updateChildren(map)

            database = FirebaseDatabase.getInstance().getReference("Diary").child(city).push()
//            database = FirebaseDatabase.getInstance().getReference("Diary").push()
//            val Diary = Diary(location, title, story)
            val diary = Diary(title, image, attraction, context, timeStamp)
            database.setValue(diary)
//            database.child(timeStamp.toString()).setValue(diary)

//            val intent = Intent(this, HomePage::class.java)
//            startActivity(intent)
            val intent = Intent(this, HomePage::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("fragmentToLoad", 1)
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