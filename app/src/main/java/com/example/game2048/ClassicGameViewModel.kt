package com.example.game2048

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel


class ClassicGameViewModel: ViewModel() {
    val numArray = Array(4) { Array(4) { it -> 0 } }
    @SuppressLint("StaticFieldLeak")
    lateinit var pContext : Context

    fun getContext(context: Context){
        pContext = context
    }

    //Initialize the game, randomly select three positions to initialize to 2, the positions can be repeated
    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.M)
    fun gameInit(textArray: Array<Array<TextView>>) {
        var randomNum : Int
        var row : Int
        var column : Int
        for(row in 0..3){
            for(column in 0..3){
                numArray[row][column] = 0
                textArray[row][column].text = ""
                textArray[row][column].setTextColor(pContext.getColor(R.color.white))
                textArray[row][column].setBackgroundColor(pContext.getColor(R.color.white))
            }
        }


        repeat(3){
            randomNum = (0..15).random()
            row = randomNum/4
            column = randomNum%4
            numArray[row][column] = 2
            textArray[row][column].text = constantManager.numUnitList[0].num.toString()
            textArray[row][column].setTextColor(pContext.getColor((constantManager.numUnitList[0].textColor)))
            textArray[row][column].setBackgroundColor(pContext.getColor((constantManager.numUnitList[0].backgroundColor)))
        }
    }

    //Add a new random number every time the user survive
    fun generateNum(){
        var row : Int
        var column : Int
        val emptyPos = mutableListOf<Int>()
        for(row in 0..3){
            for(column in 0..3){
                if(numArray[row][column] == 0){
                    emptyPos.add(row*4+column)
                }
            }
        }
        val randomNum = (0 until emptyPos.size).random()
        row = randomNum/4
        column = randomNum%4

    }

    fun swipeLeft(){

    }




}