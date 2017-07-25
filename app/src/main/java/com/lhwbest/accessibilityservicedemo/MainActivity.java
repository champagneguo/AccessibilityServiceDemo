package com.lhwbest.accessibilityservicedemo;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AccessibilityManager.AccessibilityStateChangeListener {

    private Button mBtnStatus;
    private AccessibilityManager accessibilityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        setWifiOn(getApplicationContext());

        mBtnStatus = (Button) findViewById(R.id.btn_status);

        //监听AccessibilityService 变化
        accessibilityManager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
        accessibilityManager.addAccessibilityStateChangeListener(this);

        updateServiceStatus();
    }

    public void openAccessibility(View view) {
        try {
            Intent accessibleIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(accessibleIntent);
        } catch (Exception e) {
        }

    }

    /**
     * 更新当前 HongbaoService 显示状态
     */
    private void updateServiceStatus() {
        if (isServiceEnabled()) {
            mBtnStatus.setText("关闭插件");
        } else {
            mBtnStatus.setText("开启插件");
        }
    }

    /**
     * 获取 HongbaoService 是否启用状态
     *
     * @return
     */
    private boolean isServiceEnabled() {
        List<AccessibilityServiceInfo> accessibilityServices =
                accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        for (AccessibilityServiceInfo info : accessibilityServices) {
            Log.e("serviceinfo", info.getId());
            if (info.getId().equals(getPackageName() + "/.MyAccessibilityService")) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        //移除监听服务
        accessibilityManager.removeAccessibilityStateChangeListener(this);
        super.onDestroy();
    }

    @Override
    public void onAccessibilityStateChanged(boolean enabled) {
        updateServiceStatus();
    }

    public static boolean setWifiOn(Context context) {
        WifiManager wifiMng = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiMng == null) {
            return false;
        }
        if (wifiMng.isWifiEnabled()) {
            return true;
        }
        Settings.System.putInt(context.getContentResolver(), Settings.Secure.WIFI_ON, 1);
        try {
            wifiMng.reassociate();
            wifiMng.reconnect();
            wifiMng.setWifiEnabled(true);
            Settings.System.putInt(context.getContentResolver(), Settings.Secure.WIFI_ON, 1);
            Thread.sleep(5000);
            return wifiMng.isWifiEnabled();
        } catch (Exception e) {
            return false;
        }
    }

}
