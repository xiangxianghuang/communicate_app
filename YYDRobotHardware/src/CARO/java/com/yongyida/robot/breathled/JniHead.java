package com.yongyida.robot.breathled;

import android.util.Log;

/**
 * Created by Administrator on 2017/3/22 0022.
 */

public class JniHead {
        static
        {
                System.loadLibrary("led");
        }

        /*
            val=1 工厂模式：val=0 非工厂模式  别的值无效.
         */
        public static native int current_mode_is_fatory(int val);

        /*
           返回值：1 ：表示好的   0：电量计有问题
         */
        public static native int fuel_gauge_is_good();

        /*
                打开5mic val=1 .   关闭5mic  val=0;
         */
        public static native int yyd_mic_onoff(int val);


        public static  int yyd_mic_callback(int val)
        {
                Log.i("fff", "yyd_mic_callback: "+val);
                return val;
        }

        public static native int cam_power_is_on_off();// 返回值是：1  才能打开摄像头，否则会导致系统死机。

        public static native int led_breath_mode();//呼吸灯模式

        public static native int close_all_led();

        public static native int open_all_led();


        public static native int set_led_color_and_current(int r_val,int g_val,int b_val);//val=0~255 :亮度值
}
