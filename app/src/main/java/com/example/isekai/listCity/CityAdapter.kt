package com.example.isekai.listCity

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.isekai.R
import com.example.isekai.listDiaries.Diary
import com.example.isekai.listDiaries.MyDiaryRecyclerViewAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_diary_card.view.*
import kotlinx.android.synthetic.main.fragment_diary_list.*

class CityAdapter(var c: Context,
    private val cityList: ArrayList<String>
) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {

//    private val viewPool: RecycledViewPool = RecycledViewPool()
//    private val itemList: List<City>? = null
    lateinit var mDataBase: DatabaseReference
    private lateinit var diaryList:ArrayList<Diary>
    private lateinit var mAdapter: MyDiaryRecyclerViewAdapter

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItemTitle: TextView = view.tv_item_title
        val rvSubItem: RecyclerView = itemView.findViewById(R.id.rv_sub_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_diary_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item: Item = itemList.get(i)
//        itemViewHolder.tvItemTitle.setText(item.getItemTitle())

        Log.d("test1234", holder.tvItemTitle.text.toString())
        holder.tvItemTitle.text = cityList[position]


        // Create layout manager with initial prefetch item count
//        val layoutManager = LinearLayoutManager(
//            itemViewHolder.rvSubItem.getContext(),
//            LinearLayoutManager.VERTICAL,
//            false
//        )
//        layoutManager.setInitialPrefetchItemCount(item.getSubItemList().size())

        // Create sub item view adapter

        // Create sub item view adapter
//        val subItemAdapter = SubItemAdapter(item.getSubItemList())
        diaryList = ArrayList()
        mDataBase = FirebaseDatabase.getInstance().getReference("Diary").child(cityList[position])
        mDataBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (animalSnapshot in snapshot.children){
                        val animal = animalSnapshot.getValue(Diary::class.java)
                        diaryList.add(animal!!)
                    }
                    mAdapter = MyDiaryRecyclerViewAdapter(c,diaryList)
                    holder.rvSubItem.adapter = mAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(this@DiaryFragment,
//                    error.message, Toast.LENGTH_SHORT).show()
//                Toast.makeText(activity,"hi", Toast.LENGTH_SHORT).show()
            }
        })
//
        holder.rvSubItem.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
//        holder.rvSubItem.adapter = subItemAdapter
//        holder.rvSubItem.setRecycledViewPool(viewPool)
    }


    override fun getItemCount(): Int = cityList.size

}