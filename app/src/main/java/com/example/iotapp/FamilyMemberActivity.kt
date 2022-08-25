package com.example.iotapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.iotapp.databinding.ActivityFamilyEditBinding


class FamilyMemberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFamilyEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFamilyEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        when (intent.getStringExtra("FamilyMemberActivity")) {
            "addMember" -> {
                findNavController(R.id.nav_host_fragment_family_edit).navigate(R.id.navigation_family_member_add)
            }
            "editMember" -> {
                Toast.makeText(this, "edit", Toast.LENGTH_SHORT).show()
            }
            "addFamily" -> {
                findNavController(R.id.nav_host_fragment_family_edit).navigate(R.id.navigation_family_add)
            }
        }
    }
}
