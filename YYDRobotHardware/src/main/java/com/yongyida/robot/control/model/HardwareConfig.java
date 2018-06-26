package com.yongyida.robot.control.model;

import android.content.Context;

import com.yongyida.robot.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.hardware.BaseSendHandlers;
import com.yongyida.robot.communicate.app.hardware.led.LedSendHandlers;
import com.yongyida.robot.communicate.app.hardware.motion.MotionSendHandlers;
import com.yongyida.robot.communicate.app.hardware.touch.TouchSendHandlers;
import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.model.y128.battery.Y128BatterySendHandlers;
import com.yongyida.robot.model.y128.led.Y128LedSendHandlers;
import com.yongyida.robot.model.y128.motion.Y128MotionSendHandlers;
import com.yongyida.robot.model.y128.touch.Y128TouchSendHandlers;
import com.yongyida.robot.model.y148.led.Y148LedSendHandlers;
import com.yongyida.robot.model.y148.montrol.Y148MotionSendHandlers;
import com.yongyida.robot.model.y165.battery.Y165BatterySendHandlers;
import com.yongyida.robot.model.y165.led.Y165LedSendHandlers;
import com.yongyida.robot.model.y165.pir.Y165PirSendHandlers;
import com.yongyida.robot.model.y20.battery.Y20BatterySendHandlers;

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



    private HashMap<Class, BaseSendHandlers<BaseSend>> mControls = new HashMap() ;
//    private void addControl(Class clazz,BaseSendHandlers control){
//
//        mControls.put(clazz, control);
//    }

    private void addControl(BaseSendHandlers control){

        Class clazz = control.getSendClass();
        LogHelper.i(TAG , LogHelper.__TAG__() + ", clazz : " + clazz) ;

        mControls.put(control.getSendClass(), control);
    }

    public BaseSendHandlers<BaseSend> getControl(Class clazz){

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

//        if(model.contains("Y128") | model.contains("YQ110")){
//
//            initY128();
//
//        }else if(model.contains("Y165")){
//
//            initY165() ;
//
//        }else if(model.contains("Y138")){
//
//            initY138();
//
//        }


        initY128();
//        initY138();
    }


    /**
     * Y20 模式
     *
     * */
    private void initY20(){

        //动作
//        Y20MotionSendHandlers motionControl = new Y20MotionSendHandlers() ;
//        addControl(motionControl);




    }
    private void initY20D(){

//        Y20DLedSendHandlers y20DLedControl = new Y20DLedSendHandlers() ;
//        addControl(y20DLedControl) ;

    }

    private void initY50(){


    }

    private void initY128(){

        LogHelper.i(TAG , LogHelper.__TAG__() );

        addControl(new Y128BatterySendHandlers(mContext)) ;
        addControl(new Y128TouchSendHandlers(mContext)) ;
        addControl(new Y128LedSendHandlers(mContext)) ;
        addControl(new Y128MotionSendHandlers(mContext)) ;

    }


    private void initY138(){

        LogHelper.i(TAG , LogHelper.__TAG__() );

//        BatterySendHandlers batteryControl = new Y128BatterySendHandlers(mContext) ;
//        addControl(BatterySend.class, batteryControl) ;
//
//        TouchSendHandlers touchControl = new Y128TouchSendHandlers(mContext) ;
//        addControl(TouchSend.class, touchControl) ;
//
        LedSendHandlers ledControl = new Y148LedSendHandlers(mContext) ;
        addControl(ledControl) ;

        MotionSendHandlers motionControl = new Y148MotionSendHandlers(mContext) ;
        addControl( motionControl) ;

//        HandHandler handHandler = new Y138HandHandler(mContext) ;
//        addControl( handHandler) ;

        addControl(new Y20BatterySendHandlers(mContext)) ;

    }


    private void initY165(){

        LogHelper.i(TAG , LogHelper.__TAG__() );

        Y165BatterySendHandlers batteryControl = new Y165BatterySendHandlers(mContext) ;
        addControl(batteryControl) ;

        Y165PirSendHandlers pirControl = new Y165PirSendHandlers(mContext);
        addControl(pirControl) ;

        LedSendHandlers ledControl = new Y165LedSendHandlers(mContext);
        addControl(ledControl) ;

    }




}
