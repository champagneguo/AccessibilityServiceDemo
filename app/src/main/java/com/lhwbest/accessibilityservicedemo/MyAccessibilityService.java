package com.lhwbest.accessibilityservicedemo;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Created by hongwei21 on 2017/3/10.
 */
public class MyAccessibilityService extends AccessibilityService {

    private PowerUtil powerUtil;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        this.powerUtil = new PowerUtil(this);
        this.powerUtil.handleWakeLock(true);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        printlog(event);

        String model = android.os.Build.MODEL;
        switch (model) {
            case "MX4 Pro": {
                //安装对话框
                findAndPerformActionButton("允许");
                findAndPerformActionButton("始终允许");
            }
            break;
            case "YQ603": {
                //安装对话框
                findAndPerformActionButton("同意");
                findAndPerformActionButton("始终允许");
            }
            break;
            case "vivo X9": {
                findAndPerformActionButton("安装");
                findAndPerformActionButton("继续安装");
                findAndPerformActionButton("允许");
            }
            break;
            case "OPPO R11": {
                findAndPerformActionButton("安装");
                findAndPerformActionButton("继续安装");
                findAndPerformActionButton("允许");
            }
            break;
            case "FRD-AL00":
            case "PLK-TL01H": {
                //安装对话框
                findAndPerformActionButton("始终允许");
                findAndPerformActionButton("允许");
                findAndPerformActionButton("立即删除");
                findAndPerformActionButton("同意");
                findAndPerformActionButton("继续");
            }
            break;
            case "MI 5s":
            case "Redmi Note 4X": {
                //安装对话框
                findAndPerformActionButton("继续安装");
            }
            break;
            case "SM-A8000":
            case "SM-G9308": {
                //安装对话框
//                findAndPerformActionButton("关");
//                findAndPerformActionButton("关闭");
                findAndPerformActionButton("确定");
                findAndPerformActionButton("确认");
            }
            break;
            case "MIX 2":
                findAndPerformActionButton("确定");
                break;

        }
    }

    @Override
    public void onInterrupt() {

    }

    public void log(String msg) {
        Log.e("MyAccessibilityService", msg);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void findAndPerformActionButton(String text) {
        if (getRootInActiveWindow() == null)//取得当前激活窗体的根节点
            return;
        //通过文字找到当前的节点
        List<AccessibilityNodeInfo> nodes = getRootInActiveWindow().findAccessibilityNodeInfosByText(text);
        for (int i = 0; i < nodes.size(); i++) {
            AccessibilityNodeInfo node = nodes.get(i);
            // 执行点击行为
            if (node.getClassName().equals("android.widget.Button") && node.isEnabled()) {
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void findAndPerformActionTextView(String text) {
        if (getRootInActiveWindow() == null)
            return;
        //通过文字找到当前的节点
        List<AccessibilityNodeInfo> nodes = getRootInActiveWindow().findAccessibilityNodeInfosByText(text);
        for (int i = 0; i < nodes.size(); i++) {
            AccessibilityNodeInfo node = nodes.get(i);
            // 执行按钮点击行为
            if (node.getClassName().equals("android.widget.TextView") && node.isEnabled()) {
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    private void printlog(AccessibilityEvent event) {
        log("-------------------------------------------------------------");
        int eventType = event.getEventType();//事件类型
        log("packageName:" + event.getPackageName() + "");//响应事件的包名，也就是哪个应用才响应了这个事件
        log("source:" + event.getSource() + "");//事件源信息
        log("source class:" + event.getClassName() + "");//事件源的类名，比如android.widget.TextView
        log("event type(int):" + eventType + "");

        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:// 通知栏事件
                log("event type:TYPE_NOTIFICATION_STATE_CHANGED");
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED://窗体状态改变
                log("event type:TYPE_WINDOW_STATE_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED://View获取到焦点
                log("event type:TYPE_VIEW_ACCESSIBILITY_FOCUSED");
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_START:
                log("event type:TYPE_VIEW_ACCESSIBILITY_FOCUSED");
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_END:
                log("event type:TYPE_GESTURE_DETECTION_END");
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                log("event type:TYPE_WINDOW_CONTENT_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                log("event type:TYPE_VIEW_CLICKED");
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                log("event type:TYPE_VIEW_TEXT_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                log("event type:TYPE_VIEW_SCROLLED");
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                log("event type:TYPE_VIEW_TEXT_SELECTION_CHANGED");
                break;
        }

        for (CharSequence txt : event.getText()) {
            log("text:" + txt);//输出当前事件包含的文本信息
        }

        log("Description:" + event.getContentDescription());//输出当前事件包含的文本信息

        log("-------------------------------------------------------------");

    }


}
