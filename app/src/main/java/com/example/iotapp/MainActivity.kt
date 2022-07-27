package com.example.iotapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
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
    private var isLogin: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.appBarMain.bottomNavigation
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val toolbar = binding.appBarMain.toolbar
        val drawerLayout: DrawerLayout = binding.drawerLayout
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_family,
                R.id.navigation_mode,
                R.id.navigation_log
            ), drawerLayout
        )
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)
        navView.setupWithNavController(navController)
//        val toggle = ActionBarDrawerToggle(
//            this,
//            drawerLayout,
//            toolbar,
//            R.string.drawer_open,
//            R.string.drawer_close
//        )
//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()
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

        binding.appBarMain.btnNotification.setOnClickListener { v ->
            initPopWindow(v)
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


    @SuppressLint("ClickableViewAccessibility")
    private fun initPopWindow(v: View) {
        val view = LayoutInflater.from(this).inflate(R.layout.notification_window, null, false)
        val popWindow = PopupWindow(
            view,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )
        popWindow.setOnDismissListener {
            backgroundAlpha(1f)
        }
        if (popWindow.isShowing) {
            popWindow.dismiss()
        } else {
            popWindow.animationStyle = R.style.AnimationPopup
            popWindow.isTouchable = true
            popWindow.isOutsideTouchable = true
            backgroundAlpha(0.8f)
            popWindow.setTouchInterceptor { _, _ -> false }
            popWindow.setBackgroundDrawable(ColorDrawable(-0x00000))
            val locate = IntArray(2)
            v.getLocationOnScreen(locate)
            popWindow.showAtLocation(
                binding.appBarMain.btnNotification,
                Gravity.TOP, 0, locate[1] + 70
            )
            Toast.makeText(this, "X位置" + locate[0] + "\nY位置" + locate[1], Toast.LENGTH_SHORT).show()
        }

    }

    private fun backgroundAlpha(f: Float) {
        val lp = window.attributes
        lp.alpha = f
        window.attributes = lp
    }

}