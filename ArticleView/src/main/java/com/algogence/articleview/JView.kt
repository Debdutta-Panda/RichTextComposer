package com.algogence.articleview

import android.util.Size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

data class NumberPair(
    val comp1: Number,
    val comp2: Number
)

object JConst{
    const val empty = ""
    const val type = "type"
    const val surface = "surface"
    const val text = "text"
    const val value = "value"
    const val name = "name"
    const val children = "children"
    const val modifiers = "modifiers"
    const val width = "width"
    const val height = "height"
    const val background = "background"
    const val size = "size"
    const val widthHeight = "widthHeight"
    const val widthIn = "widthIn"
    const val heightIn = "heightIn"
    const val min = "min"
    const val max = "max"
}
val J.viewType: String
get(){
    return get(JConst.type)?.asString()?:JConst.empty
}
val J.viewName: String
get(){
    return get(JConst.name)?.asString()?:JConst.empty
}
val J.viewValueWidthHeight: NumberPair
get(){
    val value = get(JConst.value)
    val c1 = value?.get(JConst.width)?.asNumber()?:0
    val c2 = value?.get(JConst.height)?.asNumber()?:0
    return NumberPair(c1,c2)
}
val J.viewValueMinMax: NumberPair
    get(){
        val value = get(JConst.value)
        val c1 = value?.get(JConst.min)?.asNumber()?:0
        val c2 = value?.get(JConst.max)?.asNumber()?:0
        return NumberPair(c1,c2)
    }

val J.viewValue: String
get(){
    return get(JConst.value)?.asString()?:""
}

val J.viewModifiers: J?
get(){
    return get(JConst.modifiers)
}
val J.viewValueNumber: Number
get(){
    val v = get("value")
    return when(v?.type){
        JBase.Type.FLOAT -> v.asFloat() ?:0f
        JBase.Type.INTEGER -> v.asInt() ?:0
        JBase.Type.LONG -> v.asLong() ?:0L
        JBase.Type.DOUBLE -> v.asDouble() ?:0.0
        else -> 0
    }
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

@Composable
fun J.forEachModifier(block: (J)-> Modifier): Modifier{
    val children = this[JConst.modifiers]?.asArray()?.children
    val count = children?.size?:0
    var e = Modifier
    var r = e.then(e)
    for(i in 0 until count){
        val child = children?.get(i)
        if(child!=null){
            val m = block(child)
            r = r.then(m)
        }
    }
    return r
}

val Number.dp: Dp
get(){
    return Dp(value = this.toFloat())
}

fun Color.Companion.parse(colorString: String): Color =
    Color(color = android.graphics.Color.parseColor(colorString))