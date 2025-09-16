package com.example.miseryreminder.ui

import android.content.Context
import android.widget.Toast

fun toast(message: String, ctx: Context){
    Toast.makeText(ctx,message, Toast.LENGTH_LONG).show()
}