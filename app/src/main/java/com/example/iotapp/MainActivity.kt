package com.example.iotapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.iotapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null
    private var title: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.inflateMenu(R.menu.menu_main)
        title = findViewById(R.id.toolbar_title)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_Layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            findViewById(R.id.toolbar),
            R.string.drawer_open,
            R.string.drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navController = findNavController(R.id.nav_host_fragment_content_main)
        onNavigationItemClick()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    //控制工具列右邊圖示的點擊
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    //控制底下导航栏的点击事件
    private fun onNavigationItemClick() {

        val navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_settings -> {
                    title?.text = getString(R.string.fragment_home)
                    navController?.navigate(R.id.FirstFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.action_manage -> {
                    title?.text = getString(R.string.fragment_family)
                    navController?.navigate(R.id.SecondFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.action_mode -> {
                    title?.text = getString(R.string.fragment_mode)
                    navController?.navigate(R.id.ThirdFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.action_log -> {
                    title?.text = getString(R.string.fragment_log)
                    navController?.navigate(R.id.FourthFragment)
                    return@setOnItemSelectedListener true
                }
                else -> {
                    super.onOptionsItemSelected(item)
                }
            }
        }
    }
}