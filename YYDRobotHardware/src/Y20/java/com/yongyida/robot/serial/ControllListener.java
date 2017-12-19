package com.yongyida.robot.serial;

public interface ControllListener {

	public void onCallBack(int cmdType, byte[] data);
}
