package com.yongyida.robot.hardware.test.item.camera;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;

import java.io.IOException;

/**
 * Created by HuangXiangXiang on 2018/2/9.
 * 测试摄像头
 */
public class TestCameraActivity extends TestBaseActivity implements View.OnClickListener {

    private static final String TAG = TestCameraActivity.class.getSimpleName() ;

    private FrameLayout mPreFlt;
    /**
     * 拍照
     */
    private Button mTakePhoneBtn;
    private ImageView mSmallIvw;
    private ImageView mBigIvw;


    private Camera mCamera;
    private CameraPreviewView mPreviewView;

    @Override
    protected View initContentView() {

        View view = LayoutInflater.from(this).inflate(R.layout.activity_test_camera, null);
        initView(view);

        return view;
    }

    @Override
    protected String getTips() {
        return null;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK) ;   //打开后置摄像头

        }catch (Exception e){

            LogHelper.e(TAG, "CAMERA_FACING_BACK : " + e.getMessage());
        }

        if(mCamera == null){

            try{
                mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT) ;//打开前置摄像头

            }catch (Exception e){
                LogHelper.e(TAG, "CAMERA_FACING_FRONT : " + e.getMessage());
            }
        }


        if(mCamera == null){

            Toast.makeText(this,"摄像头打开失败",Toast.LENGTH_LONG).show();
            onFail();

            return ;
        }

        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPreviewSize(1280, 720);
        mCamera.setParameters(parameters);

        mPreviewView = new CameraPreviewView(this,mCamera) ;
        mPreFlt.addView(mPreviewView);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView(View view) {

        mPreFlt = (FrameLayout) view.findViewById(R.id.pre_flt);
        mTakePhoneBtn = (Button) view.findViewById(R.id.take_phone_btn);
        mTakePhoneBtn.setOnClickListener(this);
        mSmallIvw = (ImageView) view.findViewById(R.id.small_ivw);
        mSmallIvw.setOnClickListener(this);
        mBigIvw = (ImageView) view.findViewById(R.id.big_ivw);
        mBigIvw.setOnClickListener(this);

    }

    private Bitmap mBitmap ;

    @Override
    public void onClick(View v) {

        if(v == mTakePhoneBtn){

            mCamera.takePicture(null, null, mPictureCallback);

        }else if(v == mSmallIvw){

            mBigIvw.setVisibility(View.VISIBLE);
            mBigIvw.setImageBitmap(mBitmap);
            mTakePhoneBtn.setVisibility(View.GONE);


        }else if(v == mBigIvw){

            mBigIvw.setVisibility(View.GONE);
            mTakePhoneBtn.setVisibility(View.VISIBLE);

        }

    }

    public class CameraPreviewView extends SurfaceView implements SurfaceHolder.Callback {
        private SurfaceHolder holder;
        private Camera mCamera;

        public CameraPreviewView(Context context, Camera camera) {
            super(context);
            this.mCamera = camera;

            holder = getHolder();
            holder.addCallback(this);
            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (holder.getSurface() == null) {
                return;
            }
            if (mCamera != null) {
                try {
                    mCamera.stopPreview();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {


            mBitmap = BitmapFactory.decodeByteArray(data,0, data.length) ;

            mSmallIvw.setImageBitmap(mBitmap);

            mCamera.startPreview();
        }
    };


}
