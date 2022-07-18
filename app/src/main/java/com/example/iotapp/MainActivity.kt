package com.example.iotapp

import android.animation.AnimatorInflater
import android.app.Dialog
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.iotapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.NonCancellable.start

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var isLogin: Boolean = true
    private var isOpeningNotificationBar: Boolean = false

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
            val switchToLoginPage = Intent(this, LoginActivity::class.java)
            startActivity(switchToLoginPage)
        }
        binding.loginPage.btnSignup.setOnClickListener {
            val switchToSignupPage = Intent(this, SignupActivity::class.java)
            startActivity(switchToSignupPage)
        }
        binding.loginPage.btnBack.setOnClickListener {
            drawerLayout.close()
        }
        binding.profilePage.btnBack.setOnClickListener {
            drawerLayout.close()
        }
        binding.profilePage.btnLogout.setOnClickListener {
            isLogin = false
            switchSideBarContent(isLogin)
        }
        switchSideBarContent(isLogin)

        if (!isOpeningNotificationBar) {
            binding.notificationBar.notificationBar.isVisible = false
        }
        binding.appBarMain.btnNotification.setOnClickListener {
            if (!isOpeningNotificationBar) {
                binding.notificationBar.notificationBar.isVisible = true
                binding.notificationBar.notificationBar.let { this.openingAnimation(it) }
                isOpeningNotificationBar = true
            } else {
                binding.notificationBar.notificationBar.isVisible = false
                binding.notificationBar.notificationBar.let { this.closingAnimation(it) }
                isOpeningNotificationBar = false
            }
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

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerVisible(GravityCompat.START)) {
            binding.drawerLayout.close()
        } else {
            super.onBackPressed()
        }
    }

    private fun switchSideBarContent(isLogin: Boolean) {
        if (isLogin) {
            binding.notLogin.isVisible = false
            binding.hasLogin.isVisible = true
        } else {
            binding.notLogin.isVisible = true
            binding.hasLogin.isVisible = false
        }
    }

    fun openingAnimation(view: View) {
        val animation: Animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.opening_anim)
        view.startAnimation(animation)
    }

    fun closingAnimation(view: View) {
        val animation: Animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.closing_anim)
        view.startAnimation(animation)
    }

}