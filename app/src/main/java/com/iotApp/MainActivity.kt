package com.iotApp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.iotApp.api.IotApi
import com.iotApp.api.SessionManager
import com.iotApp.api.UserInfo
import com.iotApp.databinding.ActivityMainBinding
import com.iotApp.ui.family.FamilyFragment
import com.iotApp.ui.home.HomeFragment
import com.iotApp.ui.log.LogFragment
import com.iotApp.ui.mode.ModeFragment
import com.iotApp.ui.notLogin.NotLoginFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**判斷使用者是否登入並修改側邊攔 */
        val userinfo: UserInfo? = SessionManager(this).fetchUserInfo()
        if (userinfo != null) {
            binding.profilePage.username.text = userinfo.username
            switchSideBarContent(true)
        } else {
            switchSideBarContent(false)
            binding.appBarMain.btnNotification.isVisible = false
        }


        viewPager()
        buttonListener()
        IotApi.getFamily(this, binding.profilePage, SessionManager(this))
    }


//override fun onSupportNavigateUp(): Boolean {
//    val navController = findNavController(R.id.nav_host_fragment_activity_main)
//    return navController.navigateUp(appBarConfiguration)
//            || super.onSupportNavigateUp()
//}

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
        }

    }

    private fun backgroundAlpha(f: Float) {
        val lp = window.attributes
        lp.alpha = f
        window.attributes = lp
    }

    private fun buttonListener() {
        binding.loginPage.btnBack.setOnClickListener {
            binding.drawerLayout.close()
        }
        binding.profilePage.btnBack.setOnClickListener {
            binding.drawerLayout.close()
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

        binding.profilePage.btnLogout.setOnClickListener {
            binding.loading.isVisible = true
            binding.profilePage.btnLogout.isEnabled = false
            IotApi.logout(this, SessionManager(this))
            SessionManager(this).logout()
            Handler(Looper.getMainLooper()).postDelayed({
                binding.loading.isVisible = false
                finish()
                startActivity(intent)
            }, 500)

        }
        binding.profilePage.btnSet.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            intent.putExtra("Login", "SetPassword")
            finish()
            startActivity(intent)
        }
        binding.profilePage.addFamilyItem?.setOnClickListener {
            val intent = Intent(this, FamilyMemberActivity::class.java)
            intent.putExtra("FamilyMemberActivity", "addFamily")
            startActivity(intent)
        }

        binding.appBarMain.btnNotification.setOnClickListener { v ->
            initPopWindow(v)
        }
    }

    private fun viewPager() {
        val navView: BottomNavigationView = binding.appBarMain.bottomNavigation
        val toolbar = binding.appBarMain.toolbar
        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = binding.appBarMain.content?.viewPager2!!

        // The pager adapter, which provides the pages to the view pager widget.
        setSupportActionBar(toolbar)
        supportActionBar?.title = "居家狀態"

        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter
        viewPager.setCurrentItem(200, false)
        viewPager.setPageTransformer(ZoomOutPageTransformer())
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(position%4){
                    0 -> {
                        supportActionBar?.title = "居家狀態"
                        navView.menu.getItem(0).isChecked = true
                    }
                    1 -> {
                        supportActionBar!!.title = "家庭管理"
                        navView.menu.getItem(1).isChecked = true
                    }
                    2 -> {
                        supportActionBar!!.title = "組合鍵設置"
                        navView.menu.getItem(2).isChecked = true
                    }
                    3 -> {
                        supportActionBar!!.title =  "設備日誌"
                        navView.menu.getItem(3).isChecked = true
                    }
                    else -> {
                        supportActionBar!!.title = "居家狀態"
                        navView.menu.getItem(0).isChecked = true
                    }

                }

            }
        })
        navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    supportActionBar!!.title = "居家狀態"
                    viewPager.setCurrentItem(0, true)
                }
                R.id.navigation_family_in -> {
                    supportActionBar!!.title = "家庭管理"
                    viewPager.setCurrentItem(1, true)
                }
                R.id.navigation_mode -> {
                    supportActionBar!!.title = "組合鍵設置"
                    viewPager.setCurrentItem(2, true)
                }
                R.id.navigation_log -> {
                    supportActionBar!!.title = "設備日誌"
                    viewPager.setCurrentItem(3, true)
                }
            }
            return@setOnItemSelectedListener true

        }

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        toolbar.setNavigationIcon(R.drawable.ic_navigation_icon)
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

        override fun getItemCount(): Int {
            return 400
        }

        override fun createFragment(position: Int): Fragment {
            SessionManager(this@MainActivity).fetchUserInfo()
                ?: return NotLoginFragment()
            return when (position%4) {
                0 -> HomeFragment()
                1 -> FamilyFragment()
                2 -> ModeFragment()
                3 -> LogFragment()
                else -> HomeFragment()
            }
        }
    }


}