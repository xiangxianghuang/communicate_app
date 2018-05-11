package cn.com.aratek.demo;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.aratek.idcard.IDCard;
import cn.com.aratek.idcard.IDCardReader;
import cn.com.aratek.util.OnUsbPermissionGrantedListener;
import cn.com.aratek.util.Result;

@SuppressLint("HandlerLeak")
public class IDCardDemo extends Activity implements View.OnClickListener {

    private static final int MSG_SHOW_ERROR = 0;
    private static final int MSG_SHOW_INFO = 1;
    private static final int MSG_UPDATE_INFO = 2;
    private static final int MSG_UPDATE_BUTTON = 3;
    private static final int MSG_UPDATE_SN = 4;
    private static final int MSG_SHOW_PROGRESS_DIALOG = 5;
    private static final int MSG_DISMISS_PROGRESS_DIALOG = 6;

    private TextView mSN;
    private ImageView mPhoto;
    private EditText mName;
    private EditText mSex;
    private EditText mNationality;
    private EditText mBirthday;
    private EditText mAddress;
    private EditText mNumber;
    private EditText mAuthority;
    private EditText mValidDate;
    private EditText mFinger;
    private Button mBtnOpenOrCloseDevice;
    private Button mBtnReadIdCard;
    private ProgressDialog mProgressDialog;
    private IDCardReader mReader;
    private boolean mDeviceOpened = false;
    private IDCardTask mTask;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SHOW_ERROR: {
                    showDialog(0, (Bundle) msg.obj);
                    break;
                }
                case MSG_SHOW_INFO: {
                    Toast.makeText(IDCardDemo.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                }
                case MSG_UPDATE_INFO: {
                    IDCard card = (IDCard) msg.obj;
                    SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
                    mName.setText(card.getName());
                    mSex.setText(card.getSex().toString());
                    mNationality.setText(card.getNationality().toString());
                    mBirthday.setText(df.format(card.getBirthday()));
                    mAddress.setText(card.getAddress());
                    mNumber.setText(card.getNumber());
                    mAuthority.setText(card.getAuthority());
                    mValidDate.setText(df.format(card.getValidFrom()) + " - " + (card.getValidTo() == null ? getString(R.string.long_term) : df.format(card.getValidTo())));
                    mFinger.setText(card.isSupportFingerprint() ? R.string.exist : R.string.not_exist);
                    if (card.getPhoto() != null) {
                        mPhoto.setImageBitmap(card.getPhoto());
                    } else {
                        mPhoto.setImageResource(R.drawable.nophoto);
                    }
                    break;
                }
                case MSG_UPDATE_BUTTON: {
                    Boolean enable = (Boolean) msg.obj;
                    if (mDeviceOpened) {
                        mBtnOpenOrCloseDevice.setText(getString(R.string.close_device));
                    } else {
                        mBtnOpenOrCloseDevice.setText(getString(R.string.open_device));
                    }
                    mBtnReadIdCard.setEnabled(enable);
                    break;
                }
                case MSG_UPDATE_SN: {
                    mSN.setText((String) msg.obj);
                    break;
                }
                case MSG_SHOW_PROGRESS_DIALOG: {
                    String[] info = (String[]) msg.obj;
                    mProgressDialog.setTitle(info[0]);
                    mProgressDialog.setMessage(info[1]);
                    mProgressDialog.show();
                    break;
                }
                case MSG_DISMISS_PROGRESS_DIALOG: {
                    mProgressDialog.dismiss();
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mReader = new IDCardReader(this);
        mReader.setOnUsbPermissionGrantedListener(new OnUsbPermissionGrantedListener() {
            @Override
            public void onUsbPermissionGranted(boolean isGranted) {
                if (isGranted) {
                    mSN.setText(getString(R.string.idcard_sn, (String) mReader.getSN().data));
                    enableControl(true);
                } else {
                    mSN.setText(getString(R.string.idcard_sn, "null"));
                    enableControl(false);
                }
            }
        });

        setContentView(R.layout.activity_idcard);

        mSN = (TextView) findViewById(R.id.tv_idcard_sn);
        mPhoto = (ImageView) findViewById(R.id.iv_idcard_photo);
        mName = (EditText) findViewById(R.id.et_idcard_name);
        mSex = (EditText) findViewById(R.id.et_idcard_sex);
        mNationality = (EditText) findViewById(R.id.et_idcard_nationality);
        mBirthday = (EditText) findViewById(R.id.et_idcard_birthday);
        mAddress = (EditText) findViewById(R.id.et_idcard_address);
        mNumber = (EditText) findViewById(R.id.et_idcard_number);
        mAuthority = (EditText) findViewById(R.id.et_idcard_authority);
        mValidDate = (EditText) findViewById(R.id.et_idcard_validDate);
        mFinger = (EditText) findViewById(R.id.et_idcard_finger);
        mBtnOpenOrCloseDevice = (Button) findViewById(R.id.bt_open_close);
        mBtnReadIdCard = (Button) findViewById(R.id.bt_readidcard);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIcon(android.R.drawable.ic_dialog_info);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);

        enableControl(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (mTask != null && mTask.getStatus() != AsyncTask.Status.FINISHED) {
            mTask.cancel(false);
            mTask.waitForDone();
        }
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.bt_open_close) {
            if (!mDeviceOpened) {
                openDevice();
            } else {
                closeDevice();
            }

        } else if (i == R.id.bt_readidcard) {
            readCard();

        }
    }

