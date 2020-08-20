package org.kurodev.pictionary.logic.timer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author kuro
 **/
public class Countdown extends TimerTask {
    private final Timer timer = new Timer();
    private final int time;
    private final TimerCallback callback;
    private int currentTime;

    public Countdown(int time) {
        this(time, null);
    }

    public Countdown(int time, TimerCallback callback) {

        this.time = time;
        currentTime = time;
        this.callback = callback;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    @Override
    public void run() {
        currentTime -= 1;
        if (callback != null)
            callback.onTimerChanged(currentTime);
        if (currentTime <= 0) {
            stop();
        }
    }

    public void reset() {
        currentTime = time;
    }

    public void start() {
        timer.schedule(this, 0, 1000);
    }

    public void stop() {
        timer.cancel();
    }
}
