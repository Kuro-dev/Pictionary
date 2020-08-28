package org.kurodev.pictionary.overlay.util;

public class PackageHost {

    // int ip;
    int time;
    String[] words;
    int port;

    public PackageHost(int time, String[] words, int port) {
        this.time = time;
        this.words = words;
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public int getTime() {
        return time;
    }
}
