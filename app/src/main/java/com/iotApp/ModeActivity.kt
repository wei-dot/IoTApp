package com.iotApp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iotApp.databinding.ActivityModeBinding

class ModeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityModeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
