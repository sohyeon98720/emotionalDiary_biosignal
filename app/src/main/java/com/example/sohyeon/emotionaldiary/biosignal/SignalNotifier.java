package com.example.sohyeon.emotionaldiary.biosignal;

/**
 * Created by lhw48 on 2016-06-22.
 */
public interface SignalNotifier {
    public void didReadPpg(int ppg);
    public void didReadHrv(double[] hrv);
}
