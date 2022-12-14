package com.cookandroid.ch_12

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cookandroid.ch_12.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    class MyFragmentPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity){
        val fragments: List<Fragment>
        init {
            fragments= listOf(OneFragment(), TwoFragment(), ThreeFragment())
        }
        override fun getItemCount(): Int = fragments.size

        override fun createFragment(position: Int): Fragment = fragments[position]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val tabLayout = binding.tabs

        val tab1 : TabLayout.Tab = tabLayout.newTab()
        tab1.text="Tab1"
        tabLayout.addTab(tab1)
        val tab2 : TabLayout.Tab = tabLayout.newTab()
        tab2.text="Tab2"
        tabLayout.addTab(tab1)
        val tab3 : TabLayout.Tab = tabLayout.newTab()
        tab3.text="Tab3"
        tabLayout.addTab(tab3)
    }
}