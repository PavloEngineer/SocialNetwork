package com.shpp.application.level_4.presentation.fragments.viewPager_fragment.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.shpp.application.level_4.presentation.fragments.MyProfileFragment
import com.shpp.application.level_4.presentation.fragments.my_contacts.MyContactsFragment

class ViewPagerAdapter(mainFragment: Fragment): FragmentStateAdapter(mainFragment) {

    private val fragmentsList = listOf<Fragment>(MyProfileFragment(), MyContactsFragment())
    override fun getItemCount(): Int = fragmentsList.size

    override fun createFragment(position: Int): Fragment {
        return  fragmentsList[position]
    }
}