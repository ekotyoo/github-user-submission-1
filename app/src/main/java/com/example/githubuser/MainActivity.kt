package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mFragmentManager = supportFragmentManager
        val mListUserFragment = ListUserFragment()
        val fragment = mFragmentManager.findFragmentByTag(ListUserFragment::class.java.simpleName)

        if (fragment !is ListUserFragment) {
            mFragmentManager.beginTransaction()
                .add(R.id.fragment_container, mListUserFragment, ListUserFragment::class.java.simpleName)
                .commit()
        }
    }
}