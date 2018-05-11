package com.yongyida.robot.hardware.test.item.horn;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;

import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;

import java.io.IOException;

/**
 * Created by HuangXiangXiang on 2018/2/10.
 */
public class TestHornActivity extends TestBaseActivity {

    @Override
    protected View initContentView() {
        return null;
    }

    @Override
    protected String getTips() {
        return getString(R.string.horn_tips);
    }

    @Override
    public void onResume() {
        super.onResume();
        startPlay() ;
    }

    @Override
    public void onPause() {
        super.onPause();
        stopPlay() ;
    }

    private int preVolume ;
    private MediaPlayer mPlayer ;
    private void startPlay(){

        //将声音设置最大
        AudioManager localAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        localAudioManager.setRingerMode(2);
        int i = localAudioManager.getStreamMaxVolume(3);

        preVolume = localAudioManager.getStreamVolume(3) ;

        localAudioManager.setStreamVolume(3, i/3, 4);


        if(mPlayer == null){

            try {

                mPlayer = new MediaPlayer() ;
                mPlayer.setLooping(true);

                AssetFileDescriptor fd = getAssets().openFd("test_horn.ogg") ;
                mPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
                mPlayer.prepare();
                mPlayer.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (!mPlayer.isPlaying()){

            mPlayer.start();
        }

    }

    private void stopPlay(){

        if(mPlayer != null && mPlayer.isPlaying()){

            mPlayer.pause();
            mPlayer = null ;
        }

        // 将声音恢复
        AudioManager localAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        localAudioManager.setRingerMode(2);
        localAudioManager.setStreamVolume(3,preVolume, 4);
    }


}
