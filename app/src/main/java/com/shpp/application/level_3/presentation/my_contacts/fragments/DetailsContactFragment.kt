package com.shpp.application.level_3.presentation.my_contacts.fragments

import android.os.Build
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.shpp.application.R
import com.shpp.application.databinding.FragmentContactDetailsBinding
import com.shpp.application.level_3.presentation.my_contacts.BaseFragment
import com.shpp.application.level_3.presentation.utils.extensions.downloadAndPutPhoto

class DetailsContactFragment: BaseFragment() {

    private lateinit var binding: FragmentContactDetailsBinding

    private val navigationArgs: DetailsContactFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
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
        with (binding) {
            avatar.downloadAndPutPhoto(navigationArgs.photoAddress)
            textName.text = navigationArgs.name
            textCareer.text = navigationArgs.career
            textAddress.text = navigationArgs.address
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = NavHostFragment.findNavController(this)


        requireActivity().onBackPressedDispatcher.addCallback(this) {
            navController.popBackStack()
        }
    }

    private fun addBacklineListener() {
        binding.baselineBack.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_detailsContactFragment_to_myContactsFragment)
        }
    }
}