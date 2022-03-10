package com.algogence.articleview

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathNode
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.*
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun renderView(
    j: J,
    scope: Any? = null
) {
    when(j.viewType){
        JConst.surface-> Surface(
            modifier = composeModifier(j,scope)
        ){
            j.forEachChildView {
                renderView(it)
            }
        }
        JConst.text-> Text(
            j.viewValue,
            modifier = composeModifier(j, scope),
            color = getColor(j),
            fontSize = getFontSize(j),
            fontStyle = getFontStyle(j),
            fontWeight = getFontWeight(j),
            letterSpacing = getLetterSpacing(j),
            textDecoration = getTextDecoraton(j),
            textAlign = getTextAlign(j),
            lineHeight = getLineHeight(j),
            overflow = getTextOverflow(j),
            softWrap = getSoftWrap(j),
            maxLines = getMaxLinesCount(j),
            fontFamily = getFontFamily(j)
        )
        JConst.image-> GlideImage(
            imageModel = j.viewUrl,
            contentScale = getContentScale(j.viewContentScale),
            modifier = composeModifier(j, scope)
        )
        JConst.column-> Column(
            modifier = composeModifier(j, scope),
            verticalArrangement = getVerticalArrangement(j),
            horizontalAlignment = getHorizontalAlignment(j)
        ) {
            j.forEachChildView {
                renderView(it,this)
            }
        }
        JConst.row-> Row(
            modifier = composeModifier(j, scope),
            horizontalArrangement = getHorizontalArrangement(j),
            verticalAlignment = getVerticalAlignment(j)
        ) {
            j.forEachChildView {
                renderView(it,this)
            }
        }
        JConst.card-> Card(
            modifier = composeModifier(j, scope),
            shape = getCardShape(j),
            backgroundColor = j.viewBackgroundColor,
            contentColor = j.viewContentColor,
            elevation = j.viewElevation.dp,
            border = getBorderStroke(j)
        ){
            j.forEachChildView {
                renderView(it)
            }
        }
        JConst.box-> Box(
            modifier = composeModifier(j, scope),
            contentAlignment = getContentAlignment(j)
        ){
            j.forEachChildView {
                renderView(it,this)
            }
        }
        JConst.icon-> Icon(
            imageVector = getIcon(j),
            contentDescription = "Icon",
            modifier = composeModifier(j, scope),
            tint = getTint(j)
        )
        JConst.flowRow-> {
            val mainAxisAlignment = getMainAxisAlignment(j)
            FlowRow(
                modifier = composeModifier(j, scope),
                mainAxisSize = getMainAxisSize(j),
                mainAxisAlignment = mainAxisAlignment,
                mainAxisSpacing = getMainAxisSpacing(j),
                crossAxisAlignment = getCrossAxisAlignment(j),
                crossAxisSpacing = getCrossAxisSpacing(j),
                lastLineMainAxisAlignment = getLastLineMainAxisAlignment(j,mainAxisAlignment)
            ){
                j.forEachChildView {
                    renderView(it)
                }
            }
        }
        JConst.flowColumn-> {
            val mainAxisAlignment = getMainAxisAlignment(j)
            FlowColumn(
                modifier = composeModifier(j, scope),
                mainAxisSize = getMainAxisSize(j),
                mainAxisAlignment = mainAxisAlignment,
                mainAxisSpacing = getMainAxisSpacing(j),
                crossAxisAlignment = getCrossAxisAlignment(j),
                crossAxisSpacing = getCrossAxisSpacing(j),
                lastLineMainAxisAlignment = getLastLineMainAxisAlignment(j,mainAxisAlignment)
            ){
                j.forEachChildView {
                    renderView(it)
                }
            }
        }
        JConst.richText-> {
            Text(
                buildAnnotatedString {
                    j.forEachChildView {
                        withStyle(style = getSpanStyle(it)) {
                            append(j.viewValue)
                        }
                    }
                },
                modifier = composeModifier(j, scope),
            )
        }
        JConst.selectionContainer-> {
            SelectionContainer(modifier = composeModifier(j, scope),) {
                j.forEachChildView {
                    renderView(it)
                }
            }
        }
        JConst.disableSelection-> {
            DisableSelection {
                j.forEachChildView {
                    renderView(it)
                }
            }
        }
        JConst.lazyRow-> {
            LazyRow(
                modifier = composeModifier(j, scope),
                contentPadding = getPaddingValues(j),
                reverseLayout = isReverseLayout(j),
                horizontalArrangement = getHorizontalArrangement(j),
                verticalAlignment = getVerticalAlignment(j),
                userScrollEnabled = isUserScrollEnabled(j)
            ){
                items(j.children){
                    renderView(it,this)
                }
            }
        }
        JConst.lazyColumn-> {
            LazyColumn(
                modifier = composeModifier(j, scope),
                contentPadding = getPaddingValues(j),
                reverseLayout = isReverseLayout(j),
                verticalArrangement = getVerticalArrangement(j),
                horizontalAlignment = getHorizontalAlignment(j),
                userScrollEnabled = isUserScrollEnabled(j)
            ){
                items(j.children){
                    renderView(it,this)
                }
            }
        }
    }
}

