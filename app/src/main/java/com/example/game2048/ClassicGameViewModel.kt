package com.example.game2048

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.SystemClock
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.sign


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
                textArray[row][column].setBackgroundColor(pContext.getColor(androidx.appcompat.R.color.material_grey_600))
            }
        }


        repeat(3){
            randomNum = (0..15).random()
            row = randomNum/4
            column = randomNum%4
            numArray[row][column] = constantManager.numUnitList[0].num
            textArray[row][column].text = constantManager.numUnitList[0].num.toString()
            textArray[row][column].setTextColor(pContext.getColor((constantManager.numUnitList[0].textColor)))
            textArray[row][column].setBackgroundColor(pContext.getColor((constantManager.numUnitList[0].backgroundColor)))
        }
    }

    //Add a new random number every time the user survive
    fun generateNum(){
        var randomPos:Int
        var done = false
        do{
            randomPos = (0..15).random()
            if(numArray[randomPos/4][randomPos%4] == 0){
                numArray[randomPos/4][randomPos%4] = constantManager.numUnitList[0].num
                done = true
            }
        }while (!done)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun swipeLeft(textArray: Array<Array<TextView>>){
        var tempColumn : Int
        var move_flag = false
        for(row in 0..3){
            for(column in 0..3){
                tempColumn = column
                if(isNotEmpty(numArray[row][column])){
                    while(leftIsEmpty(row,tempColumn)){
                        moveLeft(row,tempColumn)
                        move_flag = true
                        tempColumn--
                    }
                    if(tempColumn>=1){
                        if(numArray[row][tempColumn] == numArray[row][tempColumn-1]){
                            mergeLeftNum(row,tempColumn)
                            numArray[row][tempColumn] = 0
                        }
                    }
                }

            }
        }
        if(move_flag){
            generateNum()
        }
        updateGame(textArray)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun swipeRight(textArray: Array<Array<TextView>>){
        var tempColumn : Int
        var move_flag = false
        for(row in 0..3){
            for(column in 0..3){
                tempColumn = column
                if(isNotEmpty(numArray[row][column])){
                    while(rightIsEmpty(row,tempColumn)){
                        moveRight(row,tempColumn)
                        move_flag = true
                        tempColumn++
                    }
                    if(tempColumn<=2){
                        if(numArray[row][tempColumn] == numArray[row][tempColumn+1]){
                            mergeRightNum(row,tempColumn)
                            numArray[row][tempColumn] = 0
                        }
                    }
                }

            }
        }
        if(move_flag){
            generateNum()
        }
        updateGame(textArray)
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun swipeUp(textArray: Array<Array<TextView>>){
        var tempRow : Int
        var move_flag = false
        for(row in 0..3){
            for(column in 0..3){
                tempRow = row
                if(isNotEmpty(numArray[row][column])){
                    while(upIsEmpty(tempRow,column)){
                        moveUp(tempRow,column)
                        move_flag = true
                        tempRow--
                    }
                    if(tempRow>=1){
                        if(numArray[tempRow][column] == numArray[tempRow-1][column]){
                            mergeUpNum(tempRow,column)
                            numArray[tempRow][column] = 0
                        }
                    }
                }

            }
        }
        if(move_flag){
            generateNum()
        }
        updateGame(textArray)
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun swipeDown(textArray: Array<Array<TextView>>){
        var tempRow : Int
        var move_flag = false
        for(row in 0..3){
            for(column in 0..3){
                tempRow = row
                if(isNotEmpty(numArray[row][column])){
                    while(downIsEmpty(tempRow,column)){
                        moveDown(tempRow,column)
                        move_flag = true
                        tempRow++
                    }
                    if(tempRow<=2){
                        if(numArray[tempRow][column] == numArray[tempRow+1][column]){
                            mergeDownNum(tempRow,column)
                            numArray[tempRow][column] = 0
                        }
                    }
                }

            }
        }
        if(move_flag){
            generateNum()
        }
        updateGame(textArray)
    }





    private fun mergeLeftNum(row: Int, column: Int) {
        for(i in 0 until constantManager.numUnitList.size-1){
            if(numArray[row][column-1] == constantManager.numUnitList[i].num){
                numArray[row][column-1] = constantManager.numUnitList[i+1].num
                return
            }
        }
        if(numArray[row][column] == constantManager.numUnitList[constantManager.numUnitList.size-1].num){
            Toast.makeText(pContext,"success",Toast.LENGTH_SHORT).show()
        }
    }
    private fun mergeRightNum(row: Int, column: Int) {
        for(i in 0 until constantManager.numUnitList.size-1){
            if(numArray[row][column+1] == constantManager.numUnitList[i].num){
                numArray[row][column+1] = constantManager.numUnitList[i+1].num
                return
            }
        }
        if(numArray[row][column] == constantManager.numUnitList[constantManager.numUnitList.size-1].num){
            Toast.makeText(pContext,"success",Toast.LENGTH_SHORT).show()
        }
    }
    private fun mergeUpNum(row: Int, column: Int) {
        for(i in 0 until constantManager.numUnitList.size-1){
            if(numArray[row-1][column] == constantManager.numUnitList[i].num){
                numArray[row-1][column] = constantManager.numUnitList[i+1].num
                return
            }
        }
        if(numArray[row][column] == constantManager.numUnitList[constantManager.numUnitList.size-1].num){
            Toast.makeText(pContext,"success",Toast.LENGTH_SHORT).show()
        }
    }
    private fun mergeDownNum(row: Int, column: Int) {
        for(i in 0 until constantManager.numUnitList.size-1){
            if(numArray[row+1][column] == constantManager.numUnitList[i].num){
                numArray[row+1][column] = constantManager.numUnitList[i+1].num
                return
            }
        }
        if(numArray[row][column] == constantManager.numUnitList[constantManager.numUnitList.size-1].num){
            Toast.makeText(pContext,"success",Toast.LENGTH_SHORT).show()
        }
    }






    private fun moveLeft(row: Int,column: Int){
        numArray[row][column-1] = numArray[row][column]
        numArray[row][column] = 0
    }
    private fun moveRight(row: Int,column: Int){
        numArray[row][column+1] = numArray[row][column]
        numArray[row][column] = 0
    }
    private fun moveUp(row: Int,column: Int){
        numArray[row-1][column] = numArray[row][column]
        numArray[row][column] = 0
    }
    private fun moveDown(row: Int,column: Int){
        numArray[row+1][column] = numArray[row][column]
        numArray[row][column] = 0
    }








    private fun leftIsEmpty(row: Int,column: Int): Boolean {
        if((column-1)<0){
            return false
        }
        return !isNotEmpty(numArray[row][column-1])
    }
    private fun rightIsEmpty(row: Int,column: Int): Boolean {
        if((column+1)>3){
            return false
        }
        return !isNotEmpty(numArray[row][column+1])
    }
    private fun upIsEmpty(row: Int,column: Int): Boolean {
        if((row-1)<0){
            return false
        }
        return !isNotEmpty(numArray[row-1][column])
    }
    private fun downIsEmpty(row: Int,column: Int): Boolean {
        if((row+1)>3){
            return false
        }
        return !isNotEmpty(numArray[row+1][column])
    }





    @RequiresApi(Build.VERSION_CODES.M)
    private fun updateGame(textArray: Array<Array<TextView>>) {
        for(row in 0..3){
            for(column in 0..3){
                when (numArray[row][column]) {
                    2 -> {
                        textArray[row][column].text = constantManager.numUnitList[0].num.toString()
                        textArray[row][column].setTextColor(pContext.getColor((constantManager.numUnitList[0].textColor)))
                        textArray[row][column].setBackgroundColor(pContext.getColor((constantManager.numUnitList[0].backgroundColor)))
                    }
                    4 -> {
                        textArray[row][column].text = constantManager.numUnitList[1].num.toString()
                        textArray[row][column].setTextColor(pContext.getColor((constantManager.numUnitList[1].textColor)))
                        textArray[row][column].setBackgroundColor(pContext.getColor((constantManager.numUnitList[1].backgroundColor)))
                    }
                    8 -> {
                        textArray[row][column].text = constantManager.numUnitList[2].num.toString()
                        textArray[row][column].setTextColor(pContext.getColor((constantManager.numUnitList[2].textColor)))
                        textArray[row][column].setBackgroundColor(pContext.getColor((constantManager.numUnitList[2].backgroundColor)))
                    }
                    16 -> {
                        textArray[row][column].text = constantManager.numUnitList[3].num.toString()
                        textArray[row][column].setTextColor(pContext.getColor((constantManager.numUnitList[3].textColor)))
                        textArray[row][column].setBackgroundColor(pContext.getColor((constantManager.numUnitList[3].backgroundColor)))
                    }
                    else -> {
                        textArray[row][column].text = "${numArray[row][column]}"
                        textArray[row][column].setTextColor(pContext.getColor((constantManager.numUnitList[0].textColor)))
                        textArray[row][column].setBackgroundColor(pContext.getColor(androidx.appcompat.R.color.material_grey_600))
                    }
                }
            }
        }
    }



    private fun isNotEmpty(i: Int): Boolean {
        return i != 0
    }






}
