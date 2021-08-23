package com.onedev.dicoding.submission_three.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.onedev.dicoding.submission_three.R
import com.onedev.dicoding.submission_three.databinding.FragmentSettingBinding
import com.onedev.dicoding.submission_three.helper.LocaleHelper
import com.onedev.dicoding.submission_three.helper.SupportHelper
import com.onedev.dicoding.submission_three.helper.SupportHelper.ALARM_ID_REPEATING
import com.onedev.dicoding.submission_three.locale.Constant
import com.onedev.dicoding.submission_three.locale.PreferenceManager
import com.onedev.dicoding.submission_three.service.AlarmHelper
import java.util.*

class SettingFragment : Fragment(), View.OnClickListener {

    private lateinit var preferenceManager: PreferenceManager
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

        preferenceManager = PreferenceManager(requireContext())

        if (LocaleHelper.getLanguage(requireContext()) == "in")
            binding?.tvSelectedLanguage?.text = getString(R.string.indonesia)
        else
            binding?.tvSelectedLanguage?.text = getString(R.string.english)

        binding?.relChangeLanguage?.setOnClickListener(this)

        val reminder = preferenceManager.getBoolean(Constant.REMINDER)
        binding?.switchWidgetReminder?.isChecked = reminder
        binding?.switchWidgetReminder?.setOnCheckedChangeListener { _, isChecked ->
            setReminder(isChecked)
        }

    }

    private fun setReminder(status: Boolean) {
        preferenceManager.putBoolean(Constant.REMINDER, status)

        if (status) {
            AlarmHelper.createAlarm(
                context = requireContext(),
                title = getString(R.string.app_name),
                message = getString(R.string.message_reminder),
                requestCode = ALARM_ID_REPEATING,
                time = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 9)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                }
            )

            SupportHelper.showSnackBar(requireView(), getString(R.string.success_add_reminder))
        } else {
            AlarmHelper.cancelAlarm(requireContext(), ALARM_ID_REPEATING)
            SupportHelper.showSnackBar(requireView(), getString(R.string.success_delete_reminder))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v) {
            binding?.relChangeLanguage -> {
                v?.findNavController()
                    ?.navigate(R.id.action_settingFragment_to_changeLanguageFragment)
            }
        }
    }
}