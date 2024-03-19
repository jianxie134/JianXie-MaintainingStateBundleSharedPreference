package com.example.maintainingstatebundlesharedpreference

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var imageView: ImageView
    private lateinit var changeImageButton: Button
    private lateinit var sharedPreferences: SharedPreferences
    private var imageResId: Int = 0

    private val imageResources = listOf( // Add drawable resource IDs here
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
        imageView = findViewById(R.id.imageView)
        changeImageButton = findViewById(R.id.button)
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        loadState() // Load the previous state

        changeImageButton.setOnClickListener {
            changeImage()
            saveState() // Save state after a change
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        saveState() // Ensure the state is saved when the app is destroyed
    }

    private fun changeImage() {
        val randomIndex = Random().nextInt(imageResources.size)
        imageResId = imageResources[randomIndex] // Store the ID
        imageView.setImageResource(imageResId)
    }

    private fun saveState() {
        with(sharedPreferences.edit()) {
            putString("editTextValue", editText.text.toString())
            putInt("imageResId", imageResId) // Save the stored image ID
            apply()
        }
    }

    private fun loadState() {
        editText.setText(sharedPreferences.getString("editTextValue", ""))
        val imageResId = sharedPreferences.getInt("imageResId", 0)
        if (imageResId != 0) {
            imageView.setImageResource(imageResId)
        }
    }
}
