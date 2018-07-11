package com.yongyida.robot.communicate.app.hardware.motion.response.data;

import com.yongyida.robot.communicate.app.common.response.BaseResponseControl;

import java.util.ArrayList;

/**
 * Created by HuangXiangXiang on 2018/3/8.
 *
 * 舵机系统信息
 */
public class MotionSystem extends BaseResponseControl {

    private ArrayList<Item> items = new ArrayList<>() ;

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public boolean addItem(String title, String info){

        int size = items.size() ;
        for (int i = 0 ; i < size ; i++){

            Item item = items.get(i) ;
            if(title.equals(item.title)){

                if(info != null && info.equals(item.info)){

                    return false ;
                }else {

                    item.info = info ;

                    return true ;
                }
            }
        }

        items.add(new Item(title, info)) ;
        return true ;
    }


    public static class Item{

        // 信息类型
        public String title ;
        // 信息数据值
        public String info ;

        Item( String title, String info ){

            this.title = title ;
            this.info = info ;
        }

    }

}
