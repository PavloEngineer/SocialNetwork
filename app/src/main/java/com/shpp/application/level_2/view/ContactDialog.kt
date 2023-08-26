package com.shpp.application.level_2.view

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.shpp.application.R
import com.shpp.application.databinding.AddUserDialogBinding
import com.shpp.application.level_2.model.User
import com.shpp.application.level_2.viewModel.MyContactsViewModel

class ContactDialog (val contactsViewModel: MyContactsViewModel) : AppCompatActivity() {

    //private val binding : AddUserDialogBinding by lazy {
      //  AddUserDialogBinding.inflate(layoutInflater)
    //}

    fun show(context: Context) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        val alertDialog = alertDialogBuilder.create()

        // Підключення макету до AlertDialog
        val view = LayoutInflater.from(context).inflate(R.layout.add_user_dialog, null)
        alertDialogBuilder.setView(view)

        val binding = AddUserDialogBinding.bind(view)

        binding.buttonSave.setOnClickListener {
            with(binding) {
                val user = User (
                    name = editUsername.text.toString(),
                    job = editCareer.text.toString(),
                    address = editAddress.text.toString(),
                    email = editEmail.text.toString(),
                    birth = editBirth.text.toString(),
                    phone = editPhone.text.toString(),
                    photo = "#"
                )
                contactsViewModel.addUser(user)
                alertDialog.dismiss()
            }
        }
        alertDialog.show()
    }
}
