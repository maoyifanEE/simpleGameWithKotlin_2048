package com.example.game2048.Fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.game2048.GestureDirection
import com.example.game2048.ViewModel.ClassicGameViewModel
import com.example.game2048.R
import com.example.game2048.ViewModel.MainActivityViewModel

class ClassicGameFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_classic_game,container,false)


        val gameSharedPref = requireActivity().getSharedPreferences("gamePref",0)
        val gameEditor = gameSharedPref.edit()

        val gameViewModel = ViewModelProvider(this)[ClassicGameViewModel::class.java]
        val mainActivityViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]

        context?.let { gameViewModel.getContext(it) }

        val textArray = Array<Array<TextView>>(4){Array<TextView>(4){it->view.findViewById<TextView>(
            R.id.fragment_classicGame_tv_00
        )} }
        val textBackgroundArray = Array<Array<TextView>>(4){Array<TextView>(4){it->view.findViewById<TextView>(
            R.id.fragment_classicGameBackground_tv_00
        )} }
        textArray[0][0] = view.findViewById<TextView>(R.id.fragment_classicGame_tv_00)
        textArray[0][1] = view.findViewById<TextView>(R.id.fragment_classicGame_tv_01)
        textArray[0][2] = view.findViewById<TextView>(R.id.fragment_classicGame_tv_02)
        textArray[0][3] = view.findViewById<TextView>(R.id.fragment_classicGame_tv_03)
        textArray[1][0] = view.findViewById<TextView>(R.id.fragment_classicGame_tv_10)
        textArray[1][1] = view.findViewById<TextView>(R.id.fragment_classicGame_tv_11)
        textArray[1][2] = view.findViewById<TextView>(R.id.fragment_classicGame_tv_12)
        textArray[1][3] = view.findViewById<TextView>(R.id.fragment_classicGame_tv_13)
        textArray[2][0] = view.findViewById<TextView>(R.id.fragment_classicGame_tv_20)
        textArray[2][1] = view.findViewById<TextView>(R.id.fragment_classicGame_tv_21)
        textArray[2][2] = view.findViewById<TextView>(R.id.fragment_classicGame_tv_22)
        textArray[2][3] = view.findViewById<TextView>(R.id.fragment_classicGame_tv_23)
        textArray[3][0] = view.findViewById<TextView>(R.id.fragment_classicGame_tv_30)
        textArray[3][1] = view.findViewById<TextView>(R.id.fragment_classicGame_tv_31)
        textArray[3][2] = view.findViewById<TextView>(R.id.fragment_classicGame_tv_32)
        textArray[3][3] = view.findViewById<TextView>(R.id.fragment_classicGame_tv_33)

        textBackgroundArray[0][0] = view.findViewById<TextView>(R.id.fragment_classicGameBackground_tv_00)
        textBackgroundArray[0][1] = view.findViewById<TextView>(R.id.fragment_classicGameBackground_tv_01)
        textBackgroundArray[0][2] = view.findViewById<TextView>(R.id.fragment_classicGameBackground_tv_02)
        textBackgroundArray[0][3] = view.findViewById<TextView>(R.id.fragment_classicGameBackground_tv_03)
        textBackgroundArray[1][0] = view.findViewById<TextView>(R.id.fragment_classicGameBackground_tv_10)
        textBackgroundArray[1][1] = view.findViewById<TextView>(R.id.fragment_classicGameBackground_tv_11)
        textBackgroundArray[1][2] = view.findViewById<TextView>(R.id.fragment_classicGameBackground_tv_12)
        textBackgroundArray[1][3] = view.findViewById<TextView>(R.id.fragment_classicGameBackground_tv_13)
        textBackgroundArray[2][0] = view.findViewById<TextView>(R.id.fragment_classicGameBackground_tv_20)
        textBackgroundArray[2][1] = view.findViewById<TextView>(R.id.fragment_classicGameBackground_tv_21)
        textBackgroundArray[2][2] = view.findViewById<TextView>(R.id.fragment_classicGameBackground_tv_22)
        textBackgroundArray[2][3] = view.findViewById<TextView>(R.id.fragment_classicGameBackground_tv_23)
        textBackgroundArray[3][0] = view.findViewById<TextView>(R.id.fragment_classicGameBackground_tv_30)
        textBackgroundArray[3][1] = view.findViewById<TextView>(R.id.fragment_classicGameBackground_tv_31)
        textBackgroundArray[3][2] = view.findViewById<TextView>(R.id.fragment_classicGameBackground_tv_32)
        textBackgroundArray[3][3] = view.findViewById<TextView>(R.id.fragment_classicGameBackground_tv_33)
        val btnUp = view.findViewById<Button>(R.id.fragment_classicGame_btn_up)
        val btnDown = view.findViewById<Button>(R.id.fragment_classicGame_btn_down)
        val btnLeft = view.findViewById<Button>(R.id.fragment_classicGame_btn_left)
        val btnRight = view.findViewById<Button>(R.id.fragment_classicGame_btn_right)
        val btnRestart = view.findViewById<Button>(R.id.fragment_classicGame_btn_restart)
        val btnGoToMenu = view.findViewById<Button>(R.id.fragment_classicGame_btn_goToStartFragment)

        gameViewModel.gameInit(textArray,textBackgroundArray)
        btnRestart.setOnClickListener {
            gameViewModel.gameInit(textArray, textBackgroundArray)
        }
        btnGoToMenu.setOnClickListener{
            goToFragment(StartFragment())
        }

        mainActivityViewModel.gestureDirection.observe(viewLifecycleOwner){
            when(mainActivityViewModel.gestureDirection.value){
                GestureDirection.UP.value -> {
                    gameViewModel.swipeUp(textArray)
                }
                GestureDirection.DOWN.value -> {
                    gameViewModel.swipeDown(textArray)
                }
                GestureDirection.LEFT.value -> {
                    gameViewModel.swipeLeft(textArray)
                }
                GestureDirection.RIGHT.value -> {
                    gameViewModel.swipeRight(textArray)
                }
            }
        }


        btnUp.setOnClickListener {
            gameViewModel.swipeUp(textArray)
        }
        btnDown.setOnClickListener {
            gameViewModel.swipeDown(textArray)
        }
        btnLeft.setOnClickListener {
            gameViewModel.swipeLeft(textArray)
        }
        btnRight.setOnClickListener {
            gameViewModel.swipeRight(textArray)
        }








        return view
    }



    private fun goToFragment(fragment: Fragment) {


        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivity_fcv_default,fragment)
            .addToBackStack(null)
            .commit()
    }
}