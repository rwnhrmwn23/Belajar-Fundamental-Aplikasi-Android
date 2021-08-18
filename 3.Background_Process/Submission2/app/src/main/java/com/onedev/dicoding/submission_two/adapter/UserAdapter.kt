package com.onedev.dicoding.submission_two.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.onedev.dicoding.submission_two.R
import com.onedev.dicoding.submission_two.databinding.ListUserBinding
import com.onedev.dicoding.submission_two.model.ItemSearchUser
import com.onedev.dicoding.submission_two.ui.HomeFragmentDirections

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolderRecyclerview>() {

    private val mListUsers = ArrayList<ItemSearchUser>()

    @SuppressLint("NotifyDataSetChanged")
    fun setListUser(items: ArrayList<ItemSearchUser>) {
        mListUsers.clear()
        mListUsers.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolderRecyclerview(private val binding: ListUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(items: ItemSearchUser) {
            with(binding) {
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

    override fun onCreateViewHolder(parent: ViewGroup, postition: Int): UserAdapter.ViewHolderRecyclerview {
        val linearBinding = ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderRecyclerview(linearBinding)
    }

    override fun getItemCount(): Int = mListUsers.size

    override fun onBindViewHolder(holder: UserAdapter.ViewHolderRecyclerview, position: Int) {
        holder.bind(mListUsers[position])
    }
}