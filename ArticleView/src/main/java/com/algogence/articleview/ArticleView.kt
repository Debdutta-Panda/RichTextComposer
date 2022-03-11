package com.algogence.articleview

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathNode
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.drawable.toBitmap
import com.algogence.articleview.svg.SVG
import com.algogence.articleview.svg.SVGImageView
import com.algogence.articleview.youtubevideoviewlibrary.YoutubeView
import com.google.accompanist.flowlayout.*
import com.google.android.material.R
import com.skydoves.landscapist.glide.GlideImage
import ru.noties.jlatexmath.JLatexMathDrawable

@Composable
fun renderView(
    j: J,
    scope: Any? = null
) {
    when(j.viewType){
        JConst.latex->{
            val d = getLatexDrawable(j.viewValue)
            Image(
                bitmap = d.toBitmap().asImageBitmap(),
                contentDescription = null,
                modifier = composeModifier(j,scope),
            )
        }
        JConst.youtube->{
            Box(
                modifier = composeModifier(j,scope),
                contentAlignment = Alignment.Center
            ){
                GlideImage(
                    imageModel = j[JConst.thumbnail]?.asString(),
                    contentScale = ContentScale.Crop,
                    modifier = composeModifier(j,scope)
                )
                val context = LocalContext.current
                IconButton(
                    onClick = { onPlayClick(context,j.viewUrl) },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.PlayArrow,
                        tint = Color.Red,
                        contentDescription = "Play",
                        modifier = Modifier.size(64.dp)
                    )
                }
            }
        }
        JConst.svg->{
            AndroidView(
                modifier = composeModifier(j,scope),
                factory = { context ->
                    // Creates custom view
                    SVGImageView(context).apply {
                        setSVG(SVG.getFromString("""
<svg xmlns="http://www.w3.org/2000/svg" width="96.608px" height="33.616px" viewBox="0 -1149.5 5337.6 1857.5" xmlns:xlink="http://www.w3.org/1999/xlink" style=""><defs><path id="MJX-6-TEX-I-78" d="M52 289Q59 331 106 386T222 442Q257 442 286 424T329 379Q371 442 430 442Q467 442 494 420T522 361Q522 332 508 314T481 292T458 288Q439 288 427 299T415 328Q415 374 465 391Q454 404 425 404Q412 404 406 402Q368 386 350 336Q290 115 290 78Q290 50 306 38T341 26Q378 26 414 59T463 140Q466 150 469 151T485 153H489Q504 153 504 145Q504 144 502 134Q486 77 440 33T333 -11Q263 -11 227 52Q186 -10 133 -10H127Q78 -10 57 16T35 71Q35 103 54 123T99 143Q142 143 142 101Q142 81 130 66T107 46T94 41L91 40Q91 39 97 36T113 29T132 26Q168 26 194 71Q203 87 217 139T245 247T261 313Q266 340 266 352Q266 380 251 392T217 404Q177 404 142 372T93 290Q91 281 88 280T72 278H58Q52 284 52 289Z"></path><path id="MJX-6-TEX-N-3D" d="M56 347Q56 360 70 367H707Q722 359 722 347Q722 336 708 328L390 327H72Q56 332 56 347ZM56 153Q56 168 72 173H708Q722 163 722 153Q722 140 707 133H70Q56 140 56 153Z"></path><path id="MJX-6-TEX-N-73" d="M295 316Q295 356 268 385T190 414Q154 414 128 401Q98 382 98 349Q97 344 98 336T114 312T157 287Q175 282 201 278T245 269T277 256Q294 248 310 236T342 195T359 133Q359 71 321 31T198 -10H190Q138 -10 94 26L86 19L77 10Q71 4 65 -1L54 -11H46H42Q39 -11 33 -5V74V132Q33 153 35 157T45 162H54Q66 162 70 158T75 146T82 119T101 77Q136 26 198 26Q295 26 295 104Q295 133 277 151Q257 175 194 187T111 210Q75 227 54 256T33 318Q33 357 50 384T93 424T143 442T187 447H198Q238 447 268 432L283 424L292 431Q302 440 314 448H322H326Q329 448 335 442V310L329 304H301Q295 310 295 316Z"></path><path id="MJX-6-TEX-N-69" d="M69 609Q69 637 87 653T131 669Q154 667 171 652T188 609Q188 579 171 564T129 549Q104 549 87 564T69 609ZM247 0Q232 3 143 3Q132 3 106 3T56 1L34 0H26V46H42Q70 46 91 49Q100 53 102 60T104 102V205V293Q104 345 102 359T88 378Q74 385 41 385H30V408Q30 431 32 431L42 432Q52 433 70 434T106 436Q123 437 142 438T171 441T182 442H185V62Q190 52 197 50T232 46H255V0H247Z"></path><path id="MJX-6-TEX-N-6E" d="M41 46H55Q94 46 102 60V68Q102 77 102 91T102 122T103 161T103 203Q103 234 103 269T102 328V351Q99 370 88 376T43 385H25V408Q25 431 27 431L37 432Q47 433 65 434T102 436Q119 437 138 438T167 441T178 442H181V402Q181 364 182 364T187 369T199 384T218 402T247 421T285 437Q305 442 336 442Q450 438 463 329Q464 322 464 190V104Q464 66 466 59T477 49Q498 46 526 46H542V0H534L510 1Q487 2 460 2T422 3Q319 3 310 0H302V46H318Q379 46 379 62Q380 64 380 200Q379 335 378 343Q372 371 358 385T334 402T308 404Q263 404 229 370Q202 343 195 315T187 232V168V108Q187 78 188 68T191 55T200 49Q221 46 249 46H265V0H257L234 1Q210 2 183 2T145 3Q42 3 33 0H25V46H41Z"></path><path id="MJX-6-TEX-N-2061" d=""></path><path id="MJX-6-TEX-LO-28" d="M180 96T180 250T205 541T266 770T353 944T444 1069T527 1150H555Q561 1144 561 1141Q561 1137 545 1120T504 1072T447 995T386 878T330 721T288 513T272 251Q272 133 280 56Q293 -87 326 -209T399 -405T475 -531T536 -609T561 -640Q561 -643 555 -649H527Q483 -612 443 -568T353 -443T266 -270T205 -41Z"></path><path id="MJX-6-TEX-I-3C0" d="M132 -11Q98 -11 98 22V33L111 61Q186 219 220 334L228 358H196Q158 358 142 355T103 336Q92 329 81 318T62 297T53 285Q51 284 38 284Q19 284 19 294Q19 300 38 329T93 391T164 429Q171 431 389 431Q549 431 553 430Q573 423 573 402Q573 371 541 360Q535 358 472 358H408L405 341Q393 269 393 222Q393 170 402 129T421 65T431 37Q431 20 417 5T381 -10Q370 -10 363 -7T347 17T331 77Q330 86 330 121Q330 170 339 226T357 318T367 358H269L268 354Q268 351 249 275T206 114T175 17Q164 -11 132 -11Z"></path><path id="MJX-6-TEX-N-36" d="M42 313Q42 476 123 571T303 666Q372 666 402 630T432 550Q432 525 418 510T379 495Q356 495 341 509T326 548Q326 592 373 601Q351 623 311 626Q240 626 194 566Q147 500 147 364L148 360Q153 366 156 373Q197 433 263 433H267Q313 433 348 414Q372 400 396 374T435 317Q456 268 456 210V192Q456 169 451 149Q440 90 387 34T253 -22Q225 -22 199 -14T143 16T92 75T56 172T42 313ZM257 397Q227 397 205 380T171 335T154 278T148 216Q148 133 160 97T198 39Q222 21 251 21Q302 21 329 59Q342 77 347 104T352 209Q352 289 347 316T329 361Q302 397 257 397Z"></path><path id="MJX-6-TEX-LO-29" d="M35 1138Q35 1150 51 1150H56H69Q113 1113 153 1069T243 944T330 771T391 541T416 250T391 -40T330 -270T243 -443T152 -568T69 -649H56Q43 -649 39 -647T35 -637Q65 -607 110 -548Q283 -316 316 56Q324 133 324 251Q324 368 316 445Q278 877 48 1123Q36 1137 35 1138Z"></path></defs><g stroke="currentColor" fill="currentColor" stroke-width="0" transform="matrix(1 0 0 -1 0 0)"><g data-mml-node="math"><g data-mml-node="mi"><use xlink:href="#MJX-6-TEX-I-78"></use></g><g data-mml-node="mo" transform="translate(849.8, 0)"><use xlink:href="#MJX-6-TEX-N-3D"></use></g><g data-mml-node="mi" transform="translate(1905.6, 0)"><use xlink:href="#MJX-6-TEX-N-73"></use><use xlink:href="#MJX-6-TEX-N-69" transform="translate(394, 0)"></use><use xlink:href="#MJX-6-TEX-N-6E" transform="translate(672, 0)"></use></g><g data-mml-node="mo" transform="translate(3133.6, 0)"><use xlink:href="#MJX-6-TEX-N-2061"></use></g><g data-mml-node="mrow" transform="translate(3133.6, 0)"><g data-mml-node="mo"><use xlink:href="#MJX-6-TEX-LO-28"></use></g><g data-mml-node="mfrac" transform="translate(597, 0)"><g data-mml-node="mi" transform="translate(220, 676)"><use xlink:href="#MJX-6-TEX-I-3C0"></use></g><g data-mml-node="mn" transform="translate(255, -686)"><use xlink:href="#MJX-6-TEX-N-36"></use></g><rect width="770" height="60" x="120" y="220"></rect></g><g data-mml-node="mo" transform="translate(1607, 0)"><use xlink:href="#MJX-6-TEX-LO-29"></use></g></g></g></g></svg>
                        """.trimIndent()))
                    }
                },
                update = { view ->
                    // View's been inflated or state read in this block has been updated
                    // Add logic here if necessary

                    // As selectedItem is read here, AndroidView will recompose
                    // whenever the state changes
                    // Example of Compose -> View communication
                    //view.coordinator.selectedItem = selectedItem.value
                }
            )
        }
        JConst.surface-> Surface(
            modifier = composeModifier(j,scope)
        ){
            j.forEachChildView {
                renderView(it)
            }
        }
        JConst.text-> {
            Text(
                j.viewValue,
                modifier = composeModifier(j, scope),
                color = getColor(j),
                fontSize = getFontSize(j),
                fontStyle = getFontStyle(j),
                fontWeight = getFontWeight(j),
                letterSpacing = getLetterSpacing(j),
                textDecoration = getTextDecoration(j),
                textAlign = getTextAlign(j),
                lineHeight = getLineHeight(j),
                overflow = getTextOverflow(j),
                softWrap = getSoftWrap(j),
                maxLines = getMaxLinesCount(j),
                fontFamily = getFontFamily(j)
            )
        }
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

fun getLatexDrawable(viewValue: String): Drawable {
    return JLatexMathDrawable
        .builder(viewValue)
        .textSize(100f)
        .build()
}

fun onPlayClick(context: Context, viewUrl: String) {

    val intent = Intent()
    intent.setClassName(
        context,
        "com.algogence.articleview.youtubevideoviewlibrary.VideoActivity"
    ).apply {
        putExtra("url", viewUrl)
    }
    context.startActivity(intent)
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
        textDecoration = getTextDecoration(j),
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

fun getMainAxisSize(j: J): com.google.accompanist.flowlayout.SizeMode {
    return when(j[JConst.mainAxisSize]?.asString()?:""){
        JConst.expand->com.google.accompanist.flowlayout.SizeMode.Expand
        else->com.google.accompanist.flowlayout.SizeMode.Wrap
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

fun getTextDecoration(j: J): TextDecoration {
    val value = j[JConst.textDecoration]?.asInt()?:0
    val list = mutableListOf<TextDecoration>()
    if(1 and value == 1){
        list.add(TextDecoration.Underline)
    }
    if(2 and value == 2){
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
    var value = j[JConst.fontSize]?.asNumber(-1)
    if(value==null){
        value = -1
    }
    return if(value==-1){
        TextUnit.Unspecified
    }
    else{
        value.toFloat().sp
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
