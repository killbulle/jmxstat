package com.zenika.jmxstat.monitoring;

/**
 * Special Metric for timestamp
 */
public class TimeMetric extends NagiosMetric {
    public static final String NCLASS = "internTime";

    public TimeMetric() {
        super();
        this.nclass = NCLASS;

    }

    public TimeMetric(String name, String alias) {
        super(name, alias);
        this.nclass = NCLASS;

    }

}
