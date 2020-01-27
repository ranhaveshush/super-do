package com.example.baseapp.api.superdo

import com.example.baseapp.vo.Grocery

fun GroceryResponse.toGrocery() = Grocery(
    name,
    weight,
    bagColor
)
