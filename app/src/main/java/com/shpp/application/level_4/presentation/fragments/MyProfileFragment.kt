package com.shpp.application.level_4.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.shpp.application.databinding.FragmentMyProfileBinding
import com.shpp.application.level_4.App
import com.shpp.application.level_4.presentation.activities.AuthActivity
import com.shpp.application.level_4.presentation.fragments.viewPager_fragment.ViewPagerFragment
import com.shpp.application.level_4.utils.Constants.MY_CONTACTS_SCREEN

class MyProfileFragment : BaseFragment<FragmentMyProfileBinding>(FragmentMyProfileBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeTextNameView()
    }

    override fun setListeners() {
        binding.buttonLogOut.setOnClickListener { startAuthActivity() }
        binding.buttonViewContact.setOnClickListener {
            (parentFragment as ViewPagerFragment).switchToPage(MY_CONTACTS_SCREEN)
        }
    }

    private fun startAuthActivity() {
        val intent = Intent(requireContext(), AuthActivity::class.java)
        startActivity(intent)
    }

    private fun changeTextNameView() {
        val email = App.email
        if (email != null) {
            binding.textName.text = parseEmail(email)
        }
    }

    private fun parseEmail(email: String): CharSequence {
        val fullName = StringBuilder(email.substring(0, email.indexOf("@")))
        fullName[0] = fullName[0].uppercaseChar()

        if (fullName.indexOf('.') != -1) {
            fullName[fullName.indexOf('.')] = ' '
            fullName[fullName.indexOf(' ') + 1] = fullName[fullName.indexOf(' ') + 1].uppercaseChar()
        }

        return fullName
    }
}
