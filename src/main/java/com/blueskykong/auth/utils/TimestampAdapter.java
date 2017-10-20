package com.blueskykong.auth.utils;

import com.google.common.base.Throwables;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TimestampAdapter extends XmlAdapter<String, Timestamp> {
    private static final DatatypeFactory df;
    private static final ThreadLocal<DateFormat> simpleDateFormat = new ThreadLocal<DateFormat>(){
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    static {
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public String marshal(Timestamp v) throws Exception {
        if (v != null) {
            return simpleDateFormat.get().format(v);
        }
        return null;
    }

    @Override
    public Timestamp unmarshal(String v) throws Exception {
        XMLGregorianCalendar cal = df.newXMLGregorianCalendar(v);
        try {
            return new Timestamp(cal.toGregorianCalendar().getTimeInMillis());
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

}
