//file descriptor
collector.patternMonitoring(pattern: "java.lang:type=OperatingSystem", namekey: "type")
        {
            metric(name: "MaxFileDescriptorCount")
            metric(name: "OpenFileDescriptorCount")
        }