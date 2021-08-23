package com.onedev.dicoding.submission_three.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.onedev.dicoding.submission_three.databinding.ListUserFavoriteBinding
import com.onedev.dicoding.submission_three.model.ItemUser
import com.onedev.dicoding.submission_three.ui.FavoriteFragmentDirections
import com.onedev.dicoding.submission_three.ui.HomeFragmentDirections
import com.onedev.dicoding.submission_three.util.IFavorite
import com.onedev.dicoding.submission_three.util.Support.loadImage

class FavoriteAdapter(private val iFavorite: IFavorite) : RecyclerView.Adapter<FavoriteAdapter.ViewHolderRecyclerview>() {

    private val mListUsers = ArrayList<ItemUser>()

    @SuppressLint("NotifyDataSetChanged")
    fun setListUser(items: ArrayList<ItemUser>) {
        mListUsers.clear()
        mListUsers.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolderRecyclerview(private val binding: ListUserFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(items: ItemUser) {
            with(binding) {
                items.avatar_url?.let { imgAvatar.loadImage(it) }
                tvUsername.text = items.login
                imgDeleteFavorite.setOnClickListener {
                    items.id.let { userId ->
                        iFavorite.deleteFavorite(true, userId)
                    }
                }
                itemView.setOnClickListener {
                    val toDetailUsers = FavoriteFragmentDirections.actionFavoriteFragmentToDetailHomeFragment()
                    toDetailUsers.username = items.login
                    it.findNavController().navigate(toDetailUsers)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, postition: Int): FavoriteAdapter.ViewHolderRecyclerview {
        val binding = ListUserFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderRecyclerview(binding)
    }

    override fun getItemCount(): Int = mListUsers.size

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolderRecyclerview, position: Int) {
        holder.bind(mListUsers[position])
    }
}