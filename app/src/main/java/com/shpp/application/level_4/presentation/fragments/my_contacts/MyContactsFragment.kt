package com.shpp.application.level_4.presentation.fragments.my_contacts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shpp.application.R
import com.shpp.application.databinding.FragmentMyContactsBinding
import com.shpp.application.level_4.data.model.User
import com.shpp.application.level_4.presentation.callBacks.SwipeToDeleteCallback
import com.shpp.application.level_4.presentation.fragments.BaseFragment
import com.shpp.application.level_4.presentation.fragments.viewPager_fragment.ViewPagerFragment
import com.shpp.application.level_4.presentation.fragments.my_contacts.adapter.UsersAdapter
import com.shpp.application.level_4.presentation.fragments.my_contacts.add_contact.ContactDialog
import com.shpp.application.level_4.presentation.fragments.my_contacts.model.ContactItem
import com.shpp.application.level_4.presentation.fragments.viewPager_fragment.ViewPagerFragmentDirections
import com.shpp.application.level_4.presentation.interfaces.ContactSelectionListener
import com.shpp.application.level_4.presentation.interfaces.MyContactsAdapterListener
import com.shpp.application.level_4.utils.Constants
import com.shpp.application.level_4.utils.Constants.ADD_USER_TAG
import com.shpp.application.level_4.utils.Constants.PROFILE_SCREEN

class MyContactsFragment :
    BaseFragment<FragmentMyContactsBinding>(FragmentMyContactsBinding::inflate) {

    private val viewModel: MyContactsViewModel by viewModels()

    private val adapter: UsersAdapter by lazy {
        UsersAdapter(
            listener = object : MyContactsAdapterListener {
                override fun onClick(contact: User, extras: FragmentNavigator.Extras) {
                    startDetailFragment(contact, extras)
                }

                override fun onDeleteClick(contact: User) {
                    viewModel.deleteUser(contact)
                    showSnackBar(
                        "Removed!", R.string.snackbar_undo
                    ) { viewModel.restoreLastDeletedUser() }
                }

                override fun onLongClick(contactItem: ContactItem) {
                    // Ваш код для взаємодії з режимом виділення
                    if (viewModel.selectionModeLiveData.value == true) {
                        // Якщо вже в режимі виділення, то переключайте стан виділення контакта
                        viewModel.toggle(contactItem)
                    } else {
                        // Інакше, увімкніть режим виділення та позначайте контакт
                        viewModel.enableSelectionMode()
                        viewModel.toggle(contactItem)
                    }
                }

                override fun onCheckClick(contact: User, isChecked: Boolean) {
                    viewModel.toggle(ContactItem(contact, isChecked))
                }
            },

            contactSelectionListener = object : ContactSelectionListener {
                override fun onContactSelectionChanged(contactItem: ContactItem) {
                   binding.buttonMultiDelete.visibility = when (binding.buttonMultiDelete.visibility) {
                       View.VISIBLE -> View.GONE
                       View.GONE -> View.VISIBLE
                       else -> {View.GONE}
                   }
                }

                override fun isCheck(user: User): Boolean {
                    return viewModel.isCheck(user)
                }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun setListeners() {
        addVisibleButtonScrollListener()
        addMultiDeleteListener()
        addScrollClickedListener()
        addListenerAddContact()
        addListenerBackToProfile()
    }

    private fun addMultiDeleteListener() {
        binding.buttonMultiDelete.setOnClickListener{
            viewModel.deleteSelectedContacts()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.users.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun addListenerBackToProfile() {
        binding.buttonBack.setOnClickListener {
            (parentFragment as ViewPagerFragment).switchToPage(PROFILE_SCREEN)
        }
    }

    private fun setupRecyclerView() {
        with(binding.recyclerUsers) {
            val layoutManager = LinearLayoutManager(requireContext())
            this.layoutManager = layoutManager
            this.adapter = this@MyContactsFragment.adapter
            addSnackBarBySwipe()
        }
    }

    private fun addSnackBarBySwipe() {
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback { position ->
            viewModel.deleteUserByPosition(position)
            showSnackBar(
                "Remove!", R.string.snackbar_undo
            ) { viewModel.restoreLastDeletedUser() }
        })
        itemTouchHelper.attachToRecyclerView(binding.recyclerUsers)
    }

    private fun addListenerAddContact() {
        binding.buttonAddContacts.setOnClickListener {
            val dialogAddUser = ContactDialog()
            dialogAddUser.show(parentFragmentManager, ADD_USER_TAG)
        }
    }

    private fun addScrollClickedListener() {
        binding.buttonScroll.setOnClickListener {
            binding.recyclerUsers.smoothScrollToPosition(Constants.ZERO_POSITION)
        }
    }

    private fun addVisibleButtonScrollListener() {
        with(binding) {
            recyclerUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    // if the recycler view is scrolled above hide the button
                    if (dy > 10 && buttonScroll.visibility == View.VISIBLE) {
                        buttonScroll.visibility = View.INVISIBLE
                    }

                    // if the recycler view is scrolled above show the button
                    if (dy < -10 && buttonScroll.visibility == View.INVISIBLE) {
                        buttonScroll.visibility = View.VISIBLE
                    }

                    // if the recycler view is at the first item always show the button
                    if (!recyclerView.canScrollVertically(-1)) {
                        buttonScroll.visibility = View.INVISIBLE
                    }
                }
            })
        }
    }

    private fun startDetailFragment(contact: User, extras: FragmentNavigator.Extras) {
        val direction = ViewPagerFragmentDirections.actionViewPagerFragmentToDetailsContactFragment(
            contact.name,
            contact.job,
            contact.address,
            contact.photo ?: ""
        )
        findNavController().navigate(direction, extras)
    }
}