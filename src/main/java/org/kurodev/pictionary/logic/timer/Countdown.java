package org.kurodev.pictionary.logic.timer;

import org.kurodev.pictionary.logic.net.encoding.Encodable;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author kuro
 **/
public class Countdown extends TimerTask implements Encodable {
    private final Timer timer = new Timer();
    private final int time;
    private final TimerCallback callback;
    private int currentTime;

    public Countdown(int time, TimerCallback callback) {

        this.time = time;
        currentTime = time;
        this.callback = callback;
    }

    @Override
    public void run() {
        currentTime -= 1;
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

    @Override
    public void decode(byte[] bytes) {

    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }
}