fun isUserScrollEnabled(j: J): Boolean {
    return j[JConst.userScrollEnabled]?.asBoolean()?:false
}

fun isReverseLayout(j: J): Boolean {
    return j[JConst.reverseLayout]?.asBoolean()?:false
}

fun getPaddingValues(j: J): PaddingValues {
    val jj = j[JConst.padding] ?: return PaddingValues()
    return PaddingValues(
        start = getStart(jj),
        top = getTop(jj),
        end = getEnd(jj),
        bottom = getBottom(jj),
    )
}

fun getStart(jj: J): Dp {
    return (jj[JConst.start]?.asNumber()?:0).dp
}

fun getTop(jj: J): Dp {
    return (jj[JConst.top]?.asNumber()?:0).dp
}

fun getEnd(jj: J): Dp {
    return (jj[JConst.end]?.asNumber()?:0).dp
}

fun getBottom(jj: J): Dp {
    return (jj[JConst.bottom]?.asNumber()?:0).dp
}

fun getSpanStyle(j: J): SpanStyle {
    return SpanStyle(
        color = getColor(j),
        fontSize = getFontSize(j),
        fontWeight = getFontWeight(j),
        fontStyle = getFontStyle(j),
        fontFamily = getFontFamily(j),
        letterSpacing = getLetterSpacing(j),
        background = getBackground(j),
        textDecoration = getTextDecoraton(j),
        shadow = getShadow(j),
    )
}

fun getShadow(j: J): Shadow? {
    val jj = j[JConst.shadow] ?: return null
    return Shadow(
        color = getColor(jj),
        offset = getOffset(jj),
        blurRadius = getBlurRadius(jj)
    )
}

fun getBlurRadius(jj: J): Float {
    return jj[JConst.blurRadius]?.asNumber()?.toFloat()?:0f
}

fun getOffset(jj: J): Offset {
    val j = jj[JConst.offset]?:return Offset.Unspecified
    return Offset(
        x = getX(j),
        y = getY(j),
    )
}

fun getX(j: J): Float {
    return j[JConst.x]?.asNumber()?.toFloat()?:0f
}

fun getY(j: J): Float {
    return j[JConst.y]?.asNumber()?.toFloat()?:0f
}

fun getBackground(j: J): Color {
    return Color.parse(j[JConst.background]?.asString()?:"")
}

fun getFontFamily(j: J): FontFamily? {
    return when(j[JConst.fontFamily]?.asString()){
        JConst.default-> FontFamily.Default
        JConst.sansSerif-> FontFamily.SansSerif
        JConst.serif-> FontFamily.Serif
        JConst.monospace-> FontFamily.Monospace
        JConst.cursive-> FontFamily.Cursive
        else->null
    }
}

fun getLastLineMainAxisAlignment(j: J, default: FlowMainAxisAlignment): FlowMainAxisAlignment {
    return when(j[JConst.lastLineMainAxisAlignment]?.asString()?:""){
        JConst.center->MainAxisAlignment.Center
        JConst.end->MainAxisAlignment.End
        JConst.spaceEvenly->MainAxisAlignment.SpaceEvenly
        JConst.spaceBetween->MainAxisAlignment.SpaceBetween
        JConst.spaceAround->MainAxisAlignment.SpaceAround
        JConst.start->MainAxisAlignment.Start
        else->default
    }
}

fun getCrossAxisSpacing(j: J): Dp {
    return (j[JConst.crossAxisSpacing]?.asNumber()?:0).dp
}

fun getCrossAxisAlignment(j: J): FlowCrossAxisAlignment {
    return when(j[JConst.crossAxisAlignment]?.asString()?:""){
        JConst.center->FlowCrossAxisAlignment.Center
        JConst.end->FlowCrossAxisAlignment.End
        else->FlowCrossAxisAlignment.Start
    }
}

fun getMainAxisSpacing(j: J): Dp {
    return (j[JConst.mainAxisSpacing]?.asNumber()?:0).dp
}

fun getMainAxisAlignment(j: J): FlowMainAxisAlignment {
    return when(j[JConst.mainAxisAlignment]?.asString()?:""){
        JConst.center->MainAxisAlignment.Center
        JConst.end->MainAxisAlignment.End
        JConst.spaceEvenly->MainAxisAlignment.SpaceEvenly
        JConst.spaceBetween->MainAxisAlignment.SpaceBetween
        JConst.spaceAround->MainAxisAlignment.SpaceAround
        else->MainAxisAlignment.Start
    }
}

