/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.utility.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Logger;

import qa.com.qic.common.util.ApplicationConstants;

/**
 *
 * @author ravindar.singh
 */
public class Validatory {

    private static final Logger logger = LogUtil.getLogger(Validatory.class);

    public static boolean validateString(String inputString) {
        boolean result = true;
        if (inputString == null || "".equals(inputString.trim()) || "select".equalsIgnoreCase(inputString.trim())
                || "null".equalsIgnoreCase(inputString.trim())) {
            result = false;
        }
        return result;
    }

    public static boolean validateMobileNo(String mobileNum, int length) {

        Pattern pattern = Pattern.compile("[9]{1,1}[0-9]{" + (length - 1) + "," + (length - 1) + "}");
        Matcher matcher = pattern.matcher(mobileNum);
        return matcher.matches();

    }

    public static boolean validateMobileNo(String mobileNum, int length, String startsWith) {
        Pattern pattern = Pattern.compile("[" + startsWith + "]{1,1}[0-9]{" + (length - 1) + "," + (length - 1) + "}");
        Matcher matcher = pattern.matcher(mobileNum);
        return matcher.matches();

    }

    /*public static void main(String...a){
     //validateBackDate("9/10/2011 09:14:14", 2);
     }*/
    public static boolean validateLength(String str, int length) {
        boolean result = false;
        if (str.length() == length) {
            result = true;
        }
        return result;
    }

    public static boolean validateDate(String driverDOB) {
        boolean result = false;
        Date inputDate = getDate(driverDOB);
        Date currDate = new Date();
        if ((currDate.getTime() == (inputDate.getTime())) || (inputDate.getTime() < (currDate.getTime()))) {
            result = true;
        }
        return result;
    }

    public static boolean getComparetoBackDate(String userDate) {
        Calendar currentdate = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(ApplicationConstants.DATE_FORMAT, Locale.ENGLISH);

        String formated_currentdate = formatter.format(currentdate.getTime());
        boolean flag = false;

        try {
            Date d1 = formatter.parse(formated_currentdate);
            Date d2 = formatter.parse(userDate);
            if (d2.before(d1) || d2.equals(d1)) {
                flag = true;
            }
        } catch (ParseException ex) {
            logger.error("Exception => {}", ex);
        }
        return flag;
    }

    public static boolean getUpdateDisplayDay(String userDate, int dayToAdd) {
        Calendar currentdate = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(ApplicationConstants.DATE_FORMAT, Locale.ENGLISH);
        currentdate.add(Calendar.DATE, dayToAdd);
        boolean flag = false;
        String after15days = formatter.format(currentdate.getTime());
        try {
            Date d2 = formatter.parse(userDate);
            Date d3 = formatter.parse(after15days);
            if (d2.after(d3)) {
                flag = d2.after(d3);
            }
        } catch (ParseException ex) {
            logger.error("Exception => {}", ex);
        }

        return flag;
    }

