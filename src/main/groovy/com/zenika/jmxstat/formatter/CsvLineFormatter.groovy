package com.zenika.jmxstat.formatter

/**
 * Output metric in a csv format
 */
class CsvLineFormatter implements OutPutFormatter {
    private String separator;

    public String format(Map results, int samplenumber) {
        List data = results.values().collect { it };
        List headers = results.keySet().collect { it.name};
        StringBuffer buffer = new StringBuffer();
        if (samplenumber == 1) {
            buffer.append(tocsvLine(headers));
        }
        buffer.append(tocsvLine(data));
        return buffer.toString()
    }

    public CsvLineFormatter(String separator) {
        this.separator = separator;
    }

    public String tocsvLine(List data) {
        StringBuilder content = new StringBuilder();
        List escapedValues = data.collect { value -> escape(value as String) }
        content.append(escapedValues.join(separator));
        content.append("\r\n")
        return content
    }

    /**
     * Returns a field value escaped for special characters
     * @param input A String to be evaluated
     * @return A properly formatted String
     */
    String escape(String input) {
		if(input==null)
		{
			return "N/A"
		}
        if (input.contains(",") || input.contains("\n") || input.contains('"') || (!input.trim().equals(input))) {

            '"' + input.replaceAll('"', '""') + '"'
        }
        else {
            input
        }
    }
}
