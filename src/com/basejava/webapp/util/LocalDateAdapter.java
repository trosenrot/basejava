package com.basejava.webapp.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.YearMonth;

public class LocalDateAdapter extends XmlAdapter<String, YearMonth> {
    @Override
    public YearMonth unmarshal(String str) throws Exception {
        return YearMonth.parse(str);
    }

    @Override
    public String marshal(YearMonth ld) throws Exception {
        return ld.toString();
    }
}
