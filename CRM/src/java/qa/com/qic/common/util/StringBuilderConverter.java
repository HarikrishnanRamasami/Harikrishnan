/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.util;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.conversion.TypeConversionException;

/**
 *
 * @author ravindar.singh
 */
public class StringBuilderConverter extends StrutsTypeConverter {

    @Override
    public Object convertFromString(Map context, String[] values, Class clazz) {
        try {
            if (values[0] != null) {
                return new StringBuilder(values[0]);
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
            return value != null ? value.toString() : "";
        } catch (Exception e) {
            throw new TypeConversionException(e.getMessage());
        }
    }
}
