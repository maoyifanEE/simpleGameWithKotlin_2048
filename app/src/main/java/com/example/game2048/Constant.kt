package com.example.game2048

public class Constant {
    val numUnitList = mutableListOf(
        numUnit(2,R.color.white,R.color.purple_700),
        numUnit(4,R.color.black,R.color.purple_200),
        numUnit(8,R.color.purple_700,R.color.white),
        numUnit(16,R.color.purple_200,R.color.teal_200),
        numUnit(32,R.color.white,R.color.teal_700),
        numUnit(64,R.color.purple_700,R.color.purple_500),
        numUnit(128,R.color.teal_700, androidx.appcompat.R.color.material_grey_600),
        numUnit(256,R.color.black, androidx.constraintlayout.widget.R.color.tooltip_background_light),
        numUnit(512,R.color.teal_200,R.color.purple_700),
        numUnit(1024,R.color.purple_700,R.color.purple_200),
        numUnit(2048,R.color.white,R.color.teal_200)
    )
    val unitDistance = 335f
    val animationMoveTime = 200L
}

val constantManager = Constant()

data class numUnit(val num : Int,val textColor : Int,val backgroundColor:Int)