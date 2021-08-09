package com.onedev.dicoding.mynavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.onedev.dicoding.mynavigation.databinding.FragmentCategoryBinding


class CategoryFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCategoryLifestyle.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnCategoryLifestyle -> {
//                Send Data With Bundle
//                val mBundle = Bundle()
//                mBundle.putString(EXTRA_NAME, "Lifestyle")
//                mBundle.putLong(EXTRA_STOCK, 7)
//                Navigation.findNavController(v).navigate(R.id.action_categoryFragment_to_detailCategoryFragment, mBundle)

//                Send Data With safeArgs
                val toDetailCategoryFragment = CategoryFragmentDirections.actionCategoryFragmentToDetailCategoryFragment()
                toDetailCategoryFragment.name = "Lifestyle"
                toDetailCategoryFragment.stock = 7
                v.findNavController().navigate(toDetailCategoryFragment)
            }
        }
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_STOCK = "extra_stock"
    }
}