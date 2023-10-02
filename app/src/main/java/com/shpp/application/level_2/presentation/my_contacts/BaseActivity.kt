package com.shpp.application.level_2.presentation.my_contacts

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.shpp.application.level_2.utils.Constants.MILLIS_IN_FUTURE

typealias FunctionForSnack = () -> Unit
open class BaseActivity: AppCompatActivity() {

     val viewModel: MyContactsViewModel by viewModels()

     open fun showSnackBar(label: String, actionById: Int, functionForAction: FunctionForSnack) {
          val snackBar = Snackbar.make(
               findViewById(android.R.id.content),
               label,
               MILLIS_IN_FUTURE
          )
          snackBar.setAction(actionById) {
               functionForAction()
          }
          snackBar.show()
     }
}