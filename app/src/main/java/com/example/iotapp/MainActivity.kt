package com.example.iotapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.iotapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout

        val navView: BottomNavigationView = binding.appBarMain.bottomNavigation
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
//        監聽nav目的地變化後修改標題
        val title: TextView = findViewById(R.id.toolbar_title)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            title.text = navController.currentDestination?.label
        }
        toolbar.inflateMenu(R.menu.bottom_nav_menu)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val notLoginPageButtonLogin: ImageButton = findViewById(R.id.button_login)
        notLoginPageButtonLogin.setOnClickListener {
            val switchToLoginPage: Intent = Intent(this, LoginActivity::class.java)
            startActivity(switchToLoginPage)
        }
        val notLoginPageButtonSignup: ImageButton = findViewById(R.id.button_signup)
        notLoginPageButtonSignup.setOnClickListener {
            val switchToSignupPage: Intent = Intent(this, SignupActivity::class.java)
            startActivity(switchToSignupPage)
        }
        val notLoginPageButtonBack: ImageButton = findViewById(R.id.button_not_login_back)
        notLoginPageButtonBack.setOnClickListener {
            drawerLayout.close()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.navigation_home -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}