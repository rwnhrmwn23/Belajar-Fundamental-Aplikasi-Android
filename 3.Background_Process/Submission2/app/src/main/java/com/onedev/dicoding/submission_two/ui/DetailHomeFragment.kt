package com.onedev.dicoding.submission_two.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.onedev.dicoding.submission_two.R
import com.onedev.dicoding.submission_two.databinding.FragmentDetailHomeBinding
import com.onedev.dicoding.submission_two.util.Constant
import com.onedev.dicoding.submission_two.util.PreferenceManager
import com.onedev.dicoding.submission_two.util.Support
import com.onedev.dicoding.submission_two.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailHomeFragment : Fragment(), View.OnClickListener {

    private lateinit var preferenceManager: PreferenceManager
    private var _binding: FragmentDetailHomeBinding? = null
    private val binding get() = _binding!!
    private val args: DetailHomeFragmentArgs by navArgs()
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Support.hideActionBar(requireActivity())

        preferenceManager = PreferenceManager(requireContext())
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.toolbar.setNavigationOnClickListener {
            it.findNavController().navigate(R.id.action_detailHomeFragment_to_homeFragment)
        }

        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_share) {
                val textToShare = "Info Github User" +
                        "\nUsername : ${binding.tvToolbarTitle.text}" +
                        "\nName : ${binding.tvName.text}" +
                        "\nRepository : ${Support.convertToDec(binding.tvRepository.text.toString().toDouble())}" +
                        "\nFollowers : ${Support.convertToDec(binding.tvFollowers.text.toString().toDouble())}" +
                        "\nFollowing : ${Support.convertToDec(binding.tvFollowing.text.toString().toDouble())}" +
                        "\nLocation : ${binding.tvLocation.text}" +
                        "\nCompany : ${binding.tvCompany.text}"
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, textToShare)
                startActivity(Intent.createChooser(intent, "Share"))
                true
            } else {
                false
            }
        }

        binding.llFollowers.setOnClickListener(this)
        binding.llFollowing.setOnClickListener(this)

        bindUI()
    }

    private fun bindUI() {
        viewModel.getUserDetail(args.username!!)

        viewModel.userDetail.observe(viewLifecycleOwner, {
            lifecycleScope.launch(Dispatchers.Main) {
                Glide.with(requireContext())
                    .load(it?.avatar_url)
                    .circleCrop()
                    .placeholder(R.drawable.ic_baseline_person)
                    .into(binding.imgAvatar)
                binding.tvToolbarTitle.text = it.login
                binding.tvName.text = it.name
                binding.tvRepository.text = Support.convertToDec(it.public_repos.toDouble())
                binding.tvFollowers.text = Support.convertToDec(it.followers.toDouble())
                binding.tvFollowing.text = Support.convertToDec(it.following.toDouble())
                binding.tvLocation.text = it.location
                binding.tvCompany.text = it.company
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.llFollowers -> {
                val toFollowersFollowing = DetailHomeFragmentDirections.actionDetailHomeFragmentToFollowersFollowingFragment()
                toFollowersFollowing.username = binding.tvToolbarTitle.text.toString()
                preferenceManager.putString(Constant.USERNAME, toFollowersFollowing.username!!)
                v.findNavController().navigate(toFollowersFollowing)
            }
            binding.llFollowing -> {
                val toFollowersFollowing = DetailHomeFragmentDirections.actionDetailHomeFragmentToFollowersFollowingFragment()
                toFollowersFollowing.username = binding.tvToolbarTitle.text.toString()
                preferenceManager.putString(Constant.USERNAME, toFollowersFollowing.username!!)
                v.findNavController().navigate(toFollowersFollowing)
            }
        }
    }
}