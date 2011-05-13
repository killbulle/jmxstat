package com.zenika.jmxstat.monitoring
public class PatternMonitoring extends Monitoring {
    public String pattern;

    public boolean match(String name) {
        return name.contains(pattern);
    }

    public PatternMonitoring(String pattern, String namekey) {
        super(namekey);
        this.pattern = pattern;
    }

    public PatternMonitoring() {

    }
}
