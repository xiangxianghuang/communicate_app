package com.yongyida.robot.communicate.app ;

import com.yongyida.robot.communicate.app.ResponseListener ;

interface SendManager {

    String setResponseListener(String content, ResponseListener responseListener) ;

    String send(String content, ResponseListener responseListener) ;

}
