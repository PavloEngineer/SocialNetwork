package com.shpp.application.level_3.presentation.my_contacts

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.shpp.application.level_3.utils.Constants.MILLIS_DURATION

typealias FunctionForSnack = () -> Unit

open class BaseFragment: Fragment() {

     val viewModel: MyContactsViewModel by viewModels()

     open fun showSnackBar(label: String, actionById: Int, functionForAction: FunctionForSnack) {
          val snackBar = view?.let {
              Snackbar.make(
                  it,
                  label,
                  MILLIS_DURATION
              )
          }
          snackBar?.setAction(actionById) {
               functionForAction()
          }
          snackBar?.show()
     }
}