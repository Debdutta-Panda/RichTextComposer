package com.algogence.richtextcomposer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.algogence.articleview.*
import com.algogence.richtextcomposer.ui.theme.RichTextComposerTheme


class MainActivity : ComponentActivity() {
    private val j = J("""
            {
                "type":"surface",
                "children":[
                    {
                        "type":"text",
                        "value":"Hello, World"
                    }
                ]
            }
        """.trimIndent())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RichTextComposerTheme {
                render()
            }
        }
    }

    @Composable
    private fun render() {
        renderView(j)
    }

    @Composable
    private fun renderView(j: J) {
        when(j.viewType){
            JConst.surface->{
                var m = Modifier
                val t = m.then(m)
                Surface(
                    modifier = listOf(
                        Modifier.width(0.dp)
                    ).fold(t){a,e->
                        a.then(e)
                    }
                ){
                    j.forEachChildView {
                        renderView(it)
                    }
                }
            }
            JConst.text->{
                Text(
                    j.viewValue,
                )
            }
        }
    }
}

