package com.shpp.application.level_1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatCheckBox
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.shpp.application.R

/**
 * Class for activation of user interaction processing with auth_activity.
 * @author Pavlo Kokhanevych
 */
class AuthActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences;

    /**
     * Naming local storage.
     */
    private val SHARED_PREF: String = "SHARED_PREFERENCES"

    private val EMAIL: String = "EMAIL"

    private val PASSWORD: String = "PASSWORD"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity)

        val emailField: TextInputEditText = findViewById(R.id.editEmail)
        val passwordField: TextInputEditText = findViewById(R.id.editPassword)
        val registerButton: MaterialButton = findViewById(R.id.registerButton)
        addEmailListener(emailField)
        addPasswordListener(passwordField)
        autoLogin(emailField, passwordField)

        registerButton.setOnClickListener { v ->
            startMainActivity(emailField, passwordField);
        }
    }


    /**
     * Using SharedPreferences gets information about an email and a password. Next, puts this value to
     * text views. If sharedPreferences is empty, it just will skip another actions.
     * @param emailField email user
     * @param passwordField password user
     */
    private fun autoLogin(emailField: TextInputEditText, passwordField: TextInputEditText) {
        sharedPref = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val allMapsFromShared = sharedPref.all;
        if (allMapsFromShared.isNotEmpty()) {
            emailField.setText(allMapsFromShared[EMAIL].toString())
            passwordField.setText(allMapsFromShared[PASSWORD].toString())
        }
    }

    /**
     * Checks input value on error and goes to AuthActivity. Also sends email to next activity
     * @param emailField email user
     * @param passwordField password user
     */
    private fun startMainActivity(emailField: TextInputEditText, passwordField: TextInputEditText) {
        if (emailField.error == null && passwordField.error == null
            && !emailField.text.isNullOrEmpty() && !passwordField.text.isNullOrEmpty()
        ) {

            saveAutoLog(emailField, passwordField)
            val intentToAuth = Intent(this, MainActivity::class.java)
            intentToAuth.putExtra(Intent.EXTRA_TEXT, emailField.text.toString())
            startActivity(intentToAuth)
            overridePendingTransition(
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
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
        sharedPref = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        if (checkBoxRemember.isChecked) {
            sharedPref.edit()
                .putString(PASSWORD, passwordField.text.toString())
                .putString(EMAIL, emailField.text.toString())
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
    private fun addEmailListener(emailField: TextInputEditText) {
        emailField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!isEmailCorrect(s.toString())) {
                    emailField.error = "Incorrect. Check syntax"
                } else {
                    emailField.error = null
                }
            }
        })
    }

    /**
     * Puts listener for validating password known as addTextChangedListener. Added logic validating
     * after changed text.
     * Validation conditions: 1) more than 6 signs; 2) 1 digit min
     * @param passwordField password user
     */
    private fun addPasswordListener(passwordField: TextInputEditText) {
        passwordField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!isPasswordCorrect(s.toString())) {
                    passwordField.error = "Mustn't be less 6 signs and 1 digit"
                } else {
                    passwordField.error = null
                }
            }
        })
    }

    /**
     * Validation password conditions: 1) more than 6 signs; 2) 1 digit min
     */
    private fun isPasswordCorrect(textPassword: String): Boolean {
        val hasOneNumber: Boolean = textPassword.find { it.isDigit() } != null
        return !(textPassword.length < 7 || !hasOneNumber)
    }

    /**
     * Validating email using Regex for finding syntax mistakes.
     */
    private fun isEmailCorrect(textEmail: String): Boolean {
        val regex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        return regex.matches(textEmail)
    }
}