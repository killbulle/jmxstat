package com.zenika.jmxstat.monitoring;
/**
 * Special Metrci object qho support the nagios format
 */
public class NagiosMetric extends Metric {

    //** nagios specific**
    protected String nwarn = "";
    protected String ncrit = "";
    protected String nunit = "";
    protected String nmin = "";
    protected String nmax = "";
    protected String nclass = "";

    public NagiosMetric(String name, String alias) {
        super(name, alias);

    }

    public NagiosMetric() {
    }
}
