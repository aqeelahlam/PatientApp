package com.example.cholesterol.Observable;

import android.util.Log;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;


public class NTimer extends Observable {


    public static int n = 0;

    public static int N = 100;

    public static void setN(int value) {
        N = value;
    }

    public static void resetN() {
        n = 0;
    }

    public static Timer timer = new Timer();

    public TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (n < N) {
                n++;
                Log.d("timer", String.valueOf(n));
            } else {
                task.cancel();
                timer.purge();
                Log.d("timer", "stopped");
                setChanged();
                notifyObservers();

            }
        }
    };

    public void startTimer() {
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

}
