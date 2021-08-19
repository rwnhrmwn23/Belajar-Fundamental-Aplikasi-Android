package com.onedev.dicoding.submission_two.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.onedev.dicoding.submission_two.R
import com.onedev.dicoding.submission_two.databinding.FragmentDetailHomeBinding
import com.onedev.dicoding.submission_two.util.Constant
import com.onedev.dicoding.submission_two.util.PreferenceManager
import com.onedev.dicoding.submission_two.util.Support
import com.onedev.dicoding.submission_two.util.Support.loadImage
import com.onedev.dicoding.submission_two.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailHomeFragment : Fragment(), View.OnClickListener {

    private lateinit var preferenceManager: PreferenceManager
    private var _binding: FragmentDetailHomeBinding? = null
    private val binding get() = _binding
    private val args: DetailHomeFragmentArgs by navArgs()
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Support.hideActionBar(requireActivity())

        preferenceManager = PreferenceManager(requireContext())
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding?.toolbar?.setNavigationOnClickListener {
            it.findNavController().navigate(R.id.action_detailHomeFragment_to_homeFragment)
        }

        binding?.toolbar?.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_share) {
                val textToShare = getString(R.string.info_github_user) +
                        "\n${getString(R.string.username)} : ${binding?.tvToolbarTitle?.text}" +
                        "\n${getString(R.string.name)} : ${binding?.tvName?.text}" +
                        "\n${Support.replaceSymbol(getString(R.string.repository))} : ${Support.replaceRepo(binding?.tvRepository?.text.toString())}" +
                        "\n${getString(R.string.followers)} : ${Support.convertToDec(binding?.tvFollowers?.text.toString().toDouble())}" +
                        "\n${getString(R.string.following)} : ${Support.convertToDec(binding?.tvFollowing?.text.toString().toDouble())}" +
                        "\n${getString(R.string.location)} : ${binding?.tvLocation?.text}" +
                        "\n${getString(R.string.company_name)} : ${binding?.tvCompany?.text}"
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

        binding?.llFollowers?.setOnClickListener(this)
        binding?.llFollowing?.setOnClickListener(this)

        bindUI()
    }

    private fun bindUI() {
        args.username?.let { viewModel.getUserDetail(it) }

        viewModel.showProgress.observe(viewLifecycleOwner, {
            if (it) {
                binding?.shimmerViewContainer?.startShimmer()
                binding?.shimmerViewContainer?.visibility = View.VISIBLE
                binding?.llLayout?.visibility = View.GONE
            } else {
                binding?.shimmerViewContainer?.stopShimmer()
                binding?.shimmerViewContainer?.visibility = View.GONE
                binding?.llLayout?.visibility = View.VISIBLE
            }
        })

        viewModel.userDetail.observe(viewLifecycleOwner, {
            lifecycleScope.launch(Dispatchers.Main) {
                it?.avatar_url?.let { url -> binding?.imgAvatar?.loadImage(url) }
                binding?.tvToolbarTitle?.text = it.login
                binding?.tvName?.text = it.name
                binding?.tvRepository?.text = getString(R.string.repository, Support.convertToDec(it.public_repos.toDouble()))
                binding?.tvFollowers?.text = Support.convertToDec(it.followers.toDouble())
                binding?.tvFollowing?.text = Support.convertToDec(it.following.toDouble())
                binding?.tvLocation?.text = it.location
                binding?.tvCompany?.text = it.company
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v) {
            binding?.llFollowers -> {
                val toFollowersFollowing = DetailHomeFragmentDirections.actionDetailHomeFragmentToFollowersFollowingFragment()
                toFollowersFollowing.pageIndex = 0
                toFollowersFollowing.username = binding?.tvToolbarTitle?.text.toString()
                preferenceManager.putString(Constant.USERNAME, toFollowersFollowing.username)
                v?.findNavController()?.navigate(toFollowersFollowing)
            }
            binding?.llFollowing -> {
                val toFollowersFollowing =
                    DetailHomeFragmentDirections.actionDetailHomeFragmentToFollowersFollowingFragment()
                toFollowersFollowing.pageIndex = 1
                toFollowersFollowing.username = binding?.tvToolbarTitle?.text.toString()
                preferenceManager.putString(Constant.USERNAME, toFollowersFollowing.username)
                v?.findNavController()?.navigate(toFollowersFollowing)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding?.shimmerViewContainer?.startShimmer()
    }

    override fun onPause() {
        binding?.shimmerViewContainer?.stopShimmer()
        super.onPause()
    }
}