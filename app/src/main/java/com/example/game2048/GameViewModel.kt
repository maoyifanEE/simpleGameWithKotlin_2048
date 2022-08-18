package com.example.game2048

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    val numArray = Array(4) { Array(4) { it -> 0 } }


    //Initialize the game, randomly select three positions to initialize to 2, the positions can be repeated
    fun gameInit(){
        var randomNum : Int
        var row : Int
        var column : Int
        for(row in 0..3){
            for(column in 0..3){
                numArray[row][column] = 0
            }
        }
        repeat(3){
            randomNum = (0..15).random()
            row = randomNum/4
            column = randomNum%4
            numArray[row][column] = 2
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