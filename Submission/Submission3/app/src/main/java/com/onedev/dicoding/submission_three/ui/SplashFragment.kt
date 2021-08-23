package com.onedev.dicoding.submission_three.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.onedev.dicoding.submission_three.R
import com.onedev.dicoding.submission_three.databinding.FragmentSplashBinding
import com.onedev.dicoding.submission_three.helper.SupportHelper

class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SupportHelper.hideActionBar(requireActivity())

        Looper.myLooper()?.let {
            Handler(it).postDelayed({
                view.findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }, 2000)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}