package com.yongyida.robot.communicate.app.hardware.touch.send;

import com.hiva.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.hardware.TestData;
import com.yongyida.robot.communicate.app.hardware.touch.data.QueryTouchInfo;

/**
 * Created by HuangXiangXiang on 2018/3/1.
 */
public class TouchSend extends BaseSend {

    private TestData testData ;

    private QueryTouchInfo queryTouchPosition ;

    public TouchSend(){

    }

    public TouchSend(QueryTouchInfo queryTouchPosition){

        this.queryTouchPosition = queryTouchPosition ;
    }


    public QueryTouchInfo getQueryTouchPosition() {
        return queryTouchPosition;
    }

    public TestData getTestData() {
        return testData;
    }

    public void setTestData(TestData testData) {
        this.testData = testData;
    }
}
