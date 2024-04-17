package com.rc.android.homework.ui

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.rc.android.homework.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.app_name)

        val drawerToggle = ActionBarDrawerToggle(this,
            navigationDrawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        navigationDrawerLayout.addDrawerListener(drawerToggle)

        val navHostController = (supportFragmentManager.findFragmentById(R.id.containerMainActivity) as NavHostFragment).navController
        navigationView.setupWithNavController(navHostController)

        if (savedInstanceState == null){
        }
    }

}