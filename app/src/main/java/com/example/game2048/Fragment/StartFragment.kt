package com.example.game2048.Fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.game2048.R

class StartFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_start,container,false)

        val btnClassicGame = view.findViewById<Button>(R.id.startFragment_btn_classic)
        val btnTimeLimitGame = view.findViewById<Button>(R.id.startFragment_btn_timeLimit)

        btnClassicGame.setOnClickListener {
            goToFragment(ClassicGameFragment())
        }
        btnTimeLimitGame.visibility = INVISIBLE
        btnTimeLimitGame.setOnClickListener {
            AlertDialog.Builder(context)
                .setMessage("not open yet")
                .create()
                .show()
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