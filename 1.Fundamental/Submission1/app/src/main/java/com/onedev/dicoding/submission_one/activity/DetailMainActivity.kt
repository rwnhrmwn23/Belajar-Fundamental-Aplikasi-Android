package com.onedev.dicoding.submission_one.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.onedev.dicoding.submission_one.R
import com.onedev.dicoding.submission_one.databinding.ActivityDetailMainBinding
import com.onedev.dicoding.submission_one.model.Users
import com.onedev.dicoding.submission_one.util.Support

class DetailMainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailMainBinding
    private var data: Users? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        binding.imgShareUser.setOnClickListener(this)

        bindUI()
    }

    private fun bindUI() {
        data = Users()
        data = intent.getParcelableExtra(EXTRA_USER)
        Glide.with(this)
            .load(data?.avatar)
            .circleCrop()
            .placeholder(R.drawable.ic_baseline_person)
            .into(binding.imgAvatar)
        binding.tvToolbarTitle.text = data?.username
        binding.tvName.text = data?.name
        binding.tvRepository.text = Support.convertToDec(data?.repository!!.toDouble())
        binding.tvFollowers.text = Support.convertToDec(data?.follower!!.toDouble())
        binding.tvFollowing.text = Support.convertToDec(data?.following!!.toDouble())
        binding.tvLocation.text = data?.location
        binding.tvCompany.text = data?.company
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.imgShareUser -> {
                val textToShare = "Info Github User" +
                        "\nUsername : ${data?.username}" +
                        "\nName : ${data?.name}" +
                        "\nRepository : ${Support.convertToDec(data?.repository!!.toDouble())}" +
                        "\nFollowers : ${Support.convertToDec(data?.follower!!.toDouble())}" +
                        "\nFollowing : ${Support.convertToDec(data?.following!!.toDouble())}" +
                        "\nLocation : ${data?.location}" +
                        "\nCompany : ${data?.company}"
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, textToShare)
                startActivity(Intent.createChooser(intent, "Share"))
            }
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}