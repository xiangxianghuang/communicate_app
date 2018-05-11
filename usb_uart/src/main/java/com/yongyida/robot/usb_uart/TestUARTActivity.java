package com.yongyida.robot.usb_uart;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by HuangXiangXiang on 2018/4/18.
 */
public class TestUARTActivity extends Activity implements View.OnClickListener {


    private UART mUART ;

    /**
     * 连接
     */
    private Button mConnectBtn;
    /**
     * 写入
     */
    private Button mWriteBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUART = UART.getInstance(this) ;

        setContentView(R.layout.activity_test_uart);
        initView();
    }


    private void initView() {
        mConnectBtn = (Button) findViewById(R.id.connect_btn);
        mConnectBtn.setOnClickListener(this);
        mWriteBtn = (Button) findViewById(R.id.write_btn);
        mWriteBtn.setOnClickListener(this);
        mWriteBtn.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.connect_btn) {
            boolean result = mUART.openDevice(new UART.OpenDeviceListener() {
                @Override
                public void onOpenDevice(boolean isOpen) {

                    mWriteBtn.setEnabled(isOpen);
                }
            });
            mWriteBtn.setEnabled(result);


        } else if (i == R.id.write_btn) {

//            aa 55 0c dd 07 06 00 00 00 00 00 00 00 00 00 d0 ;        //  点赞
//            aa 55 0c dd 07 01 00 00 00 00 00 00 00 00 00 d7 ;        //  初始化


            byte[] data;

            if (isReset) {

                mWriteBtn.setText("点赞");
                data = new byte[]{(byte) 0xAA, 0x55, 0x0C, (byte) 0xdd, 0x07, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xd7};

            } else {

                mWriteBtn.setText("常态");

                data = new byte[]{(byte) 0xAA, 0x55, 0x0C, (byte) 0xdd, 0x07, 0x06, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xd0};

            }
            isReset = !isReset;

            mUART.writeData(data);


        } else {
        }
    }


    boolean isReset ;
}
