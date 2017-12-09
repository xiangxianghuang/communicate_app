package com.yongyida.robot.model.y128.serial;

import com.yongyida.robot.model.y138.agreement.old.Steering;

/**
 * Created by Huangxiangxiang on 2017/8/11.
 *
 * 串口发送
 *
 */
public interface ISerialSend {

    int sendData(Steering.SingleChip singleChip) ;

}
