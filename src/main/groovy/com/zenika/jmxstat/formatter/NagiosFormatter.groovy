package com.zenika.jmxstat.formatter

import com.zenika.jmxstat.monitoring.Metric

class NagiosFormatter implements OutPutFormatter {
    /**
     ** 	JMX OK - HeapMemoryUsage.used = 605230312 | 'HeapMemoryUsage used'=605230312;;;;
     * */
    public NagiosFormatter() {
        super();
    }

    public String format(Map results, int samplernumber) {
        List data = results.values().collect { it };
        StringBuilder builder = new StringBuilder();

        Long timestamp = data[0];
        Map classedKey = results.keySet().groupBy { it.metric.nclass};

        classedKey.keySet().each { nclass ->
            if (nclass != null && !nclass != null) {
                String begline = "${timestamp};${nclass};0;JMX OK -";
                builder.append(begline);
                List keys = classedKey.get(nclass);
                keys.each {key ->
                    builder.append(key.name.replaceAll(" ", "_") + ":" + results.get(key) + " ");
                }
                builder.append("|");
                keys.each {key ->
                    Metric metric = key.metric;
                    builder.append(key.name.replaceAll(" ", "_") + ":" + results.get(key) + "${metric?.nunit};${metric?.nwarn};${metric?.ncrit};${metric?.nmin};${metric?.nmax}");
                }
                builder.append("\n");
            }
        }

        return builder;
    }
}
