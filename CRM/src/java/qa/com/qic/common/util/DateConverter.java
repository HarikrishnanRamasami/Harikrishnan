/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.conversion.TypeConversionException;

/**
 *
 * @author ravindar.singh
 */
public class DateConverter extends StrutsTypeConverter {

    private static final DateFormat INPUT_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final DateFormat INPUT_DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static final DateFormat OUTPUT_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:MM:SS");

    @Override
    public Object convertFromString(Map context, String[] values, Class clazz) {
        try {
            if (values[0] != null && !values[0].equals("")) {
                if (values[0].length() > 12) {
                    return INPUT_DATE_TIME_FORMAT.parse(values[0]);
                } else {
                    return INPUT_DATE_FORMAT.parse(values[0]);
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new TypeConversionException(e.getMessage());
        }
    }

    @Override
    public String convertToString(Map context, Object value) {
        try {
            return OUTPUT_FORMAT.format(value);
        } catch (Exception e) {
            throw new TypeConversionException(e.getMessage());
        }
    }
}
