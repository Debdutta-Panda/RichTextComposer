package com.algogence.richtextcomposer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.algogence.richtextcomposer.ui.theme.RichTextComposerTheme
import cz.vutbr.web.css.CSSFactory
import cz.vutbr.web.css.MediaSpec
import cz.vutbr.web.css.MediaSpecAll
import org.intellij.markdown.ast.ASTNode
import org.jsoup.Jsoup
import org.jsoup.helper.W3CDom
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.io.StringReader
import java.net.URL
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RichTextComposerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MultipleStylesInText()
                }
            }
        }
    }
}

@Composable
fun MultipleStylesInText() {
    val src = Jsoup.parse("""
        <!DOCTYPE html>
        <html>
        <head>
        <style>
            p{
                color:red;
            }
        </style>
        <title>Page Title</title>
        </head>
        <body>

        <h1 style="color:red;">This is a Heading</h1>
        <p id="hfd">This is a paragraph.</p>

        </body>
        </html>
    """.trimIndent())
    val w3cDom = W3CDom()
    val w3cDoc = w3cDom.fromJsoup(src);
    val media: MediaSpec = MediaSpecAll() //use styles for all media


//create the style map

//create the style map
    val map = CSSFactory.assignDOM(w3cDoc, "UTF-8", URL("https://learnpea.com/"), media, true)

//get the style of a single element

//get the style of a single element
    val div: Element = w3cDoc.getElementById("hfd") //choose a DOM element

    val style = map[div] //get the style map for the element

    Log.d("fdfdfdffdf45",style.toString())
    /*val src = """
        Create a list by starting a line with `tt`
    """.trimIndent()
    val flavour = CommonMarkFlavourDescriptor()
    var t:ASTNode = MarkdownParser(flavour).buildMarkdownTreeFromString(src)

    Log.d("fdfdfdfdf125","formatted\n\n"+t.formatted(src))*/
    /*Text(
        buildAnnotatedString {
            for(i in 0..10){
                withStyle(style = SpanStyle(color = Color.Blue)) {
                    append("H")
                }
                appendInlineContent()
            }
            *//*withStyle(style = SpanStyle(color = Color.Blue)) {
                append("H")
            }
            append("ello ")

            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Red)) {
                append("W")
            }
            append("orld")*//*
        }
    )*/
}

private fun convertStringToXMLDocument(xmlString: String): Document? {
    //Parser that produces DOM object trees from XML content
    val factory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()

    //API to obtain DOM Document instance
    var builder: DocumentBuilder? = null
    try {
        //Create DocumentBuilder with default configuration
        builder = factory.newDocumentBuilder()

        //Parse the content to Document object
        return builder.parse(InputSource(StringReader(xmlString)))
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

fun ASTNode.formatted(src: String):String{
    return type.name+
            ":"+
            src.substring(startOffset,endOffset)+
            "\n"+
            children.map {
                it.formatted(src)
                    .split("\n")
                    .map {
                        "\t"+it
                    }.joinToString("\n")
            }.joinToString("\n")
}