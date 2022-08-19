package com.example.game2048

public class Constant {
    val numUnitList = mutableListOf(
        numUnit(2,R.color.white,R.color.purple_700),
        numUnit(4,R.color.black,R.color.purple_200),
        numUnit(8,R.color.purple_700,R.color.white),
        numUnit(16,R.color.purple_200,R.color.teal_200)
    )
}

val constantManager = Constant()

data class numUnit(val num : Int,val textColor : Int,val backgroundColor:Int)