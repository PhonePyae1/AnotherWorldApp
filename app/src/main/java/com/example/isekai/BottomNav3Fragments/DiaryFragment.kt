package com.example.isekai.BottomNav3Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.isekai.Diary.MyDiaryRecyclerViewAdapter
import com.example.isekai.R
import com.example.isekai.Diary.Diary
import com.example.isekai.writeNewDiary.NewDiary1
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_diary_list.*
import com.google.firebase.database.GenericTypeIndicator


class DiaryFragment : Fragment() {
    lateinit var mDataBase: DatabaseReference
    private lateinit var diaryList: ArrayList<Diary>
    private lateinit var mAdapter: MyDiaryRecyclerViewAdapter

//    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**initialized*/
        diaryList = ArrayList()
        mAdapter = MyDiaryRecyclerViewAdapter(requireActivity(), diaryList)
//        recyclerAnimals.layoutManager = LinearLayoutManager(this)
//        recyclerAnimals.setHasFixedSize(true)
        // recyclerAnimals.adapter = mAdapter
        /**getData firebase*/

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_diary_list, container, false)

        // Set the adapter

//        if (view is RecyclerView) {
//            with(view) {
//                layoutManager = when {
//                    columnCount <= 1 -> LinearLayoutManager(context)
//                    else -> GridLayoutManager(context, columnCount)
//                }
//                adapter = MyDiaryRecyclerViewAdapter(PlaceholderContent.ITEMS)
//            }
//        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView1.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recyclerView1.adapter = mAdapter
        getDiaryData()

        topAppBar.setOnMenuItemClickListener {
            val intent = Intent(activity, NewDiary1::class.java)
            startActivity(intent)
            true
        }

//        topAppBar.setOnMenuItemClickListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.favorite -> {
//                    // Handle favorite icon press
//                    true
//                }
//                R.id.search -> {
//                    // Handle search icon press
//                    true
//                }
//                R.id.more -> {
//                    // Handle more item (inside overflow menu) press
//                    true
//                }
//                else -> false
//            }
//        }

    }

    private fun getDiaryData() {

        mDataBase = FirebaseDatabase.getInstance().getReference("Diary")


        mDataBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (animalSnapshot in snapshot.children) {
//                    Log.d("test1", animalSnapshot.toString())
//                    lateinit var diaryList2 : ArrayList<Diary>
                    val animal = animalSnapshot.getValue(Diary::class.java)
                    diaryList.add(animal!!)
                }
                recyclerView1.adapter = mAdapter

            }

            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(this@DiaryFragment,
//                    error.message, Toast.LENGTH_SHORT).show()
                Toast.makeText(activity, "hi", Toast.LENGTH_SHORT).show()
            }
        })
    }
}


