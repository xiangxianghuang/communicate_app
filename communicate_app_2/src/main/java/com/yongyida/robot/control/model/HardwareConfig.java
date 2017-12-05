package com.yongyida.robot.control.model;

import android.util.SparseArray;

import com.yongyida.robot.communicate.app.hardware.IControl;
import com.yongyida.robot.communicate.app.hardware.vision.VisionControl;
import com.yongyida.robot.control.model.y138.version.Y138VisionControl;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 *  硬件相关
 * 1. 动作(motion)
 * 2. 呼吸灯(led)
 * 3. 触摸(touch)
 * 4. 电池状态(battery)
 * 5. 红外感应监测(infrared)
 * 6. 温湿度计(humiture)
 * 7. 亮度传感器(light)
 * 8. 摄像头状态(camera)
 * 9. Zigbee(zigbee)
 * 10. 视觉数据(vision)
 */
public class HardwareConfig {


    public static final int TYPE_MOTION                 = 0x01 ;
    public static final int TYPE_LED                    = 0x02 ;
    public static final int TYPE_TOUCH                  = 0x03 ;
    public static final int TYPE_BATTERY                = 0x04 ;
    public static final int TYPE_INFRARED               = 0x05 ;
    public static final int TYPE_HUMITURE               = 0x06 ;
    public static final int TYPE_LIGHT                  = 0x07 ;
    public static final int TYPE_CAMERA                 = 0x08 ;
    public static final int TYPE_ZIGBEE                 = 0x09 ;
    public static final int TYPE_VISION                 = 0x10 ;


    private SparseArray<IControl> mControls = new SparseArray() ;

    private static HardwareConfig mHardwareConfig;
    public static HardwareConfig getInstance() {

        if(mHardwareConfig == null){

            mHardwareConfig = new HardwareConfig() ;
        }
        return mHardwareConfig;
    }

    private HardwareConfig(){


        VisionControl versionControl = new Y138VisionControl() ;
        mControls.put(versionControl.getType(), versionControl);
    }

    private void initY20(){


    }


    private void initY50(){


    }

    private void initY128(){


    }


    public IControl getControl(int type){

        return mControls.get(type) ;
    }

}
