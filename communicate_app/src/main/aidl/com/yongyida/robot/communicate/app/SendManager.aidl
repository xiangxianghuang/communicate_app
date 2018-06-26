package com.yongyida.robot.communicate.app ;

import com.yongyida.robot.communicate.app.ResponseListener ;

interface SendManager {

    void send(String content, ResponseListener responseListener) ;

}
