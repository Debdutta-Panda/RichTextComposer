package com.algogence.richtextcomposer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.algogence.articleview.*
import com.algogence.richtextcomposer.ui.theme.RichTextComposerTheme


class MainActivity : ComponentActivity() {
    private val j = J("""
            {
                "type":"surface",
                "children":[
                    {
                        "type":"text",
                        "value":"Hello, World",
                        "modifiers":[
                            {
                                "name":"background",
                                "value":"#fc038c"
                            },
                            {
                                "name":"widthIn",
                                "value":{
                                    "min":100,
                                    "max":200
                                }
                            }
                        ]
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
                Surface(
                    modifier = composeModifier(j)
                ){
                    j.forEachChildView {
                        renderView(it)
                    }
                }
            }
            JConst.text->{
                Text(
                    j.viewValue,
                    modifier = composeModifier(j)
                )
            }
        }
    }

    @Composable
    fun composeModifier(j: J?): Modifier{
        if(j==null){
            return Modifier
        }
        return j.forEachModifier{
            when(it.viewName){
                JConst.width->{
                    val value = it.viewValueNumber
                    Modifier.width(value.dp)
                }
                JConst.height->{
                    val value = it.viewValueNumber
                    Modifier.height(value.dp)
                }
                JConst.size->{
                    val value = it.viewValueNumber
                    Modifier.size(value.dp)
                }
                JConst.widthHeight->{
                    val value = it.viewValueWidthHeight
                    Modifier.size(width = value.comp1.dp, height = value.comp2.dp)
                }
                JConst.widthIn->{
                    val value = it.viewValueMinMax
                    Modifier.widthIn(min = value.comp1.dp, max = value.comp2.dp)
                }
                JConst.background->{
                    val value = it.viewValue
                    Modifier.background(Color.parse(value))
                }
                else -> Modifier
            }
        }
    }
}

