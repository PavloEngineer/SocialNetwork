package com.shpp.application

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Class for activation of user interaction processing with activity_main.
 * @author Pavlo Kokhanevych
 */
class MainActivity: AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fullName: TextView = findViewById(R.id.name)
        changeTextNameView(fullName)
    }

    /**
     * Gets text form intent, parses and changes name on activity_main.
     * @param fullName view for saving text
     */
    private fun changeTextNameView(fullName: TextView) {
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            fullName.text = parseEmail(intent.getStringExtra(Intent.EXTRA_TEXT).toString())
        }
    }

    /**
     * Parses email and creates something similar to a real name.
     * Uses StringBuilder for changing text.
     * @param stringExtra text from intent
     */
    private fun parseEmail(stringExtra: String): CharSequence {

        // Cuts part text after '@'
        val fullName: StringBuilder = StringBuilder(stringExtra.substring(0, stringExtra.indexOf("@")))

        fullName[0] = fullName[0].uppercaseChar()

        // Splits name and surname
        if (fullName.indexOf('.') != -1) {
            fullName[fullName.indexOf('.')] = ' '
            fullName[fullName.indexOf(' ') + 1] = fullName[fullName.indexOf(' ') + 1].uppercaseChar()
        }

        return fullName
    }
}