package com.yongyida.robot.communicate.app.common.send.data;


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

import com.google.gson.Gson;
import com.yongyida.robot.communicate.app.common.send.BaseSend;
import com.yongyida.robot.communicate.app.utils.LogHelper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Create By HuangXiangXiang 2018/6/5
 */
public abstract class BaseSendControl<T extends BaseSend> {

    private static final String TAG = BaseSendControl.class.getSimpleName() ;

    protected final static Gson GSON = new Gson() ;

    private String tag ;

    protected T baseSend ;

    private boolean isControl = true ; //是否控制

    public T getSend(){

        if(baseSend == null){

            Type genType = getClass().getSuperclass().getGenericSuperclass();
            LogHelper.i(TAG, LogHelper.__TAG__() + ", genType : " + genType );

            Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
            LogHelper.i(TAG, LogHelper.__TAG__() + ", params : " + params );

            Class<T> entityClass = (Class<T>) params[0];
            LogHelper.i(TAG, LogHelper.__TAG__() + ", entityClass : " + entityClass );

            try {
                baseSend = entityClass.newInstance() ;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // 每次发送前执行
        baseSend.setBaseControl(this);
        return baseSend ;
    }


    public String getTag() {
        return tag;
    }

    public void setTag(Context context) {

        if(context != null && tag == null){

            tag = context.getPackageName() + "_" + context.toString() ;
        }

    }


    public boolean isControl() {
        return isControl;
    }

    public void setControl(boolean control) {
        isControl = control;
    }

    @Override
    public String toString() {

        return toJson() ;
    }

    public String toJson(){

        T t = this.baseSend ;
        this.baseSend = null ;

        String json = GSON.toJson(this) ;

        this.baseSend = t ;

        return json ;
    }


    public static ArrayList<? extends BaseSendControl> getNeedSendControls(ArrayList<? extends BaseSendControl> steeringControls){

        ArrayList controls = new ArrayList<>() ;

        int size = steeringControls.size() ;
        for (int i = 0 ; i < size ; i ++){

            BaseSendControl control = steeringControls.get(i) ;
            if(control.isControl()){
                controls.add(control) ;
            }
        }
        return controls ;
    }

}
