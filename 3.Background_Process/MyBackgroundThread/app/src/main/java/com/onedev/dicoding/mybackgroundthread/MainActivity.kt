package com.onedev.dicoding.mybackgroundthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.lifecycleScope
import com.onedev.dicoding.mybackgroundthread.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        // Background Process with Executor + Handler
//        val executor = Executors.newSingleThreadExecutor()
//        val handler = Handler(Looper.getMainLooper())
//
//        binding.btnStart.setOnClickListener {
//            executor.execute {
//                try {
//                    // Simulate process in background thread
//                    for (i in 0..10) {
//                        Thread.sleep(500)
//                        val percentage = i * 10
//                        // Update ui in main thread
//                        handler.post {
//                            if (percentage == 100)
//                                binding.tvStatus.setText(R.string.task_completed)
//                            else
//                                binding.tvStatus.text = String.format(getString(R.string.compressing), percentage)
//                        }
//                    }
//                } catch (e: InterruptedException) {
//                    e.printStackTrace()
//                }
//            }
//        }

        // Background Process with Coroutine
        binding.btnStart.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                // Simulate process in background thread
                for (i in 0..10) {
                    delay(500)
                    val percentage = i * 10
                    withContext(Dispatchers.Main) {
                        // Update ui in main thread
                        if (percentage == 100)
                            binding.tvStatus.setText(R.string.task_completed)
                        else
                            binding.tvStatus.text = String.format(getString(R.string.compressing), percentage)
                    }
                }
            }
        }
    }
}