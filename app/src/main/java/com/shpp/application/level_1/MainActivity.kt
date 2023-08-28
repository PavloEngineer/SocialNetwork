package com.shpp.application.level_1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.shpp.application.R
import com.shpp.application.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity()  {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        binding.buttonLogOut.setOnClickListener { startAuthActivity(); }
        changeTextNameView()


        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    val selectedImageUri = data.data
                    // Тут ви можете робити обробку обраного зображення
                    if (selectedImageUri != null) {
                        // Використовуйте Glide або іншу бібліотеку для завантаження зображення в ImageView
                        Glide.with(this)
                            .load(selectedImageUri)
                            .into(binding.avatar)

                        // Тут ви також можете зберегти адресу зображення для подальшого використання
                        val imageUrl = selectedImageUri.toString()
                    } else {
                        Log.d("PhotoPicker", "No media selected")
                    }
                }
            }
        }

        binding.avatar.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            resultLauncher.launch(intent)
        }

    }

    private fun startAuthActivity() {
        val intent = Intent(this@MainActivity, AuthActivity::class.java)
        startActivity(intent)
        overridePendingTransition(
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )

    }

    private fun changeTextNameView() {
        if (intent.getStringExtra(Intent.EXTRA_TEXT) != null) {
            binding.textName.text = parseEmail(intent.getStringExtra(Intent.EXTRA_TEXT).toString())
        }
    }

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