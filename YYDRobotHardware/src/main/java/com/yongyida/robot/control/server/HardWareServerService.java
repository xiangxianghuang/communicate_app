package com.yongyida.robot.control.server;

import com.hiva.communicate.app.common.IResponseListener;
import com.hiva.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.hardware.BaseHandler;
import com.yongyida.robot.communicate.app.hardware.battery.BatteryHandler;
import com.yongyida.robot.communicate.app.hardware.camera.CameraHandler;
import com.yongyida.robot.communicate.app.hardware.humiture.HumitureHandler;
import com.yongyida.robot.communicate.app.hardware.infrared.InfraredHandler;
import com.yongyida.robot.communicate.app.hardware.led.LedHandler;
import com.yongyida.robot.communicate.app.hardware.light.LightHandler;
import com.yongyida.robot.communicate.app.hardware.motion.MotionHandler;
import com.yongyida.robot.communicate.app.hardware.touch.TouchHandler;
import com.yongyida.robot.communicate.app.hardware.vision.VisionHandler;
import com.yongyida.robot.communicate.app.hardware.zigbee.ZigbeeHandler;
import com.hiva.communicate.app.server.ServerService;
import com.hiva.communicate.app.utils.LogHelper;

import java.util.ArrayList;

/**
 * Created by HuangXiangXiang on 2017/11/30.
 */
public class HardWareServerService extends ServerService {

    private static final String TAG = HardWareServerService.class.getSimpleName() ;

    private ArrayList<BaseHandler> mHandlers = new ArrayList<>() ;
    private void initHandlers (){

        mHandlers.add(new MotionHandler()) ;
        mHandlers.add(new LedHandler()) ;
        mHandlers.add(new TouchHandler()) ;
        mHandlers.add(new BatteryHandler()) ;
        mHandlers.add(new InfraredHandler()) ;
        mHandlers.add(new HumitureHandler()) ;
        mHandlers.add(new LightHandler()) ;
        mHandlers.add(new CameraHandler()) ;
        mHandlers.add(new ZigbeeHandler()) ;
        mHandlers.add(new VisionHandler()) ;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initHandlers() ;
    }


    @Override
    protected void onReceiver(BaseSend send, IResponseListener responseListener) {

        final int size = mHandlers.size() ;
        for (int i = 0 ; i < size ; i++){

            BaseHandler baseHandler = mHandlers.get(i) ;
            if(baseHandler.isCanHandle(send,responseListener)){

                return ;
            }
        }

        LogHelper.e(TAG, "被遗漏的SEND : " + send);
    }
}
