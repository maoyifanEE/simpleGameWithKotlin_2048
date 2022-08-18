package com.example.game2048

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
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

        val tv1 = view.findViewById<TextView>(R.id.fragment_classicGame_tv_1)



        ObjectAnimator.ofFloat(tv1,"translationX",100f).apply {
            duration = 2000
            start()
            GlobalScope.launch {
                delay(2000)
                tv1.setText("4")
            }
        }
        GlobalScope.launch {
            delay(3000)
            goToFragment(ClassicGameFragment())
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