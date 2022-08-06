package com.example.iotapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.iotapp.api.IotApi
import com.example.iotapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var isLogin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isLogin = IotApi.globalVar
        Toast.makeText(this, "isLogin: $isLogin", Toast.LENGTH_SHORT).show()
        switchSideBarContent(isLogin)
        val navView: BottomNavigationView = binding.appBarMain.bottomNavigation
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val toolbar = binding.appBarMain.toolbar
        val drawerLayout: DrawerLayout = binding.drawerLayout

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_family,
                R.id.navigation_mode,
                R.id.navigation_log,
                R.id.navigation_notLogin
            ), drawerLayout
        )
        if (!isLogin) {
            navController.navigate(R.id.navigation_notLogin)
            navView.setOnItemReselectedListener {
                navController.navigate(R.id.navigation_notLogin)
            }
        }
        else{
            navView.setupWithNavController(navController)
        }

        toolbar.setupWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, _, _ ->
            toolbar.setNavigationIcon(R.drawable.ic_navigation_icon)
        }
        binding.loginPage.btnLogin.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            finish()
            startActivity(intent)
        }
        binding.loginPage.btnSignup.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            intent.putExtra("Login", "Signup")
            startActivity(intent)
        }
        binding.loginPage.btnBack.setOnClickListener {
            drawerLayout.close()
        }
        binding.profilePage.btnBack.setOnClickListener {
            drawerLayout.close()
        }
        binding.profilePage.btnLogout.setOnClickListener {
            binding.loading?.isVisible = true
            binding.profilePage.btnLogout.isEnabled = false
            IotApi().logout(this)
            Handler(Looper.getMainLooper()).postDelayed({
                // Your Code
                binding.loading?.isVisible = false
                finish()
                startActivity(intent)
            }, 1000)

        }
        binding.profilePage.btnSet?.setOnClickListener{
            val intent = Intent(this, AccountActivity::class.java)
            intent.putExtra("Login", "SetPassword")
            startActivity(intent)
        }

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