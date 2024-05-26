package com.rc.android.homework.ui

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.rc.android.homework.R
import com.rc.android.homework.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setTitle(R.string.app_name)

        val drawerToggle = ActionBarDrawerToggle(this,
            binding.navigationDrawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        binding.navigationDrawerLayout.addDrawerListener(drawerToggle)

        val navHostController = (supportFragmentManager.findFragmentById(R.id.containerMainActivity) as NavHostFragment).navController
        binding.navigationView.setupWithNavController(navHostController)

        if (savedInstanceState == null){
        }
    }

}