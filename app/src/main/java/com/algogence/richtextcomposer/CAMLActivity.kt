package com.algogence.richtextcomposer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import org.json.JSONObject

class CAMLActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camlactivity)
        val src =
"""a
	p:
		1
		1.2
		1.2.2
		false
		null
        "123"
		v2
	b
	c"""
        val node = CNode.parse(src)
        Log.d("cnode_debug",node.toString())


    }
}

class CNode{
    var type: Type = Type.NONE
    var value: Any? = null
    private set
    private val _children = mutableListOf<CNode>()
    var isProperty = false
    private set

    val json: String
    get(){
        return ""
    }

    enum class Type{
        STRING,
        INT,
        FLOAT,
        BOOLEAN,
        NONE
    }

    val children: List<CNode>
    get(){
        return _children
    }

    companion object{
        private fun parseType(src: String):Type{
            val quote = '\"'
            val _false = "false"
            val _true = "true"
            val _null = "null"
            val digitsRange = '0'..'9'
            val dot = '.'
            if(src==""){
                return Type.STRING
            }
            if(src==_null){
                return Type.NONE
            }
            if(src.startsWith(quote)&&src.endsWith(quote)){
                return Type.STRING
            }
            if(src==_false||src==_true){
                return Type.BOOLEAN
            }
            var digitsCount = 0
            var dotCount = 0
            for(c in src){
                if(c in digitsRange){
                    ++digitsCount
                }
                else if(c == dot){
                    ++dotCount
                    if(dotCount>1){
                        return Type.STRING
                    }
                }
                else{
                    return Type.STRING
                }
            }
            if(digitsCount>0&&dotCount==0){
                return Type.INT
            }
            if(digitsCount>0&&dotCount==1){
                return Type.FLOAT
            }
            return Type.NONE
        }

        fun parse(src: String): CNode{
            val lines = src.split("\n")
            val map = mutableMapOf<Int,CNode>()
            lines.forEach {
                val node = CNode()
                val level = levelCount(it)
                var value = it.trim()
                node.isProperty = value.endsWith(":").apply{
                    value = value.removeSuffix(":")
                }
                node.type = parseType(value)
                node.value = try {
                    when(node.type){
                        Type.INT -> value.toInt()
                        Type.FLOAT -> value.toFloat()
                        Type.BOOLEAN -> value.toBoolean()
                        else-> value.removeSurrounding("\"")
                    }
                } catch (e: Exception) {
                    node.type = Type.NONE
                    null
                }
                map[level] = node
                if(level>0){
                    map[level - 1]?._children?.add(node)
                }
            }
            return map[0]?:CNode()
        }

        private fun levelCount(it: String): Int {
            var count = 0
            for(i in it.indices){
                val c = it[i]
                if(c.isWhitespace()){
                    ++count
                }
            }
            return count
        }
    }
}