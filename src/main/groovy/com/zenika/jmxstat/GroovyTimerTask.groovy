package com.zenika.jmxstat

class GroovyTimerTask extends TimerTask {
    def closure;

    public void run() {
        closure();
    }
}