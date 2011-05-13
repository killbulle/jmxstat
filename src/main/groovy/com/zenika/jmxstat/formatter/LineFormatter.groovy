package com.zenika.jmxstat.formatter
/*
    Format results in a vmstat like mode , useful with few column only
 */
class LineFormatter implements OutPutFormatter {
    boolean autopad
    private int headerrepeat;
    private int cut;


    public LineFormatter(boolean autopad, int headerrepeat, int cut) {
        super();
        this.autopad = autopad;
        this.headerrepeat = headerrepeat;
        this.cut = cut;
    }

    public String format(Map results, int samplenumber) {
        List data = results.values().collect { it };
        List headers = results.keySet().collect { it.name};
        handleNormalResults(data, headers, samplenumber);
    }

    private handleNormalResults(data, headers, samplenumber) {
        StringWriter stw = new StringWriter();
        PrintWriter pw = new PrintWriter(stw);
        String format = "%-25s";
        if (cut != 0 && !autopad) {
            headers = cutHeaders(headers);
        }
        if (autopad) {
            int max = Math.max(maxSize(headers), maxSize(data));
            format = "%-${max + 1}s";
        }
        String lineFormat = format * headers.size()
        int modulo = 0;
        if (samplenumber != 1 && headerrepeat != 0) {
            modulo = (samplenumber-1) % headerrepeat
        }
        if (modulo == 0 || samplenumber==1) {
            pw.printf(lineFormat + " \r\n", headers);

        }
        pw.printf(lineFormat, data);
        pw.print("\r\n")
        return stw.toString()
    }



    private String cutHeader(String header) {

        List splited = header.split("\\.");
        List shorter = splited.collect { val -> val.substring(0, Math.min(cut, val.length()))}
        String newname = shorter.join(".")

        return newname;
    }

    private List cutHeaders(List headers) {
        return headers.collect {val -> val = cutHeader(val) };
    }

    private int maxSize(Collection data) {
        int max = 0;
        data.collect {val -> max = Math.max(max, val.toString().length()) };
        return max;
    }


}
