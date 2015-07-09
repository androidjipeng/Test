package com.jp.administrator.mywindow;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Administrator on 2015/7/6.
 */
public class WindowUtis {
    private static View mView=null;
    private static WindowManager mWindowManager=null;
    private static Context mContext=null;
    private static Boolean isShow=false;

    public static void showPopupWindow(Context context)
    {
          if (isShow)
          {
              return;
          }
        isShow=true;
        mContext=context;
        mWindowManager= (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mView=setUpView(context);
        final WindowManager.LayoutParams layoutParams=new WindowManager.LayoutParams();
        layoutParams.type=WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        int flags=WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
        layoutParams.flags=flags;

        // 不设置这个弹出框的透明遮罩显示为黑
        layoutParams.format= PixelFormat.TRANSLUCENT;
        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // 设置 FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按
        // 不设置这个flag的话，home页的划屏会有问题
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height=WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity= Gravity.RIGHT;
        mWindowManager.addView(mView,layoutParams);
    }

    public static View setUpView(Context context)
    {
        View view= LayoutInflater.from(context).inflate(R.layout.window,null);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowUtis.hidePopupWindow();
            }
        });
        view.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowUtis.hidePopupWindow();
            }
        });
        final View popupWindowView=view.findViewById(R.id.popuWindow);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x= (int) event.getX();
                int y= (int) event.getY();
                Rect rect=new Rect();
                popupWindowView.getGlobalVisibleRect(rect);
                if (!rect.contains(x,y))
                {
                    WindowUtis.hidePopupWindow();
                }
                return false;
            }
        });
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode)
                {
                    case KeyEvent.KEYCODE_BACK:
                        WindowUtis.hidePopupWindow();
                        return true;
                    default:return false;
                }

            }
        });
        return view;
    }
    public static void hidePopupWindow() {
        if (isShow && null != mView) {
            mWindowManager.removeView(mView);
            isShow = false;
        }

    }

}
