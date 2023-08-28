package com.shpp.application.level_2.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.shpp.application.R
import com.shpp.application.databinding.AddUserDialogBinding
import com.shpp.application.databinding.MyContactsActivityBinding
import com.shpp.application.level_1.MainActivity
import com.shpp.application.level_2.model.User
import com.shpp.application.level_2.viewModel.MyContactsViewModel


class ContactDialog(
    val contactsViewModel: MyContactsViewModel,
    val myContactBinding: MyContactsActivityBinding,
    val layoutInflaterContact: LayoutInflater,
    val context: Context
): AppCompatActivity() {

    private var urlAvatar: String = ""
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
   lateinit var bindingAdd: AddUserDialogBinding

    fun show(context: Context) {
        val dialogLayout = layoutInflaterContact.inflate(R.layout.add_user_dialog, myContactBinding.root, false)
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
                contactsViewModel.addUser(user)
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

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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


        bindingAdd.buttonAddPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//            context.startActivity(intent)
            resultLauncher.launch(intent)
        }
        dialog.show()
    }
}
