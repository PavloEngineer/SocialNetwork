package com.shpp.application.level_2.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
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
    val context: Context
) {

    var urlAvatar: String = ""
    val dialogLayout: View by lazy {
        layoutInflaterContact.inflate(R.layout.add_user_dialog, myContactBinding.root, false)
    }

    val bindingAdd: AddUserDialogBinding by lazy {
        AddUserDialogBinding.bind(dialogLayout)
    }

    fun show() {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setView(dialogLayout)

        val dialog = dialogBuilder.create()

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
        dialog.show()
    }

    fun downloadImage(result: androidx.activity.result.ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (data != null) {
                val selectedImageUri = data.data
                // Тут ви можете робити обробку обраного зображення
                if (selectedImageUri != null) {
                    // Використовуйте Glide або іншу бібліотеку для завантаження зображення в ImageView
                    Glide.with(context)
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
}
