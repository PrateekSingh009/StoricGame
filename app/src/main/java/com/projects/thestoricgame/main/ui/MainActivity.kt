package com.projects.thestoricgame.main.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.projects.thestoricgame.R
import com.projects.thestoricgame.utils.extensions.addFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFragment()
    }

    private fun openFragment() {
        supportFragmentManager.addFragment(ChatsListFragment(), R.id.fragment_container)
    }
}