package com.roboautomator.app.component.util;

import java.lang.reflect.*;

import org.apache.commons.lang3.ArrayUtils;

public class StringHelper {

    private StringHelper() {
        // EMTPY
    }

    /**
     * <p>
     * Removes the following characters to protect users from log injection:
     * </p>
     * <ul>
     * <li>NEWLINE - \n</li>
     * <li>CARRIAGE RETURN - \r</li>
     * <li>TAB - \t</li>
     * </ul>
     *
     * <p>
     * Replaces the characters using "".
     * </p>
     *
     * @param stringToParse the String to clean
     *
     * @return the stringToParse without the characters that have been removed.
     */
    public static String cleanString(String stringToParse) {
        return stringToParse.replaceAll("[\n|\r|\t]", "");
    }

    /**
     * Takes a class and returns the declared values and it's super declared values
     * in string format. e.g.:
     * 
     * String name = "John";
     * String age = 18;
     * 
     * becomes: 
     * 
     * "name=Johnage=18"
     * 
     * @param clazz
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static String classToString(Object clazz) throws IllegalArgumentException, IllegalAccessException {
        Field[] superClassFields = clazz.getClass().getSuperclass().getDeclaredFields();
        Field[] classFields = clazz.getClass().getDeclaredFields();
        Field[] fields = ArrayUtils.addAll(superClassFields, classFields);

        StringBuilder builder = new StringBuilder();

        for (Field field : fields) {
            field.setAccessible(true);
            builder.append(field.getName() + "=" + field.get(clazz));
        }
        return builder.toString();
    }

}
