package com.shpp.application.level_2.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shpp.application.R
import com.shpp.application.databinding.AddUserDialogBinding
import com.shpp.application.databinding.MyContactsActivityBinding
import com.shpp.application.level_2.App
import com.shpp.application.level_2.model.User
import com.shpp.application.level_2.utils.Constants.ZERO_POSITION
import com.shpp.application.level_2.view.callBacks.SwipeToDeleteCallback
import com.shpp.application.level_2.viewModel.MyContactsViewModel
import com.shpp.application.level_2.viewModel.MyContactsViewModelFactory

class MyContactsActivity: AppCompatActivity() {

    private val binding: MyContactsActivityBinding by lazy {
        MyContactsActivityBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: UsersAdapter

    private lateinit var viewModel: MyContactsViewModel

    private var urlAvatar: String = ""
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    lateinit var bindingAdd: AddUserDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    val selectedImageUri = data.data
                    // Тут ви можете робити обробку обраного зображення
                    if (selectedImageUri != null) {
                        // Використовуйте Glide або іншу бібліотеку для завантаження зображення в ImageView
                        Glide.with(this)
                            .load(selectedImageUri)
                            .into(bindingAdd.avatar)

                        // Тут ви також можете зберегти адресу зображення для подальшого використання
                        val imageUrl = selectedImageUri.toString()
                        urlAvatar = imageUrl
                    } else {
                        Log.d("PhotoPicker", "No media selected")
                    }
                }
            }
        }

        val viewModelFactory = MyContactsViewModelFactory(App())
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MyContactsViewModel::class.java)

        adapter = UsersAdapter(viewModel, binding, resources.getString(R.string.snackbar_removed))

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerUsers.layoutManager = layoutManager
        binding.recyclerUsers.adapter = adapter
        addVisibleButtonScrollListener()

        binding.buttonScroll.setOnClickListener {
            binding.recyclerUsers.smoothScrollToPosition(ZERO_POSITION)
        }

        binding.buttonAddContacts.setOnClickListener {
//                val alertDialog = ContactDialog(viewModel, binding, layoutInflater, this)
//                alertDialog.show(this)
            show(this)
        }

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerUsers)
    }

    fun show(context: Context) {
        val dialogLayout = layoutInflater.inflate(R.layout.add_user_dialog, binding.root, false)
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setView(dialogLayout)

        val dialog = dialogBuilder.create()

        bindingAdd = AddUserDialogBinding.bind(dialogLayout)

        bindingAdd.buttonSave.setOnClickListener {
            with(bindingAdd) {
                val user = User(
                    name = editUsername.text.toString(),
                    job = editCareer.text.toString(),
                    address = editAddress.text.toString(),
                    email = editEmail.text.toString(),
                    birth = editBirth.text.toString(),
                    phone = editPhone.text.toString(),
                    photo = urlAvatar
                )
                viewModel.addUser(user)
                dialog.dismiss()
            }
        }

//        var resultLauncher = MyContactsActivity.registerForActivityResult(StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                // There are no request codes
//                val data: Intent? = result.data
//                if (data != null) {
//                    Glide.with(this)
//                        .load(data)
//                        .into(bindingAdd.avatar)
//                    urlAvatar = data.toString()
//                } else {
//                    Log.d("PhotoPicker", "No media selected")
//                }
//            }
//        }




        bindingAdd.buttonAddPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//            context.startActivity(intent)
            resultLauncher.launch(intent)
        }
        dialog.show()
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
        viewModel.users.observe(this, Observer{
            adapter.users = it
        })
    }
}