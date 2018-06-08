package com.yongyida.robot.communicate.app.hardware.touch.response.data;

import com.hiva.communicate.app.common.response.BaseResponseControl;

import java.util.ArrayList;

/**
 * Created by HuangXiangXiang on 2018/3/1.
 */
public class TouchInfo extends BaseResponseControl {

    //触摸点
    public enum Point{

        FORE_HEAD,      //前额
        BACK_HEAD,      //后脑
        LEFT_SHOULDER,  //左肩
        LEFT_ARM,       //左手臂
        RIGHT_SHOULDER, //右肩
        RIGHT_ARM,      //右手臂
    }


    private ArrayList<Position> mPositions ;

    public ArrayList<Position> getPositions() {
        return mPositions;
    }

    public void setPositions(ArrayList<Position> positions) {
        this.mPositions = positions;
    }

    public static class Position{

        private String name ;
        private Point point ;

        public Position(String name, Point point){

            this.name = name ;
            this.point = point ;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Point getPoint() {
            return point;
        }

        public void setPoint(Point point) {
            this.point = point;
        }
    }


}
