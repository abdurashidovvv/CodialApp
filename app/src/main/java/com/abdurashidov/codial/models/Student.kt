package com.abdurashidov.codial.models

data class Student(
    var id:Int?=null,
    var name:String?=null,
    var surname:String?=null,
    var phone:String?=null,
    var day:String?=null,
    var group: Group?=null
)
