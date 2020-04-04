package com.example.sohyeon.emotionaldiary;


import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sohyeon.emotionaldiary.biosignal.BiosignalConsumer;
import com.example.sohyeon.emotionaldiary.biosignal.BiosignalManager;
import com.example.sohyeon.emotionaldiary.biosignal.PeakAnalysizer;
import com.example.sohyeon.emotionaldiary.biosignal.SignalNotifier;
import com.example.sohyeon.emotionaldiary.biosignal.StateNotifier;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class HomeActivity extends AppCompatActivity implements BiosignalConsumer {
    public static Context mContext;
    private Activity act = this;
    private static final String TAG = "MainActivity";
    String filename = "C:\\Users\\user\\Desktop\\PPGData\\name_e.txt";
    boolean isSaveReferance = false;
    //boolean isConnect = false;
    String imagename;


    private BiosignalManager mBIosignalManager = null;
    private static final int REQUEST_SELECT_DEVICE_PPG  = 0;

    private Button mConnectBtn,mStartBtn, mStopBtn, DiaryBtn, CameraBtn;
    private TextView mStateTv, mDataTv, mAmpilitudeTv, mPPITv, mEmotionTv;

    int samplingRate = 80;
    int windowSize = 15;  // Seconds
    int referencewindowSize = 30;
    private double[] ppgList = new double[windowSize * samplingRate];
    private double[] refList = new double[referencewindowSize * samplingRate];
    int ppgIndex = 0;
    public static String emotion;
    public static boolean isHigh = false;
    double amplitude;
    double ppi;
    //String currentemotion;

    private PeakDetector mPeakDetector;
    private PeakAnalysizer mPeakAnalysizer = new PeakAnalysizer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext=this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        DiaryBtn = (Button) findViewById(R.id.diary);
        //CameraBtn = (Button) findViewById(R.id.camera);
        mConnectBtn = (Button) findViewById(R.id.bluetooth_connect);
        mStartBtn = (Button) findViewById(R.id.start);
        mStopBtn = (Button) findViewById(R.id.stop);
        mStateTv = (TextView) findViewById(R.id.state_tv);
        mDataTv = (TextView) findViewById(R.id.data_tv);
        mAmpilitudeTv = (TextView) findViewById(R.id.amplitude_tv);
        mPPITv = (TextView) findViewById(R.id.ppi_tv);
        mEmotionTv = (TextView) findViewById(R.id.emotion_tv);


        // Set OnClickListeners
        DiaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(act, MainActivity.class);
                startActivity(intent);
            }
        });

        mConnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(act, com.example.sohyeon.emotionaldiary.biosignal.clientutils.DeviceListActivity.class);
                startActivityForResult(newIntent, REQUEST_SELECT_DEVICE_PPG);
            }
        });
        mConnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(act, com.example.sohyeon.emotionaldiary.biosignal.clientutils.DeviceListActivity.class);
                startActivityForResult(newIntent, REQUEST_SELECT_DEVICE_PPG);
            }
        });
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBindBiosignalService(true);
            }
        });
        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBindBiosignalService(false);
            }
        });
    }

    @Override
    public void onDestroy() {
        onBindBiosignalService(false);
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_SELECT_DEVICE_PPG:
                // When the DeviceListActivity return, with the selected device address
                if (resultCode == Activity.RESULT_OK && data != null) {
                    String deviceAddress = data.getStringExtra(BluetoothDevice.EXTRA_DEVICE);
                    BluetoothDevice mDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(deviceAddress);

                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
                    pref.edit().putString("biosignal", mDevice.getAddress()).apply();
                    Toast.makeText(this, "biosignal : " + mDevice.getAddress(), Toast.LENGTH_SHORT).show();
                    //isConnect = true;
                }
                break;
            default:
                if (resultCode == RESULT_OK) {
                    Log.d("TEST", "ImageURI : " + data.getData());
                    Toast.makeText(HomeActivity.this, "사진이 저장되었습니다", Toast.LENGTH_SHORT).show();
                    String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
                    imagename = timeStamp + ".txt";
                    Log.d("TEST", "imagename=" + imagename);

                    try {
                        FileOutputStream os = openFileOutput(imagename, MODE_APPEND);
                        os.write(data.getData().toString().getBytes());
                        os.write(new String("\n").getBytes());
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("TEST", "isget : " + emotion);
                    imagename = emotion + ".txt";
                    Log.d("TEST", "emotion=" + imagename);
                    try {
                        FileOutputStream os = openFileOutput(imagename, MODE_APPEND);
                        os.write(data.getData().toString().getBytes());
                        os.write(new String("\n").getBytes());
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void onBindBiosignalService(boolean bind) {
        if(bind) {
            if(mBIosignalManager == null) mBIosignalManager = BiosignalManager.getInstanceForApplication(this);
            mBIosignalManager.bind((BiosignalConsumer) this);

        } else {
            if(mBIosignalManager == null) return;
            try {
                mBIosignalManager.stopSignaling(0);
                mBIosignalManager.disconnect(0);
                mBIosignalManager.unBind(this);
                mBIosignalManager = null;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }






    @Override
    public void onBiosignalServiceConnect() {
        mBIosignalManager.setStateNotifier(new StateNotifier() {
            @Override
            public void didChangedState(int state) {
                if (state == BiosignalManager.STATE_DISCONNECTED) {
                    mStateTv.setText("DISCONNECTED");
                }
                else if (state == BiosignalManager.STATE_CONNECTED) {
                    mStateTv.setText("CONNECTED");
                    mStateTv.setTextColor(Color.BLUE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            try {
                                if (mBIosignalManager != null) {
                                    mBIosignalManager.startSignaling(0);
                                }
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }, 5000);
                }
            }
        });

        mBIosignalManager.setSignalNotifier(new SignalNotifier() {
            @Override
            public void didReadPpg(int ppg) {
                Log.d(TAG, "didReadPpg : " + ppg);
                if(!isSaveReferance) {
                    mDataTv.setText("Measuring Reference");

                    long now = System.currentTimeMillis();


                    if(now %1000<333) {
                        mAmpilitudeTv.setText(".");
                        mPPITv.setText(".");
                    }
                    else if(now %1000>=333 && now %1000<666) {
                        mAmpilitudeTv.setText("..");
                        mPPITv.setText("..");
                    }
                    else if(now %1000>=666) {
                        mAmpilitudeTv.setText("...");
                        mPPITv.setText("...");
                    }

/*                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        public void run() {
                            String s = ".";
                            StringBuffer sbuf = new StringBuffer();
                            for (int i = 0; i < 4; i++) {
                                sbuf.append(s);
                                mAmpilitudeTv.setText("" + sbuf.toString());
                                mPPITv.setText("" + sbuf.toString());
                                if (i == 3) sbuf.delete(0, sbuf.length());
                            }
                        }
                    }, 1000);*/





                    if (ppgIndex < referencewindowSize * samplingRate)
                        refList[ppgIndex++] = ppg;

                    if (ppgIndex >= referencewindowSize * samplingRate) {
                        // detect peak
                        mPeakDetector = new PeakDetector(refList);
                        int[] peakIndex = mPeakDetector.process(35, 1.f);
                        PeakAnalysizer.ReferenceAmplitude = mPeakAnalysizer.CalculateAmplitude(refList, peakIndex);
                        PeakAnalysizer.ReferencePPI = mPeakAnalysizer.CalculatePPI(peakIndex, samplingRate);

                        Log.d("TEST","ReferenceAmp : "+PeakAnalysizer.ReferenceAmplitude);
                        Log.d("TEST","ReferencePPI : "+PeakAnalysizer.ReferencePPI);
                        ppgIndex = 0;
                        isSaveReferance = true;
                        Toast.makeText(getApplicationContext(),"Reference측정이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    mDataTv.setText(Integer.toString(ppg));
                    if(ppgIndex < windowSize * samplingRate)
                        ppgList[ppgIndex++] = ppg;

                    if(ppgIndex >= windowSize * samplingRate) {
                        // detect peak
                        mPeakDetector = new PeakDetector(ppgList);
                        int[] peakIndex = mPeakDetector.process(35, 1.f);

                        //Log.d("TEST", "PEAK DETECTION " + peakIndex.length);
                        // analysis
                        amplitude = mPeakAnalysizer.CalculateAmplitude(ppgList, peakIndex);
                        ppi = mPeakAnalysizer.CalculatePPI(peakIndex, samplingRate);
                        Log.d("TEST", "AMPLITUDE " + amplitude);
                        Log.d("TEST", "PPI " + ppi);
                        mAmpilitudeTv.setText(Double.toString(amplitude));
                        mPPITv.setText(Double.toString(ppi));
                        //Recognize emotion;
                        emotion = mPeakAnalysizer.RecognizeEmotion(amplitude, ppi);
                        Log.d("TEST","realemotion : "+emotion);


                        if(emotion.equals("Happy")){
                            mEmotionTv.setText(emotion);
                            mEmotionTv.setTextColor(Color.rgb(255,142,3));
                        }

                        else if(emotion.equals("Sad")){
                            mEmotionTv.setText(emotion);
                            mEmotionTv.setTextColor(Color.rgb(0,0,165));
                        }

                        else if(emotion.equals("Angry")){
                            mEmotionTv.setText(emotion);
                            mEmotionTv.setTextColor(Color.RED);
                        }

                        else  {
                            mEmotionTv.setText("Neutral");
                            mEmotionTv.setTextColor(Color.BLACK);
                        }
                        if(isHigh) show();
                        // Clear
                        ppgIndex = 0;
                    }
                }

            }

            @Override
            public void didReadHrv(double[] hrv) {
                Log.d(TAG, "didReadHrv : " + hrv[0]);
            }
        });

        onConnectBiosignal();
    }

    private void onConnectBiosignal() {
        String address = PreferenceManager.getDefaultSharedPreferences(this).getString("biosignal", null);
        if(address == null) return;
        try {
            if(mBIosignalManager != null) {
                Log.d(TAG, "onConnectBiosignal : " + address);
                mBIosignalManager.connect(0, address);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    void show()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("!"+emotion+" 감정포착 !");
        builder.setMessage("사진을 찍겠습니까?");
        builder.setPositiveButton("포착 기기",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent,1);
                        }
                        //Toast.makeText(getApplicationContext(),"예를 선택했습니다.",Toast.LENGTH_LONG).show();
                });
        builder.setNegativeButton("시른데~~",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(),"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }




}
