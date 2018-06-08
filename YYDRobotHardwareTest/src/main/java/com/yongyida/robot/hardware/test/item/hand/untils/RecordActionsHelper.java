package com.yongyida.robot.hardware.test.item.hand.untils;


/* 
                              _ooOoo_ 
                             o8888888o 
                             88" . "88 
                             (| -_- |) 
                             O\  =  /O 
                          ____/`---'\____ 
                        .'  \\|     |//  `. 
                       /  \\|||  :  |||//  \ 
                      /  _||||| -:- |||||-  \ 
                      |   | \\\  -  /// |   | 
                      | \_|  ''\---/''  |   | 
                      \  .-\__  `-`  ___/-. / 
                    ___`. .'  /--.--\  `. . __ 
                 ."" '<  `.___\_<|>_/___.'  >'"". 
                | | :  `- \`.;`\ _ /`;.`/ - ` : | | 
                \  \ `-.   \_ __\ /__ _/   .-` /  / 
           ======`-.____`-.___\_____/___.-`____.-'====== 
                              `=---=' 
           .............................................  
                    佛祖镇楼                  BUG辟易  
            佛曰:  
                    写字楼里写字间，写字间里程序员；  
                    程序人员写程序，又拿程序换酒钱。  
                    酒醒只在网上坐，酒醉还来网下眠；  
                    酒醉酒醒日复日，网上网下年复年。  
                    但愿老死电脑间，不愿鞠躬老板前；  
                    奔驰宝马贵者趣，公交自行程序员。  
                    别人笑我忒疯癫，我笑自己命太贱；  
                    不见满街漂亮妹，哪个归得程序员？ 
*/

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.yongyida.robot.hardware.test.item.hand.bean.RecordArmAngle;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Create By HuangXiangXiang 2018/5/30
 */
public class RecordActionsHelper {

    private static final String DATA            = "Data" ;
    private static final String RECORD_ACTIONS     = "RecordActions" ;
    private static Gson GSON = new Gson() ;

    private Context mContext;




    public RecordActions recordActions ;

    private static RecordActionsHelper mRecordActionsHelper ;
    public static RecordActionsHelper getInstance (Context context){

        if(mRecordActionsHelper == null){

            mRecordActionsHelper = new RecordActionsHelper(context.getApplicationContext());
        }

        return mRecordActionsHelper ;
    }

    private RecordActionsHelper(Context context){

        this.mContext = context ;
        this.recordActions = readRecordActions(context) ;
    }


    private RecordActions readRecordActions(Context context){

        String data = readData(context) ;

        RecordActions recordActions ;

        try{
            recordActions = GSON.fromJson(data, RecordActions.class) ;

        }catch (Exception e){

            recordActions = null ;
        }

         if(recordActions == null){

            recordActions = new RecordActions() ;
         }

        return recordActions ;
    }
    private String readData(Context context){

        SharedPreferences sp = context.getSharedPreferences(DATA, Context.MODE_PRIVATE) ;
        String data = sp.getString(RECORD_ACTIONS, null) ;

        return data ;
    }


    public void writeRecordActions(RecordAction recordAction){

        if(recordAction.id == -1){

            recordActions.lastId ++ ;
            recordAction.id = recordActions.lastId ;

            recordActions.recordActions.add(recordAction);

        }else {

            RecordAction rd = findRecordAction(recordAction);
            if(rd != null){

                rd.setRecordAction(recordAction);

            }else {

                recordActions.lastId ++ ;
                recordAction.id = recordActions.lastId ;

                recordActions.recordActions.add(recordAction);
            }
        }

        saveRecordActions() ;
    }

    private RecordAction findRecordAction(RecordAction recordAction){

        int size = recordActions.recordActions.size() ;
        for (int i = 0 ; i < size; i ++){

            RecordAction ra = recordActions.recordActions.get(i) ;

            if(ra.id == recordAction.id){

                return ra ;
            }


        }

        return null ;

    }




    public void saveRecordActions(){

        String data = toRecordActionsString(recordActions) ;

        SharedPreferences sp = mContext.getSharedPreferences(DATA, Context.MODE_PRIVATE) ;
        SharedPreferences.Editor editor = sp.edit() ;
        editor.putString(RECORD_ACTIONS, data) ;
        editor.apply();
    }


    private static String toRecordActionsString(RecordActions recordActions){

        if(recordActions == null){

            return null ;
        }
        return GSON.toJson(recordActions) ;
    }


    /**动作记录集合*/
    public static class RecordActions{

        private int lastId ;    //最后数据的id

        public ArrayList<RecordAction> recordActions = new ArrayList<>() ;

    }

    public static class RecordAction{

        private int id = -1;
        public String name = "新的动作组";
        public ArrayList<RecordArmAngle> recordArmAngles = new ArrayList<>() ;


        /**
         * 深度拷贝
         * */
        public RecordAction deepClone(){

            String data = GSON.toJson(this) ;

            return GSON.fromJson(data, RecordAction.class) ;
        }


        public void setRecordAction(RecordAction recordAction){

            this.id = recordAction.id ;
            this.name = recordAction.name ;
            this.recordArmAngles = recordAction.recordArmAngles ;
        }

    }



}
