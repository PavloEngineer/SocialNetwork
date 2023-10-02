package com.shpp.application.level_2.presentation.my_contacts

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shpp.application.R
import com.shpp.application.databinding.MyContactsActivityBinding
import com.shpp.application.level_2.data.model.User
import com.shpp.application.level_2.presentation.callBacks.SwipeToDeleteCallback
import com.shpp.application.level_2.presentation.my_contacts.adapter.UsersAdapter
import com.shpp.application.level_2.presentation.my_contacts.add_contact.ContactDialog
import com.shpp.application.level_2.presentation.my_contacts.interfaces.MyContactsAdapterListener
import com.shpp.application.level_2.utils.Constants.ZERO_POSITION

class MyContactsActivity : BaseActivity() {

    private val binding: MyContactsActivityBinding by lazy {
        MyContactsActivityBinding.inflate(layoutInflater)
    }

    private val adapter: UsersAdapter by lazy {
        UsersAdapter(
                listener = object : MyContactsAdapterListener {
                    override fun onClick(contact: User) {
                        Toast.makeText(this@MyContactsActivity, "Click on contact item", Toast.LENGTH_SHORT).show()
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRecyclerView()
        addVisibleButtonScrollListener()
        addScrollClickedListener()
        addListenerAddContact()
    }

    private fun addScrollClickedListener() {
        binding.buttonScroll.setOnClickListener {
            binding.recyclerUsers.smoothScrollToPosition(ZERO_POSITION)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.users.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerUsers.layoutManager = layoutManager
        binding.recyclerUsers.adapter = adapter
    }

    private fun addListenerAddContact() {
        binding.buttonAddContacts.setOnClickListener {
            val dialogAddUser = ContactDialog.newInstance()
            dialogAddUser.show(supportFragmentManager, "ContactDialog")
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