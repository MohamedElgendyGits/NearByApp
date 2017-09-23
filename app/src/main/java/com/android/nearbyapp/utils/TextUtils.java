package com.android.nearbyapp.utils;

import android.support.annotation.StringRes;

import com.android.nearbyapp.application.NearByApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mohamed Elgendy.
 */

public class TextUtils {

    private static final String EMAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])\n";
    private static final String USER_NAME_PATTERN = "^[a-z0-9\\-]+$";
    private static final String CONFIRMATION_CODE_PATTERN = "^[0-9\\-]+$";
    private static final String EMPTY_STRING_PATTERN = "^$|\\s+";
    private static Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
    private static Pattern userNamePattern = Pattern.compile(USER_NAME_PATTERN);
    private static Pattern confirmationCodePattern = Pattern.compile(CONFIRMATION_CODE_PATTERN);


    private static Matcher matcher;

    public static String getString(@StringRes int resId) {
        return NearByApplication.getInstance().getString(resId);
    }

    public static boolean validateEmail(String email) {

        matcher = android.util.Patterns.EMAIL_ADDRESS.matcher(email);
        return matcher.matches();

    }

    public static boolean isEmptyString(String str) {
        if (str == null || str.length() == 0 ||
                str.matches(EMPTY_STRING_PATTERN)) {
            return true;
        }
        return false;
    }


}
