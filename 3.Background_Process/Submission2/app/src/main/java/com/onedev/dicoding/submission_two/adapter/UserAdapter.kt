package com.onedev.dicoding.submission_two.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.onedev.dicoding.submission_two.R
import com.onedev.dicoding.submission_two.databinding.ListUserBinding
import com.onedev.dicoding.submission_two.model.ItemSearchUser
import com.onedev.dicoding.submission_two.ui.HomeFragmentDirections

class UserAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listUsers: List<ItemSearchUser> = ArrayList()

    fun setListUser(listUsers: List<ItemSearchUser>) {
        this.listUsers = listUsers
    }

    inner class ViewHolderRecyclerview(private val binding: ListUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            with(binding) {
                val items = listUsers[position]
                Glide.with(itemView.context)
                    .load(items.avatar_url)
                    .circleCrop()
                    .placeholder(R.drawable.ic_baseline_person)
                    .into(imgAvatar)
                tvUsername.text = items.login
                itemView.setOnClickListener {
                    val toDetailUsers = HomeFragmentDirections.actionHomeFragmentToDetailHomeFragment()
                    toDetailUsers.username = items.login
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