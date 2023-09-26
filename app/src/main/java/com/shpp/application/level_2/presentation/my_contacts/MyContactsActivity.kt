package com.shpp.application.level_2.presentation.my_contacts

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.shpp.application.R
import com.shpp.application.databinding.MyContactsActivityBinding
import com.shpp.application.level_2.data.model.User
import com.shpp.application.level_2.presentation.callBacks.SwipeToDeleteCallback
import com.shpp.application.level_2.presentation.my_contacts.adapter.UsersAdapter
import com.shpp.application.level_2.presentation.my_contacts.add_contact.ContactDialog
import com.shpp.application.level_2.presentation.my_contacts.interfaces.MyContactsAdapterListener
import com.shpp.application.level_2.utils.Constants.ZERO_POSITION

class MyContactsActivity : AppCompatActivity() {    //todo base activity/fragments

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
                        showSnackBarWithUndo()
                    }

                }
        )
    }

    private val alertDialog: ContactDialog by lazy {
        ContactDialog(viewModel, binding, layoutInflater, this)
    }

    private val viewModel: MyContactsViewModel by viewModels()

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {    //todo decompose
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerUsers.layoutManager = layoutManager
        binding.recyclerUsers.adapter = adapter
        addVisibleButtonScrollListener()

        resultLauncher =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    alertDialog.downloadImage(result)
                }

        binding.buttonScroll.setOnClickListener {
            binding.recyclerUsers.smoothScrollToPosition(ZERO_POSITION)
        }

        binding.buttonAddContacts.setOnClickListener {
            alertDialog.show()
        }

        alertDialog.bindingAdd.buttonAddPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            resultLauncher.launch(intent)
        }

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(
            onSwiped = { position -> viewModel.deleteUserByPosition(position) }
        ))
        itemTouchHelper.attachToRecyclerView(binding.recyclerUsers)
    }

    override fun onStart() {
        super.onStart()
        viewModel.users.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun addVisibleButtonScrollListener() {
        binding.recyclerUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // if the recycler view is scrolled above hide the button
                if (dy > 10 && binding.buttonScroll.visibility == View.VISIBLE) {       //todo extension
                    binding.buttonScroll.visibility = View.INVISIBLE
                }

                // if the recycler view is scrolled above show the button
                if (dy < -10 && binding.buttonScroll.visibility == View.INVISIBLE) {
                    binding.buttonScroll.visibility = View.VISIBLE
                }

                // if the recycler view is at the first item always show the button
                if (!recyclerView.canScrollVertically(-1)) {
                    binding.buttonScroll.visibility = View.VISIBLE
                }
            }
        })
    }

    fun showSnackBarWithUndo() {
        val snackBar = Snackbar.make(
            /* view = */ binding.root,
            /* resId = */ R.string.snackbar_removed,
            /* duration = */ 5000
        )
        snackBar.setAction(R.string.snackbar_undo) {
            viewModel.restoreLastDeletedUser()
        }
        snackBar.show()
    }


}