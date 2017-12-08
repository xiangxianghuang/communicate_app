package com.hiva.communicate.app ;

import com.hiva.communicate.app.ResponseListener ;

interface SendManager {

    String setResponseListener(String content, ResponseListener responseListener) ;

    String send(String content, ResponseListener responseListener) ;

}
