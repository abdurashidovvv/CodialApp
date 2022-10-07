package com.abdurashidov.codial.models

import java.io.Serializable

data class Course(
    var id:Int=0,
    var name:String,
    var info:String
):Serializable
