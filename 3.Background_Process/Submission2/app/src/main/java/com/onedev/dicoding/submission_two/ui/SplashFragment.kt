package com.onedev.dicoding.submission_two.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.onedev.dicoding.submission_two.R
import com.onedev.dicoding.submission_two.databinding.FragmentSplashBinding
import androidx.appcompat.app.AppCompatActivity
import com.onedev.dicoding.submission_two.util.Support


class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Support.hideActionBar(requireActivity())

        Handler(Looper.myLooper()!!).postDelayed({
            view.findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }, 500)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}