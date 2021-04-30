package com.example.isekai

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.getBitmap
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso



class UploadPage : AppCompatActivity() {

    lateinit var mStorageRef:StorageReference
    lateinit var mDataBaseRef:DatabaseReference
    lateinit var mImageUri:Uri
    lateinit var mprogressBar:ProgressBar
    lateinit var handler: Handler
    lateinit var input_filename:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_page)


        val btn_choosefile = findViewById<Button>(R.id.button_chooseFiles)
        val btn_upload = findViewById<Button>(R.id.uploadButton)
        input_filename = findViewById<EditText>(R.id.edit_text_name)

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads")
        mDataBaseRef = FirebaseDatabase.getInstance().getReference("uploads")

        btn_upload.setOnClickListener{
            uploadFiles()
        }

        btn_choosefile.setOnClickListener{

            openFileChooser()

        }

    }

    private fun openFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent,111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        val imageViewer = findViewById<ImageView>(R.id.imagesView1)

        if (requestCode == 111 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            mImageUri = data.data!!
            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver,mImageUri)
            imageViewer.setImageBitmap(bitmap)
        }
    }

    private fun uploadFiles() {
        if (mImageUri != null) {
            var pd = ProgressDialog(this)
            pd.setTitle("Uploading")
            pd.show()

            var imageRef = FirebaseStorage.getInstance().reference.child("Uploads/pic.jpg")
            imageRef.putFile(mImageUri)
                .addOnSuccessListener {p0 ->
                    pd.dismiss()
                    Toast.makeText(this,"File Uploaded",Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener{p0 ->
                    pd.dismiss()
                    Toast.makeText(this,p0.message,Toast.LENGTH_LONG).show()
                }
                .addOnProgressListener {p0 ->
                    var progress = (100.0 * p0.bytesTransferred) / p0.totalByteCount
                    pd.setMessage("Uploaded ${progress.toInt()}%")
                }
        }
        else {
            Toast.makeText(this,"File Not Selected",Toast.LENGTH_LONG).show()
        }
    }

}