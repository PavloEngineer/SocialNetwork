package com.shpp.application.level_2.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.shpp.application.R
import com.shpp.application.databinding.AddUserDialogBinding
import com.shpp.application.databinding.MyContactsActivityBinding
import com.shpp.application.level_2.model.User
import com.shpp.application.level_2.viewModel.MyContactsViewModel


class ContactDialog(
    val contactsViewModel: MyContactsViewModel,
    val myContactBinding: MyContactsActivityBinding,
    val layoutInflaterContact: LayoutInflater,
) {

    var urlAvatar: String = ""

    fun show(context: Context) {
        val dialogLayout = layoutInflaterContact.inflate(R.layout.add_user_dialog, myContactBinding.root, false)
        val dialogBuilder = AlertDialog.Builder(context)
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
                    photo = urlAvatar
                )
                contactsViewModel.addUser(user)
                dialog.dismiss()
            }
        }
    }

    fun uploadImage(activity: Activity, result: androidx.activity.result.ActivityResult) {

    }
}
