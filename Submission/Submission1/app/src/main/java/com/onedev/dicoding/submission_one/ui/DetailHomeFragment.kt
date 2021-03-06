package com.onedev.dicoding.submission_one.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.onedev.dicoding.submission_one.R
import com.onedev.dicoding.submission_one.databinding.FragmentDetailHomeBinding
import com.onedev.dicoding.submission_one.util.Support
import com.onedev.dicoding.submission_one.util.Support.loadImage

class DetailHomeFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDetailHomeBinding? = null
    private val binding get() = _binding!!
    private val args: DetailHomeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            it.findNavController().navigate(R.id.action_detailHomeFragment_to_homeFragment)
        }
        binding.imgShareUser.setOnClickListener(this)

        bindUI()
    }

    private fun bindUI() {
        args.dataUsers?.avatar?.let { binding.imgAvatar.loadImage(it) }
        binding.tvToolbarTitle.text = args.dataUsers?.username
        binding.tvName.text = args.dataUsers?.name
        binding.tvRepository.text = args.dataUsers?.repository?.let { Support.convertToDec(it.toDouble()) }
        binding.tvFollowers.text = args.dataUsers?.follower?.let { Support.convertToDec(it.toDouble()) }
        binding.tvFollowing.text = args.dataUsers?.following?.let { Support.convertToDec(it.toDouble()) }
        binding.tvLocation.text = args.dataUsers?.location
        binding.tvCompany.text = args.dataUsers?.company
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.imgShareUser -> {
                val textToShare = "Info Github User" +
                        "\nUsername : ${args.dataUsers?.username}" +
                        "\nName : ${args.dataUsers?.name}" +
                        "\nRepository : ${Support.convertToDec(args.dataUsers?.repository!!.toDouble())}" +
                        "\nFollowers : ${Support.convertToDec(args.dataUsers?.follower!!.toDouble())}" +
                        "\nFollowing : ${Support.convertToDec(args.dataUsers?.following!!.toDouble())}" +
                        "\nLocation : ${args.dataUsers?.location}" +
                        "\nCompany : ${args.dataUsers?.company}"
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, textToShare)
                startActivity(Intent.createChooser(intent, "Share"))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}