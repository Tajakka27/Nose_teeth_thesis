package com.example.nose_teeth_thesis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class ChatHeadService extends Service {
    private WindowManager windowManager;
    private View chatHeadView;

    public ChatHeadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Create a chat head view programmatically
        chatHeadView = LayoutInflater.from(this).inflate(R.layout.activity_chat_head_service, null);

        // Define the layout parameters for the chat head
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        // Set the initial position of the chat head (You can adjust this)
        params.x = 100;
        params.y = 100;

        // Initialize the WindowManager
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        // Add the chat head view to the WindowManager
        windowManager.addView(chatHeadView, params);

        // Implement touch listener to move the chat head
        chatHeadView.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(chatHeadView, params);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (chatHeadView != null) {
            windowManager.removeView(chatHeadView);
        }
    }
}
