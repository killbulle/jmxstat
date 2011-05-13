package com.zenika.jmxstat.monitoring

/**
 * Match a jmx name by exact name
 * @author marco
 *
 */

class ExactMonitoring extends Monitoring {

    public String name;

    public boolean match(String namein) {
        return namein.equals(name);
    }

    public ExactMonitoring(String name, String namekey) {
        super(namekey);
        this.name = name;
    }

    public ExactMonitoring() {

    }
}