fun getMainAxisSize(j: J): SizeMode {
    return when(j[JConst.mainAxisSize]?.asString()?:""){
        JConst.expand->SizeMode.Expand
        else->SizeMode.Wrap
    }
}

fun getMaxLinesCount(j: J): Int {
    return j[JConst.maxLines]?.asInt()?:Int.MAX_VALUE
}

fun getSoftWrap(j: J): Boolean {
    return j[JConst.softWrap]?.asBoolean()?:true
}

fun getTextOverflow(j: J): TextOverflow {
    return when(j[JConst.textOverflow]?.asString()){
        JConst.ellipsis->TextOverflow.Ellipsis
        JConst.visible->TextOverflow.Visible
        else->TextOverflow.Clip
    }
}

fun getLineHeight(j: J): TextUnit {
    val value = j[JConst.lineHeight]?.asNumber(-1)?:-1
    return if(value==-1){
        TextUnit.Unspecified
    }
    else{
        value.toFloat().sp
    }
}

fun getTextAlign(j: J): TextAlign? {
    val value = j[JConst.textAlign]?.asString()?:""
    return when(value){
        JConst.left->TextAlign.Left
        JConst.right->TextAlign.Right
        JConst.center->TextAlign.Center
        JConst.justify->TextAlign.Justify
        JConst.start->TextAlign.Start
        JConst.end->TextAlign.End
        else->null
    }
}

fun getTextDecoraton(j: J): TextDecoration? {
    val value = j[JConst.textDecoration]?.asInt()?:0
    val list = mutableListOf<TextDecoration>()
    if(1 or value == 1){
        list.add(TextDecoration.Underline)
    }
    if(1 or value == 2){
        list.add(TextDecoration.LineThrough)
    }
    return TextDecoration.combine(list)
}

fun getLetterSpacing(j: J): TextUnit {
    val value = j[JConst.letterSpacing]?.asNumber(-1)
    return if(value==-1){
        TextUnit.Unspecified
    }
    else{
        (value?.toFloat()?:0f).sp
    }
}

fun getFontWeight(j: J): FontWeight? {
    val value = (j[JConst.fontWeight]?.asNumber(-1)?:-1).toInt()
    if(value in 0..1000){
        return FontWeight(value)
    }
    else{
        return null
    }
}

fun getFontStyle(j: J): FontStyle? {
    return when(j[JConst.fontStyle]?.asString()){
        JConst.italic->FontStyle.Italic
        JConst.normal->FontStyle.Normal
        else->null
    }
}

fun getFontSize(j: J): TextUnit {
    val value = j[JConst.fontSize]?.asNumber(-1)
    return if(value==-1){
        TextUnit.Unspecified
    }
    else{
        (value?.toFloat()?:0f).sp
    }
}

fun getColor(j: J): Color {
    return Color.parse(j[JConst.color]?.asString()?:"")
}

fun getTint(j: J): Color {
    return Color.parse(j[JConst.tint]?.asString()?:"")
}

fun getIcon(j: J): ImageVector {
    val pathData = j[JConst.icon]?.asString()?:""
    val b = PathParser().parsePathString(pathData).toNodes()
    return materialIcon("parsed_icon"){
        materialPath{
            b.forEach {
                when(it){
                    is PathNode.RelativeMoveTo->{
                        moveToRelative(it.dx, it.dy)
                    }
                    is PathNode.MoveTo->{
                        moveTo(it.x, it.y)
                    }
                    is PathNode.RelativeLineTo->{
                        lineToRelative(it.dx, it.dy)
                    }
                    is PathNode.LineTo->{
                        lineTo(it.x,it.y)
                    }
                    is PathNode.RelativeHorizontalTo->{
                        horizontalLineToRelative(it.dx)
                    }
                    is PathNode.HorizontalTo->{
                        horizontalLineTo(it.x)
                    }
                    is PathNode.RelativeVerticalTo->{
                        verticalLineToRelative(it.dy)
                    }
                    is PathNode.VerticalTo->{
                        verticalLineTo(it.y)
                    }
                    is PathNode.RelativeCurveTo->{
                        curveToRelative(
                            it.dx1,
                            it.dy1,
                            it.dx2,
                            it.dy2,
                            it.dx3,
                            it.dy3,
                        )
                    }
                    is PathNode.CurveTo->{
                        curveTo(
                            it.x1,
                            it.y1,
                            it.x2,
                            it.y2,
                            it.x3,
                            it.y3,
                        )
                    }
                    is PathNode.RelativeReflectiveCurveTo->{
                        reflectiveCurveToRelative(
                            it.dx1,
                            it.dy1,
                            it.dx2,
                            it.dy2,
                        )
                    }
                    is PathNode.ReflectiveCurveTo->{
                        reflectiveCurveTo(
                            it.x1,
                            it.y1,
                            it.x2,
                            it.y2,
                        )
                    }
                    is PathNode.RelativeQuadTo->{
                        quadToRelative(
                            it.dx1,
                            it.dy1,
                            it.dx2,
                            it.dy2,
                        )
                    }
                    is PathNode.QuadTo->{
                        quadTo(
                            it.x1,
                            it.y1,
                            it.x2,
                            it.y2,
                        )
                    }
                    is PathNode.RelativeReflectiveQuadTo->{
                        reflectiveQuadToRelative(
                            it.dx,
                            it.dy
                        )
                    }
                    is PathNode.ReflectiveQuadTo->{
                        reflectiveQuadTo(
                            it.x,
                            it.y
                        )
                    }
                    is PathNode.RelativeArcTo->{
                        arcToRelative(
                            it.horizontalEllipseRadius,
                            it.verticalEllipseRadius,
                            it.theta,
                            it.isMoreThanHalf,
                            it.isPositiveArc,
                            it.arcStartDx,
                            it.arcStartDy
                        )
                    }
                    is PathNode.ArcTo->{
                        arcTo(
                            it.horizontalEllipseRadius,
                            it.verticalEllipseRadius,
                            it.theta,
                            it.isMoreThanHalf,
                            it.isPositiveArc,
                            it.arcStartX,
                            it.arcStartY,
                        )
                    }
                    is PathNode.Close->{
                        close()
                    }
                    //else->nothing
                }
            }
        }
    }
}



