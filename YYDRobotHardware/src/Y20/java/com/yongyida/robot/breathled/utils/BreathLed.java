package com.yongyida.robot.breathled.utils;

public class BreathLed {

	static {
		System.loadLibrary("BreathLedControl");
	}

	/**
	 * 打开LED设备
	 * @return
	 */
	public static native int openDev();

	/**
	 * 关闭LED设备
	 * @return
	 */
	public static native int closeDev();

	/**
	 * 打开或关闭机器人某些部位的LED
	 *
	 * @param Led
	 * ear ------------ 耳朵
	 * chest ---------- 胸膛
	 * all ------------ 全部
	 *
	 * @param on
	 * on ----------- 打开
	 * off ---------- 关闭
	 * @return
	 */
	public static native int setOnoff(String Led, String on);

	/**
	 * 获取机器人某些部位的LED
	 * @param Led
	 * ear ------------ 耳朵
	 * chest ---------- 胸膛
	 * all ------------ 全部
	 *
	 * @return
	 */
	public static native char getOnoff(String Led);

	/**
	 * 设置机器人某些部位的LED颜色
	 * @param Led
	 * ear ------------ 耳朵
	 * chest ---------- 胸膛
	 * all ------------ 全部
	 *
	 * @param color
	 * Red
	 * Green
	 * Blue
	 * GreenBlue // cyan
	 * RedBlue	// 品红
	 * RedGreen //yellow
	 * White
	 *
	 * @return
	 */
	public static native int setColor(String Led, String color);

	/**
	 * 得到机器人某些部位的LED颜色
	 * @param Led
	 * @return
	 */
	public static native char getColor(String Led);

	/**
	 * 设置机器人某些部位的LED闪动频率
	 * @param Led
	 * ear ------------ 耳朵
	 * chest ---------- 胸膛
	 * all ------------ 全部
	 *
	 * @param freq
	 * Low ----------------- 低
	 * Middle -------------- 中
	 * High ---------------- 高
	 * Const --------------- 常亮
	 * @return
	 */
	public static native int setFreq(String Led, String freq);

	/**+.
	 * 得到机器人某些部位的LED闪动频率
	 * @param Led
	 * @return
	 */
	public static native char getFreq(String Led);
			
}


