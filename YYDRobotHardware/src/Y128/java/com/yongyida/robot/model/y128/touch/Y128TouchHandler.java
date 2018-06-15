package com.yongyida.robot.model.y128.touch;

import android.content.Context;

import com.hiva.communicate.app.common.response.SendResponse;
import com.hiva.communicate.app.common.send.data.BaseSendControl;
import com.hiva.communicate.app.server.IResponseListener;
import com.yongyida.robot.communicate.app.hardware.touch.TouchHandler;
import com.yongyida.robot.communicate.app.hardware.touch.send.data.QueryTouchInfo;
import com.yongyida.robot.communicate.app.hardware.touch.response.data.TouchInfo;
import com.yongyida.robot.communicate.app.hardware.touch.send.TouchSend;
import com.yongyida.robot.control.model.ModelInfo;
import com.yongyida.robot.model.agreement.Y128Receive;

import java.util.ArrayList;

/**
 * Created by HuangXiangXiang on 2018/3/1.
 */
public class Y128TouchHandler extends TouchHandler {

    private TouchInfo touchPositions ;

    public Y128TouchHandler(Context context){
        super(context);

        ModelInfo modelInfo = ModelInfo.getInstance() ;
        if(modelInfo.getModel().contains("YQ110")){

            touchPositions = initYQ110() ;
        }else{

            touchPositions = initY128() ;
        }

        startListenTouch() ;
    }

    private TouchInfo initY128(){

        ArrayList<TouchInfo.Position> positions = new ArrayList<>() ;

        TouchInfo.Position position = new TouchInfo.Position("前脑", TouchInfo.Point.FORE_HEAD) ;
        positions.add(position) ;

        position = new TouchInfo.Position("后脑勺", TouchInfo.Point.BACK_HEAD) ;
        positions.add(position) ;

        position = new TouchInfo.Position("左肩", TouchInfo.Point.LEFT_SHOULDER) ;
        positions.add(position) ;

        position = new TouchInfo.Position("左手臂", TouchInfo.Point.LEFT_ARM) ;
        positions.add(position) ;

        position = new TouchInfo.Position("右肩", TouchInfo.Point.RIGHT_SHOULDER) ;
        positions.add(position) ;

        position = new TouchInfo.Position("右手臂", TouchInfo.Point.RIGHT_ARM) ;
        positions.add(position) ;

        TouchInfo touchPositions = new TouchInfo() ;
        touchPositions.setPositions(positions);

        return touchPositions;
    }

    private TouchInfo initYQ110(){

        ArrayList<TouchInfo.Position> positions = new ArrayList<>() ;

        TouchInfo.Position position = new TouchInfo.Position("前脑", TouchInfo.Point.FORE_HEAD) ;
        positions.add(position) ;

        position = new TouchInfo.Position("后脑勺", TouchInfo.Point.BACK_HEAD) ;
        positions.add(position) ;

        position = new TouchInfo.Position("左肩", TouchInfo.Point.LEFT_SHOULDER) ;
        positions.add(position) ;

        position = new TouchInfo.Position("右肩", TouchInfo.Point.RIGHT_SHOULDER) ;
        positions.add(position) ;

        TouchInfo touchPositions = new TouchInfo() ;
        touchPositions.setPositions(positions);

        return touchPositions;
    }



    private void startListenTouch(){

        Y128Receive serialReceive = Y128Receive.getInstance() ;
        serialReceive.setOnTouchListener(onTouchListener);

    }


    @Override
    public SendResponse onHandler(TouchSend send, IResponseListener responseListener) {

        BaseSendControl baseControl = send.getBaseControl() ;

        if(baseControl instanceof QueryTouchInfo){
            // 查询
            return touchPositions.getResponse() ;
        }


        return null;
    }



    private OnTouchListener onTouchListener = new OnTouchListener(){

        @Override
        public void onTouchListener(TouchInfo.Point point) {

            onTouch(point);
        }
    };

}
