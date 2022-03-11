package com.algogence.richtextcomposer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.algogence.articleview.*
import com.algogence.articleview.JConst.textAlign
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
                                "type":"svg",
                                "modifiers":[
                                    {
                                        "name":"size",
                                        "value":120
                                    }
                                ]
                            },
                            {
                                "type":"image",
                                "url":"https://img.youtube.com/vi/PLXkFL2kZvc/mqdefault.jpg",
                                "modifiers":[
                                    {
                                        "name":"fillMaxWidth"
                                    },
                                    {
                                        "name":"height",
                                        "value":120
                                    }
                                ]
                            },
                            {
                                "type":"youtube",
                                "url":"https://www.youtube.com/watch?v=PLXkFL2kZvc",
                                "thumbnail":"https://img.youtube.com/vi/PLXkFL2kZvc/mqdefault.jpg",
                                "modifiers":[
                                    {
                                        "name":"fillMaxWidth"
                                    },
                                    {
                                        "name":"height",
                                        "value":200
                                    }
                                ]
                            },
                            {
                                "type":"icon",
                                "icon":"m 4.3385724,6.4803993 3.8443047,-3.8992233 9.6107619,3.4049557 3.075444,-3.0205251 1.153291,7.2492604 -4.777922,4.173816 4.393492,4.997596 -3.624631,2.03199 -4.942677,-3.569711 -7.0845043,2.691013 -3.2401996,-3.2402 0.9885354,-3.404955 7.1394225,1.263128 c 0,0 3.295119,0.549187 2.800851,-2.086908 C 13.180473,10.434541 11.587833,14.663276 7.0295856,12.0821 2.4713387,9.5009244 18.397744,13.619822 17.134615,11.148484 15.871486,8.677145 4.3385724,6.4803993 4.3385724,6.4803993 Z",
                                "tint":"#ff0000",
                                "modifiers":[
                                    {
                                        "name":"size",
                                        "value":24
                                    }
                                ]
                            },
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
                                "border":{
                                    "stroke":0.5,
                                    "color":"#efefef"
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

