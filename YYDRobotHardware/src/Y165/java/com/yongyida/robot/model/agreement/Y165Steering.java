package com.yongyida.robot.model.agreement;

/**
 * Created by HuangXiangXiang on 2018/4/17.
 */
public class Y165Steering {


    public static abstract class SingleChip {

        protected byte[] content = new byte[1] ;


        public SingleChip() {


        }

        public abstract byte getFunction();  // 功能码

        public byte[] getContent() {

            return content;
        }
    }

    public static class EyeLed extends SingleChip {

        @Override
        public byte getFunction() {
            return 0x08;
        }

        public void setOnOff(boolean isOnOff){

            content[0] = (byte) (isOnOff ? 0xAA : 0xBB);
        }


    }

}
