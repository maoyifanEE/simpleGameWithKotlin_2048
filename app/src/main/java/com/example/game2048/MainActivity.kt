package com.example.game2048

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.game2048.Fragment.StartFragment
import com.example.game2048.ViewModel.ClassicGameViewModel
import com.example.game2048.ViewModel.MainActivityViewModel

open class MainActivity : AppCompatActivity() {
    private var gestureDetector: GestureDetector? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainActivityViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        gestureDetector = GestureDetector(this@MainActivity,
            object : GestureDetector.SimpleOnGestureListener() {
                /**
                 * e1: 第一次按下的位置
                 * e2   当手离开屏幕 时的位置
                 * velocityX  沿x 轴的速度
                 * velocityY： 沿Y轴方向的速度
                 * @param e1
                 * @param e2
                 * @param velocityX
                 * @param velocityY
                 * @return
                 */
                override fun onFling(
                    e1: MotionEvent, e2: MotionEvent, velocityX: Float,
                    velocityY: Float
                ): Boolean {
                    // 手势向上滑动
                    if (e1.rawY - e2.rawY > constantManager.gestureDirectionStandard) {
                        mainActivityViewModel.gestureDirection.value = GestureDirection.UP.value
                        return true
                    }
                    // 手势向下滑动
                    if (e2.rawY - e1.rawY > constantManager.gestureDirectionStandard) {
                        mainActivityViewModel.gestureDirection.value = GestureDirection.DOWN.value
                        return true
                    }

                    // 手势向左边滑动
                    if (e1.rawX - e2.rawX > constantManager.gestureDirectionStandard) {
                        mainActivityViewModel.gestureDirection.value = GestureDirection.LEFT.value
                        return true
                    }

                    // 手势向右滑动
                    if (e2.rawX - e1.rawX > constantManager.gestureDirectionStandard) {
                        mainActivityViewModel.gestureDirection.value = GestureDirection.RIGHT.value
                        return true // 消费掉当前事件  不让当前事件继续向下传递
                    }
                    return super.onFling(e1, e2, velocityX, velocityY)
                }
            })

        goToFragment(StartFragment())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector!!.onTouchEvent(event)
    }

    private fun goToFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivity_fcv_default,fragment)
            .addToBackStack(null)
            .commit()
    }
}