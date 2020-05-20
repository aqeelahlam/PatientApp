package com.example.cholesterol;

import android.util.Log;


import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;


public class NTimer extends Observable {


    public static int n = 0;

    public static void resetN() {
        n = 0;
    }

    public static Timer timer = new Timer();
    public TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (n < 10) {
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
