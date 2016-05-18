package com.carlos.capstone.jsonpconverter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Carlos on 22/12/2015.
 */
public class JsonpParser {

    private static final Pattern JSONP = Pattern.compile("(?s)\\w+\\((.*)\\).*");

    public static String jsonpToJson(String jsonStr) {
        Matcher matcher = JSONP.matcher(jsonStr);
        if(matcher.find()) {
            return matcher.group(1);
        } else {
            throw new IllegalArgumentException("Unknown jsonp format");
        }
    }
}
