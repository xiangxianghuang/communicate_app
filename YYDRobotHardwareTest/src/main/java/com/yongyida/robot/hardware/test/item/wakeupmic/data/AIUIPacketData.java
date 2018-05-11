package com.yongyida.robot.hardware.test.item.wakeupmic.data;

/**
 * Created by HuangXiangXiang on 2018/3/28.
 *
 *  {
 * 　　"content":{
 * 　　　　"arg1":0,
 * 　　　　"info":{
 * 　　　　　　"power":12682733289472,
 * 　　　　　　"beam":3,
 * 　　　　　　"CMScore":178,
 * 　　　　　　"channel":1,
 * 　　　　　　"angle":155
 * 　　　　},
 * 　　　　"arg2":0,
 * 　　　　"eventType":4
 * 　　},
 * 　　"type":"aiui_event"
 *  }
 */
public class AIUIPacketData {

    private String type ;
    private Content content ;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public static class Content{

        private int arg1 ;
        private int arg2 ;
        private int eventType ;
        private Info info ;

        public int getArg1() {
            return arg1;
        }

        public void setArg1(int arg1) {
            this.arg1 = arg1;
        }

        public int getArg2() {
            return arg2;
        }

        public void setArg2(int arg2) {
            this.arg2 = arg2;
        }

        public int getEventType() {
            return eventType;
        }

        public void setEventType(int eventType) {
            this.eventType = eventType;
        }

        public Info getInfo() {
            return info;
        }

        public void setInfo(Info info) {
            this.info = info;
        }

        public static class Info{

            private long power ;
            private int bean ;
            private int CMScore ;
            private int channel ;
            private int angle ;

            public long getPower() {
                return power;
            }

            public void setPower(long power) {
                this.power = power;
            }

            public int getBean() {
                return bean;
            }

            public void setBean(int bean) {
                this.bean = bean;
            }

            public int getCMScore() {
                return CMScore;
            }

            public void setCMScore(int CMScore) {
                this.CMScore = CMScore;
            }

            public int getChannel() {
                return channel;
            }

            public void setChannel(int channel) {
                this.channel = channel;
            }

            public int getAngle() {
                return angle;
            }

            public void setAngle(int angle) {
                this.angle = angle;
            }
        }



    }

}
