package com.shpp.application.level_2.view

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shpp.application.R
import com.shpp.application.databinding.MyContactsActivityBinding
import com.shpp.application.level_2.App
import com.shpp.application.level_2.utils.Constants.ZERO_POSITION
import com.shpp.application.level_2.view.callBacks.SwipeToDeleteCallback
import com.shpp.application.level_2.viewModel.MyContactsViewModel
import com.shpp.application.level_2.viewModel.MyContactsViewModelFactory

class MyContactsActivity : AppCompatActivity() {

    private val binding: MyContactsActivityBinding by lazy {
        MyContactsActivityBinding.inflate(layoutInflater)
    }

    private val adapter: UsersAdapter by lazy {
        UsersAdapter(viewModel, binding, resources.getString(R.string.snackbar_removed))
    }

    private val alertDialog: ContactDialog by lazy {
        ContactDialog(viewModel, binding, layoutInflater, this)
    }

    private lateinit var viewModel: MyContactsViewModel

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val viewModelFactory = MyContactsViewModelFactory(App())
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MyContactsViewModel::class.java)

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

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerUsers)
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
                    binding.buttonScroll.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.users.observe(this, Observer {
            adapter.users = it
        })
    }
}