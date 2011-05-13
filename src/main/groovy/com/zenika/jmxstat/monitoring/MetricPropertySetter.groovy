package com.zenika.jmxstat.monitoring;


import groovy.util.ObjectGraphBuilder.DefaultChildPropertySetter

//allow a form off object inherintence in the graphbuilder
public class MetricPropertySetter extends DefaultChildPropertySetter {

    @Override
    public void setChild(Object parent, Object child, String parentName,
                         String propertyName) {
        if (propertyName.endsWith("Metric")) {
            propertyName = "metrics";
        }
        super.setChild(parent, child, parentName, propertyName);
    }

}
