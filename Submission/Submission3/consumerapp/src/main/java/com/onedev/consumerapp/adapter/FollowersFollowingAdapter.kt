package com.onedev.consumerapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.onedev.consumerapp.databinding.ListUserBinding
import com.onedev.consumerapp.model.ItemFollowersAndFollowingItem
import com.onedev.consumerapp.ui.FollowersFollowingFragmentDirections
import com.onedev.consumerapp.helper.SupportHelper.loadImage

class FollowersFollowingAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listUsers: List<ItemFollowersAndFollowingItem> = ArrayList()

    fun setListUser(listUsers: List<ItemFollowersAndFollowingItem>) {
        this.listUsers = listUsers
    }

    inner class ViewHolderRecyclerview(private val binding: ListUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            with(binding) {
                val items = listUsers[position]
                imgAvatar.loadImage(items.avatar_url)
                tvUsername.text = items.login
                itemView.setOnClickListener {
                    val toDetailUsers = FollowersFollowingFragmentDirections.actionFollowersFollowingFragmentToDetailHomeFragment()
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