package com.onedev.dicoding.submission_three.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.onedev.dicoding.submission_three.databinding.ListUserBinding
import com.onedev.dicoding.submission_three.model.ItemUser
import com.onedev.dicoding.submission_three.ui.HomeFragmentDirections
import com.onedev.dicoding.submission_three.helper.SupportHelper.loadImage

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolderRecyclerview>() {

    private val mListUsers = ArrayList<ItemUser>()

    @SuppressLint("NotifyDataSetChanged")
    fun setListUser(items: ArrayList<ItemUser>?) {
        mListUsers.clear()
        items?.let {
            mListUsers.addAll(it)
        }
        notifyDataSetChanged()
    }

    inner class ViewHolderRecyclerview(private val binding: ListUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(items: ItemUser) {
            with(binding) {
                items.avatar_url?.let { imgAvatar.loadImage(it) }
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
        val binding = ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderRecyclerview(binding)
    }

    override fun getItemCount(): Int = mListUsers.size

    override fun onBindViewHolder(holder: UserAdapter.ViewHolderRecyclerview, position: Int) {
        holder.bind(mListUsers[position])
    }
}