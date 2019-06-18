package com.leo.pro.app.base

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.pm.ActivityInfo
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast

import com.leo.pro.R
import com.leo.pro.app.Constant
import com.leo.pro.app.CustomApplication
import com.leo.pro.app.MyActivityManager
import com.leo.pro.app.utils.ActivityUtils
import com.leo.pro.app.utils.Res
import com.leo.pro.app.utils.ScreenUtils

/**
 * 创建人 LEO
 * 创建时间 2019/1/29 15:10
 */

abstract class Base2Activity<G : ViewDataBinding> : Activity(), View.OnClickListener {

    var mDoubleKeyExit = false//是否双击推出应用
    lateinit var mContext: Context//上下文对象
    private var mFirstTime: Long = 0//第一次按下返回键
    private var mSecondTime: Long = 0//第二次按下返回键
    private var mToast: Toast? = null//toast
    lateinit var mParentView: View//main view
    lateinit var mInflater: LayoutInflater
    lateinit var mApplication: CustomApplication
    var mViewBinding: G? = null
    var mIsAlph = false//状态栏是否透明
    var mIsTop = false//布局是否要到最顶部
    var mFullScreen = false//是否全屏
    var mOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT//默认竖屏

    /**
     * 判断当前应用程序处于前台还是后台
     */
    val isBackground: Boolean
        get() {
            val activityManager = applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager

            val packageName = applicationContext.packageName
            val appProcesses = activityManager.runningAppProcesses ?: return false

            for (appProcess in appProcesses) {

                if (appProcess.processName == packageName && appProcess.importance == android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true
                }
            }
            return false
        }

    /**
     * 设置activity布局
     */
    abstract fun onSetContentView(): Int

    /**
     * 初始化组件
     */
    abstract fun onInitView()

    /**
     * 初始化数据
     */
    abstract fun onInitData()

    /**
     * 初始化监听
     */
    abstract fun onInitListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = mOrientation//竖屏
        mApplication = application as CustomApplication
        this.mContext = this
        mParentView = View.inflate(mContext, onSetContentView(), null)
        try {
            mViewBinding = DataBindingUtil.bind(mParentView)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        if (mFullScreen) {
            this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        setContentView(mParentView)
        mInflater = LayoutInflater.from(mContext)
        if (!mFullScreen) {
            if (!mIsTop) {
                mParentView.fitsSystemWindows = true
            }
            ScreenUtils.titleAlph(if (mIsAlph) true else mIsTop, Res.getColorRes(R.color.app_main_color, mContext), this)
        }
        MyActivityManager.addActivity(this)
        onSetContentView()
        onInitView()
        onInitData()
        onInitListener()
    }

    fun context(): Context {
        return mContext
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDoubleKeyExit) {
                if (mFirstTime == 0L) {
                    mFirstTime = System.currentTimeMillis()
                    showShortToast("再次点击返回键退出应用")
                } else {
                    mSecondTime = System.currentTimeMillis()
                    val interval = mSecondTime - mFirstTime
                    if (interval <= Constant.DOUBLE_CLICK_EXIT_DENY) {
                        MyActivityManager.exitApplicaion()
                    } else {
                        mFirstTime = 0
                        mSecondTime = 0
                    }
                }
            } else {
                onBack()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 返回键
     */
    fun onBack() {
        finish()
    }

    @CallSuper
    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> onBack()
        }
    }

    /**
     * 弹出short toast提示
     *
     * @param msg
     */
    fun showShortToast(msg: String) {
        runOnUiThread {
            try {
                mToast!!.cancel()
            } catch (e: Exception) {
            }

            mToast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT)
            mToast!!.show()
        }
    }

    override fun onPause() {
        if (mToast != null) {//toast随页面消失而消失
            try {
                mToast!!.cancel()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        val GONE = View.GONE
        val VISIBLE = View.VISIBLE
        val INVISIBLE = View.INVISIBLE
    }

}
