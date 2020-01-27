package com.example.baseapp.vo

import org.json.JSONObject

data class Grocery(
    val bagColor: String,
    val weight: String,
    val name: String
) {
    companion object {
        fun fromJson(jsonObject: JSONObject) = Grocery(
            jsonObject.getString("bagColor"),
            jsonObject.getString("weight"),
            jsonObject.getString("name")
        )
    }
}
