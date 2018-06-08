package com.hiva.communicate.app.common.send.data;


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

import com.hiva.communicate.app.common.send.BaseSend;
import com.hiva.communicate.app.utils.LogHelper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Create By HuangXiangXiang 2018/6/5
 */
public abstract class BaseSendControl<T extends BaseSend> {

    private static final String TAG = BaseSendControl.class.getSimpleName() ;

    public T baseSend ;

    public BaseSend getSend(){

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



}
