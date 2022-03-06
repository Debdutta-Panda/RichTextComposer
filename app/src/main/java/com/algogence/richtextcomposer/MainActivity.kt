package com.algogence.richtextcomposer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.algogence.articleview.J
import com.algogence.richtextcomposer.ui.theme.RichTextComposerTheme


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        J("""
            {
                "a":{
                    "b":1
                }
            }
        """.trimIndent()).apply {
            Log.d("fdfdfsfsf",get("a").type.name)
        }
        setContent {
            RichTextComposerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White,
                ) {

                }
            }
        }
    }
}

