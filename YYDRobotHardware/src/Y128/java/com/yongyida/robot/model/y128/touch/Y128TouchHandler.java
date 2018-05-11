package com.yongyida.robot.model.y128.touch;

import android.content.Context;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.TestData;
import com.yongyida.robot.communicate.app.hardware.touch.TouchHandler;
import com.yongyida.robot.communicate.app.hardware.touch.data.QueryTouchInfo;
import com.yongyida.robot.communicate.app.hardware.touch.data.TouchInfo;
import com.yongyida.robot.communicate.app.hardware.touch.response.TouchResponse;
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
    public BaseResponse onHandler(TouchSend send) {

        QueryTouchInfo queryTouchPosition = send.getQueryTouchPosition() ;
        if(queryTouchPosition != null){
            // 查询

            TouchResponse touchResponse = new TouchResponse() ;
            touchResponse.setTouchPositions(touchPositions);

            return touchResponse ;
        }


        TestData testData = send.getTestData();
        if(testData != null){

            setTest(testData.isTest());

            return  null ;
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
