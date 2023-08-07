package com.shpp.application.level_1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.shpp.application.R

/**
 * Class for activation of user interaction processing with activity_main.
 * @author Pavlo Kokhanevych
 */
class MainActivity: AppCompatActivity()  { // TODO: binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fullName: TextView = findViewById(R.id.name)
        changeTextNameView()
    }

    /**
     * Gets text form intent, parses and changes name on activity_main.
     * @param fullName view for saving text
     */
    private fun changeTextNameView() {
        if (intent.getStringExtra("data") != null) {
            //binding.fullName.text = parseEmail(intent.getStringExtra("data").toString())
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