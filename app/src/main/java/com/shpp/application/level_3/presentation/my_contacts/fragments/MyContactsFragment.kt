package com.shpp.application.level_3.presentation.my_contacts.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shpp.application.R
import com.shpp.application.databinding.FragmentMyContactsBinding
import com.shpp.application.level_3.data.model.User
import com.shpp.application.level_3.presentation.callBacks.SwipeToDeleteCallback
import com.shpp.application.level_3.presentation.my_contacts.BaseFragment
import com.shpp.application.level_3.presentation.my_contacts.adapter.UsersAdapter
import com.shpp.application.level_3.presentation.my_contacts.add_contact.ContactDialog
import com.shpp.application.level_3.presentation.my_contacts.interfaces.MyContactsAdapterListener
import com.shpp.application.level_3.utils.Constants

class MyContactsFragment: BaseFragment() {

    private lateinit var binding: FragmentMyContactsBinding

    private val adapter: UsersAdapter by lazy {
        UsersAdapter(
            listener = object : MyContactsAdapterListener {
                override fun onClick(contact: User, extras: FragmentNavigator.Extras) {
                    val action = MyContactsFragmentDirections.actionMyContactsFragmentToDetailsContactFragment(
                        contact.name,
                        contact.job,
                        contact.address,
                        contact.photo?: ""
                    )
                    Navigation.findNavController(binding.root).navigate(action, extras)
                }

                override fun onDeleteClick(contact: User) {
                    viewModel.deleteUser(contact)
                    showSnackBar("Remove!", R.string.snackbar_undo
                    ) { viewModel.restoreLastDeletedUser() }
                }

                override fun addSwipeLeftHelper() {
                    val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback { position ->
                        viewModel.deleteUserByPosition(position)
                        showSnackBar("Remove!", R.string.snackbar_undo
                        ) { viewModel.restoreLastDeletedUser() }
                    })
                    itemTouchHelper.attachToRecyclerView(binding.recyclerUsers)
                }

            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

         binding = FragmentMyContactsBinding.inflate(inflater, container, false)
        setupRecyclerView()
        addVisibleButtonScrollListener()
        addScrollClickedListener()
        addListenerAddContact()

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.users.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerUsers.layoutManager = layoutManager
        binding.recyclerUsers.adapter = adapter
    }

    private fun addListenerAddContact() {
        binding.buttonAddContacts.setOnClickListener {
            val dialogAddUser = ContactDialog.newInstance()
            dialogAddUser.show(parentFragmentManager, "DialogFragment")
        }
    }

    private fun addScrollClickedListener() {
        binding.buttonScroll.setOnClickListener {
            binding.recyclerUsers.smoothScrollToPosition(Constants.ZERO_POSITION)
        }
    }

    private fun addVisibleButtonScrollListener() {
        binding.recyclerUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // if the recycler view is scrolled above hide the button
                if (dy > 10 && binding.buttonScroll.visibility == View.VISIBLE) {
                    binding.buttonScroll.visibility = View.INVISIBLE
                }

                // if the recycler view is scrolled above show the button
                if (dy < -10 && binding.buttonScroll.visibility == View.INVISIBLE) {
                    binding.buttonScroll.visibility = View.VISIBLE
                }

                // if the recycler view is at the first item always show the button
                if (!recyclerView.canScrollVertically(-1)) {
                    binding.buttonScroll.visibility = View.INVISIBLE
                }
            }
        })
    }
}