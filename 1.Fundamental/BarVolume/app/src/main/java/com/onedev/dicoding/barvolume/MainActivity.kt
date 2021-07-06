package com.onedev.dicoding.barvolume

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.onedev.dicoding.barvolume.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalculate.setOnClickListener(this)

        if (savedInstanceState != null) {
            val result = savedInstanceState.getString(STATE_RESULT)
            binding.tvResult.text = result
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_RESULT, binding.tvResult.text.toString())
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.btnCalculate -> {
                val inputWidth = binding.edtWidth.text.toString().trim()
                val inputHeight = binding.edtHeight.text.toString().trim()
                val inputLength = binding.edtLength.text.toString().trim()

                var isEmptyFields = false

                if (inputWidth.isEmpty()) {
                    isEmptyFields = true
                    binding.edtWidth.error = "Field ini tidak boleh kosong"
                }

                if (inputHeight.isEmpty()) {
                    isEmptyFields = true
                    binding.edtHeight.error = "Field ini tidak boleh kosong"
                }

                if (inputLength.isEmpty()) {
                    isEmptyFields = true
                    binding.edtLength.error = "Field ini tidak boleh kosong"
                }

                if (!isEmptyFields) {
                    val volume = inputLength.toDouble() * inputWidth.toDouble() * inputHeight.toDouble()
                    binding.tvResult.text = volume.toString()
                }
            }
        }
    }

    companion object {
        private const val STATE_RESULT = "state_result"
    }
}