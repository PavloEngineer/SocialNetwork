package com.shpp.application.level_4.presentation.fragments.viewPager_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.shpp.application.R
import com.shpp.application.databinding.FragmentViewpagerBinding
import com.shpp.application.level_4.presentation.fragments.DetailsContactFragment
import com.shpp.application.level_4.presentation.fragments.viewPager_fragment.adapter.ViewPagerAdapter
import com.shpp.application.level_4.utils.Constants.MY_CONTACTS_SCREEN
import com.shpp.application.level_4.utils.Constants.PROFILE_SCREEN

class ViewPagerFragment: Fragment() {

    private val binding: FragmentViewpagerBinding by lazy {
        FragmentViewpagerBinding .inflate(layoutInflater)
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.viewPager.adapter = ViewPagerAdapter(this@ViewPagerFragment)
        attachTabLayout()
        return binding.root
    }


    fun switchToPage(position: Int) {
        binding.viewPager.currentItem = position
    }

    private fun attachTabLayout() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when(position) {
                PROFILE_SCREEN -> resources.getString(R.string.profile_screen)
                MY_CONTACTS_SCREEN -> resources.getString(R.string.my_contacts_screen)
                else -> throw IllegalStateException("Unknown fragment!")
            }
        }.attach()
    }
}