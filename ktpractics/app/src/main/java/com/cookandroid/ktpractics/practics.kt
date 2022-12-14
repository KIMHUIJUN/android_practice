package com.cookandroid.ktpractics
val data1 = 10
var data2 = 10
lateinit var data3 : String
fun main(){
    println("data1: $data1")
    println("data2: $data2")
    data2 = 20
    println("data2: $data2")
    data3 = "안녕하세요"
    println("$data3")
    fun sum(no: Int):Int{
        var sum = 0
        for (i in 1..no){
            sum += i
        }
        return sum
    }
    println("${sum(10)}")
}