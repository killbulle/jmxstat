package com.zenika.jmxstat.monitoring

import javax.management.MBeanServerConnection

class ServerInfo {
    public String server;
    public int port;
    public MBeanServerConnection connection;

    public ServerInfo(String server, int port, MBeanServerConnection mBeanServerConnection) {
        super();
        this.server = server;
        this.port = port;
        connection = mBeanServerConnection;
    }

}
