package com.shpp.application.level_1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatCheckBox
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.shpp.application.MainActivity
import com.shpp.application.R

/**
 * Class for activation of user interaction processing with auth_activity.
 * @author Pavlo Kokhanevych
 */
class AuthActivity : AppCompatActivity() {

    private lateinit var  sharedPref: SharedPreferences;
    private val SHARED_PREF: String = "SHARED_PREFERENCES"

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

    private fun autoLogin(emailField: TextInputEditText, passwordField: TextInputEditText) {
        sharedPref = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val allMapsFromShared = sharedPref.all;
        if (allMapsFromShared.isNotEmpty()) {
            emailField.setText(allMapsFromShared.keys.first())
            passwordField.setText(allMapsFromShared[allMapsFromShared.keys.first()].toString())
        }
    }

    /**
     * Checks input value on error and goes to AuthActivity. Also sends email to next activity
     */
    private fun startMainActivity(emailField: TextInputEditText, passwordField: TextInputEditText) {
        if (emailField.error == null && passwordField.error == null) {
            saveAuthLog(emailField, passwordField)
            val intentToAuth = Intent(this, MainActivity::class.java)
            intentToAuth.putExtra(Intent.EXTRA_TEXT, emailField.text.toString())
            startActivity(intentToAuth)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left)
        }
    }

    private fun saveAuthLog(emailField: TextInputEditText, passwordField: TextInputEditText) {
        val checkRememberMe: AppCompatCheckBox = findViewById(R.id.checkboxRemember)
        sharedPref = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        if (checkRememberMe.isChecked) {
            sharedPref.edit()
                .putString(emailField.text.toString(), passwordField.text.toString())
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