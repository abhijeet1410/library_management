package com.smarttersstudio.libraryapp.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {
    public static final String API_URL = "http://library.uuimca.org/api/";
    public static final String REGISTER_URL=API_URL+"register.php";
    public static final String LOGIN_URL=API_URL+"login.php";
    public static final String ISSUED_BOOK = API_URL+"getMyIssuedBooks.php";
    public static final String DIRECTORY = API_URL+"getAllBooks.php";
    public static final String NOTIFICATION = API_URL+"getMyRequests.php";
    public static final String HISTORY_URL = API_URL+"getMyHistory.php";
    public static final String RESOLVE = API_URL+"resolveMyRequest.php";

    private static int index;

    public static int colorMapper(int position, Context current){

        int returnColor = Color.BLACK;
        int arrayId = current.getResources().getIdentifier(
                "lecturenotes_color_array",
                "array",
                current.getPackageName()
        );

        TypedArray colors = current.getResources().obtainTypedArray(arrayId);

        if (position == 0) {

            index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.BLACK);
            colors.recycle();
        } else {
            index++;
            if (index > colors.length() - 1) index = index % colors.length();
            returnColor = colors.getColor(index, Color.BLACK);
            colors.recycle();
        }
        return returnColor;
    }
    public static String getFormattedDate(String datetime){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date d = new Date();
        try {
            d = sdf.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(d);
    }
}
