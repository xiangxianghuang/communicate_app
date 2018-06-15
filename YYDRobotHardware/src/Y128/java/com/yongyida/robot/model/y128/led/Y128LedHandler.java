package com.yongyida.robot.model.y128.led;

import android.content.Context;

import com.hiva.communicate.app.common.response.SendResponse;
import com.hiva.communicate.app.server.IResponseListener;
import com.yongyida.robot.communicate.app.hardware.led.LedHandler;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;
import com.yongyida.robot.model.agreement.Y128Steering;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class Y128LedHandler extends LedHandler {

    private Y128Steering.SteerLed breathLed = new Y128Steering.SteerLed() ;
    private Y128Steering.SteerLed2 breathLed2 = new Y128Steering.SteerLed2() ;


    public Y128LedHandler(Context context) {
        super(context);
    }

    @Override
    public SendResponse onHandler(LedSend ledSend, IResponseListener responseListener) {

//        LedControl ledControl = ledSend.getLedControl() ;
//        if(ledControl != null){
//
//            LedControl.Effect e = ledControl.getEffect() ;
//            if(e == null){
//
//                return null ;
//            }
//
//            int p = ledControl.getPosition() ;
//            switch (p){
//
//                case LedControl.POSITION_LEFT_EAR :
//
//                    breathLed.setPosition(Y128Steering.SteerLed.POSITION_LEFT_EAR);
//                    breathLed2.setPosition(Y128Steering.SteerLed.POSITION_LEFT_EAR);
//                    break;
//
//                case LedControl.POSITION_RIGHT_EAR :
//
//                    breathLed.setPosition(Y128Steering.SteerLed.POSITION_RIGHT_EAR);
//                    breathLed2.setPosition(Y128Steering.SteerLed.POSITION_RIGHT_EAR);
//                    break;
//
//                case LedControl.POSITION_CHEST :
//
//                    breathLed.setPosition(Y128Steering.SteerLed.POSITION_CHEST);
//                    breathLed2.setPosition(Y128Steering.SteerLed.POSITION_CHEST);
//                    break;
//
//            }
//
//
//            LedControl.Color c = ledControl.getColor() ;
//            if(c != null){
//
//                int value = (0xFF<< 24 )|c.getColor() ;
//                switch (value){
//
//                    case Color.RED:
//
//                        breathLed.setColor(Y128Steering.SteerLed.COLOR_RED);
//                        breathLed2.setColor(Y128Steering.SteerLed.COLOR_RED);
//                        break;
//
//                    case Color.GREEN:
//
//                        breathLed.setColor(Y128Steering.SteerLed.COLOR_GREEN);
//                        breathLed2.setColor(Y128Steering.SteerLed.COLOR_GREEN);
//                        break;
//
//                    case Color.BLUE:
//
//                        breathLed.setColor(Y128Steering.SteerLed.COLOR_BLUE);
//                        breathLed2.setColor(Y128Steering.SteerLed.COLOR_BLUE);
//                        break;
//                }
//
//            }
//
//            switch (e){
//
//                case NORMAL:
//
//                    breathLed2.setOn(true);
//                    Y128Send.getInstance().sendData(breathLed2) ;
//                    break;
//
//                case LED_OFF:
//
//                    breathLed2.setOn(false);
//                    Y128Send.getInstance().sendData(breathLed2) ;
//                    break ;
//
//                case BREATH_LOW:
//
//                    breathLed.setSpeed((byte) 60);
//                    Y128Send.getInstance().sendData(breathLed) ;
//                    break;
//
//                case BREATH_MIDDLE:
//
//                    breathLed.setSpeed((byte) 30);
//                    Y128Send.getInstance().sendData(breathLed) ;
//                    break;
//
//                case BREATH_FAST:
//
//                    breathLed.setSpeed((byte) 1);
//                    Y128Send.getInstance().sendData(breathLed) ;
//                    break;
//            }
//
//        }

        return null;
    }


}
