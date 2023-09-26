package com.shpp.application.level_2.presentation.my_contacts.add_contact

import android.app.Activity
import android.app.AlertDialog
import android.app.AlertDialog.Builder
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import com.shpp.application.R
import com.shpp.application.databinding.AddUserDialogBinding
import com.shpp.application.databinding.MyContactsActivityBinding
import com.shpp.application.level_2.data.model.User
import com.shpp.application.level_2.presentation.my_contacts.MyContactsViewModel

/**
 * ContactDialog.kt
 * @author Pavlo Kokhanevych
 */
class ContactDialog(        //todo Dialog fragment
    private val contactsViewModel: MyContactsViewModel,
    private val myContactBinding: MyContactsActivityBinding,
    private val layoutInflaterContact: LayoutInflater,
    private val context: Context
) {


    val bindingAdd: AddUserDialogBinding by lazy {
        AddUserDialogBinding.bind(dialogLayout)
    }

    private var urlAvatar: String = ""
    private var dialogBuilder: Builder = AlertDialog.Builder(context)
    private val dialog: Dialog

    private val dialogLayout: View by lazy {
        layoutInflaterContact.inflate(R.layout.add_user_dialog, myContactBinding.root, false)
    }

    init {
        dialogBuilder.setView(dialogLayout)
        dialog = dialogBuilder.create()
    }

    fun show() {
        clearAllField()
        bindingAdd.buttonSave.setOnClickListener {
            with(bindingAdd) {
                val user = User(        //todo id ?
                    name = editUsername.text.toString(),
                    job = editCareer.text.toString(),
                    address = editAddress.text.toString(),
                    email = editEmail.text.toString(),
                    birth = editBirth.text.toString(),
                    phone = editPhone.text.toString(),
                    photo = urlAvatar
                )
                Log.d("myLog", user.toString())
                contactsViewModel.addUser(user)
                dialog.dismiss()
            }
        }

        bindingAdd.baselineBack.setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun clearAllField() {
        with(bindingAdd) {
             editUsername.text = null
             editCareer.text = null
             editAddress.text = null
             editEmail.text = null
             editBirth.text = null
             editPhone.text = null
             urlAvatar = ""
        }
    }

    fun downloadImage(result: androidx.activity.result.ActivityResult) {

        // Checks ending operation choosing photo.
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (data != null) {
                val selectedImageUri = data.data

                // Loads photo and saves URL.
                if (selectedImageUri != null) {
                    Glide.with(context) //todo extension
                        .load(selectedImageUri)
                        .into(bindingAdd.avatar)

                    urlAvatar = selectedImageUri.toString()
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }
        }
    }
}
