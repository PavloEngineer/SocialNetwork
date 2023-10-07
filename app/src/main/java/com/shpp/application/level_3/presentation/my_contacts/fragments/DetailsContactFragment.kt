package com.shpp.application.level_3.presentation.my_contacts.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.shpp.application.R
import com.shpp.application.databinding.FragmentContactDetailsBinding
import com.shpp.application.level_3.App
import com.shpp.application.level_3.data.enum.UserInfo
import com.shpp.application.level_3.presentation.my_contacts.BaseFragment
import com.shpp.application.level_3.presentation.utils.extensions.downloadAndPutPhoto

class DetailsContactFragment : BaseFragment() {

    private lateinit var binding: FragmentContactDetailsBinding

    private val navigationArgs: DetailsContactFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactDetailsBinding.inflate(inflater, container, false)
        fillUserProfile()
        addBacklineListener()
        return binding.root
    }

    private fun fillUserProfile() {
        with(binding) {
            if (App.isFeatureNavigationEnable) {
                avatar.downloadAndPutPhoto(navigationArgs.photoAddress)
                textName.text = navigationArgs.name
                textCareer.text = navigationArgs.career
                textAddress.text = navigationArgs.address
            } else {
                arguments?.let {
                    textName.text = it.getString(UserInfo.NAME.key)
                    textCareer.text = it.getString(UserInfo.CAREER.key)
                    textAddress.text = it.getString(UserInfo.ADDRESS.key)
                    avatar.downloadAndPutPhoto(it.getString(UserInfo.PHOTO_ADDRESS.key))
                }
            }
        }
    }

    private fun addBacklineListener() {
        binding.baselineBack.setOnClickListener {
            startContactsFragment()
        }
    }

    private fun startContactsFragment() {
        if (App.isFeatureNavigationEnable) {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_detailsContactFragment_to_myContactsFragment)
        } else {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, MyContactsFragment(), "activityToContactsFragment")
                .commit()
        }
    }
}