package com.algogence.articleview

import androidx.compose.runtime.Composable

object JConst{
    const val empty = ""
    const val type = "type"
    const val surface = "surface"
    const val text = "text"
    const val value = "value"
    const val children = "children"
}
val J.viewType: String
get(){
    return get(JConst.type)?.asString()?:JConst.empty
}

val J.viewValue: String
get(){
    return get("value")?.asString()?:""
}

@Composable
fun J.forEachChildView(block: @Composable (J)->Unit){
    val children = this[JConst.children]?.asArray()?.children
    val count = children?.size?:0
    for(i in 0 until count){
        val child = children?.get(i)
        if(child!=null){
            block(child)
        }
    }
}