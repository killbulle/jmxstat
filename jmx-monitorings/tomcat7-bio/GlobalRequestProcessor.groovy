collector.patternMonitoring(pattern: "Catalina:type=GlobalRequestProcessor,name=\"http-bio-8080\"", namekey: "type")
        {
            metric(name: "errorCount")
            metric(name: "requestCount")
            metric(name: "processingTime")
            metric(name: "bytesSent")
            metric(name: "bytesReceived");
        }
