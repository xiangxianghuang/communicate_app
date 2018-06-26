package com.yongyida.robot.hardware.test.item.wakeupmic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.iflytek.aiui.uartkit.UARTAgent;
import com.iflytek.aiui.uartkit.constant.UARTConstant;
import com.iflytek.aiui.uartkit.entity.AIUIPacket;
import com.iflytek.aiui.uartkit.entity.CustomPacket;
import com.iflytek.aiui.uartkit.entity.MsgPacket;
import com.iflytek.aiui.uartkit.listener.EventListener;
import com.iflytek.aiui.uartkit.listener.UARTEvent;
import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by HuangXiangXiang on 2018/3/20.
 */
public class TestWakeUpMicActivity extends TestBaseActivity {

    private static final String TAG = TestWakeUpMicActivity.class.getSimpleName() ;

    private UARTAgent mAgent;

    private TextView mWakeUpResultTvw ;


    @Override
    protected View initContentView() {

        View view = mLayoutInflater.inflate(R.layout.activity_test_wake_up_mic, null) ;

        mWakeUpResultTvw = view.findViewById(R.id.wake_up_result_tvw) ;

        return view;
    }

    @Override
    protected String getTips() {
        return "唤醒词\"叮咚叮咚\"";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//
//        Intent intent = new Intent() ;
//        intent.setClassName("com.yongyida.robot.voice", "com.zccl.ruiqianqi.presentation.mictest.MicTestActivity" );
//        startActivity(intent);

        init() ;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        mAgent.destroy();
    }

    private void init(){

        mAgent = UARTAgent.createAgent("/dev/ttyS4", 115200, new EventListener() {

            @Override
            public void onEvent(UARTEvent event) {
                switch (event.eventType) {
                    case UARTConstant.EVENT_INIT_SUCCESS:
                        LogHelper.i(TAG, LogHelper.__TAG__() + "Init UART Success");
                        break;

                    case UARTConstant.EVENT_INIT_FAILED:
                        LogHelper.i(TAG, LogHelper.__TAG__() + "Init UART Failed");
                        break;

                    case UARTConstant.EVENT_MSG:
                        MsgPacket recvPacket = (MsgPacket) event.data;
                        LogHelper.i(TAG, LogHelper.__TAG__() + "recvPacket " + recvPacket.toString());
                        processPacket(recvPacket);
                        break;

                    case UARTConstant.EVENT_SEND_FAILED:
                        MsgPacket sendPacket = (MsgPacket) event.data;
                        mAgent.sendMessage(sendPacket);
                    default:
                        break;
                }
            }
        });

    }


    private static Gson mGson = new Gson() ;

    private void processPacket(MsgPacket packet) {
        LogHelper.i(TAG, LogHelper.__TAG__() + "type " + packet.getMsgType());
        switch (packet.getMsgType()) {
            case MsgPacket.AIUI_PACKET_TYPE:
                String content = new String(((AIUIPacket) packet).content);
                LogHelper.i(TAG, LogHelper.__TAG__() + "recv aiui result" + content);
                proecssAIUIPacket(content);
                break;

            case MsgPacket.CUSTOM_PACKET_TYPE:
                LogHelper.i(TAG, LogHelper.__TAG__() + "recv aiui custom data " + Arrays.toString(((CustomPacket)packet).customData));
                break;

            default:
                break;
        }
    }

    private void proecssAIUIPacket(String json) {

//        AIUIPacketData aiuiPacketData = mGson.fromJson(json, AIUIPacketData.class) ;
//        if(aiuiPacketData != null){
//
//            AIUIPacketData.Content content = aiuiPacketData.getContent() ;
//            if(content != null){
//
//                AIUIPacketData.Content.Info info = content.getInfo() ;
//                int angle = info.getAngle() ;
//
//                LogHelper.i(TAG, LogHelper.__TAG__() + "angle : " + angle);
//            }
//        }


        try {
            JSONObject data = new JSONObject(json);
            String type = data.optString("type", "");
            //OTA message
            if (type.equals("ota")) {
//                OTAProcessor.process(data);
            } else if (type.equals("smartConfig")) {
//                SmartConfigProcessor.process(data);
                LogHelper.i(TAG, LogHelper.__TAG__() + "data " + data);

            } else if (type.equals("aiui_event")) {

                JSONObject content = data.getJSONObject("content") ;
                JSONObject info =  content.getJSONObject("info") ;

                final int angle = info.getInt("angle") ;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mWakeUpResultTvw.setText("角度 : " + angle + "度");
                    }
                });

                LogHelper.i(TAG, LogHelper.__TAG__() + "angle : " + angle);
            }
            else {
                LogHelper.i(TAG, LogHelper.__TAG__() + "type: " + type);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
