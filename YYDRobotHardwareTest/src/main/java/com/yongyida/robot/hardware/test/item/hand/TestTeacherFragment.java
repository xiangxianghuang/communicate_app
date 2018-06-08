package com.yongyida.robot.hardware.test.item.hand;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hiva.communicate.app.common.SendResponse;
import com.hiva.communicate.app.common.send.SendResponseListener;
import com.hiva.communicate.app.common.response.BaseResponse;
import com.hiva.communicate.app.common.response.BaseResponseControl;
import com.hiva.communicate.app.common.send.SendClient;
import com.hiva.communicate.app.utils.LogHelper;
import com.yongyida.robot.communicate.app.hardware.hand.response.data.HandAngle;
import com.yongyida.robot.communicate.app.hardware.hand.send.data.QueryHandAngle;
import com.yongyida.robot.communicate.app.hardware.hand.send.data.TeacherSendControl;
import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.item.hand.adapter.AngleAdapter;
import com.yongyida.robot.hardware.test.item.hand.adapter.RecordAngleAdapter;
import com.yongyida.robot.hardware.test.item.hand.bean.RecordArmAngle;
import com.yongyida.robot.hardware.test.item.hand.dialog.ReadDanceDialog;
import com.yongyida.robot.hardware.test.item.hand.dialog.RecordArmDialog;
import com.yongyida.robot.hardware.test.item.hand.untils.RecordActionsHelper;

import java.io.IOException;



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

/**
 * Create By HuangXiangXiang 2018/5/23
 */
