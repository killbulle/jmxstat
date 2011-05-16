import com.zenika.jmxstat.formatter.OutPutFormatter

/*
 * Format Metric in an brutal form for cumulative stats for example
 */
class BrutFormatter implements OutPutFormatter {

    public String format(Map results, int samplenumber) {
        StringBuilder builder= new StringBuilder();

        results.each {
            key, value ->
             builder.append("-------------------------------------------------------------------------------------");
             builder.append("${key.name}\t\t name:${key?.metric?.name}\n");
             builder.append(value)
             builder.append("-------------------------------------------------------------------------------------");
        }
        return builder.toString();


    }

}
