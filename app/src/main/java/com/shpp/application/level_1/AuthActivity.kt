package com.shpp.application.level_1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.shpp.application.R

/**
 * Class for activation of user interaction processing with auth_activity.
 * @author Pavlo Kokhanevych
 */
class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity)

        val emailField: TextInputEditText = findViewById(R.id.editEmail)
        val passwordField: TextInputEditText = findViewById(R.id.editPassword)
        addEmailListener(emailField)
        addPasswordListener(passwordField)
    }

    /**
     * Puts listener for validating email known as addTextChangedListener. Added logic validating
     * after changed text. Using Regex for finding syntax mistakes.
     */
    private fun addEmailListener(emailField: TextInputEditText) {
        emailField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

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