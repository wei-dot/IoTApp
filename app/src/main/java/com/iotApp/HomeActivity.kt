package com.iotApp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.iotApp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (intent.getStringExtra("Home") == "AddDevice") {
            findNavController(R.id.nav_host_fragment_home).navigate(R.id.action_device_list_fragment_to_add_device_fragment)
        }
    }
}
