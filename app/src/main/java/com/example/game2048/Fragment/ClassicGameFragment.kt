package com.example.game2048.Fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.game2048.ClassicGameViewModel
import com.example.game2048.R

class ClassicGameFragment : Fragment() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_classic_game,container,false)


        val gameViewModel = ViewModelProvider(this).get(ClassicGameViewModel::class.java)
        context?.let { gameViewModel.getContext(it) }

        val textArray = Array<Array<TextView>>(4){Array<TextView>(4){it->view.findViewById<TextView>(
            R.id.fragment_classicGame_tv_00
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
        val btnUp = view.findViewById<Button>(R.id.fragment_classicGame_btn_up)
        val btnDown = view.findViewById<Button>(R.id.fragment_classicGame_btn_down)
        val btnLeft = view.findViewById<Button>(R.id.fragment_classicGame_btn_left)
        val btnRight = view.findViewById<Button>(R.id.fragment_classicGame_btn_right)
        val btnRestart = view.findViewById<Button>(R.id.fragment_classicGame_btn_restart)

        btnRestart.setOnClickListener {
            gameViewModel.gameInit(textArray)
        }
        btnUp.setOnClickListener {
            gameViewModel.swipeUp()
        }
        btnDown.setOnClickListener {
            gameViewModel.swipeDown()
        }
        btnLeft.setOnClickListener {
            gameViewModel.swipeLeft(textArray)
        }
        btnRight.setOnClickListener {
            gameViewModel.swipeRight()
        }

        gameViewModel.gameInit(textArray)




//        animation
//        ObjectAnimator.ofFloat(tv1,"translationX",100f).apply {
//            duration = 1000
//            start()
//            GlobalScope.launch {
//                delay(1000)
//                tv1.setText("4")
//            }
//        }
//        GlobalScope.launch {
//            delay(3000)
//            goToFragment(ClassicGameFragment())
//        }


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