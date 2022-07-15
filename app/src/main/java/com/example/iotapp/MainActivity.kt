package com.example.iotapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
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

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private  var navController:NavController? = null

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
            findViewById(R.id.toolbar),
            R.string.drawer_open,
            R.string.drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navController = findNavController(R.id.nav_host_fragment_content_main)

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
        navController?.navigate(R.id.FirstFragment)
        val tv1: TextView = findViewById(R.id.toolbar_title)
        tv1.text = getString(R.string.fragment_home)
    }

    fun onFamilyClick(item: MenuItem) {
        navController?.navigate(R.id.SecondFragment)
        val tv1: TextView = findViewById(R.id.toolbar_title)
        tv1.text = getString(R.string.fragment_family)
    }

    fun onClickLogin(view: View) {
        navController?.navigate(R.id.SecondFragment)
        val tv1: TextView = findViewById(R.id.toolbar_title)
        tv1.text = getString(R.string.fragment_family)
    }

    fun onModeClick(item: MenuItem) {
        navController?.navigate(R.id.ThirdFragment)
        val tv1: TextView = findViewById(R.id.toolbar_title)
        tv1.text = getString(R.string.fragment_mode)
    }
    fun onLogClick(item: MenuItem) {
        navController?.navigate(R.id.FourthFragment)
        val tv1: TextView = findViewById(R.id.toolbar_title)
        tv1.text = getString(R.string.fragment_log)
    }
}