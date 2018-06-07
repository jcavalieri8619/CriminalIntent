package com.bignerdranch.android.criminalintent.model;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public interface Crime {




    UUID getID();

    void setID(final UUID ID);

    String getTitle();

    void setTitle(final String title);

    String getSuspect();

    void setSuspect(final String suspect);

    boolean getPhotoPath();

    void setPhotoPath(boolean path);


    Date getDate();

    void setDate(final Date date);

    boolean isSolved();

    void setSolved(final boolean solved);

    boolean isSerious();

    void setSerious(final boolean seriousCrime);





    static String crimeDateToString(Date date) {


        final DateFormat sCrimeDateFormat = new SimpleDateFormat("MMMM d, yyyy  h:mm:ss a", Locale.ENGLISH);

        return sCrimeDateFormat.format(date);
    }

    static Date crimeDateFromString(String datestring) {

        final DateFormat sCrimeDateFormat = new SimpleDateFormat("MMMM d, yyyy  h:mm:ss a", Locale.ENGLISH);


        Date date=null;
        try {
            date = sCrimeDateFormat.parse(datestring);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }

        return date;
    }

    default String buildPhotoPath() {
        return "IMG_" + getID().toString() + ".jpg";
    }



}
