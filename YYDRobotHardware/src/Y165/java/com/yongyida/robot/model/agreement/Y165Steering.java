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

//            content[0] = (byte) (isOnOff ? 0x0A : 0x0B);

            content = isOnOff ? CMD_ON : CMD_OFF ;
            content[content.length-1] = getCheck(content) ;
        }
    }

    private static final byte HEAD_0 = 0x55 ;
    private static final byte HEAD_1 = (byte) 0xAA;

    private static final byte[] CMD_ON  = {HEAD_0, HEAD_1, 0x02, 0x01, 0x01, 0x00} ;
    private static final byte[] CMD_OFF = {HEAD_0, HEAD_1, 0x02, 0x01, 0x00, 0x00} ;

    private static byte getCheck(byte[] cmd){

        byte check = 0 ;

        final int length = cmd.length - 1 ;
        for (int i = 2 ; i < length ; i++){

            check ^= cmd[i];
        }

        return check ;
    }

}
