package com.onedev.dicoding.submission_three.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onedev.dicoding.submission_three.databinding.ListUserBinding
import com.onedev.dicoding.submission_three.model.ItemSearchUser

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
                imgAvatar.loadImage(items.avatar_url)
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