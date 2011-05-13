package com.zenika.jmxstat.monitoring

class MonitoringGraphBuilder extends ObjectGraphBuilder {
    List<Monitoring> monitorings = new ArrayList<Monitoring>();

    @Override
    protected void postInstantiate(Object name, Map attributes, Object node) {
        super.postInstantiate(name, attributes, node);
        if (node instanceof Monitoring) {
            monitorings.add(node);
        }
    }

    public MonitoringGraphBuilder() {
        super();
    }


}
