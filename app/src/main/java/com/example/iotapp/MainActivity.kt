package com.example.iotapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.iotapp.api.SessionManager
import com.example.iotapp.api.UserInfo
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
        Log.d("userinfo", (intent.getSerializableExtra("userInfo") != null).toString())
        if (intent.getSerializableExtra("userInfo") != null) {
            val userinfo = intent.getSerializableExtra("userInfo") as UserInfo
            binding.profilePage.username.text = userinfo.username
            isLogin = true
        }
        isLogin = SessionManager(this).fetchAuthToken() != null
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
                R.id.navigation_notLogin,
                R.id.navigation_family_in
            ), drawerLayout
        )


        if (!isLogin) {
            binding.appBarMain.btnNotification.isVisible = false
            navController.navigate(R.id.navigation_notLogin)
            navView.setOnItemSelectedListener {
                when (it.title.toString()) {
                    "居家狀態" -> toolbar.title = "居家狀態"
                    "家庭管理" -> toolbar.title = "家庭管理"
                    "組合鍵設置" -> toolbar.title = "組合鍵設置"
                    "設備日誌" -> toolbar.title = "設備日誌"
                }
                return@setOnItemSelectedListener true
            }
        } else {
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
            finish()
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
            IotApi.logout(this, SessionManager(this))
            SessionManager(this).clearAuthToken()
            binding.loading?.isVisible = false
            finish()
            startActivity(intent)
            Handler(Looper.getMainLooper()).postDelayed({
            }, 500)

        }
        binding.profilePage.btnSet?.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            intent.putExtra("Login", "SetPassword")
            finish()
            startActivity(intent)
        }

        binding.appBarMain.btnNotification.setOnClickListener { v ->
            initPopWindow(v)
        }
//        val temp :StringRequest=



    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerVisible(GravityCompat.START)) {
            binding.drawerLayout.close()
        } else if (R.id.navigation_notLogin == findNavController(R.id.nav_host_fragment_activity_main).currentDestination?.id) {
            //防止在未登入頁面觸發back鍵
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


    @SuppressLint("ClickableViewAccessibility", "InflateParams")
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
            popWindow.animationStyle = R.style.notificationAnimationPopup
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