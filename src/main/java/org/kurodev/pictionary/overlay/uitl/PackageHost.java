package org.kurodev.pictionary.overlay.uitl;

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
}
