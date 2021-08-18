package com.onedev.dicoding.submission_two.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.onedev.dicoding.submission_two.R
import com.onedev.dicoding.submission_two.databinding.FragmentChangeLanguageBinding
import com.onedev.dicoding.submission_two.util.LocaleHelper
import com.onedev.dicoding.submission_two.util.Support

class ChangeLanguageFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentChangeLanguageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChangeLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Support.hideActionBar(requireActivity())

        binding.toolbar.setNavigationOnClickListener {
            it.findNavController().navigate(R.id.action_changeLanguageFragment_to_settingFragment)
        }

        if (LocaleHelper.getLanguage(requireContext()) == "in")
            binding.rbIndonesia.isChecked = true
        else
            binding.rbEnglish.isChecked = true

        binding.btnSaveLanguage.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnSaveLanguage -> {
                val checkRadioButtonId = binding.rgChangeLanguage.checkedRadioButtonId
                if (checkRadioButtonId != -1) {
                    var chooseLanguage: String? = null
                    when (checkRadioButtonId) {
                        R.id.rb_english -> chooseLanguage = "en-us"
                        R.id.rb_indonesia -> chooseLanguage = "in"
                    }

                    LocaleHelper.setLocale(requireContext(), chooseLanguage)
                    startActivity(Intent(requireContext(), MainActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                }
            }
        }
    }
}