package com.yongyida.robot.hardware.test.item.microphone;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by HuangXiangXiang on 2018/2/28.
 */
public class TestMicrophoneActivity extends TestBaseActivity {

    private Button microphoneBtn ;
    private VUMeterView voiceVmv ;
    private TextView tipsTvw ;

    private int preVolume ;

    @Override
    protected View initContentView() {

        View view = LayoutInflater.from(this).inflate(R.layout.activity_test_microphone, null) ;

        this.microphoneBtn = view.findViewById(R.id.microphone_btn) ;
        this.voiceVmv = view.findViewById(R.id.voice_vmv) ;
        this.tipsTvw = view.findViewById(R.id.tips_tvw) ;

        this.microphoneBtn.setOnClickListener(onClickListener);

        return view ;
    }

    @Override
    protected String getTips() {
        return null ;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //将声音设置最大
//        AudioManager localAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        localAudioManager.setRingerMode(2);
//        int i = localAudioManager.getStreamMaxVolume(3);
//        preVolume = localAudioManager.getStreamVolume(3) ;
//        localAudioManager.setStreamVolume(3, i/3, 4);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 将声音恢复
//        AudioManager localAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        localAudioManager.setRingerMode(2);
//        localAudioManager.setStreamVolume(3,preVolume, 4);

        stopRecord();
        stopPlay();
    }


    private View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            randomTest() ;
        }
    } ;

    private void randomTest(){

//        int time = 3 + new Random().nextInt(7) ;

        int time = 5 ;

        test(time) ;
    }

    private void test(int time){

        if(createFile() && startRecord() ){

            microphoneBtn.setEnabled(false);
            this.time = time + 1;
            mHandler.post(mRunnable);

            tipsTvw.setText(getString(R.string.microphone_ready_success, time) + "\n");

        }else{

//            tipsTvw.setText(getString(R.string.microphone_ready_fail) + "\n");

            Toast.makeText(TestMicrophoneActivity.this,R.string.microphone_ready_fail, Toast.LENGTH_LONG).show();
//            onFail() ;

        }
    }

    private int time ;
    private Handler mHandler = new Handler() ;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {

            if(--time <= 0){

                if(stopRecord()){

                    tipsTvw.append(getString(R.string.microphone_recorded) + "\n");

                    if(startPlay()){

                        tipsTvw.append(getString(R.string.microphone_playing) + "\n");

                    }else{

//                        onFail(getString(R.string.microphone_play_start_error) + "\n");

                        Toast.makeText(TestMicrophoneActivity.this,R.string.microphone_play_start_error, Toast.LENGTH_LONG).show();
//                        onFail();
                    }

                }else{

//                    onFail(getString(R.string.microphone_record_done_error) + "\n");
                    Toast.makeText(TestMicrophoneActivity.this,R.string.microphone_record_done_error, Toast.LENGTH_LONG).show();
//                    onFail();
                }

            }else{

                tipsTvw.append(getString(R.string.microphone_recording, time) + "\n");
                mHandler.postDelayed(this, 1000) ;
            }


        }
    } ;
    private Runnable mRunnable1 = new Runnable() {
        @Override
        public void run() {

            voiceVmv.invalidate();
            mHandler.postDelayed(this,100) ;
        }
    } ;

    private File mFile ;

    private MediaRecorder mRecorder ;
    private MediaPlayer mPlayer ;


    private boolean createFile(){

        File file = new File("/data/data/"+getPackageName()+"/test") ;

        if(file.exists() || file.mkdirs()){

            mFile = new File(file,"mic.mp3") ;

            return true ;
        }

        return false ;
    }


    private boolean startRecord(){

        if(mRecorder == null){
            try {

                this.mRecorder = new MediaRecorder() ;
                this.mRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
                this.mRecorder.setOutputFormat(1);
                this.mRecorder.setAudioEncoder(3);
                this.mRecorder.setOutputFile(mFile.getPath());

                voiceVmv.setRecorder(mRecorder);

                mRecorder.prepare();
            } catch (Exception e ) {
                e.printStackTrace();
                mRecorder = null ;
                return false ;
            }
        }

        mRecorder.start();
        mHandler.post(mRunnable1) ;

        return true ;
    }


    private boolean stopRecord(){

        if(mRecorder != null){

            mRecorder.stop();
            mRecorder = null ;

            mHandler.removeCallbacks(mRunnable1);
            voiceVmv.setCurrentAngle(0);
        }

        return true ;
    }

    private boolean startPlay(){

        if(mPlayer == null){

            mPlayer = new MediaPlayer() ;
            mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {

//                    onFail(getString(R.string.microphone_play_done_error) + "\n");

                    Toast.makeText(TestMicrophoneActivity.this,R.string.microphone_play_done_error, Toast.LENGTH_LONG).show();
//                    onFail();
                    return false;
                }
            });

            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

//                    onSuccess(getString(R.string.microphone_play_done_success) + "\n") ;

                    Toast.makeText(TestMicrophoneActivity.this,R.string.microphone_play_done_success, Toast.LENGTH_LONG).show();
//                    onSuccess();
                }
            });
        }else{

            mPlayer.reset();
        }


        try {
            mPlayer.setDataSource(mFile.getPath());

            mPlayer.prepare();
            mPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
            return false ;
        }
        return true ;
    }


    private void stopPlay(){

        if(mPlayer != null){

            mPlayer.stop();
        }
    }


}
