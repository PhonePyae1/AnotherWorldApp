package com.example.isekai.EditPages

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.example.isekai.BottomNav3Fragments.ProfileFragment
import com.example.isekai.HomePage
import com.example.isekai.R
import com.example.isekai.SignIn.SignupActivity
import com.example.isekai.databinding.ActivityEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.w3c.dom.Document
import java.net.URI
import java.util.jar.Manifest

class editProfile : AppCompatActivity() {
    lateinit var fStore: FirebaseFirestore
    lateinit var databaseReference: DatabaseReference
    lateinit var user: FirebaseUser
    lateinit var userid:String
    lateinit var imageUri:Uri
    lateinit var uploadTask: UploadTask
    lateinit var storageProfilePicsRef:StorageReference
    lateinit var binding: ActivityEditProfileBinding
    lateinit var ImageUri: URI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val fStore:FirebaseFirestore = FirebaseFirestore.getInstance()

        databaseReference = FirebaseDatabase.getInstance().getReference().child("User")

        btneditprofileCancel.setOnClickListener {
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }

        user = FirebaseAuth.getInstance().currentUser!!
       // userid = FirebaseDatabase.getInstance().getReference("Users").toString()
        userid = user.uid

        btneditprofilesave.setOnClickListener {
            var name:String = editprofileName.text.toString().trim()
            var bio:String = editprofileBio.text.toString().trim()
            var exp:String = editprofileExp.text.toString().trim()
            val documentReference: DocumentReference = fStore.collection("users").document(userid)
            val storageReference = FirebaseStorage.getInstance().getReference("userProfiles/$userid")
            var user = HashMap<String, Any>()
            user.put("name",name)
            user.put("bio",bio)
            user.put("exp",exp)
            storageReference.putFile(imageUri)
            documentReference.set(user)
            Toast.makeText(
                this,
                "Saved",
                Toast.LENGTH_SHORT).show()

            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)

        }
        editprofilePic.setOnClickListener {
           selectImage()
        }

    }


    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==100 && resultCode== RESULT_OK) {
            imageUri = data?.data!!
            binding.editprofilePic.setImageURI(imageUri)
        }
    }


}