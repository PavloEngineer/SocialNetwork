package com.shpp.application.level_2.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shpp.application.R
import com.shpp.application.databinding.AddUserDialogBinding
import com.shpp.application.databinding.MyContactsActivityBinding
import com.shpp.application.level_2.App
import com.shpp.application.level_2.model.User
import com.shpp.application.level_2.viewModel.MyContactsViewModel
import com.shpp.application.level_2.viewModel.MyContactsViewModelFactory

class MyContactsActivity: AppCompatActivity() {

    private val binding: MyContactsActivityBinding by lazy {
        MyContactsActivityBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: UsersAdapter

    private lateinit var viewModel: MyContactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val viewModelFactory = MyContactsViewModelFactory(App())
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MyContactsViewModel::class.java)

        adapter = UsersAdapter(viewModel, binding, resources.getString(R.string.snackbar_removed))

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerUsers.layoutManager = layoutManager
        binding.recyclerUsers.adapter = adapter
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
                    binding.buttonScroll.visibility = View.VISIBLE
                }
            }
        })

        binding.buttonScroll.setOnClickListener {
            binding.recyclerUsers.smoothScrollToPosition(0)
        }

        binding.buttonAddContacts.setOnClickListener {
                val dialogLayout = layoutInflater.inflate(R.layout.add_user_dialog, binding.root, false)
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setView(dialogLayout)

                val dialog = dialogBuilder.create()

                val bindingAdd = AddUserDialogBinding.bind(dialogLayout)

                bindingAdd.buttonSave.setOnClickListener {
                    with(bindingAdd) {
                        val user = User(
                            name = editUsername.text.toString(),
                            job = editCareer.text.toString(),
                            address = editAddress.text.toString(),
                            email = editEmail.text.toString(),
                            birth = editBirth.text.toString(),
                            phone = editPhone.text.toString(),
                            photo = ""
                        )
                        viewModel.addUser(user)
                        dialog.dismiss()
                    }
                }
                dialog.show()
            }

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerUsers)
    }

    override fun onStart() {
        super.onStart()
        viewModel.users.observe(this, Observer{
            adapter.users = it
        })
    }
}