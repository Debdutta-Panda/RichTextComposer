package com.algogence.richtextcomposer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.algogence.articleview.*
import com.algogence.richtextcomposer.ui.theme.RichTextComposerTheme


class MainActivity : ComponentActivity() {
    private val j = J("""
            {
                "type":"surface",
                "children":[
                    {
                        "type":"column",
                        "verticalArrangement":"center",
                        "horizontalAlignment":"center",
                        "children":[
                            {
                                "type":"card",
                                "modifiers":[
                                    {
                                        "name":"size",
                                        "value":200
                                    }
                                ],
                                "shape":{
                                    "name":"roundedCornerShape",
                                    "value":12
                                },
                                "backgroundColor":"#ffffff",
                                "elevation":20
                            },
                            {
                                "type":"text",
                                "value":"hello"
                            },
                            {
                                "type":"text",
                                "value":"hello",
                                "modifiers":[
                                    {
                                        "name":"columnScopeAlign",
                                        "value":"start"
                                    },
                                    {
                                        "name":"columnScopeWeight",
                                        "value":1.0
                                    }
                                ]
                            }
                        ],
                        "modifiers":[
                            {
                                "name":"fillMaxSize"
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
        renderView(j, this)
    }
}

