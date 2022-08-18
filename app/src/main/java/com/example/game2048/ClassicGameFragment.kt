package com.example.game2048

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ClassicGameFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_classic_game,container,false)

        val gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)


        val textArray = Array<Array<TextView>>(4){Array<TextView>(4){it -> view.findViewById<TextView>(R.id.fragment_classicGame_tv_00)} }
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