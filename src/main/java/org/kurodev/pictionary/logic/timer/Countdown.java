package org.kurodev.pictionary.logic.timer;

import org.kurodev.pictionary.logic.callbacks.TimerCallback;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author kuro
 **/
public class Countdown extends TimerTask {
    private final Timer timer = new Timer();
    private final int time;
    private TimerCallback callback;
    private int currentTime;

    public Countdown(int time) {
        this(time, null);
    }

    public Countdown(int time, TimerCallback callback) {

        this.time = time;
        currentTime = time;
        this.callback = callback;
    }

    public void setCallback(TimerCallback callback) {
        this.callback = callback;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public int getInitialTime() {
        return time;
    }

    @Override
    public void run() {
        currentTime -= 1;
        inform();
        if (currentTime <= 0) {
            stop();
        }
    }

    private void inform() {
        if (callback != null)
            callback.onTimerChanged(currentTime);
    }

    public void reset() {
        currentTime = time;
        inform();
    }

    public void start() {
        timer.schedule(this, 0, 1000);
    }

    public void stop() {
        timer.cancel();
    }
}
