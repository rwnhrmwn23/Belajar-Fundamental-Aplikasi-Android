package com.onedev.dicoding.submission_one.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.onedev.dicoding.submission_one.R
import com.onedev.dicoding.submission_one.model.Users

class UserAdapter(
    private val context: Context,
    private val viewType: String,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listUsers = arrayListOf<Users>()

    inner class ViewHolderRecyclerview(view: View) : RecyclerView.ViewHolder(view) {
        private val imgAvatar = view.findViewById(R.id.img_avatar) as ImageView
        private val tvUsername = view.findViewById(R.id.tv_username) as TextView

        fun bind(position: Int) {
            val items = listUsers[position]
            Glide.with(context)
                .load(items.avatar)
                .circleCrop()
                .placeholder(R.drawable.ic_baseline_person)
                .into(imgAvatar)
            tvUsername.text = items.username
            itemView.setOnClickListener {
                Toast.makeText(context, items.name, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, postition: Int): RecyclerView.ViewHolder {
        return if (viewType == "view_linear") {
            val view = LayoutInflater.from(context).inflate(R.layout.list_user_linear, parent, false)
            ViewHolderRecyclerview(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.list_user_grid, parent, false)
            ViewHolderRecyclerview(view)
        }
    }

    override fun getItemCount(): Int = listUsers.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderRecyclerview).bind(position)
    }
}