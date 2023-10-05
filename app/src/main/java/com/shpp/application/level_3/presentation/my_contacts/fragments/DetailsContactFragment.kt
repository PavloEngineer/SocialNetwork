package com.shpp.application.level_3.presentation.my_contacts.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.shpp.application.R
import com.shpp.application.databinding.FragmentContactDetailsBinding
import com.shpp.application.level_3.presentation.my_contacts.BaseFragment

class DetailsContactFragment: BaseFragment() {

    private lateinit var binding: FragmentContactDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactDetailsBinding.inflate(inflater, container, false)
        addBacklineListener()
        return binding.root
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