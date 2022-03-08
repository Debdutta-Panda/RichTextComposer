package com.algogence.articleview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun renderView(
    j: J,
    scope: Any? = null
) {
    when(j.viewType){
        JConst.surface->{
            Surface(
                modifier = composeModifier(j,scope)
            ){
                j.forEachChildView {
                    renderView(it)
                }
            }
        }
        JConst.text->{
            Text(
                j.viewValue,
                modifier = composeModifier(j, scope)
            )
        }
        JConst.image->{
            GlideImage(
                imageModel = j.viewUrl,
                contentScale = getContentScale(j.viewContentScale),
                modifier = composeModifier(j, scope)
            )
        }
        JConst.column->{
            Column(
                modifier = composeModifier(j, scope),
                verticalArrangement = getVerticalArrangement(j),
                horizontalAlignment = getHorizontalAlignment(j)
            ) {
                j.forEachChildView {
                    renderView(it,this)
                }
            }
        }
        JConst.row->{
            Row(
                modifier = composeModifier(j, scope),
                horizontalArrangement = getHorizontalArrangement(j),
                verticalAlignment = getVerticalAlignment(j)
            ) {
                j.forEachChildView {
                    renderView(it,this)
                }
            }
        }
        JConst.card->{
            Card(
                modifier = composeModifier(j, scope),
                shape = getCardShape(j),
                backgroundColor = j.viewBackgroundColor,
                contentColor = j.viewContentColor,
                elevation = j.viewElevation.dp
            ){
                j.forEachChildView {
                    renderView(it)
                }
            }
        }
    }
}


fun getCardShape(j: J): Shape {
    val shape = j.viewShape
    val name = shape?.viewName?:""
    return when(name){
        JConst.roundedCornerShape->{
            val value = shape?.viewValueNumber?:0
            RoundedCornerShape(value.dp)
        }
        JConst.circleShape->{
            CircleShape
        }
        JConst.roundedCornersShape->{
            val value = shape?.viewValueJ
            val corners = value?.viewValueCorners
            RoundedCornerShape(
                topStart = (corners?.comp1?:0).dp,
                topEnd = (corners?.comp2?:0).dp,
                bottomEnd = (corners?.comp3?:0).dp,
                bottomStart = (corners?.comp4?:0).dp,
            )
        }
        else -> RoundedCornerShape(0.dp)
    }
}

fun getHorizontalAlignment(value: String): Alignment.Horizontal{
    return when(value){
        JConst.end-> Alignment.End
        JConst.center-> Alignment.CenterHorizontally
        else -> Alignment.Start
    }
}

fun getHorizontalAlignment(j: J): Alignment.Horizontal {
    return getHorizontalAlignment(j.viewHorizontalAlignment)
}

fun getVerticalArrangement(j: J): Arrangement.Vertical {
    return when(j.viewVerticalArrangement){
        JConst.bottom->Arrangement.Bottom
        JConst.center->Arrangement.Center
        JConst.spaceAround->Arrangement.SpaceAround
        JConst.spaceBetween->Arrangement.SpaceBetween
        JConst.spaceEvenly->Arrangement.SpaceEvenly
        else->Arrangement.Top
    }
}

fun getVerticalAlignment(j: J): Alignment.Vertical {
    return getVerticalAlignment(j.viewVerticalAlignment)
}

fun getVerticalAlignment(value: String): Alignment.Vertical {
    return when(value){
        JConst.bottom->Alignment.Bottom
        JConst.center->Alignment.CenterVertically
        else->Alignment.Top
    }
}

fun getHorizontalArrangement(j: J): Arrangement.Horizontal {
    return when(j.viewHorizontalArrangement){
        JConst.center->Arrangement.Center
        JConst.end->Arrangement.End
        JConst.spaceAround->Arrangement.SpaceAround
        JConst.spaceBetween->Arrangement.SpaceBetween
        JConst.spaceEvenly->Arrangement.SpaceEvenly
        else->Arrangement.Start
    }
}

fun getContentScale(viewContentScale: String): ContentScale {
    return when(viewContentScale){
        JConst.crop->ContentScale.Crop
        JConst.fit->ContentScale.Fit
        JConst.fillHeight->ContentScale.FillHeight
        JConst.fillWidth->ContentScale.FillWidth
        JConst.inside->ContentScale.Inside
        JConst.fillBounds->ContentScale.FillBounds
        else ->ContentScale.None
    }
}

typealias ColumnScopeBlock = ColumnScope.() ->Modifier?
typealias RowScopeBlock = RowScope.() ->Modifier?

fun ColumnScopeModifier(scope: Any?,block: ColumnScopeBlock): Modifier?{
    if(scope is ColumnScope){
        return block(scope)
    }
    return null
}
fun RowScopeModifier(scope: Any?,block: RowScopeBlock): Modifier?{
    if(scope is RowScope){
        return block(scope)
    }
    return null
}

