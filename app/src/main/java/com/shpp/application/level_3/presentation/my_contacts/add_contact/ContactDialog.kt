package com.shpp.application.level_3.presentation.my_contacts.add_contact

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.shpp.application.databinding.AddUserDialogBinding
import com.shpp.application.level_3.data.model.User
import com.shpp.application.level_3.presentation.my_contacts.MyContactsViewModel
import com.shpp.application.level_3.presentation.utils.extensions.downloadAndPutPhoto

/**
 * ContactDialog.kt
 * @author Pavlo Kokhanevych
 */
class ContactDialog : DialogFragment() {

    private val contactsViewModel: MyContactsViewModel by viewModels() // TODO: screen -> personal her view model
    private lateinit var bindingAdd: AddUserDialogBinding

    private var urlAvatar: String = ""

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    companion object {
        fun newInstance(): ContactDialog { // TODO: why?
            return ContactDialog()
        }
    }

//    override fun onCreateView( // TODO: not correctly
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        bindingAdd = AddUserDialogBinding.inflate(inflater, container, false)
//        return bindingAdd.root
//    }
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // TODO: not correctly
//        super.onViewCreated(view, savedInstanceState)
//
//    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(requireContext())
        bindingAdd = AddUserDialogBinding.inflate(layoutInflater)
        builder.setView(bindingAdd.root)
        clearAllField()
        initializeResultLauncher()
        setListeners()
        addBaselineListener()
        addButtonAddPhotoListener()
        return builder.create()
    }

    private fun setListeners() {
        bindingAdd.buttonAddPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            resultLauncher.launch(intent)
        }
        bindingAdd.baselineBack.setOnClickListener {
            dismiss()
        }
        bindingAdd.buttonSave.setOnClickListener { addNewUser() }
    }

    private fun addButtonAddPhotoListener() {

    }

    private fun addBaselineListener() {

    }

    private fun addNewUser() {
        with(bindingAdd) {
            val user = User(
                // id = (0..100).random(), // TODO: random.. bad
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

    private fun initializeResultLauncher() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultForActivity ->
                downloadImage(resultForActivity)
            }
    }

    private fun clearAllField() { // TODO: think about it
//        with(bindingAdd) {
//            editUsername.text = null
//            editCareer.text = null
//            editAddress.text = null
//            editEmail.text = null
//            editBirth.text = null
//            editPhone.text = null
//            urlAvatar = ""
//        }
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
