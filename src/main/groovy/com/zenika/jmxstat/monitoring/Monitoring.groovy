package com.zenika.jmxstat.monitoring
/**
 * Generic class for abstract the notion off monitoring
 */
public abstract class Monitoring {
    public List<Metric> metrics = [];
    public String namekey;
    public Closure rename;

    public Monitoring() {

    }

    public void collectResults(Map results, mbeanmodule, int id, ServerInfo serverinfos) {
        metrics.each {
            metric ->
            Object value = metric.getValue(mbeanmodule, results, id)
            String name = getMetricName(mbeanmodule, metric, id);
            ResultKey key = new ResultKey(name, id, this, metric, serverinfos);
            results.put(key, value)
        }
    }
    /*
    Overddide it to match jmx metric name
     */
    public abstract boolean match(String name);

    /**
     * Generate an explicit Mbean Name
     * @param gmbeans
     * @param id
     * @return
     */
    public String getMbeanExplicitName(GroovyMBean gmbeans, int id) {
		String cplxname=gmbeans.name().getKeyProperty(namekey).replaceAll("\"", ""); 
        if (rename == null) {
            return cplxname
        }
        else {
            return rename(id,cplxname);
        }
    }

    /**
     * Generate a metric name
     * @param gmbeans
     * @param metric
     * @param id
     * @return
     */
    public String getMetricName(GroovyMBean gmbeans, Metric metric, int id) {

        return getMbeanExplicitName(gmbeans, id) + "." + metric.getDisplayName();
    }

    /**
     * Register a metric
     * @param metric
     * @return
     */
    public Monitoring addMetric(Metric metric) {
        metrics.add(metric)
        return this;
    };

    /**
     * Register a metric based on a name
     * @param name
     * @return
     */
    public Monitoring addMetric(String name) {
        metrics.add(new Metric(name))
        return this;
    };
    /**
     * Allow registring a metric List
     * @param names
     * @return
     */
    public Monitoring addMetrics(List names) {
        names.each {name ->
            metrics.add(new Metric(name))
        }

        return this;
    };
}
