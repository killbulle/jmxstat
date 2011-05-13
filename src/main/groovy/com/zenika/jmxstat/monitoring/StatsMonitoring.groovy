package com.zenika.jmxstat.monitoring
/**
 * Special monitoring who merge results by a special metric
 * useful in only certain case see sample TomcatStats
 */
class StatsMonitoring extends PatternMonitoring {

    protected String joinName;

    public void collectResults(Map results, mbeanmodule, int id, ServerInfo serverinfos) {
        //we join on this metric name
        Metric joinedby = metrics.find {m -> joinName.equals(m.name)}
        //search same metric name;
        Object value = joinedby.getValue(mbeanmodule, results, id)
        //we search similar metric

        StatsResult current = null;
        ResultKey keycurrent = null;
        Collection joinNameKeys = results.keySet().findAll {joinName.equals(it.metric.name)}
        if (joinNameKeys != null) {
            joinNameKeys.each { key ->
                StatsResult stres = results.get(key);
                if (stres.name.equals(value)) {
                    current = stres;
                    keycurrent = key;
                }
            }
        }
        if (current == null) {
            current = new StatsResult(value);
        }

        metrics.each { metric ->

            if (!joinName.equals(metric.name)) {
                Object metvalue = metric.getValue(mbeanmodule, results, id)
                current.addValue(metric.name, metvalue)
            }
        }
        if (keycurrent == null) {
            String name = joinName + ":" + value;
            keycurrent = new ResultKey(name, id, this, joinedby, serverinfos);
            results.put(keycurrent, current);
        }
    }
}