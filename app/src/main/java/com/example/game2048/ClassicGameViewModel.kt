package com.example.game2048

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*


class ClassicGameViewModel: ViewModel() {
    private val numArray = Array(4) { Array(4) { it -> 0 } }
    private val numLastStepArray = Array(4) { Array(4) { it -> 0 } }
    private val changedPosList = mutableListOf<Int>() //偶数存储原位置，偶数+1存储改变后位置
    @SuppressLint("StaticFieldLeak")
    lateinit var pContext : Context

    fun getContext(context: Context){
        pContext = context
    }

    //Initialize the game, randomly select three positions to initialize to 2, the positions can be repeated
    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.M)
    fun gameInit(textArray: Array<Array<TextView>>, textBackgroundArray: Array<Array<TextView>>) {
        var randomNum : Int
        for(row in 0..3){
            for(column in 0..3){
                textBackgroundArray[row][column].setBackgroundColor(pContext.getColor(androidx.appcompat.R.color.material_grey_600))
            }
        }
        for(row in 0..3){
            for(column in 0..3){
                numArray[row][column] = 0
                textArray[row][column].text = ""
                textArray[row][column].setBackgroundColor(pContext.getColor(androidx.appcompat.R.color.material_grey_600))
            }
        }


        repeat(3){
            randomNum = (0..15).random()
            numArray[randomNum/4][randomNum%4] = constantManager.numUnitList[0].num
            textArray[randomNum/4][randomNum%4].text = constantManager.numUnitList[0].num.toString()
            textArray[randomNum/4][randomNum%4].setTextColor(pContext.getColor((constantManager.numUnitList[0].textColor)))
            textArray[randomNum/4][randomNum%4].setBackgroundColor(pContext.getColor((constantManager.numUnitList[0].backgroundColor)))
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
//        Log.d("mao","The random position is ${randomPos/4},${randomPos%4}")
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun swipeLeft(textArray: Array<Array<TextView>>){
        var tempColumn : Int
        var moveDone = false
        var addDone = false
        storeLastStep()
        for(row in 0..3){
            for(column in 0..3){
                tempColumn = column
                if(isNotEmpty(numArray[row][column])){
                    while(leftIsEmpty(row,tempColumn)){
                        if(tempColumn == column){
                            changedPosList.add(row*4+column)
                            addDone = true
                        }
                        moveLeft(row,tempColumn)
                        moveDone = true
                        tempColumn--
                    }
                    if(tempColumn>=1){
                        if(numArray[row][tempColumn] == numArray[row][tempColumn-1]){
                            mergeLeftNum(row,tempColumn)
                            numArray[row][tempColumn] = 0
                        }
                    }
                    if(addDone){
                        changedPosList.add(row*4+tempColumn)
                        addDone = false
                    }
                }

            }
        }

        moveAnimation(textArray,"Horizontal")
        if(moveDone){
            generateNum()
        }
        if(gameLose()){
            Toast.makeText(pContext,"Lose",Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun swipeRight(textArray: Array<Array<TextView>>){
        var tempColumn : Int
        var moveDone = false
        var addDone = false
        storeLastStep()
        for(row in 0..3){
            for(column in 0..3){
                tempColumn = column
                if(isNotEmpty(numArray[row][column])){
                    while(rightIsEmpty(row,tempColumn)){
                        if(tempColumn == column){
                            changedPosList.add(row*4+column)
                            addDone = true
                        }
                        moveRight(row,tempColumn)
                        moveDone = true
                        tempColumn++
                    }
                    if(tempColumn<=2){
                        if(numArray[row][tempColumn] == numArray[row][tempColumn+1]){
                            mergeRightNum(row,tempColumn)
                            numArray[row][tempColumn] = 0
                        }
                    }
                    if(addDone){
                        changedPosList.add(row*4+tempColumn)
                        addDone = false
                    }
                }

            }
        }
        moveAnimation(textArray,"Horizontal")
        if(moveDone){
            generateNum()
        }
        if(gameLose()){
            Toast.makeText(pContext,"Lose", Toast.LENGTH_SHORT).show()
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun swipeUp(textArray: Array<Array<TextView>>){
        var tempRow : Int
        var moveDone = false
        var addDone = false
        storeLastStep()
        for(row in 0..3){
            for(column in 0..3){
                tempRow = row
                if(isNotEmpty(numArray[row][column])){
                    while(upIsEmpty(tempRow,column)){
                        if(tempRow == row){
                            changedPosList.add(row*4+column)
                            addDone = true
                        }
                        moveUp(tempRow,column)
                        moveDone = true
                        tempRow--
                    }
                    if(tempRow>=1){
                        if(numArray[tempRow][column] == numArray[tempRow-1][column]){
                            mergeUpNum(tempRow,column)
                            numArray[tempRow][column] = 0
                        }
                    }
                    if(addDone){
                        changedPosList.add(tempRow*4+column)
                        addDone = false
                    }
                }

            }
        }
        moveAnimation(textArray,"Vertical")
        if(moveDone){
            generateNum()
        }
        if(gameLose()){
            Toast.makeText(pContext,"Lose", Toast.LENGTH_SHORT).show()
        }
    }



    @RequiresApi(Build.VERSION_CODES.M)
    fun swipeDown(textArray: Array<Array<TextView>>){
        var tempRow : Int
        var moveDone = false
        var addDone = false
        storeLastStep()
        for(row in 0..3){
            for(column in 0..3){
                tempRow = row
                if(isNotEmpty(numArray[row][column])){
                    while(downIsEmpty(tempRow,column)){
                        if(tempRow == row){
                            changedPosList.add(row*4+column)
                            addDone = true
                        }
                        moveDown(tempRow,column)
                        moveDone = true
                        tempRow++
                    }
                    if(tempRow<=2){
                        if(numArray[tempRow][column] == numArray[tempRow+1][column]){
                            mergeDownNum(tempRow,column)
                            numArray[tempRow][column] = 0
                        }
                    }
                    if(addDone){
                        changedPosList.add(tempRow*4+column)
                        addDone = false
                    }
                }

            }
        }
        moveAnimation(textArray,"Vertical")
        if(moveDone){
            generateNum()
        }
        if(gameLose()){
            Toast.makeText(pContext,"Lose", Toast.LENGTH_SHORT).show()
        }
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
                    32 -> {
                        textArray[row][column].text = constantManager.numUnitList[4].num.toString()
                        textArray[row][column].setTextColor(pContext.getColor((constantManager.numUnitList[4].textColor)))
                        textArray[row][column].setBackgroundColor(pContext.getColor((constantManager.numUnitList[4].backgroundColor)))
                    }
                    64 -> {
                        textArray[row][column].text = constantManager.numUnitList[5].num.toString()
                        textArray[row][column].setTextColor(pContext.getColor((constantManager.numUnitList[5].textColor)))
                        textArray[row][column].setBackgroundColor(pContext.getColor((constantManager.numUnitList[5].backgroundColor)))
                    }
                    128 -> {
                        textArray[row][column].text = constantManager.numUnitList[6].num.toString()
                        textArray[row][column].setTextColor(pContext.getColor((constantManager.numUnitList[6].textColor)))
                        textArray[row][column].setBackgroundColor(pContext.getColor((constantManager.numUnitList[6].backgroundColor)))
                    }
                    256 -> {
                        textArray[row][column].text = constantManager.numUnitList[7].num.toString()
                        textArray[row][column].setTextColor(pContext.getColor((constantManager.numUnitList[7].textColor)))
                        textArray[row][column].setBackgroundColor(pContext.getColor((constantManager.numUnitList[7].backgroundColor)))
                    }
                    512 -> {
                        textArray[row][column].text = constantManager.numUnitList[8].num.toString()
                        textArray[row][column].setTextColor(pContext.getColor((constantManager.numUnitList[8].textColor)))
                        textArray[row][column].setBackgroundColor(pContext.getColor((constantManager.numUnitList[8].backgroundColor)))
                    }
                    1024 -> {
                        textArray[row][column].text = constantManager.numUnitList[9].num.toString()
                        textArray[row][column].setTextColor(pContext.getColor((constantManager.numUnitList[9].textColor)))
                        textArray[row][column].setBackgroundColor(pContext.getColor((constantManager.numUnitList[9].backgroundColor)))
                    }
                    2048 -> {
                        textArray[row][column].text = constantManager.numUnitList[10].num.toString()
                        textArray[row][column].setTextColor(pContext.getColor((constantManager.numUnitList[10].textColor)))
                        textArray[row][column].setBackgroundColor(pContext.getColor((constantManager.numUnitList[10].backgroundColor)))
                    }
                    else -> {
                        textArray[row][column].text = ""
                        textArray[row][column].setTextColor(pContext.getColor((constantManager.numUnitList[0].textColor)))
                        textArray[row][column].setBackgroundColor(pContext.getColor(androidx.appcompat.R.color.material_grey_600))
                    }
                }
            }
        }
    }

    private fun gameLose(): Boolean {
        for(row in 0..3){
            for(column in 0..3){
                if(numArray[row][column] == 0){
                    return false
                }
                if((row-1) >= 0){
                    if(numArray[row][column] == numArray[row-1][column]){
                        return false
                    }
                }
                if((row+1) <= 3){
                    if(numArray[row][column] == numArray[row+1][column]){
                        return false
                    }
                }
                if((column-1) >= 0){
                    if(numArray[row][column] == numArray[row][column-1]){
                        return false
                    }
                }
                if((column+1) <= 3){
                    if(numArray[row][column] == numArray[row][column+1]){
                        return false
                    }
                }
            }
        }
        return true
    }

    private fun isNotEmpty(i: Int): Boolean {
        return i != 0
    }

    private fun storeLastStep(){
        for(row in 0..3){
            for(column in 0..3){
                numLastStepArray[row][column] = numArray[row][column]
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun moveAnimation(textArray: Array<Array<TextView>>, direction: String){
//        for(i in 0 until changedPosList.size){
//            Log.d("mao","i is $i,position is ${changedPosList[i]/4},${changedPosList[i]%4}")
//        }
        when(direction){
            "Horizontal" -> {
                for(i in 0 .. changedPosList.size-2 step 2){
                    moveAnimationAchieve(textArray,
                        changedPosList[i]/4,
                        changedPosList[i]%4,
                        changedPosList[i+1]%4-changedPosList[i]%4,
                        "Horizontal"
                    )
                }
            }
            "Vertical" -> {
                for(i in 0 .. changedPosList.size-2 step 2){
                    moveAnimationAchieve(textArray,
                        changedPosList[i]/4,
                        changedPosList[i]%4,
                        changedPosList[i+1]/4-changedPosList[i]/4,
                        "Vertical"
                    )
                }
            }
            else -> {
                Toast.makeText(pContext,"Invalid direction",Toast.LENGTH_SHORT).show()
            }
        }
        changedPosList.clear()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun moveAnimationAchieve(textArray: Array<Array<TextView>>, row:Int, column:Int, unit:Int, direction:String) {
        when(direction){
            "Vertical" -> {
                ObjectAnimator.ofFloat(textArray[row][column],
                    "translationY",
                    unit*constantManager.unitDistance).let {
                    it.duration = constantManager.animationMoveTime
                    it.start()
                    it.doOnEnd {
                        textArray[row][column].translationY = 0f
                        updateGame(textArray)
                    }
                }
            }
            "Horizontal" -> {
                ObjectAnimator.ofFloat(textArray[row][column],
                    "translationX",
                    unit*constantManager.unitDistance).let {
                    it.duration = constantManager.animationMoveTime
                    it.start()
                    it.doOnEnd {
                        textArray[row][column].translationX = 0f
                        updateGame(textArray)
                    }
                }
            }
            else -> {
                Toast.makeText(pContext,"Invalid direction",Toast.LENGTH_SHORT).show()
            }
        }
    }






}
