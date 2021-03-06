package com.onedev.dicoding.submission_three.ui

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.onedev.dicoding.submission_three.R
import com.onedev.dicoding.submission_three.databinding.FragmentDetailHomeBinding
import com.onedev.dicoding.submission_three.model.ItemUser
import com.onedev.dicoding.submission_three.locale.Constant
import com.onedev.dicoding.submission_three.helper.MappingHelper
import com.onedev.dicoding.submission_three.locale.PreferenceManager
import com.onedev.dicoding.submission_three.helper.SupportHelper
import com.onedev.dicoding.submission_three.helper.SupportHelper.CONTENT_URI
import com.onedev.dicoding.submission_three.helper.SupportHelper.USER_AVATAR_URL
import com.onedev.dicoding.submission_three.helper.SupportHelper.USER_ID
import com.onedev.dicoding.submission_three.helper.SupportHelper.USER_USERNAME
import com.onedev.dicoding.submission_three.helper.SupportHelper.loadImage
import com.onedev.dicoding.submission_three.helper.SupportHelper.refreshWidget
import com.onedev.dicoding.submission_three.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailHomeFragment : Fragment(), View.OnClickListener {

    private var avatarUrl = ""
    private var username = ""
    private var userId = 0

    private var _binding: FragmentDetailHomeBinding? = null
    private val binding get() = _binding
    private val args: DetailHomeFragmentArgs by navArgs()
    private var currentUser: ItemUser? = null

    private lateinit var urlWithId: Uri
    private lateinit var viewModel: MainViewModel
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
                    "\n${SupportHelper.replaceSymbol(getString(R.string.repository))} : ${
                        SupportHelper.replaceRepo(
                            binding?.tvRepository?.text.toString()
                        )
                    }" +
                    "\n${getString(R.string.followers)} : ${
                        SupportHelper.convertToDec(
                            binding?.tvFollowers?.text.toString().toDouble()
                        )
                    }" +
                    "\n${getString(R.string.following)} : ${
                        SupportHelper.convertToDec(
                            binding?.tvFollowing?.text.toString().toDouble()
                        )
                    }" +
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
            userId = it.id
            username = it.login
            avatarUrl = it.avatar_url

            (activity as AppCompatActivity?)?.supportActionBar?.title = username

            binding?.imgAvatar?.loadImage(avatarUrl)
            binding?.tvName?.text = it.name
            binding?.tvRepository?.text =
                getString(R.string.repository, SupportHelper.convertToDec(it.public_repos.toDouble()))
            binding?.tvFollowers?.text = SupportHelper.convertToDec(it.followers.toDouble())
            binding?.tvFollowing?.text = SupportHelper.convertToDec(it.following.toDouble())
            binding?.tvLocation?.text = it.location
            binding?.tvCompany?.text = it.company


            urlWithId = Uri.parse("$CONTENT_URI/$userId")

            lifecycleScope.launch(Dispatchers.Main) {
                val deferredUsername = async(Dispatchers.IO) {
                    val cursor = activity?.contentResolver?.query(urlWithId, null, null, null, null)
                    MappingHelper.mapCursorToObject(cursor)
                }

                currentUser = deferredUsername.await()
                if (currentUser?.id != 0) {
                    binding?.fabDeleteFavorite?.visibility = View.VISIBLE
                    binding?.fabAddFavorite?.visibility = View.INVISIBLE
                } else {
                    binding?.fabDeleteFavorite?.visibility = View.INVISIBLE
                    binding?.fabAddFavorite?.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View?) {
        when (view) {
            binding?.llFollowers -> {
                val toFollowersFollowing =
                    DetailHomeFragmentDirections.actionDetailHomeFragmentToFollowersFollowingFragment()
                toFollowersFollowing.pageIndex = 0
                toFollowersFollowing.username = username
                preferenceManager.putString(Constant.USERNAME, toFollowersFollowing.username)
                view?.findNavController()?.navigate(toFollowersFollowing)
            }
            binding?.llFollowing -> {
                val toFollowersFollowing =
                    DetailHomeFragmentDirections.actionDetailHomeFragmentToFollowersFollowingFragment()
                toFollowersFollowing.pageIndex = 1
                toFollowersFollowing.username = username
                preferenceManager.putString(Constant.USERNAME, toFollowersFollowing.username)
                view?.findNavController()?.navigate(toFollowersFollowing)
            }
            binding?.fabAddFavorite -> {
                val values = ContentValues()
                values.put(USER_ID, userId)
                values.put(USER_USERNAME, username)
                values.put(USER_AVATAR_URL, avatarUrl)

                lifecycleScope.launch(Dispatchers.IO) {
                    activity?.contentResolver?.insert(CONTENT_URI, values)
                }
                refreshWidget(requireContext())

                view?.let {
                    SupportHelper.showSnackBar(it, getString(R.string.success_add_favorite))
                }

                binding?.fabAddFavorite?.visibility = View.INVISIBLE
                binding?.fabDeleteFavorite?.visibility = View.VISIBLE

            }
            binding?.fabDeleteFavorite -> {
                currentUser?.id.let {
                    lifecycleScope.launch(Dispatchers.IO) {
                        activity?.contentResolver?.delete(urlWithId, null, null)
                    }
                }
                refreshWidget(requireContext())

                view?.let {
                    SupportHelper.showSnackBar(it, getString(R.string.success_delete_favorite))
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