package com.shpp.application.level_1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.shpp.application.R
import com.shpp.application.databinding.AuthActivityBinding
import com.shpp.application.level_1.utils.Constants.EMAIL
import com.shpp.application.level_1.utils.Constants.MIN_LENGTH_PASSWORD
import com.shpp.application.level_1.utils.Constants.PASSWORD
import com.shpp.application.level_1.utils.Constants.SHARED_PREFERENCES

class AuthActivity : AppCompatActivity() {

    private val binding: AuthActivityBinding by lazy {
        AuthActivityBinding.inflate(layoutInflater)
    }

    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        addEmailListener()
        addPasswordListener()
        autoLogin()
        binding.registerButton.setOnClickListener { startMainActivity(); }
    }


    private fun autoLogin() {
        val allMapsFromShared = sharedPreferences.all;
        if (allMapsFromShared.isNotEmpty()) {
            binding.editEmail?.setText(allMapsFromShared[EMAIL].toString())
            binding.editPassword?.setText(allMapsFromShared[PASSWORD].toString())
        }
    }

    private fun startMainActivity() {
        with(binding) {
            if (editEmail.error == null && editPassword.error == null
                && !editEmail.text.isNullOrEmpty() && !editEmail.text.isNullOrEmpty()
            ) {

                saveAutoLog()
                val intentToAuth = Intent(this@AuthActivity, MainActivity::class.java)
                intentToAuth.putExtra(Intent.EXTRA_TEXT, editEmail.text.toString())
                startActivity(intentToAuth)
                overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
                )
            }
        }
    }

    private fun saveAutoLog() {
        with(binding) {
            if (checkboxRemember.isChecked) {
                sharedPreferences.edit()
                    .putString(PASSWORD, editPassword.text.toString())
                    .putString(EMAIL, editEmail.text.toString())
                    .apply()
            } else {
                sharedPreferences.edit()
                    .clear()
                    .apply()
            }
        }
    }

    private fun addEmailListener() {
        // TODO: delete not need methods
        binding.editEmail.doOnTextChanged { text, _, _, _ ->
            if (!isEmailCorrect(text.toString())) {
                binding.editEmail.error = "Incorrect. Check syntax" // TODO: to res
            } else {
                binding.editEmail.error = null
            }
        }
    }

    private fun addPasswordListener() {
        // TODO: delete not need methods
        binding.editPassword.doOnTextChanged { text, _, _, _ ->
            if (!isPasswordCorrect(text.toString())) {
                binding.editPassword.error = "Unreliable password.Use upper and lower case, digits" // TODO: to res
            } else {
                binding.editPassword.error = null
            }
        }
    }

    private fun isPasswordCorrect(password: String): Boolean {
            val hasNumberAndLetter: Boolean = password.any { it.isDigit() } &&
                    password.any { it.isUpperCase() } &&
                    password.any{ it.isLowerCase()}
            return !(password.length < MIN_LENGTH_PASSWORD || !hasNumberAndLetter) // TODO: length to constants
    }

    private fun isEmailCorrect(textEmail: String) = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$").matches(textEmail)

}