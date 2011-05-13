package com.zenika.jmxstat.monitoring;
/**
 * Key for collecting metric
 */
public class ResultKey {
    String name;
    Monitoring monitoring;
    int id;
    ServerInfo info;
    Metric metric;

    public ResultKey(String name, int id, Monitoring monitoring, Metric metric, ServerInfo info) {
        this.monitoring = monitoring;
        this.id = id;
        this.info = info;
        this.name = name;
        this.metric = metric;
    }
}
