package com.yongyida.robot.hardware.client;

import android.content.Context;

import com.hiva.communicate.app.common.IResponseListener;
import com.hiva.communicate.app.common.SendResponse;
import com.yongyida.robot.communicate.app.hardware.battery.data.BatteryInfo;
import com.yongyida.robot.communicate.app.hardware.battery.data.QueryBattery;
import com.yongyida.robot.communicate.app.hardware.battery.send.BatterySend;

/**
 * Created by HuangXiangXiang on 2018/2/24.
 */
public final class BatteryClient extends BaseClient<BatterySend> {

    private static BatteryClient mBatteryClient ;
    public static BatteryClient getInstance(Context context){

        if(mBatteryClient == null){

            mBatteryClient = new BatteryClient(context.getApplicationContext()) ;
        }
        return mBatteryClient ;
    }

    private BatteryClient(Context context) {
        super(context);
    }

    /**查询电池电量水平*/
    public SendResponse queryBattery( final IResponseListener response) {

        QueryBattery queryBattery = new QueryBattery() ;

        BatterySend batterySend = new BatterySend() ;
        batterySend.setQueryBattery(queryBattery);

        return send(batterySend, response) ;
    }

    public void queryBatteryInMainThread(final IResponseListener response) {

        QueryBattery queryBattery = new QueryBattery() ;

        BatterySend batterySend = new BatterySend() ;
        batterySend.setQueryBattery(queryBattery);

        sendInMainThread(batterySend, response);
    }




}
