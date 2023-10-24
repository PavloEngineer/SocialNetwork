package com.shpp.application.level_4.presentation.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.shpp.application.R
import com.shpp.application.databinding.FragmentContactDetailsBinding
import com.shpp.application.level_4.presentation.utils.extensions.downloadAndPutPhoto

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
            avatar.downloadAndPutPhoto(navigationArgs.photoAddress)
            textViewName.text = navigationArgs.name
            textCareer.text = navigationArgs.career
            textAddress.text = navigationArgs.address
        }
    }

    private fun startContactsFragment() {
        val direction =
            DetailsContactFragmentDirections.actionDetailsContactFragmentToViewPagerFragment()
        findNavController().navigate(direction)
    }
}