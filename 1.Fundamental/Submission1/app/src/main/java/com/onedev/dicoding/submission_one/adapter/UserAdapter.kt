package com.onedev.dicoding.submission_one.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.onedev.dicoding.submission_one.R
import com.onedev.dicoding.submission_one.databinding.ListUserBinding
import com.onedev.dicoding.submission_one.model.Users
import com.onedev.dicoding.submission_one.ui.HomeFragmentDirections

class UserAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listUsers: List<Users> = ArrayList()

    fun setListUser(listUsers: List<Users>) {
        this.listUsers = listUsers
    }

    inner class ViewHolderRecyclerview(private val binding: ListUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            with(binding) {
                val items = listUsers[position]
                Glide.with(itemView.context)
                    .load(items.avatar)
                    .circleCrop()
                    .placeholder(R.drawable.ic_baseline_person)
                    .into(imgAvatar)
                tvName.text = items.name
                tvUsername.text = items.username
                itemView.setOnClickListener {
                    val toDetailUsers = HomeFragmentDirections.actionHomeFragmentToDetailHomeFragment()
                    toDetailUsers.dataUsers = items
                    it.findNavController().navigate(toDetailUsers)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, postition: Int): RecyclerView.ViewHolder {
        val linearBinding = ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderRecyclerview(linearBinding)
    }

    override fun getItemCount(): Int = listUsers.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderRecyclerview).bind(position)
    }
}