    private void enableControl(boolean enable) {
        mHandler.sendMessage(mHandler.obtainMessage(MSG_UPDATE_BUTTON, enable));
    }

    private void openDevice() {
        new Thread() {
            @Override
            public void run() {
                showProgressDialog(getString(R.string.loading), getString(R.string.preparing_device));
                int error;
                if ((error = mReader.open()) != IDCardReader.RESULT_OK) {
                    showErrorDialog(getString(R.string.id_card_reader_open_failed), getIDCardErrorString(error));
                } else {
                    showInfoToast(getString(R.string.id_card_reader_open_success));
                    mDeviceOpened = true;
                    enableControl(true);
                }
                dismissProgressDialog();
            }
        }.start();
    }

    private void closeDevice() {
        new Thread() {
            @Override
            public void run() {
                showProgressDialog(getString(R.string.loading), getString(R.string.closing_device));
                int error;
                if ((error = mReader.close()) != IDCardReader.RESULT_OK) {
                    showErrorDialog(getString(R.string.id_card_reader_close_failed), getIDCardErrorString(error));
                } else {
                    showInfoToast(getString(R.string.id_card_reader_close_success));
                }
                mDeviceOpened = false;
                enableControl(false);
                dismissProgressDialog();
            }
        }.start();
    }

    private void readCard() {
        mTask = new IDCardTask();
        mTask.execute("read");
    }

    private void showPeopleInfo(IDCard card) {
        mHandler.sendMessage(mHandler.obtainMessage(MSG_UPDATE_INFO, card));
    }

    @Override
    protected Dialog onCreateDialog(int id, Bundle args) {
        String operation = args.getString("operation");
        String errString = args.getString("errString");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle(R.string.info_error);
        if (errString != null && !errString.equals("")) {
            builder.setMessage(operation + "\n" + errString);
        } else {
            builder.setMessage(operation);
        }
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }

    private void showProgressDialog(String title, String message) {
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SHOW_PROGRESS_DIALOG, new String[] { title, message }));
    }

    private void dismissProgressDialog() {
        mHandler.sendMessage(mHandler.obtainMessage(MSG_DISMISS_PROGRESS_DIALOG));
    }

    private void showErrorDialog(String operation, String errString) {
        Bundle bundle = new Bundle();
        bundle.putString("operation", operation);
        bundle.putString("errString", errString);
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SHOW_ERROR, bundle));
    }

    private void showInfoToast(String info) {
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SHOW_INFO, info));
    }

    private String getIDCardErrorString(int error) {
        int strid;
        switch (error) {
            case IDCardReader.RESULT_OK:
                strid = R.string.operation_successful;
                break;
            case IDCardReader.RESULT_FAIL:
                strid = R.string.error_operation_failed;
                break;
            case IDCardReader.WRONG_CONNECTION:
                strid = R.string.error_wrong_connection;
                break;
            case IDCardReader.DEVICE_BUSY:
                strid = R.string.error_device_busy;
                break;
            case IDCardReader.DEVICE_NOT_OPEN:
                strid = R.string.error_device_not_open;
                break;
            case IDCardReader.TIMEOUT:
                strid = R.string.error_timeout;
                break;
            case IDCardReader.NO_PERMISSION:
                strid = R.string.error_no_permission;
                break;
            case IDCardReader.WRONG_PARAMETER:
                strid = R.string.error_wrong_parameter;
                break;
            case IDCardReader.DECODE_ERROR:
                strid = R.string.error_decode;
                break;
            case IDCardReader.INIT_FAIL:
                strid = R.string.error_initialization_failed;
                break;
            case IDCardReader.UNKNOWN_ERROR:
                strid = R.string.error_unknown;
                break;
            case IDCardReader.NOT_SUPPORT:
                strid = R.string.error_not_support;
                break;
            case IDCardReader.NOT_ENOUGH_MEMORY:
                strid = R.string.error_not_enough_memory;
                break;
            case IDCardReader.DEVICE_NOT_FOUND:
                strid = R.string.error_device_not_found;
                break;
            case IDCardReader.DEVICE_REOPEN:
                strid = R.string.error_device_reopen;
                break;
            case IDCardReader.NO_CARD:
                strid = R.string.error_no_card;
                break;
            case IDCardReader.INVALID_CARD:
                strid = R.string.error_invalid_card;
                break;
            case IDCardReader.INVALID_DECODE_LIB:
                strid = R.string.error_invalid_decode_lib;
                break;
            default:
                strid = R.string.error_other;
                break;
        }
        return getString(strid);
    }

    private class IDCardTask extends AsyncTask<String, Integer, Void> {
        private boolean mIsDone = false;

        @Override
        protected void onPreExecute() {
            enableControl(false);
        }

        @Override
        protected Void doInBackground(String... params) {
            Result res;
            if (params[0].equals("read")) {
                showProgressDialog(getString(R.string.loading), getString(R.string.place_idcard));
                do {
                    res = mReader.read();
                } while (res.error == IDCardReader.NO_CARD && !isCancelled());
                if (!isCancelled()) {
                    if (res.error == IDCardReader.RESULT_OK) {
                        showInfoToast(getString(R.string.id_card_read_success));
                        showPeopleInfo((IDCard) res.data);
                    } else {
                        showErrorDialog(getString(R.string.id_card_read_failed), getIDCardErrorString(res.error));
                    }
                }
                dismissProgressDialog();
            }
            enableControl(true);
            mIsDone = true;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onCancelled() {
        }

        public void waitForDone() {
            while (!mIsDone) {
            }
        }
    }
}
