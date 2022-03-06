package com.algogence.articleview

import org.json.JSONArray
import org.json.JSONObject

open class JBase{
    enum class Type{
        UNKNOWN,
        OBJECT,
        ARRAY,
        STRING,
        FLOAT,
        INTEGER,
        LONG,
        BOOLEAN,
        DOUBLE
    }
    var type: Type = Type.UNKNOWN
}
class JArray(private val jsonArray: JSONArray?): JBase() {
    init {
        type = Type.ARRAY
    }
}
class J(private val src: Any? = null, private val forceObject: Boolean = true): JBase() {
    var value: Any? = null
        private set
    private var json: JSONObject? = null
        private set
    private var array: JSONArray? = null
    init {
        json = try {
            when(src){
                is JSONArray->{
                    array = src
                    type = Type.ARRAY
                    null
                }
                is String->{
                    if(forceObject){
                        type = Type.OBJECT
                        JSONObject(src)
                    }
                    else{
                        value = src
                        type = Type.STRING
                        null
                    }
                }
                is Float->{
                    value = src
                    type = Type.FLOAT
                    null
                }
                is Long->{
                    value = src
                    type = Type.LONG
                    null
                }
                is Double->{
                    value = src
                    type = Type.DOUBLE
                    null
                }
                is Int->{
                    value = src
                    type = Type.INTEGER
                    null
                }
                is Boolean->{
                    value = src
                    type = Type.BOOLEAN
                    null
                }
                is JSONObject->{
                    type = Type.OBJECT
                    src
                }
                else->null
            }
        } catch (e: Exception) {
            null
        }
    }
    operator fun get(key: String): J{
        if(json?.has(key)==true){
            return J(json?.get(key)?:"",false)
        }
        return J()
    }

    fun asString(): String?{
        return if(type==Type.STRING){
            value as? String
        } else{
            null
        }
    }

    fun asInt(): Int?{
        return if(type==Type.INTEGER){
            value as? Int
        } else{
            null
        }
    }

    fun asFloat(): Float?{
        return if(type==Type.FLOAT){
            value as? Float
        } else{
            null
        }
    }

    fun asDouble(): Double?{
        return if(type==Type.DOUBLE){
            value as? Double
        } else{
            null
        }
    }

    fun asBoolean(): Boolean?{
        return if(type==Type.BOOLEAN){
            value as? Boolean
        } else{
            null
        }
    }

    fun asLong(): Long?{
        return if(type==Type.LONG){
            value as? Long
        } else{
            null
        }
    }

    fun asArray(): JArray?{
        return if(type==Type.ARRAY){
            JArray(array)
        } else{
            null
        }
    }
}