@Composable
fun composeModifier(j: J?, scope: Any?): Modifier {
    if(j==null){
        return Modifier
    }
    return j.forEachModifier{
        when(it.viewName){
            JConst.columnScopeAlign->{
                ColumnScopeModifier(scope){
                    Modifier.align(getHorizontalAlignment(it.viewValue))
                }
            }
            JConst.rowScopeAlign->{
                RowScopeModifier(scope){
                    Modifier.align(getVerticalAlignment(it.viewValue))
                }
            }
            JConst.columnScopeWeight->{
                ColumnScopeModifier(scope){
                    Modifier.weight(it.viewValueNumber.toFloat())
                }
            }
            JConst.rowScopeWeight->{
                RowScopeModifier(scope){
                    Modifier.weight(it.viewValueNumber.toFloat())
                }
            }
            JConst.width->{
                val value = it.viewValueNumber
                Modifier.width(value.dp)
            }
            JConst.requiredWidth->{
                val value = it.viewValueNumber
                Modifier.requiredWidth(value.dp)
            }
            JConst.requiredHeight->{
                val value = it.viewValueNumber
                Modifier.requiredHeight(value.dp)
            }
            JConst.height->{
                val value = it.viewValueNumber
                Modifier.height(value.dp)
            }
            JConst.size->{
                val value = it.viewValueNumber
                Modifier.size(value.dp)
            }
            JConst.requiredSize->{
                val value = it.viewValueNumber
                Modifier.requiredSize(value.dp)
            }
            JConst.widthHeight->{
                val value = it.viewValueWidthHeight
                Modifier.size(width = value.comp1.dp, height = value.comp2.dp)
            }
            JConst.requiredWidthHeight->{
                val value = it.viewValueWidthHeight
                Modifier.requiredSize(width = value.comp1.dp, height = value.comp2.dp)
            }
            JConst.widthIn->{
                val value = it.viewValueMinMax
                Modifier.widthIn(min = value.comp1.dp, max = value.comp2.dp)
            }
            JConst.requiredWidthIn->{
                val value = it.viewValueMinMax
                Modifier.requiredWidthIn(min = value.comp1.dp, max = value.comp2.dp)
            }
            JConst.requiredHeightIn->{
                val value = it.viewValueMinMax
                Modifier.requiredHeightIn(min = value.comp1.dp, max = value.comp2.dp)
            }
            JConst.sizeIn->{
                val value = it.viewValueSizeLimit
                Modifier.sizeIn(
                    minWidth = value.comp1.dp,
                    minHeight = value.comp2.dp,
                    maxWidth = value.comp3.dp,
                    maxHeight = value.comp4.dp
                )
            }
            JConst.requiredSizeIn->{
                val value = it.viewValueSizeLimit
                Modifier.sizeIn(
                    minWidth = value.comp1.dp,
                    minHeight = value.comp2.dp,
                    maxWidth = value.comp3.dp,
                    maxHeight = value.comp4.dp
                )
            }
            JConst.heightIn->{
                val value = it.viewValueMinMax
                Modifier.heightIn(min = value.comp1.dp, max = value.comp2.dp)
            }
            JConst.background->{
                val value = it.viewValue
                Modifier.background(Color.parse(value))
            }
            JConst.fillMaxWidth->{
                Modifier.fillMaxWidth()
            }
            JConst.fillMaxHeight->{
                Modifier.fillMaxHeight()
            }
            JConst.fillMaxSize->{
                Modifier.fillMaxSize()
            }
            JConst.wrapContentWidth->{
                Modifier.wrapContentWidth()
            }
            JConst.wrapContentHeight->{
                Modifier.wrapContentHeight()
            }
            JConst.wrapContentSize->{
                Modifier.wrapContentSize()
            }
            JConst.defaultMinSize->{
                val value = it.viewValueMinWidthHeight
                Modifier.defaultMinSize(
                    minWidth = value.comp1.dp,
                    minHeight = value.comp2.dp
                )
            }
            JConst.padding->{
                val value = it.viewValueSides
                Modifier.padding(
                    start = value.comp1.dp,
                    top = value.comp2.dp,
                    end = value.comp3.dp,
                    bottom = value.comp4.dp,
                )
            }
            JConst.paddingSymmetric->{
                val value = it.viewValueSymmetric
                Modifier.padding(
                    horizontal = value.comp1.dp,
                    vertical = value.comp2.dp,
                )
            }
            JConst.paddingAll->{
                val value = it.viewValueNumber
                Modifier.padding(
                    value.dp,
                )
            }
            JConst.offset->{
                val value = it.viewValueXY
                Modifier.offset(
                    x = value.comp1.dp,
                    y = value.comp2.dp,
                )
            }
            JConst.absoluteOffset->{
                val value = it.viewValueXY
                Modifier.absoluteOffset(
                    x = value.comp1.dp,
                    y = value.comp2.dp,
                )
            }
            JConst.paddingAbsolute->{
                val value = it.viewValueSidesAbsolute
                Modifier.absolutePadding(
                    left = value.comp1.dp,
                    top = value.comp1.dp,
                    right = value.comp1.dp,
                    bottom = value.comp1.dp,
                )
            }
            else -> Modifier
        }
    }
}