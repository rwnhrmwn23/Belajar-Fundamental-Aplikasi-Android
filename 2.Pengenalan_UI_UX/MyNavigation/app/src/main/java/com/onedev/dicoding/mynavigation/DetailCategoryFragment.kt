package com.onedev.dicoding.mynavigation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.onedev.dicoding.mynavigation.databinding.FragmentDetailCategoryBinding


class DetailCategoryFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDetailCategoryBinding? = null
    private val binding get() = _binding!!
    private val args: DetailCategoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvCategoryName.text = args.name
        binding.tvCategoryDescription.text = "Stock : ${args.stock}"
        binding.btnProfile.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnProfile -> {
                v.findNavController().navigate(R.id.action_detailCategoryFragment_to_homeFragment)
            }
        }
    }
}