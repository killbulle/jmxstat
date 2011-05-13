package com.zenika.jmxstat.formatter;

import java.util.Map;
 /*
    Generic interface for formating output
  */
public interface OutPutFormatter {
    public String format(Map results, int samplenumber);
}

