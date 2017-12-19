package com.yongyida.robot.communicate.app.hardware.led;

import com.hiva.communicate.app.common.response.BaseResponse;
import com.yongyida.robot.communicate.app.hardware.IControl;
import com.yongyida.robot.communicate.app.hardware.led.data.LedScene;
import com.yongyida.robot.communicate.app.hardware.led.data.LedStatue;
import com.yongyida.robot.communicate.app.hardware.led.send.LedSend;
import com.yongyida.robot.communicate.app.hardware.vision.send.VisionDataSend;
import com.yongyida.robot.control.model.HardwareConfig;

/**
 * Created by HuangXiangXiang on 2017/12/5.
 */
public abstract class LedControl implements IControl {

    public BaseResponse onControl(LedSend ledSend){

      LedScene ledScene = ledSend.getLedScene() ;
      if(ledScene != null){

          return onControl(ledScene) ;
      }


      LedStatue ledStatue = ledSend.getLedStatue() ;
      if(ledStatue != null){

          return onControl(ledStatue) ;
      }

      return null ;
  }

    public abstract BaseResponse onControl(LedStatue ledStatue) ;

    public abstract BaseResponse onControl(LedScene ledScene) ;

    @Override
    public int getType() {

        return HardwareConfig.TYPE_LED;
    }

}
