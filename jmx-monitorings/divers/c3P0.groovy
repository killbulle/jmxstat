collector.patternMonitoring(pattern: "com.mchange.v2.c3p0:type=PooledDataSource", namekey: "type")
        {
            metric(name: "jdbcUrl")
            metric(name: "numBusyConnections")
            metric(name: "numConnections")
            metric(name: "maxPoolSize")
            metric(name: "numIdleConnections")
        }