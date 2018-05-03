package com.yongyida.robot.control.model;

import android.content.Context;

import com.hiva.communicate.app.common.send.BaseSend;
import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.communicate.app.hardware.BaseHandler;
import com.yongyida.robot.communicate.app.hardware.battery.BatteryHandler;
import com.yongyida.robot.communicate.app.hardware.battery.send.BatterySend;
import com.yongyida.robot.communicate.app.hardware.led.LedHandler;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;
import com.yongyida.robot.communicate.app.hardware.motion.MotionHandler;
import com.yongyida.robot.communicate.app.hardware.motion.send.MotionSend;
import com.yongyida.robot.communicate.app.hardware.pir.PirSend;
import com.yongyida.robot.communicate.app.hardware.touch.TouchHandler;
import com.yongyida.robot.communicate.app.hardware.touch.send.TouchSend;
import com.yongyida.robot.model.y128.battery.Y128BatteryHandler;
import com.yongyida.robot.model.y128.led.Y128LedHandler;
import com.yongyida.robot.model.y128.motion.Y128MotionHandler;
import com.yongyida.robot.model.y128.touch.Y128TouchHandler;
import com.yongyida.robot.model.y138.montrol.Y138MotionHandler;
import com.yongyida.robot.model.y165.battery.Y165BatteryHandler;
import com.yongyida.robot.model.y165.led.Y165LedHandler;
import com.yongyida.robot.model.y165.pir.Y165PirHandler;

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



    private HashMap<Class, BaseHandler<BaseSend>> mControls = new HashMap() ;
    private void addControl(Class clazz,BaseHandler control){

        mControls.put(clazz, control);
    }
    public BaseHandler<BaseSend> getControl(Class clazz){

        return mControls.get(clazz) ;
    }

    private ModelInfo mModelInfo = ModelInfo.getInstance();
    private Context mContext ;

    private HardwareConfig(Context context){

        this.mContext = context ;

        LogHelper.i(TAG , LogHelper.__TAG__() + ", mModelInfo : " + mModelInfo);

        String model = mModelInfo.getModel() ;
        if(model == null){

            return;
        }

        if(model.contains("Y128") | model.contains("YQ110")){

            initY128();

        }else if(model.contains("Y165")){

            initY165() ;

        }else if(model.contains("Y138")){

            initY138();

        }



    }


    /**
     * Y20 模式
     *
     * */
    private void initY20(){

        //动作
//        Y20MotionHandler motionControl = new Y20MotionHandler() ;
//        addControl(motionControl);




    }
    private void initY20D(){

//        Y20DLedHandler y20DLedControl = new Y20DLedHandler() ;
//        addControl(y20DLedControl) ;

    }

    private void initY50(){


    }

    private void initY128(){

        LogHelper.i(TAG , LogHelper.__TAG__() );

        Y128BatteryHandler batteryControl = new Y128BatteryHandler(mContext) ;
        addControl(BatterySend.class, batteryControl) ;

        TouchHandler touchControl = new Y128TouchHandler(mContext) ;
        addControl(TouchSend.class, touchControl) ;

        LedHandler ledControl = new Y128LedHandler(mContext) ;
        addControl(LedSend.class, ledControl) ;

        MotionHandler motionControl = new Y128MotionHandler(mContext) ;
        addControl(MotionSend.class, motionControl) ;

    }


    private void initY138(){

        LogHelper.i(TAG , LogHelper.__TAG__() );

//        BatteryHandler batteryControl = new Y128BatteryHandler(mContext) ;
//        addControl(BatterySend.class, batteryControl) ;
//
//        TouchHandler touchControl = new Y128TouchHandler(mContext) ;
//        addControl(TouchSend.class, touchControl) ;
//
//        LedHandler ledControl = new Y128LedHandler(mContext) ;
//        addControl(LedSend.class, ledControl) ;

        MotionHandler motionControl = new Y138MotionHandler(mContext) ;
        addControl(MotionSend.class, motionControl) ;

    }


    private void initY165(){

        LogHelper.i(TAG , LogHelper.__TAG__() );

        Y165BatteryHandler batteryControl = new Y165BatteryHandler(mContext) ;
        addControl(BatterySend.class, batteryControl) ;

        Y165PirHandler pirControl = new Y165PirHandler(mContext);
        addControl(PirSend.class, pirControl) ;

        LedHandler ledControl = new Y165LedHandler(mContext);
        addControl(LedSend.class, ledControl) ;


    }




}
