package com.example.sohyeon.emotionaldiary.biosignal;


import android.util.Log;
import android.widget.Switch;

import com.example.sohyeon.emotionaldiary.HomeActivity;

import java.util.ArrayList;

public class PeakAnalysizer {

    public static double ReferenceAmplitude = 453.865;
    public static double ReferencePPI = 1.127;
    String[] emotions = {"Happy", "Sad", "Angry"};

    public double CalculateAmplitude(double[] signal, int[] peaks) {
        double amp = 0;
        for(int i=0; i<peaks.length; i++) {
            amp += signal[i];
        }
        amp /= peaks.length;
        return amp;
    }

    public double CalculatePPI(int[] peaks, int samplingRate) {
        double ppi = 0;
        for(int i=0; i<peaks.length - 1; i++) {
            ppi += (peaks[i+1] - peaks[i]);
        }
        ppi /= (peaks.length - 1);
        ppi /= samplingRate;
        return ppi;
    }

    public String RecognizeEmotion(double amplitude, double ppi) {
        String selectEmotion;

        double AmpRateOfChange = (amplitude - ReferenceAmplitude)/ReferenceAmplitude;
        double PPIRateOfChange = (ppi - ReferencePPI)/ReferencePPI;
        Log.d("TEST","PPIRateOfChange : "+PPIRateOfChange);
        Log.d("TEST","AmpRateOfChange : "+AmpRateOfChange);
        if(AmpRateOfChange>0.05 && PPIRateOfChange > 0.05) {
            if(AmpRateOfChange >0.098197 && PPIRateOfChange > 0.1887086) {HomeActivity.isHigh =true; selectEmotion = emotions[0];}
            else {HomeActivity.isHigh =false; selectEmotion = emotions[0];}
        }
        else if (Math.abs(AmpRateOfChange)<0.1 && PPIRateOfChange >0.1){
            if(PPIRateOfChange >0.1184374) {HomeActivity.isHigh =true; selectEmotion = emotions[1];}
            else {HomeActivity.isHigh =false; selectEmotion = emotions[1];}
        }

        else if (AmpRateOfChange<0 && Math.abs(PPIRateOfChange) <0.1){
            if(AmpRateOfChange <-0.086868) {HomeActivity.isHigh =true; selectEmotion = emotions[2];}
            else {HomeActivity.isHigh =false; selectEmotion = emotions[2];}
        }
        else {
            selectEmotion = "Neutral";
            HomeActivity.isHigh =false;
        }
        //HomeActivity.isHigh = true;
        return selectEmotion;



    }
}