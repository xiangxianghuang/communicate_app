package com.hiva.communicate.app.hardware;

import android.content.Context;

import com.hiva.communicate.app.client.Client;
import com.hiva.communicate.app.client.Receiver;
import com.hiva.communicate.app.common.SendResponse;
import com.hiva.communicate.app.hardware.vision.VersionData;
import com.hiva.communicate.app.hardware.vision.send.VersionDataSend;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public class HardwareClient {

    private static final String HARDWARE_PACKAGE_NAME   = "com.yongyida.robot.hardware" ;
    private static final String HARDWARE_ACTION         = "com.yongyida.robot.HARDWARE" ;

    private Context mContext ;
    private Client mClient ;
    private Receiver mReceiver ;

    private static HardwareClient mHardwareClient ;
    public static HardwareClient getInstance(Context context){

        if(mHardwareClient == null){

            mHardwareClient = new HardwareClient(context.getApplicationContext()) ;
        }

        return mHardwareClient ;
    }
    private HardwareClient(Context context){

        this.mContext = context ;
        this.mClient = Client.getInstance(context) ;
    }

    public Receiver getHardwareReceiver(){

        if(mReceiver == null){

            mReceiver = mClient.getReceiver(HARDWARE_PACKAGE_NAME,HARDWARE_ACTION) ;
        }

        return  mReceiver;
    }



    public SendResponse sendVisionData(VersionData versionData){

        getHardwareReceiver() ;

        VersionDataSend versionDataSend = new VersionDataSend() ;
        versionDataSend.setVersionData(versionData);

        return mReceiver.send(versionDataSend, null) ;
    }


}
