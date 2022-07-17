package com.example.iotapp

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
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
    private var isLogin:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.appBarMain.bottomNavigation
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
//        監聽nav目的地變化後修改標題
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.appBarMain.toolbarTitle.text = navController.currentDestination?.label
        }
        val toolbar = binding.appBarMain.toolbar
        val drawerLayout: DrawerLayout = binding.drawerLayout
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
        toolbar.setNavigationIcon(R.drawable.ic_navigation_icon)
        binding.loginPage.btnLogin.setOnClickListener {
            val switchToLoginPage: Intent = Intent(this, LoginActivity::class.java)
            startActivity(switchToLoginPage)
        }
        binding.loginPage.btnSignup.setOnClickListener {
            val switchToSignupPage: Intent = Intent(this, SignupActivity::class.java)
            startActivity(switchToSignupPage)
        }
        binding.loginPage.btnBack.setOnClickListener {
            drawerLayout.close()
        }
        findViewById<ImageButton>(R.id.btn_logout).setOnClickListener {
            isLogin = false
            switchSideBarContent(isLogin)
        }
        switchSideBarContent(isLogin)
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

    private fun switchSideBarContent(isLogin:Boolean){
        if (isLogin){
            binding.loginPage?.btnLogin?.visibility = View.GONE
            binding.loginPage?.btnSignup?.visibility = View.GONE
            binding.loginPage?.textNotLogin?.visibility = View.GONE
            binding.loginPage?.avatarArea?.visibility = View.VISIBLE
            binding.loginPage?.username?.visibility = View.VISIBLE
            binding.loginPage?.profileDetailPlace?.visibility = View.VISIBLE
            binding.loginPage?.btnLogout?.visibility = View.VISIBLE
        }
        else{
            binding.loginPage?.btnLogin?.visibility = View.VISIBLE
            binding.loginPage?.btnSignup?.visibility = View.VISIBLE
            binding.loginPage?.textNotLogin?.visibility = View.VISIBLE
            binding.loginPage?.avatarArea?.visibility = View.GONE
            binding.loginPage?.username?.visibility = View.GONE
            binding.loginPage?.profileDetailPlace?.visibility = View.GONE
            binding.loginPage?.btnLogout?.visibility = View.GONE
        }
    }
}