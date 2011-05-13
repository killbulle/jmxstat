package com.zenika.jmxstat.monitoring

import javax.management.openmbean.CompositeData
import javax.management.openmbean.CompositeType
import javax.management.openmbean.TabularDataSupport

public class Metric {
    protected String name;
    //metric rename name and id 
    protected Closure rename;

    //TODO support des formulas
    protected Closure formula;

   

    public String getDisplayName() {
		
        return (rename == null) ? name : rename(name);
    }

    public Metric() {
    }



    private Object describeCompositeData(CompositeData item, Object path) {
        CompositeType compositeType = item.getCompositeType()
        Object value = item.get(path)
        return value
    }

    //TODO re think data acess for tabular value

    public Object getValue(bean, Map results, int id) {
       
        Object value = null;
        int pointpos = name.indexOf(".")
        if (pointpos != -1 && bean instanceof GroovyMBean) {
            //gestion des composite type
            String[] recurs = name.toString().split("\\.");
            int i = 0;
            Object tmpvalue = bean[recurs[0]]
            while (tmpvalue instanceof CompositeData) {
                tmpvalue = describeCompositeData(bean[recurs[i]], recurs[i + 1])
                //degueu
                if (tmpvalue instanceof TabularDataSupport) {
                    String[] keys = [recurs[i + 2]]
                    tmpvalue = tmpvalue.get(keys)
                    tmpvalue = describeCompositeData(tmpvalue, "value")
                    tmpvalue = describeCompositeData(tmpvalue, recurs[i + 3])
                }
                i++;
            }
            value = tmpvalue;
        }
        else {
            value = bean[name]
        }
		if (formula != null) {
			return formula(bean,results,name,id,value);
		}
		return value;
    }
}
