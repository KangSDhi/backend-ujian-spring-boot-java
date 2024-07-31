package dev.kangsdhi.backendujianspringbootjava.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class ConvertUtils {

    private static final Pattern DATE_TIME_PATTERN = Pattern.compile("\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}");
    private static final Pattern TIME_PATTERN = Pattern.compile("\\d{2}:\\d{2}:\\d{2}");

    Logger logger = LoggerFactory.getLogger(ConvertUtils.class);

    public Date convertStringToDatetimeOrTime(String dateOrTimeStr){
        Date date = null;
        if (DATE_TIME_PATTERN.matcher(dateOrTimeStr).matches()){
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                date = sdf.parse(dateOrTimeStr);
            } catch (ParseException e) {
                logger.error(e.getMessage());
            }
        } else if (TIME_PATTERN.matcher(dateOrTimeStr).matches()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                date = sdf.parse(dateOrTimeStr);
            } catch (ParseException e) {
                logger.error(e.getMessage());
            }
        }

        return date;
    }

    public String convertDateToStringFormat(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return sdf.format(date);
    }
}
