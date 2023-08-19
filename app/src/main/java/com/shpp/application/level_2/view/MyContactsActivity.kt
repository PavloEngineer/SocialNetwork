package com.shpp.application.level_2.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shpp.application.databinding.MyContactsActivityBinding
import com.shpp.application.level_2.App
import com.shpp.application.level_2.model.UserListeners
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

        adapter = UsersAdapter(viewModel)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerUsers.layoutManager = layoutManager
        binding.recyclerUsers.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        viewModel.users.observe(this, Observer{
            adapter.users = it
        })
    }


}