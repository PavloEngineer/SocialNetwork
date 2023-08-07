package com.shpp.application.level_1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.shpp.application.R
import com.shpp.application.databinding.AuthActivityBinding
import com.shpp.application.level_1.utils.Constants
import com.shpp.application.level_1.utils.Constants.SHARED_PREF

/**
 * Class for activation of user interaction processing with auth_activity.
 * @author Pavlo Kokhanevych
 */
class AuthActivity : AppCompatActivity() {

    private val binding: AuthActivityBinding by lazy {
        AuthActivityBinding.inflate(layoutInflater)
    }

    private val sharedPref: SharedPreferences by lazy {
        getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    }

    /**
     * Naming local storage.
     */
    // TODO: to place


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        addEmailListener()
        addPasswordListener()
        autoLogin()
        binding.registerButton.setOnClickListener { startMainActivity(); }
    }


    /**
     * Using SharedPreferences gets information about an email and a password. Next, puts this value to
     * text views. If sharedPreferences is empty, it just will skip another actions.
     * @param emailField email user
     * @param passwordField password user
     */
    private fun autoLogin() {
        val allMapsFromShared = sharedPref.all;
        if (allMapsFromShared.isNotEmpty()) {
            binding.editEmail.setText(sharedPref.toString())
            binding.editPassword.setText(allMapsFromShared[Constants.PASSWORD].toString())
        }
    }

    /**
     * Checks input value on error and goes to AuthActivity. Also sends email to next activity
     * @param emailField email user
     * @param passwordField password user
     */
    private fun startMainActivity() {
        with(binding) {
            if (editEmail.error == null && editPassword.error == null
                && !editEmail.text.isNullOrEmpty() && !editEmail.text.isNullOrEmpty()
            ) {

               // saveAutoLog()
                val intentToAuth = Intent(this@AuthActivity, MainActivity::class.java)
                intentToAuth.putExtra("data", editEmail.text.toString())
                startActivity(intentToAuth)
                overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
                )
            }
        }
    }


    /**
     *  Saves the email and the password users to SharedPreferences, if checkBox "Remember me?"
     *  is chosen. If checkBox "Remember me?" isn`t chosen, it will clear all information in
     *  SharedPreferences that keeps password and email.
     *  @param emailField email user
     * @param passwordField password user
     */
    private fun saveAutoLog(emailField: TextInputEditText, passwordField: TextInputEditText) {
        val checkBoxRemember: AppCompatCheckBox = findViewById(R.id.checkboxRemember)
        if (checkBoxRemember.isChecked) {
            sharedPref.edit()
                .putString(Constants.PASSWORD, passwordField.text.toString())
                .putString(Constants.EMAIL, emailField.text.toString())
                .apply()
        } else {
            sharedPref.edit()
                .clear()
                .apply()
        }
    }


    /**
     * Puts listener for validating email known as addTextChangedListener. Added logic validating
     * after changed text. Using Regex for finding syntax mistakes.
     * @param emailField email user
     */
    private fun addEmailListener() {
        // TODO: delete not need methods
        binding.editEmail.doOnTextChanged { text, _, _, _ ->

        }
//        emailField.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                if (!isEmailCorrect(s.toString())) {
//                    emailField.error = "Incorrect. Check syntax" // TODO: to res
//                } else {
//                    emailField.error = null
//                }
//            }
//        })
    }

    /**
     * Puts listener for validating password known as addTextChangedListener. Added logic validating
     * after changed text.
     * Validation conditions: 1) more than 6 signs; 2) 1 digit min
     * @param passwordField password user
     */
    private fun addPasswordListener() {
        // TODO: delete not need methods
//        passwordField.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//
//            override fun afterTextChanged(s: Editable?) {
//                if (!isPasswordCorrect(s.toString())) {
//                    passwordField.error = "Mustn't be less 6 signs and 1 digit" // TODO: to res
//                } else {
//                    passwordField.error = null
//                }
//            }
//        })
    }

    /**
     * Validation password conditions: 1) more than 6 signs; 2) 1 digit min
     */
    private fun isPasswordCorrect(textPassword: String): Boolean {
        val hasOneNumber: Boolean = textPassword.find { it.isDigit() } != null
        return !(textPassword.length < 7 || !hasOneNumber) // TODO: length to constants
    }

    /**
     * // Regex(Constants.PASSWORD_REGEX).matches(password)
     * Validating email using Regex for finding syntax mistakes.
     */
    private fun isEmailCorrect(textEmail: String) = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$").matches(textEmail)

}