public class TestTeacherFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

    private final static String TAG = TestTeacherFragment.class.getSimpleName();

    /**
     * 示教开关
     */
    private Switch mTeacherSih;
    private Button mResetBtn;

    private TeacherSendControl mTeacherControl = new TeacherSendControl();
    private QueryHandAngle mQueryHandAngle;
    private GridView mAngleGvw;
    /**
     * 记录
     */
    private Button mRecordBtn;
    /**
     * 执行
     */
    private Button mExecuteBtn;
    /**
     * 读取
     */
    private Button mReadBtn;
    /**
     * 清空
     */
    private Button mClearBtn;
    /**
     * 保存
     */
    private Button mSaveBtn;
    private TextView mActionNameTvw ;


    private GridView mRecordAngleGvw;

    private AngleAdapter mAngleAdapter;



    private final int HAND_ANGLE                = 0x01;
    private final int EXECUTING_ACTION          = 0x02;
    private final int EXECUTED_ACTION           = 0x03;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case HAND_ANGLE:

                    mAngleAdapter.setHandAngle(mHandAngle);
                    break;

                case EXECUTING_ACTION:

                    int index = msg.arg1 ;

                    mRecordAngleAdapter.setSelectIndex(index);
                    mRecordAngleAdapter.notifyDataSetChanged();

                    mRecordAngleGvw.smoothScrollToPosition(index);

                    break;

                case EXECUTED_ACTION:

                    stopRunAction();
                    break;

            }

        }
    };

    private HandAngle mHandAngle = new HandAngle();

    private RecordArmAngle mResetRecordArmAngle = new RecordArmAngle();



    private SendResponseListener mSendResponseListener = new SendResponseListener<HandAngle>(){

        @Override
        public void onSuccess(HandAngle handAngle) {

            mHandAngle = handAngle;
            LogHelper.i(TAG, "leftArmAngle : " + mHandAngle.leftArmAngle.angles);

//                    LogHelper.i(TAG, LogHelper.__TAG__() + "右手臂指运行状态：" + 0 +
//                            ", rightPalm0 : " + handAngle.rightArms[0] + ", rightPalm1 : " + handAngle.rightArms[1] +
//                            ", rightPalm2 : " + handAngle.rightArms[2] + ", rightPalm3 : " + handAngle.rightArms[2] +
//                            ", rightPalm4 : " + handAngle.rightArms[4]);

            mHandler.sendEmptyMessage(HAND_ANGLE);
        }

        @Override
        public void onFail(int result, String message) {

        }
    } ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mQueryHandAngle = new QueryHandAngle(getActivity());
        SendClient.getInstance(getActivity()).send(mQueryHandAngle, mSendResponseListener);


        View view = inflater.inflate(R.layout.fragment_test_teacher, null);

        initView(view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        SendClient.getInstance(getActivity()).send(mQueryHandAngle, null);
    }

    private void initView(View view) {

        mTeacherSih = (Switch) view.findViewById(R.id.teacher_sih);
        mTeacherSih.setOnCheckedChangeListener(this);
        mResetBtn = (Button) view.findViewById(R.id.reset_btn);
        mResetBtn.setOnClickListener(this);
        mAngleGvw = (GridView) view.findViewById(R.id.angle_gvw);
        mRecordBtn = (Button) view.findViewById(R.id.record_btn);
        mRecordBtn.setOnClickListener(this);
        mExecuteBtn = (Button) view.findViewById(R.id.execute_btn);
        mExecuteBtn.setOnClickListener(this);
        mRecordAngleGvw = (GridView) view.findViewById(R.id.record_angle_gvw);

        mAngleAdapter = new AngleAdapter(getActivity());
        mAngleGvw.setAdapter(mAngleAdapter);

        mRecordAngleAdapter = new RecordAngleAdapter(getActivity());
        mRecordAngleGvw.setAdapter(mRecordAngleAdapter);
        mRecordAngleGvw.setOnItemClickListener(this);
        mRecordAngleGvw.setOnItemLongClickListener(this);


        mReadBtn = (Button) view.findViewById(R.id.read_btn);
        mReadBtn.setOnClickListener(this);
        mClearBtn = (Button) view.findViewById(R.id.clear_btn);
        mClearBtn.setOnClickListener(this);
        mSaveBtn = (Button) view.findViewById(R.id.save_btn);
        mSaveBtn.setOnClickListener(this);
        mActionNameTvw = (TextView) view.findViewById(R.id.action_name_tvw);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        mExecuteBtn.setEnabled((mRecordAction != null) && !isChecked);
        mResetBtn.setEnabled(!isChecked);

        mTeacherControl.setTeacher(isChecked);

        SendClient.getInstance(getActivity()).send(mTeacherControl, null);
    }

    @Override
    public void onClick(View v) {

        if (v == mRecordBtn) {

            if (mHandAngle != null) {

                showRecordArmDialog(null);

            }else{

                Toast.makeText(getActivity(), "没有接受到当前数据" , Toast.LENGTH_SHORT).show();
            }


        } else if (v == mResetBtn) {

            //恢复初始化
            new Thread(){
                @Override
                public void run() {

                    executeRecordArmAngle(mResetRecordArmAngle);

                }
            }.start();

        } else if (v == mExecuteBtn) {


            if(isRunActionThread()){

                stopRunAction();

            }else {

                startRunAction();
            }


        } else if (v == mReadBtn) {

            // 读取本地
            ReadDanceDialog readDanceDialog = new ReadDanceDialog(getActivity()) ;
            readDanceDialog.setOnSelectDataListener(mOnSelectDataListener);
            readDanceDialog.show();

        } else if (v == mClearBtn) {

            // 清空
            DialogInterface.OnClickListener mOnClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if(which == DialogInterface.BUTTON_POSITIVE){

                        mRecordAction.recordArmAngles.clear();
                        mRecordAngleAdapter.notifyDataSetChanged();
                    }
                }
            };
            new AlertDialog.Builder(getActivity())
                    .setTitle("清空全部动作")
                    .setMessage("是否清空全部动作?")
                    .setNegativeButton("取消",null)
                    .setPositiveButton("确定",mOnClickListener)
                    .create()
                    .show();


        } else if (v == mSaveBtn) {

            final EditText editText = new EditText(getActivity()) ;
            DialogInterface.OnClickListener mOnClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if(which == DialogInterface.BUTTON_POSITIVE){

                        mRecordAction.name = editText.getText().toString() ;

                        RecordActionsHelper.getInstance(getActivity()).writeRecordActions(mRecordAction);
                    }
                }
            };
            editText.setText(mRecordAction.name);

            // 保存
            new AlertDialog.Builder(getActivity())
                    .setTitle("保存动作组")
                    .setTitle("请输入动作组名称")
                    .setView(editText)
                    .setNegativeButton("取消",null)
                    .setPositiveButton("确定",mOnClickListener)
                    .create()
                    .show();


        } else {

        }
    }


    private void startRunAction(){

        startMusic() ;
        mExecuteBtn.setText("停止");

        mReadBtn.setEnabled(false);
        mRecordBtn.setEnabled(false);
        mClearBtn.setEnabled(false);
        mSaveBtn.setEnabled(false);

        mRunActionThread = new RunActionThread() ;
        mRunActionThread.start();
    }


    private void stopRunAction(){

        stopMusic() ;
        mExecuteBtn.setText("执行");

        mReadBtn.setEnabled(true);
        mRecordBtn.setEnabled(true);
        mClearBtn.setEnabled(true);
        mSaveBtn.setEnabled(true);

        mRecordAngleAdapter.setSelectIndex(-1);
        mRecordAngleAdapter.notifyDataSetChanged();

        if(isRunActionThread()){

            mRunActionThread.stopRun();
            mRunActionThread = null ;

        }
    }

    private MediaPlayer mediaPlayer = new MediaPlayer() ;
    private void startMusic(){

        try {
            AssetFileDescriptor afd = getActivity().getAssets().openFd("xiao_xing_yun.mp3") ;
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void stopMusic(){

        if(mediaPlayer.isPlaying()){

            mediaPlayer.stop();
            mediaPlayer.reset();
        }

    }


    private boolean isRunActionThread(){

        return (mRunActionThread != null && mRunActionThread.isRun);
    }


    private RunActionThread mRunActionThread ;
    private class RunActionThread extends Thread{

        private boolean isRun = true ;

        @Override
        public void run() {

            int size = mRecordAction.recordArmAngles.size();
            for (int i = 0; i < size; i++) {

//                mRecordAngleAdapter.setSelectIndex(i);
//                mHandler.sendEmptyMessage(EXECUTING_ACTION) ;

                Message msg = mHandler.obtainMessage(EXECUTING_ACTION) ;
                msg.arg1 = i ;
                mHandler.sendMessage(msg) ;

                RecordArmAngle recordArmAngle = mRecordAction.recordArmAngles.get(i);

                executeRecordArmAngle(recordArmAngle) ;

                try {
                    Thread.sleep(recordArmAngle.getTime() + recordArmAngle.getDelay());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(!isRun){

                    return;
                }

            }

            mHandler.sendEmptyMessage(EXECUTED_ACTION) ;

        }

        private void stopRun(){

            if(isRun){

                isRun = false ;
            }

        }

    }



    /**
     * 执行一个指定动作脚本（包括左右手臂及左右手指）
     * */
    private void executeRecordArmAngle(RecordArmAngle recordArmAngle){

        SendClient.getInstance(getActivity()).sendInNotMainThread(recordArmAngle.getLeftArmControl(), null);

        SendClient.getInstance(getActivity()).sendInNotMainThread(recordArmAngle.getRightArmControl(), null);

        SendClient.getInstance(getActivity()).sendInNotMainThread(recordArmAngle.getLeftFingerControl(), null);

        SendClient.getInstance(getActivity()).sendInNotMainThread(recordArmAngle.getRightFingerControl(), null);
    }



    private RecordActionsHelper.RecordAction mRecordAction ;
    private ReadDanceDialog.OnSelectDataListener mOnSelectDataListener = new ReadDanceDialog.OnSelectDataListener(){

        @Override
        public void onSelected(RecordActionsHelper.RecordAction recordAction) {

            mRecordBtn.setEnabled(true);
            mExecuteBtn.setEnabled(!mTeacherSih.isChecked());
            mClearBtn.setEnabled(true);
            mSaveBtn.setEnabled(true);

            mRecordAction = recordAction ;

            mActionNameTvw.setText(recordAction.name);
            mRecordAngleAdapter.setRecordArmAngles(recordAction.recordArmAngles);
            mRecordAngleAdapter.notifyDataSetChanged();

        }
    };


    private RecordArmDialog mRecordArmDialog;

    private RecordArmAngle mRecordArmAngle; //
    private RecordAngleAdapter mRecordAngleAdapter;


    private RecordArmDialog.OnChangedListener mOnChangedListener = new RecordArmDialog.OnChangedListener() {

        @Override
        public void onChanged(RecordArmAngle recordArmAngle, boolean isChangedAction) {

            if(mRecordArmAngle == null){
                // 新增动作组

                recordArmAngle.setHandAngle(mHandAngle);

                mRecordArmAngle = recordArmAngle ;
                mRecordAction.recordArmAngles.add(mRecordArmAngle);

            }else {

                if(isChangedAction){

                    recordArmAngle.setHandAngle(mHandAngle);
                }

                mRecordArmAngle.setRecordArmAngle(recordArmAngle) ;
            }

            mRecordAngleAdapter.notifyDataSetChanged();

        }

    };

    private void showRecordArmDialog(RecordArmAngle recordArmAngle) {

        if (mRecordArmDialog == null) {

            mRecordArmDialog = new RecordArmDialog(getActivity());
            mRecordArmDialog.setOnChangedListener(mOnChangedListener);
        }

        if(recordArmAngle == null){

            mRecordArmAngle = null ;
            mRecordArmDialog.setRecordArmAngle(new RecordArmAngle());

        }else {

            mRecordArmAngle = recordArmAngle ;
            mRecordArmDialog.setRecordArmAngle(mRecordArmAngle.deepClone());
        }

        mRecordArmDialog.show();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        DialogInterface.OnClickListener mOnClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which == DialogInterface.BUTTON_POSITIVE){

                    mRecordAction.recordArmAngles.remove(position);
                    mRecordAngleAdapter.notifyDataSetChanged();
                }
            }
        };
        new AlertDialog.Builder(getActivity())
                .setTitle("清空当前动作")
                .setMessage("是否清空当前动作?")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定",mOnClickListener)
                .create()
                .show();


        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        showRecordArmDialog(mRecordAction.recordArmAngles.get(position) ) ;
    }
}
