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
                    "b":{
                        "c":"d",
                        "e":[
                            1,2,3,4
                        ]
                    }
                }
            }
        """.trimIndent()).apply {
            Log.d("fdfdfsfsf", getByPath("a.b.e[1]")?.asArray()?.get(2)?.type?.name?:"")
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

