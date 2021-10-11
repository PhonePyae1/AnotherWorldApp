package com.example.isekai.listDiaries

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast

import com.example.isekai.R
import com.example.isekai.diaryDetails.DiaryDetails
import com.example.isekai.listCity.CityAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_diary.view.*


/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyDiaryRecyclerViewAdapter(var c: Context, private val diaryList: ArrayList<Diary>) : RecyclerView.Adapter<MyDiaryRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iv = view.iv as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_diary, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val path = diaryList[position].image
//        holder.idView.text = item.id
//        holder.contentView.text = item.content
        Picasso.get()
            .load(path)
            .resize(250, 250)
            .centerCrop()
            .into(holder.iv)

        holder.itemView.rootView.setOnClickListener{
//            val mIntent = Intent(c, NewActivity::class.java)
//            mIntent.putExtra("img", img)
//            mIntent.putExtra("name", name)
//            mIntent.putExtra("info", info)
//            c.startActivity(mIntent)
            Toast.makeText(it.context,"hi",Toast.LENGTH_SHORT).show()

            val title = diaryList[position].title
            val image = diaryList[position].image
            val attraction = diaryList[position].attraction
            val context = diaryList[position].context
            val timeStamp = diaryList[position].timeStamp

            val detailIntent = Intent(c, DiaryDetails::class.java)
            detailIntent.putExtra("title", title)
            detailIntent.putExtra("image", image)
            detailIntent.putExtra("attraction", attraction)
            detailIntent.putExtra("context", context)
            detailIntent.putExtra("timeStamp", timeStamp)
            c.startActivity(detailIntent)
        }
    }


    override fun getItemCount(): Int = diaryList.size

//    inner class ViewHolder(view: @NonNull FragmentDiaryBinding, binding: FragmentDiaryBinding) : RecyclerView.ViewHolder(binding.root) {
//        val iv = view.iv as ImageView
//        val idView: TextView = binding.itemNumber
//        val contentView: TextView = binding.content

//        override fun toString(): String {
//            return super.toString() + " '" + contentView.text + "'"
//        }
//    }

}