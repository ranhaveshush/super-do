package com.example.baseapp.api.superdo

import org.json.JSONObject

data class GroceryResponse(
    val bagColor: String,
    val weight: String,
    val name: String
) {
    companion object {
        fun fromJson(jsonObject: JSONObject) = GroceryResponse(
            jsonObject.getString("bagColor"),
            jsonObject.getString("weight"),
            jsonObject.getString("name")
        )
    }
}
