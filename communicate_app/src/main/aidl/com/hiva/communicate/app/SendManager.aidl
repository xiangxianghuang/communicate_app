package com.hiva.communicate.app ;

import com.hiva.communicate.app.ResponseListener ;

interface SendManager {

    void send(String content, ResponseListener responseListener) ;

}
