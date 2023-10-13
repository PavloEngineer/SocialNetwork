package com.shpp.application.level_3.presentation.my_contacts.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shpp.application.R
import com.shpp.application.databinding.FragmentMyContactsBinding
import com.shpp.application.level_3.App
import com.shpp.application.level_3.data.enum.UserInfo
import com.shpp.application.level_3.data.model.User
import com.shpp.application.level_3.presentation.callBacks.SwipeToDeleteCallback
import com.shpp.application.level_3.presentation.my_contacts.BaseFragment
import com.shpp.application.level_3.presentation.my_contacts.FunctionForSnack
import com.shpp.application.level_3.presentation.my_contacts.adapter.UsersAdapter
import com.shpp.application.level_3.presentation.my_contacts.add_contact.ContactDialog
import com.shpp.application.level_3.presentation.my_contacts.interfaces.MyContactsAdapterListener
import com.shpp.application.level_3.utils.Constants

class MyContactsFragment : BaseFragment() {

    private lateinit var binding: FragmentMyContactsBinding // TODO: by lazy

    private val adapter: UsersAdapter by lazy {
        UsersAdapter(
            listener = object : MyContactsAdapterListener {
                override fun onClick(contact: User, extras: FragmentNavigator.Extras) {
                    startDetailFragment(contact, extras)
                }

                override fun onDeleteClick(contact: User) {
                    viewModel.deleteUser(contact)
                    showSnackBar(
                        "Remove!", R.string.snackbar_undo
                    ) { viewModel.restoreLastDeletedUser() }
                }

                override fun addSwipeLeftHelper() { // TODO: delete
//                    val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback { position ->
//                        viewModel.deleteUserByPosition(position)
//                        showSnackBar(
//                            "Remove!", R.string.snackbar_undo
//                        ) { viewModel.restoreLastDeletedUser() }
//                    })
//                    itemTouchHelper.attachToRecyclerView(binding.recyclerUsers)
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
        with(binding.recyclerUsers) {
            val layoutManager = LinearLayoutManager(requireContext())
            this.layoutManager = layoutManager
            this.adapter = this@MyContactsFragment.adapter

            val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback { position ->
                viewModel.deleteUserByPosition(position)
                showSnackBar(
                    "Remove!", R.string.snackbar_undo
                ) { viewModel.restoreLastDeletedUser() }
            })
            itemTouchHelper.attachToRecyclerView(this)
        }
    }

    private fun addListenerAddContact() {
        binding.buttonAddContacts.setOnClickListener {
            val dialogAddUser = ContactDialog.newInstance()
            dialogAddUser.show(parentFragmentManager, "DialogFragment") // TODO: to constants
        }
    }

    private fun addScrollClickedListener() {
        binding.buttonScroll.setOnClickListener {
            binding.recyclerUsers.smoothScrollToPosition(Constants.ZERO_POSITION)
        }
    }

    private fun addVisibleButtonScrollListener() {// TODO: with
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

    private fun startDetailFragment(contact: User, extras: FragmentNavigator.Extras) {
        if (App.isFeatureNavigationEnable) {
            val action =
                MyContactsFragmentDirections.actionMyContactsFragmentToDetailsContactFragment(
                    contact.name,
                    contact.job,
                    contact.address,
                    contact.photo ?: ""
                )
            Navigation.findNavController(binding.root).navigate(action, extras)
        } else {
            val detailsFragment = DetailsContactFragment()
            setArgumentsForDetails(detailsFragment, contact)
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, detailsFragment)
                .addToBackStack("ToDetailsFragment") // TODO:
                .commit()
        }
    }

    private fun setArgumentsForDetails(detailsFragment: DetailsContactFragment, contact: User) {
        val args: Bundle = Bundle()
        args.putString(UserInfo.NAME.key, contact.name)
        args.putString(UserInfo.CAREER.key, contact.job)
        args.putString(UserInfo.ADDRESS.key, contact.address)
        args.putString(UserInfo.PHOTO_ADDRESS.key, contact.photo)
        detailsFragment.arguments = args
    }
}