package com.example.game2048

public class Constant {
    val numUnitList = mutableListOf(
        NumUnit(2,R.color.white,R.color.purple_700),
        NumUnit(4,R.color.black,R.color.purple_200),
        NumUnit(8,R.color.purple_700,R.color.white),
        NumUnit(16,R.color.purple_200,R.color.teal_200),
        NumUnit(32,R.color.white,R.color.teal_700),
        NumUnit(64,R.color.black,R.color.purple_500),
        NumUnit(128,R.color.teal_700, androidx.appcompat.R.color.material_grey_600),
        NumUnit(256,R.color.black, androidx.constraintlayout.widget.R.color.tooltip_background_light),
        NumUnit(512,R.color.teal_200,R.color.purple_700),
        NumUnit(1024,R.color.purple_700,R.color.purple_200),
        NumUnit(2048,R.color.white,R.color.teal_200)
    )
    val unitDistance = 335f
    val animationMoveTime = 200L
}

val constantManager = Constant()

data class NumUnit(val num : Int, val textColor : Int, val backgroundColor:Int)