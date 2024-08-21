package cn.taskeren.op.utils

fun Int?.notNullAndLessThan(other: Int) = this != null && this < other
fun Int?.notNullAndLessThanOrEquals(other: Int) = this != null && this <= other
