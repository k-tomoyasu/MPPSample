package com.github.ktomoyasu.mppsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.github.ktomoyasu.mppsample.common.*

class MainActivity : AppCompatActivity() {
    private lateinit var searchButton: Button
    private lateinit var searchText: EditText

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Preference.setUp(this)
        searchText = findViewById(R.id.searchText)
        val lastSearch = Preference.get(KEY_LAST_SEARCH)
        searchText.setText(lastSearch, TextView.BufferType.NORMAL)

        searchButton = findViewById(R.id.search)
        searchButton.setOnClickListener {
            ApiClient().searchRepository(searchText.text.toString(),
                onSuccess = {
                    Log.d("result", it.count.toString())
                    handler.post {
                        val mostStarGazer = it.items.firstOrNull()
                        mostStarGazer?.let { repository ->
                            val name = repository.name
                            val text = "Top of Stargazer is $name"
                            Toast.makeText(this, text, Toast.LENGTH_LONG).show()
                        }
                    }
                },
                onError = {
                    Log.e("error", it.message ?: "")
                }
                )
        }
    }
}
