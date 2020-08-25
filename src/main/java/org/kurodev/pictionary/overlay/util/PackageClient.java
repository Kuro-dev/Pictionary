package org.kurodev.pictionary.overlay.util;

public class PackageClient {

    String ip;
    int port;

    public PackageClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
