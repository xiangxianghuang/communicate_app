package com.yongyida.robot.control.model;

import android.content.Context;
import android.util.SparseArray;

import com.hiva.communicate.app.common.send.BaseSend;
import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.communicate.app.hardware.BaseControl;
import com.yongyida.robot.communicate.app.hardware.battery.send.BatterySend;
import com.yongyida.robot.communicate.app.hardware.led.LedControl;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;
import com.yongyida.robot.communicate.app.hardware.motion.MotionControl;
import com.yongyida.robot.communicate.app.hardware.motion.send.MotionSend;
import com.yongyida.robot.communicate.app.hardware.pir.PirSend;
import com.yongyida.robot.communicate.app.hardware.touch.TouchControl;
import com.yongyida.robot.communicate.app.hardware.touch.send.TouchSend;
import com.yongyida.robot.model.y128.battery.Y128BatteryControl;
import com.yongyida.robot.model.y128.led.Y128LedControl;
import com.yongyida.robot.model.y128.motion.Y128MotionControl;
import com.yongyida.robot.model.y128.touch.Y128TouchControl;
import com.yongyida.robot.model.y165.battery.Y165BatteryControl;
import com.yongyida.robot.model.y165.led.Y165LedControl;
import com.yongyida.robot.model.y165.pir.Y165PirControl;
import com.yongyida.robot.model.y20.led.Y20DLedControl;
import com.yongyida.robot.model.y20.motion.Y20MotionControl;

import java.util.HashMap;

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
public final class HardwareConfig {

    private static final String TAG = HardwareConfig.class.getSimpleName() ;

    private static HardwareConfig mHardwareConfig;
    public static HardwareConfig getInstance(Context context) {

        if(mHardwareConfig == null){

            mHardwareConfig = new HardwareConfig(context) ;
        }
        return mHardwareConfig;
    }



    private HashMap<Class, BaseControl<BaseSend>> mControls = new HashMap() ;
    private void addControl(Class clazz,BaseControl control){

        mControls.put(clazz, control);
    }
    public BaseControl<BaseSend> getControl(Class clazz){

        return mControls.get(clazz) ;
    }

    private ModelInfo mModelInfo = ModelInfo.getInstance();
    private Context mContext ;

    private HardwareConfig(Context context){

        this.mContext = context ;

        LogHelper.i(TAG , LogHelper.__TAG__() + ", mModelInfo : " + mModelInfo);
//        if(mModelInfo.getModel().contains("Y20D")){
//
//            initY20D();
//        } else if(mModelInfo.getModel().contains("Y20")){
//
//            initY20D();
//        }
//
//
//        initTest() ;

//        initY128();

        String model = mModelInfo.getModel() ;
        if(model == null){

            return;
        }

        if(model.contains("Y128") | model.contains("YQ110")){

            initY128();

        }else if(model.contains("Y165")){

            initY165() ;
        }

    }


    /**
     * Y20 模式
     *
     * */
    private void initY20(){

        //动作
//        Y20MotionControl motionControl = new Y20MotionControl() ;
//        addControl(motionControl);




    }
    private void initY20D(){

//        Y20DLedControl y20DLedControl = new Y20DLedControl() ;
//        addControl(y20DLedControl) ;

    }

    private void initY50(){


    }

    private void initY128(){

        LogHelper.i(TAG , LogHelper.__TAG__() );

        Y128BatteryControl batteryControl = new Y128BatteryControl(mContext) ;
        addControl(BatterySend.class, batteryControl) ;

        TouchControl touchControl = new Y128TouchControl(mContext) ;
        addControl(TouchSend.class, touchControl) ;

        LedControl ledControl = new Y128LedControl(mContext) ;
        addControl(LedSend.class, ledControl) ;

        MotionControl motionControl = new Y128MotionControl(mContext) ;
        addControl(MotionSend.class, motionControl) ;

    }


    private void initY165(){

        LogHelper.i(TAG , LogHelper.__TAG__() );

        Y165BatteryControl batteryControl = new Y165BatteryControl(mContext) ;
        addControl(BatterySend.class, batteryControl) ;

        Y165PirControl pirControl = new Y165PirControl(mContext);
        addControl(PirSend.class, pirControl) ;

        LedControl ledControl = new Y165LedControl(mContext);
        addControl(LedSend.class, ledControl) ;


    }


    private void initTest(){

//        Serial serial = new Serial() ;
//        serial.open() ;
//
//        VisionSerialSend visionSerialSend = new VisionSerialSend(serial);
//
//        Y138VisionControl versionControl = new Y138VisionControl() ;
//        versionControl.setVisionSerialSend(visionSerialSend);
//        addControl(versionControl) ;
//
//
////        Y20DLedControl y20DLedControl = new Y20DLedControl() ;
////        addControl(y20DLedControl) ;
////        CaroLedControl caroLedControl = new CaroLedControl() ;
////        addControl(caroLedControl);
//
//        Y20MotionControl y20MotionControl = new Y20MotionControl(MotionHelper.SERIAL_PORT_8735) ;
//        addControl(y20MotionControl) ;
    }



}
