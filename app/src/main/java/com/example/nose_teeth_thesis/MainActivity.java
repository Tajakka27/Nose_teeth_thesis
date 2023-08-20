package com.example.nose_teeth_thesis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.net.Uri;
import android.os.Build;
import android.provider.Settings;


public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if the permission is granted
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                !Settings.canDrawOverlays(this)) {
            // Request the permission
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    Settings.canDrawOverlays(this)) {
                // The permission is granted, you can start your ChatHeadService
                startChatHeadService();
            }
        }
    }

    private void startChatHeadService() {
        Intent intent = new Intent(this, ChatHeadService.class);
        startService(intent);
    }
}
