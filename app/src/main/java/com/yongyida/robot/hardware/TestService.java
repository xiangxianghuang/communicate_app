package com.yongyida.robot.hardware;

import com.yongyida.robot.communicate.app.common.IResponseListener;
import com.yongyida.robot.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.hardware.led.LedControl;
import com.yongyida.robot.communicate.app.hardware.led.LedStatue;
import com.yongyida.robot.communicate.app.hardware.led.response.LedResponse;
import com.yongyida.robot.communicate.app.hardware.led.response.LedStatueResponse;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;
import com.yongyida.robot.communicate.app.hardware.led.send.LedStatueSend;
import com.yongyida.robot.communicate.app.server.HardWareServerService;
import com.yongyida.robot.communicate.app.utils.LogHelper;

/**
 * Created by HuangXiangXiang on 2017/11/29.
 */
public class TestService extends HardWareServerService {

    private static final String TAG = TestService.class.getSimpleName() ;

    private LedControl mLedControl = new LedControl() ;

    @Override
    protected void onReceiver(BaseSend send, IResponseListener responseListener) {

        if(send instanceof LedStatueSend){

            if(responseListener != null){

                LedStatueResponse ledStatueResponse = new LedStatueResponse() ;
                mLedControl.getLedStatue().setColor(0xffffff);
                ledStatueResponse.setLedStatue(mLedControl.getLedStatue());
                responseListener.onResponse(ledStatueResponse) ;
            }

        }else if(send instanceof LedSend){

            LedSend ledSend = (LedSend) send;
            LedStatue ledStatue = ledSend.getLedStatue() ;
            mLedControl.setLedStatue(ledStatue);

            LogHelper.i(TAG, LogHelper.__TAG__() + ", mLedStatue : " + ledStatue);

            if(responseListener != null){

                LedResponse ledResponse = new LedResponse() ;
                responseListener.onResponse(ledResponse) ;
            }
        }

    }

}
