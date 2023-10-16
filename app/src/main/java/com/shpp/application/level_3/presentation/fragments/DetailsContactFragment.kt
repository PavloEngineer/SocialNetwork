package com.shpp.application.level_3.presentation.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.shpp.application.R
import com.shpp.application.databinding.FragmentContactDetailsBinding
import com.shpp.application.level_3.App
import com.shpp.application.level_3.data.enum.UserInfo
import com.shpp.application.level_3.presentation.fragments.my_contacts.MyContactsFragment
import com.shpp.application.level_3.presentation.utils.extensions.downloadAndPutPhoto
import com.shpp.application.level_3.utils.Constants.TRANSACTION_TO_CONTACTS

class DetailsContactFragment :
    BaseFragment<FragmentContactDetailsBinding>(FragmentContactDetailsBinding::inflate) {

    private val navigationArgs: DetailsContactFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(R.transition.custom_move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillUserProfile()
    }

    override fun setListeners() {
        binding.buttonBack.setOnClickListener {
            startContactsFragment()
        }
    }

    private fun fillUserProfile() {
        with(binding) {
            if (App.isFeatureNavigationEnable) {
                avatar.downloadAndPutPhoto(navigationArgs.photoAddress)
                textViewName.text = navigationArgs.name
                textCareer.text = navigationArgs.career
                textAddress.text = navigationArgs.address
            } else {
                arguments?.let {
                    textViewName.text = it.getString(UserInfo.NAME.key)
                    textCareer.text = it.getString(UserInfo.CAREER.key)
                    textAddress.text = it.getString(UserInfo.ADDRESS.key)
                    avatar.downloadAndPutPhoto(it.getString(UserInfo.PHOTO_ADDRESS.key))
                }
            }
        }
    }

    private fun startContactsFragment() {
        if (App.isFeatureNavigationEnable) {
            val direction =
                DetailsContactFragmentDirections.actionDetailsContactFragmentToMyContactsFragment()
            findNavController().navigate(direction)
        } else {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, MyContactsFragment(), TRANSACTION_TO_CONTACTS)
                .commit()
        }
    }
}