collector.patternMonitoring(pattern: "flex.runtime.ose:type=MessageBroker.AMFEndpoint,MessageBroker=MessageBroker1,id=my-amf", namekey: "id")
        {
            metric(name: "BytesDeserialized")
            metric(name: "BytesSerialized")
            metric(name: "ServiceMessageFrequency")
            metric(name: "ServiceMessageCount")
        }
