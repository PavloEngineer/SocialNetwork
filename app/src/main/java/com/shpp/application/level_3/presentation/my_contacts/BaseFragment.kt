package com.shpp.application.level_3.presentation.my_contacts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.shpp.application.level_3.utils.Constants.MILLIS_DURATION

typealias FunctionForSnack = () -> Unit
abstract class BaseFragment<VBinding : ViewBinding>(
    private val inflaterMethod: (LayoutInflater, ViewGroup?, Boolean) -> VBinding
) :
    Fragment() {

    private var _binding: VBinding? = null
    val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflaterMethod.invoke(inflater, container, false)
        setListeners()
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun showSnackBar(label: String, actionById: Int, functionForAction: FunctionForSnack) {
           view?.let {
              Snackbar.make(
                  it,
                  label,
                  MILLIS_DURATION
              )
          }?.setAction(actionById) {
              functionForAction()
          }?.show()
     }
    abstract fun setListeners()
}