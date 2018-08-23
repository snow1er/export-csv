package com.demo.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Provide common API for String
 * @author Neo.Li
 */
public class StringUtils {

	/**
	 * Return <code>true</code> if the supplied String is <code>null</code>
     * or empty. Otherwise, return <code>false</code>.
	 * @param str
	 * @return whether the given String is empty
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}

	
	/**
	 * Return true if the supplied String is not null or empty.
	 * Otherwise, return false.
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	/**
	 * Return true if the supplied String is UUID. Otherwise, return false.
	 * @param str
	 * @return
	 */
	public static boolean isUUID(String str) {
		return isEmpty(str) ? false : str.matches("[A-Fa-f0-9]{8}-[A-Fa-f0-9]{4}-[A-Fa-f0-9]{4}-[A-Fa-f0-9]{4}-[A-Fa-f0-9]{12}");
	}
	
	/**
	 * Return true if the supplied String is not UUID or empty.
	 * @param str
	 * @return
	 */
	public static boolean isNotUUID(String str) {
		return isEmpty(str) || !isUUID(str);
	}
	
	/**
	 * join all entities of collection using the default separator. 
	 * @param c
	 * @return
	 */
	public static String join(Collection<String> c) {
		return join(c, null);
	}
	
	/**
	 * join all entities of collection using the specified separator. 
	 * @param c
	 * @param separator
	 * @return
	 */
	public static String join(Collection<String> c, String separator) {
		if(c == null || c.isEmpty()) {
			return null;
		}
		if(separator == null) {
			separator = ",";
		}
		return c.stream().collect(Collectors.joining(separator));
	}
	
	public static String join(String[] c, String separator) {
		if(c == null || c.length == 0) {
			return null;
		}
		return join(Arrays.asList(c), separator);
	}
	
	/**
     * Check if two strings are equal. Here, null is equal to null.
     *
     * @param a the first value
     * @param b the second value
     * @return true if both are null or both are equal
     */
    public static boolean equals(String a, String b) {
        if (a == null) {
            return b == null;
        }
        return a.equals(b);
    }
    
    /**
	 * Return random alphanumeric characters with varying capitalization having specify length
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) { 
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";//alphanumeric characters with varying capitalization
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) { 
            int number = random.nextInt(62);// [0,62) 
            sb.append(str.charAt(number)); 
        } 
        return sb.toString(); 
	}
}
