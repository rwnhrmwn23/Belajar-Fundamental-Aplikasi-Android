package com.onedev.dicoding.submission_three.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.onedev.dicoding.submission_three.R
import com.onedev.dicoding.submission_three.databinding.FragmentSettingBinding
import com.onedev.dicoding.submission_three.util.LocaleHelper
import com.onedev.dicoding.submission_three.util.Support

class SettingFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Support.hideActionBar(requireActivity())

        binding?.toolbar?.setNavigationOnClickListener {
            it.findNavController().navigate(R.id.action_settingFragment_to_homeFragment)
        }

        if (LocaleHelper.getLanguage(requireContext()) == "in")
            binding?.tvSelectedLanguage?.text = getString(R.string.indonesia)
        else
            binding?.tvSelectedLanguage?.text = getString(R.string.english)

        binding?.relChangeLanguage?.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v) {
            binding?.relChangeLanguage -> {
                v?.findNavController()?.navigate(R.id.action_settingFragment_to_changeLanguageFragment)
            }
        }
    }
}