fun getContentAlignment(j: J): Alignment {
    val contentAlignment = j[JConst.contentAlignment]?.asString()
    return when(contentAlignment){
        JConst.topstart->Alignment.TopStart
        JConst.topcenter->Alignment.TopCenter
        JConst.topend->Alignment.TopEnd
        JConst.centerstart->Alignment.CenterStart
        JConst.center->Alignment.Center
        JConst.centerend->Alignment.CenterEnd
        JConst.bottomstart->Alignment.BottomStart
        JConst.bottomcenter->Alignment.BottomCenter
        JConst.bottomend->Alignment.BottomEnd
        else->Alignment.TopStart
    }
}

fun getBorderStroke(j: J): BorderStroke? {
    val border = j[JConst.border]
    if(border!=null){
        val color = border[JConst.color]?.asString()?:"#ffffff"
        val col = Color.parse(color)
        val strokeWidth = border[JConst.stroke]?.asNumber()?:0
        return BorderStroke(strokeWidth.dp,col)
    }
    return BorderStroke(0.dp,Color.Transparent)
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
typealias BoxScopeBlock = BoxScope.() ->Modifier?
typealias LazyItemScopeBlock = LazyItemScope.() ->Modifier?

fun ColumnScopeModifier(scope: Any?,block: ColumnScopeBlock): Modifier?{
    if(scope is ColumnScope){
        return block(scope)
    }
    return null
}
fun LazyItemScopeModifier(scope: Any?,block: LazyItemScopeBlock): Modifier?{
    if(scope is LazyItemScope){
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
fun BoxScopeModifier(scope: Any?,block: BoxScopeBlock): Modifier?{
    if(scope is BoxScope){
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
            JConst.lazyItemScopeFillParentMaxSize->{
                LazyItemScopeModifier(scope){
                    Modifier.fillParentMaxSize(getFraction(j))
                }
            }
            JConst.lazyItemScopeFillParentMaxWidth->{
                LazyItemScopeModifier(scope){
                    Modifier.fillParentMaxWidth(getFraction(j))
                }
            }
            JConst.lazyItemScopeFillParentMaxHeight->{
                LazyItemScopeModifier(scope){
                    Modifier.fillParentMaxHeight(getFraction(j))
                }
            }
            JConst.columnScopeAlign->{
                ColumnScopeModifier(scope){
                    Modifier.align(getHorizontalAlignment(it.viewValue))
                }
            }
            JConst.boxScopeAlign->{
                BoxScopeModifier(scope){
                    Modifier.align(get2dAlignment(it.viewValue))
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

fun getFraction(j: J): Float {
    return j[JConst.fraction]?.asFloat()?:1f
}

fun get2dAlignment(viewValue: String): Alignment {
    return when(viewValue){
        JConst.topstart->Alignment.TopStart
        JConst.topcenter->Alignment.TopCenter
        JConst.topend->Alignment.TopEnd
        JConst.centerstart->Alignment.CenterStart
        JConst.center->Alignment.Center
        JConst.centerend->Alignment.CenterEnd
        JConst.bottomstart->Alignment.BottomStart
        JConst.bottomcenter->Alignment.BottomCenter
        JConst.bottomend->Alignment.BottomEnd
        else->Alignment.TopStart
    }
}
