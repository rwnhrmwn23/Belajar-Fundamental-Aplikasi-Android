package com.onedev.dicoding.submission_three.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.onedev.dicoding.submission_three.R
import com.onedev.dicoding.submission_three.databinding.FragmentDetailHomeBinding
import com.onedev.dicoding.submission_three.model.ItemUser
import com.onedev.dicoding.submission_three.util.Constant
import com.onedev.dicoding.submission_three.util.PreferenceManager
import com.onedev.dicoding.submission_three.util.Support
import com.onedev.dicoding.submission_three.util.Support.loadImage
import com.onedev.dicoding.submission_three.viewmodel.FavoriteViewModel
import com.onedev.dicoding.submission_three.viewmodel.MainViewModel

class DetailHomeFragment : Fragment(), View.OnClickListener {

    private var avatarUrl = ""
    private var username = ""

    private var _binding: FragmentDetailHomeBinding? = null
    private val binding get() = _binding
    private val args: DetailHomeFragmentArgs by navArgs()
    private var currentUser: ItemUser? = null

    private lateinit var viewModel: MainViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        preferenceManager = PreferenceManager(requireContext())
        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding?.llFollowers?.setOnClickListener(this)
        binding?.llFollowing?.setOnClickListener(this)
        binding?.fabAddFavorite?.setOnClickListener(this)
        binding?.fabDeleteFavorite?.setOnClickListener(this)

        populateView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_share) {
            val textToShare = getString(R.string.info_github_user) +
                    "\n${getString(R.string.username)} : $username" +
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
        }
        return super.onOptionsItemSelected(item)
    }

    private fun populateView() {
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
            username = it.login
            (activity as AppCompatActivity?)?.supportActionBar?.title = username

            it?.avatar_url?.let { url ->
                avatarUrl = url
                binding?.imgAvatar?.loadImage(avatarUrl)
            }
            binding?.tvName?.text = it.name
            binding?.tvRepository?.text = getString(R.string.repository, Support.convertToDec(it.public_repos.toDouble()))
            binding?.tvFollowers?.text = Support.convertToDec(it.followers.toDouble())
            binding?.tvFollowing?.text = Support.convertToDec(it.following.toDouble())
            binding?.tvLocation?.text = it.location
            binding?.tvCompany?.text = it.company
        })

        args.username?.let {
            favoriteViewModel.selectSpecificFavorite(it)
            favoriteViewModel.itemUser.observe(viewLifecycleOwner, { itemUser ->
                if (itemUser != null) {
                    currentUser = itemUser
                    binding?.fabDeleteFavorite?.visibility = View.VISIBLE
                    binding?.fabAddFavorite?.visibility = View.INVISIBLE
                } else {
                    binding?.fabDeleteFavorite?.visibility = View.INVISIBLE
                    binding?.fabAddFavorite?.visibility = View.VISIBLE
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View?) {
        when (view) {
            binding?.llFollowers -> {
                val toFollowersFollowing = DetailHomeFragmentDirections.actionDetailHomeFragmentToFollowersFollowingFragment()
                toFollowersFollowing.pageIndex = 0
                toFollowersFollowing.username = username
                preferenceManager.putString(Constant.USERNAME, toFollowersFollowing.username)
                view?.findNavController()?.navigate(toFollowersFollowing)
            }
            binding?.llFollowing -> {
                val toFollowersFollowing = DetailHomeFragmentDirections.actionDetailHomeFragmentToFollowersFollowingFragment()
                toFollowersFollowing.pageIndex = 1
                toFollowersFollowing.username = username
                preferenceManager.putString(Constant.USERNAME, toFollowersFollowing.username)
                view?.findNavController()?.navigate(toFollowersFollowing)
            }
            binding?.fabAddFavorite -> {
                val user = ItemUser(0, avatarUrl, username)
                favoriteViewModel.addFavorite(user)

                view?.let {
                    Support.showSnackBar(it, getString(R.string.success_add_favorite))
                }

                binding?.fabAddFavorite?.visibility = View.INVISIBLE
                binding?.fabDeleteFavorite?.visibility = View.VISIBLE

            }
            binding?.fabDeleteFavorite -> {
                currentUser?.let {
                    favoriteViewModel.deleteFavorite(it)
                }

                view?.let {
                    Support.showSnackBar(it, getString(R.string.success_delete_favorite))
                }

                binding?.fabAddFavorite?.visibility = View.VISIBLE
                binding?.fabDeleteFavorite?.visibility = View.INVISIBLE
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