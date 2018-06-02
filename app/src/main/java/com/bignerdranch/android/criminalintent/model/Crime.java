package com.bignerdranch.android.criminalintent.model;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.util.Log;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public interface Crime {

    DateFormat crimeDateFormat = new SimpleDateFormat("MMMM d, yyyy  h:m:s a", Locale.ENGLISH);

    default String getFormattedDate() {
        return crimeDateFormat.format(getDate());
    }

    default Date SetDateFromFromString(String datestring) {
        Date date=null;
        try {
            date = crimeDateFormat.parse(datestring);
        } catch (ParseException e) {
            Log.e("CRIMINALINTENT:CRIME_ENTITY",
                    "SetDateFromFromString: ERROR CONVERTING STRING INTO DATE");

        }

        return date;
    }

    UUID getID();

    void setID(final UUID ID);

    String getTitle();

    void setTitle(final String title);

    Date getDate();

    void setDate(final Date date);

    boolean isSolved();

    void setSolved(final boolean solved);

    boolean isSeriousCrime();

    void setSeriousCrime(final boolean seriousCrime);


}
