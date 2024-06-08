package com.example.goalkeeper.model

data class UserRoutine(
    val routineNum: Int,
    val userId: Int,
    var routineName: String,
    var routineAlert: Boolean
)
{
    constructor():this(0,0,"noName", false)
}