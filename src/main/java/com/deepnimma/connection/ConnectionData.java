package com.deepnimma.connection;

import com.deepnimma.util.Constants;

public class ConnectionData {
    public String host;
    public int port;

    public ConnectionData() {
        this.host = Constants.DEFAULT_HOST;
        this.port = Constants.DEFAULT_PORT;
    } // ConnectionData

    public ConnectionData(String host) {
        this.host = host;
        this.port = Constants.DEFAULT_PORT;
    } // ConnectionData

    public ConnectionData(String host, int port) {
        this.host = host;
        this.port = port;
    } // ConnectionData

    @Override
    public String toString() {
        return this.host + ":" + this.port;
    } // toString
} // ConnectionData
