package com.yongyida.robot.model.agreement;

/**
 * Created by HuangXiangXiang on 2018/4/17.
 */
public class Y165Steering {


    public static abstract class SingleChip {

        protected byte[] content = new byte[1] ;

        public SingleChip() {
        }

        protected abstract byte getFunction();  // 功能码

        public byte[] getCmd() {

            return content;
        }

    }

    public static class EyeLed extends SingleChip {

        @Override
        public byte getFunction() {
            return 0;
        }

        public void setOnOff(boolean isOnOff){

            content[0] = (byte) (isOnOff ? 0x0A : 0x0B);
        }
    }

}
