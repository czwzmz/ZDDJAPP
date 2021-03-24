package com.example.zddjapp;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.view.accessibility.AccessibilityEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.widget.Button;
import android.widget.FrameLayout;
import android.media.AudioManager;

import java.util.ArrayDeque;
import java.util.Deque;

import android.util.Log;

public class MyAccessibilityService extends AccessibilityService {
    String TAG = "AliAccessibilityService";

    /**
     * event: EventType: TYPE_WINDOW_STATE_CHANGED;
     * EventTime: 382223;
     * PackageName: cn.manstep.phonemirrorBox;
     * MovementGranularity: 0;
     * Action: 0;
     * ContentChangeTypes: [];
     * WindowChangeTypes: [] [
     * ClassName: android.app.Dialog;
     * Text: [YSY200506-1SW, 要允许YSY200506-1SW访问USB Serial Converter吗？, 连接USB Serial Converter后一律打开YSY200506-1SW, 取消, 确定];
     */

    //接收到系统发送AccessibilityEvent时的回调
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getPackageName().equals("cn.manstep.phonemirrorBox") && event.getClassName().equals("android.app.Dialog") && event.getText().get(0).equals("AutoKit")) {
            AccessibilityNodeInfo nodeInfo = event.getSource();
            for (int i = 0; i < nodeInfo.getChildCount(); i++) {
                if (nodeInfo.getChild(i).getClassName().equals("android.widget.CheckBox")) {
                    Log.d(TAG, "onAccessibilityEvent: " + nodeInfo.getChild(i).getClassName() + ": " + nodeInfo.getChild(i).getText());
                    nodeInfo.getChild(i).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
            for (int i = 0; i < nodeInfo.getChildCount(); i++) {
                if (nodeInfo.getChild(i).getClassName().equals("android.widget.ScrollView")) {
                    for (int j = 0; j < nodeInfo.getChild(i).getChildCount(); j++) {
                        if (nodeInfo.getChild(i).getChild(j).getClassName().equals("android.widget.Button") && nodeInfo.getChild(i).getChild(j).getText().equals("OK")) {
                            Log.d(TAG, "onAccessibilityEvent: " + nodeInfo.getChild(i).getChild(j).getClassName() + ": " + nodeInfo.getChild(i).getChild(j).getText());
                            nodeInfo.getChild(i).getChild(j).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            return;
                        }
                    }
                }
            }


        }
    }

    //服务中断时的回调
    @Override
    public void onInterrupt() {
        Log.d(TAG, "onInterrupt");
    }
}
