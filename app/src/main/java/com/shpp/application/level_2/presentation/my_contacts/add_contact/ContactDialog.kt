package com.shpp.application.level_2.presentation.my_contacts.add_contact

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import com.shpp.application.databinding.AddUserDialogBinding
import com.shpp.application.level_2.data.model.User
import com.shpp.application.level_2.presentation.my_contacts.MyContactsViewModel
import com.shpp.application.level_2.presentation.utils.extensions.downloadAndPutPhoto

/**
 * ContactDialog.kt
 * @author Pavlo Kokhanevych
 */
class ContactDialog : DialogFragment() {

    private val contactsViewModel: MyContactsViewModel = MyContactsViewModel()
    private lateinit var bindingAdd: AddUserDialogBinding

    private var urlAvatar: String = ""

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    companion object {
        fun newInstance(): ContactDialog {
            return ContactDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingAdd = AddUserDialogBinding.inflate(inflater, container, false)
        return bindingAdd.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clearAllField()
        initializeResultLauncher()
        addButtonSaveListener()
        addBaselineListener()
        addButtonAddPhotoListener()
    }

    private fun addButtonAddPhotoListener() {
        bindingAdd.buttonAddPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            resultLauncher.launch(intent)
        }
    }

    private fun addBaselineListener() {
        bindingAdd.baselineBack.setOnClickListener {
            dismiss()
        }
    }

    private fun addButtonSaveListener() {
        bindingAdd.buttonSave.setOnClickListener {
            with(bindingAdd) {
                val user = User(
                    id = (0..100).random(),
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
                dismiss()
            }
        }
    }

    private fun initializeResultLauncher() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultForActivity ->
                downloadImage(resultForActivity)
            }
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

    private fun downloadImage(result: androidx.activity.result.ActivityResult) {

        // Checks ending operation choosing photo.
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (data != null) {
                val selectedImageUri = data.data

                // Loads photo and saves URL.
                if (selectedImageUri != null) {
                    urlAvatar = selectedImageUri.toString()
                    bindingAdd.avatar.downloadAndPutPhoto(urlAvatar)
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }
        }
    }
}
