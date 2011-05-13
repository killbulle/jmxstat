package com.zenika.jmxstat.monitoring

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics

class StatsResult {

    protected name;
    Map stats = new HashMap<String, DescriptiveStatistics>();

    public StatsResult(Object name) {
        super();
        this.name = name;
    }

    public addValue(String name, Long value) {
        DescriptiveStatistics stat = stats.get(name);
        if (stat == null) {
            stat = new DescriptiveStatistics();
            stats.put(name, stat);
        }
        stat.addValue(new Double(value));

    }


    public String toString() {
        StringBuilder builder = new StringBuilder();

        stats.each {name, stat ->
            String statspattern = "${name}: Nombre:${stat.n}| Mean:${stat.mean}| Max:${stat.max}| min:${stat.min}| Percen.95:${stat.getPercentile(0.95)}\n"
            builder.append(statspattern);
        }
        return builder.toString();
    }
}