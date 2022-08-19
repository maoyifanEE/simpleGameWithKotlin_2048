package com.example.game2048

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.game2048.Fragment.ClassicGameFragment

open class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        goToFragment(ClassicGameFragment())

    }



    private fun goToFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivity_fcv_default,fragment)
            .addToBackStack(null)
            .commit()
    }
}