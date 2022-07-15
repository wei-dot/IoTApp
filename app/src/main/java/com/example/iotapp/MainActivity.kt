package com.example.iotapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.iotapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.inflateMenu(R.menu.menu_main)
        val tv1: TextView = findViewById(R.id.toolbar_title)
        tv1.text = getString(R.string.fragment_home)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_Layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val notLoginPageButtonLogin : ImageButton = findViewById(R.id.button_login)
        notLoginPageButtonLogin.setOnClickListener{
            val switchToLoginPage : Intent = Intent(this,LoginActivity::class.java)
            startActivity(switchToLoginPage)
        }
        val notLoginPageButtonSignup : ImageButton = findViewById(R.id.button_signup)
        notLoginPageButtonSignup.setOnClickListener{
            val switchToSignupPage : Intent = Intent(this,SignupActivity::class.java)
            startActivity(switchToSignupPage)
        }

        notLoginPageButtonSignup.setOnClickListener{
            val switchToSignupPage : Intent = Intent(this,SignupActivity::class.java)
            startActivity(switchToSignupPage)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

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

    fun onHomeClick(item: MenuItem) {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        navController.navigate(R.id.FirstFragment)
        val tv1: TextView = findViewById(R.id.toolbar_title)
        tv1.text = getString(R.string.fragment_home)
    }

    fun onFamilyClick(item: MenuItem) {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        navController.navigate(R.id.SecondFragment)
        val tv1: TextView = findViewById(R.id.toolbar_title)
        tv1.text = getString(R.string.fragment_family)
    }

}