    public static boolean validateAlphaNumber(String in) {
        boolean result = true;
        if (in != null) {
            for (Character ch : in.toCharArray()) {
                if (!Character.isLetterOrDigit(ch) && '-' != ch) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    public static boolean validateNumber(String in) {
        boolean result = true;
        try {
            in = in.trim();
            Long.parseLong(in);
        } catch (NumberFormatException num) {
            logger.info("Exception => {}", "It is not a Number");
            result = false;
        }
        return result;
    }

    /* @param precision
     * @param scale
     * @description check the given precision with exactly match to scale
     *
     * @autour ravindar.singh
     */
    public static boolean isValidNumber(String precision, int scale) {
        boolean result = false;
        try {
            if (precision == null || "".equals(precision) || scale < 0) {
                throw new NullPointerException("the given number is null");
            } else {
                precision = precision.trim();
            }
            try {
                Double.parseDouble(precision);
            } catch (NumberFormatException num) {
                throw new NumberFormatException(precision + " is not a Number");
            }
            if (scale > 0) {
                if (precision.indexOf(".") == -1) {
                    throw new NumberFormatException(precision + " not have a valid scale of " + scale);
                } else if (precision.substring(precision.indexOf(".") + 1, precision.length()).length() != scale) {
                    throw new NumberFormatException(precision + " not have a valid scale of " + scale);
                }
            } else if (scale == 0) {
                if (precision.indexOf(".") >= 0) {
                    throw new NumberFormatException(precision + " not have a valid scale of " + scale);
                }
            }
            result = true;
        } catch (Exception e) {
            logger.info("Exception => {}", e.getMessage());
        }
        return result;
    }

    public static boolean validateFloat(String in) {
        boolean result = true;
        try {
            Float.parseFloat(in);
        } catch (NumberFormatException num) {
            logger.error("Exception => {}", num);
            result = false;
        }
        return result;
    }

    public static boolean validatePercentage(double in) {
        boolean result = true;
        if (in < 0 || in > 100) {
            return false;
        }
        return result;
    }

    public static boolean checkMaxValue(String checkedValue, String maxRange) {
        boolean result = false;
        float checkVal = Float.parseFloat(checkedValue);
        float maxValue = Float.parseFloat(maxRange);
        if (checkVal > maxValue) {
            result = true;
        }
        return result;
    }

    public static boolean validateBackDateYear(String start, int maxBackYear) {
        boolean result = false;
        Date inputDate = getDate(start);
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(inputDate);
        Calendar currCal = Calendar.getInstance();
        currCal.add(Calendar.YEAR, -maxBackYear);
        long count = (currCal.getTimeInMillis() - startCal.getTimeInMillis()) / (1000 * 60 * 60 * 24);
        if (count < 0) {
            result = true;
        }
        return result;
    }

    public static boolean validateBackDate(String start, int maxBackDate) {
        boolean result = false;
        Date inputDate = getDate(start);
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(inputDate);
        Calendar currCal = Calendar.getInstance();
        long count = (currCal.getTimeInMillis() - startCal.getTimeInMillis()) / (1000 * 60 * 60 * 24);
        if (count > maxBackDate) {
            result = true;
        }
        return result;
    }

    public static boolean validateBackDateWithTime(String start, int maxBackDate) {
        boolean result = false;
        Date inputDate = getDateWithTime(start);
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(inputDate);
        Calendar currCal = Calendar.getInstance();
        long count = (currCal.getTimeInMillis() - startCal.getTimeInMillis()) / (1000 * 60 * 60 * 24);
        if (count > maxBackDate) {
            result = true;
        }
        return result;
    }

    public static long getDaysTime(String from, String to) {
        Date fromDate = getDateWithTime(from);
        Date toDate = getDateWithTime(to);
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(fromDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(toDate);
        return (endCal.getTimeInMillis() - startCal.getTimeInMillis()) / (1000 * 60 * 60 * 24);
    }

    public static long getTimeDiff(String from, String to) {
        Date fromDate = getDateWithTime(from);
        Date toDate = getDateWithTime(to);
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(fromDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(toDate);
        return (endCal.getTimeInMillis() - startCal.getTimeInMillis());
    }

    public static long getDays(String from, String to) {
        Date fromDate = getDate(from);
        Date toDate = getDate(to);
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(fromDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(toDate);
        return (endCal.getTimeInMillis() - startCal.getTimeInMillis()) / (1000 * 60 * 60 * 24);
    }

    public static long getDays(String from, String to, int daysToAdd) {
        Date fromDate = getDate(from);
        Date toDate = getDate(to);
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(fromDate);
        startCal.add(Calendar.DAY_OF_MONTH, -(daysToAdd));
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(toDate);
        return (endCal.getTimeInMillis() - startCal.getTimeInMillis()) / (1000 * 60 * 60 * 24);
    }

    public static boolean validateFutureDateWithTime(String end, int maxFutureDate) {
        boolean result = false;
        Date inputDate = getDateWithTime(end);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(inputDate);
        Calendar currCal = Calendar.getInstance();
        long count = (endCal.getTimeInMillis() - currCal.getTimeInMillis()) / (1000 * 60 * 60 * 24);
        if (count > maxFutureDate) {
            result = true;
        }
        return result;
    }

    public static boolean validateFutureDate(String end, int maxFutureDate) {
        boolean result = false;
        Date inputDate = getDate(end);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(inputDate);
        endCal.set(Calendar.MILLISECOND, 0);
        Calendar currCal = Calendar.getInstance();
        currCal.set(Calendar.HOUR_OF_DAY, 0);
        currCal.set(Calendar.MINUTE, 0);
        currCal.set(Calendar.SECOND, 0);
        currCal.set(Calendar.MILLISECOND, 0);
        long count = (endCal.getTimeInMillis() - currCal.getTimeInMillis()) / (1000 * 60 * 60 * 24);
        if (count > maxFutureDate) {
            result = true;
        }
        return result;
    }

    public static boolean validateBackDate(Date start, int maxBackDate) {
        boolean result = false;
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(start);
        Calendar currCal = Calendar.getInstance();
        long count = (currCal.getTimeInMillis() - startCal.getTimeInMillis()) / (1000 * 60 * 60 * 24);
        if (count > maxBackDate) {
            result = true;
        }
        return result;
    }

    public static boolean validateFutureDate(Date end, int maxFutureDate) {
        boolean result = false;
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(end);
        endCal.set(Calendar.MILLISECOND, 0);
        Calendar currCal = Calendar.getInstance();
        currCal.set(Calendar.HOUR_OF_DAY, 0);
        currCal.set(Calendar.MINUTE, 0);
        currCal.set(Calendar.SECOND, 0);
        currCal.set(Calendar.MILLISECOND, 0);
        long count = (endCal.getTimeInMillis() - currCal.getTimeInMillis()) / (1000 * 60 * 60 * 24);
        if (count > maxFutureDate) {
            result = true;
        }
        return result;
    }

    public static long getTimeDiff(Date from, Date to) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(from);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(to);
        return (endCal.getTimeInMillis() - startCal.getTimeInMillis());
    }

    public static Date getDateWithTime(String date) {
        DateFormat df = new SimpleDateFormat(ApplicationConstants.DATE_TIME_FORMAT, Locale.ENGLISH);
        Date inputDate = null;
        try {
            inputDate = df.parse(date);
        } catch (ParseException ex) {
            logger.error("Exception => {}", ex);
        }
        return inputDate;
    }

    public static Date getDate(String date) {
        DateFormat df = new SimpleDateFormat(ApplicationConstants.DATE_FORMAT, Locale.ENGLISH);
        Date inputDate = null;
        try {
            inputDate = df.parse(date);
        } catch (ParseException ex) {
            logger.error("Exception => {}", ex);
        }
        return inputDate;
    }

    public static boolean isValidDate(String date) {
        Pattern pattern = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/](19|20)\\d\\d$");
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    public static boolean isValidPattern(String value, String regex) {
        if(null == value) return false;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static boolean isValidDateTime(String dateTime) {
        Pattern pattern = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/](19|20)\\d\\d (([0-9])|([0-1][0-9])|([2][0-3])):(([0-9])|([0-5][0-9]))$");
        Matcher matcher = pattern.matcher(dateTime);
        return matcher.matches();
    }

    public static boolean validateFutureDateForRenewal(String renPolStartDate, String policyStartDate, int maxFutureDate) {
        System.out.println("maxFutureDate = " + maxFutureDate);
        boolean result = false;
        Date inputDate = getDate(policyStartDate);
        Date cmpDate = getDate(renPolStartDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(inputDate);
        Calendar currCal = Calendar.getInstance();
        currCal.setTime(cmpDate);
        long count = (endCal.getTimeInMillis() - currCal.getTimeInMillis()) / (1000 * 60 * 60 * 24);
        if (count > maxFutureDate) {
            result = true;
        }
        return result;
    }

    /**
     * Checks if is valid string.
     *
     * @param inputString the input string
     * @return true, if is valid string
     */
    public static boolean isValidString(String inputString){
        boolean result = true;
        if(inputString==null || "".equals(inputString.trim()) || "select".equalsIgnoreCase(inputString.trim())
                || "null".equalsIgnoreCase(inputString.trim())){
            result = false;
        }
        return result;
    }

    /**
     * Checks if is valid mobile no.
     *
     * @param mobileNum the mobile num
     * @param length the length
     * @return true, if is valid mobile no
     */
    public static boolean isValidMobileNo(String mobileNum, int length){
        Pattern pattern = Pattern.compile("[9]{1,1}[0-9]{"+(length-1)+","+(length-1)+"}");
        Matcher matcher = pattern.matcher(mobileNum);
        return matcher.matches();

    }

    /**
     * Checks if is valid mobile no.
     *
     * @param mobileNum the mobile num
     * @param length the length
     * @param startsWith the starts with
     * @return true, if is valid mobile no
     */
    public static boolean isValidMobileNo(String mobileNum, int length, String startsWith){
        Pattern pattern = Pattern.compile("["+startsWith+"]{1,1}[0-9]{"+(length-1)+","+(length-1)+"}");
        Matcher matcher = pattern.matcher(mobileNum);
        return matcher.matches();

    }

    /**
     * Checks if is valid email.
     *
     * @param email the email
     * @return true, if is valid email
     */
    public static boolean isValidEmail(String email){
        if(null == email) return false;
        Pattern pattern = Pattern.compile("^[A-Za-z0-9_\\+-]+(\\.[A-Za-z0-9_\\+-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.([A-Za-z]{2,4})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Checks if is valid length.
     *
     * @param str the str
     * @param length the length
     * @return true, if is valid length
     */
    public static boolean isValidLength(String str, int length) {
        boolean result = true;
        if(str.length() == length){
            result = false;
        }
        return result;
    }

    /**
     * Checks if is valid alpha numeric.
     *
     * @param in the in
     * @return true, if is valid alpha numeric
     */
    public static boolean isValidAlphaNumeric(String in){
        boolean result = true;
        if(in!=null){
            for(Character ch:in.toCharArray()){
                if(!Character.isLetterOrDigit(ch) && '-'!=ch){
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Checks if is valid number.
     *
     * @param in the in
     * @return true, if is valid number
     */
    public static boolean isValidNumber(String in){
        boolean result = true;
        try{
            in = in.trim();
            Long.parseLong(in);
        }catch(NumberFormatException num){
            result = false;
        }
        return result;
    }

    /**
     * Checks if is valid float.
     *
     * @param in the in
     * @return true, if is valid float
     */
    public static boolean isValidFloat(String in){
        boolean result = true;
        try{
            Float.parseFloat(in);
        }catch(NumberFormatException num){
            result = false;
        }
        return result;
    }
}
