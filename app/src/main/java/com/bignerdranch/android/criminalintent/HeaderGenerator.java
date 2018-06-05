package com.bignerdranch.android.criminalintent;

import com.bignerdranch.android.criminalintent.database.entity.CrimeEntity;

import java.util.UUID;

public class HeaderGenerator {


    private static HeaderGenerator instance;
    private static CrimeEntity header;

    //
    private static final byte[] header_uuid_bytes = HeaderGenerator.class.getCanonicalName().getBytes();
    private static final String header_title = "CRIME_HEADER";


    public static  HeaderGenerator getInstance() {
        initializeInstance();

        return instance;
    }

    private static void initializeInstance() {
        synchronized (HeaderGenerator.class) {
            if (instance == null) {
                instance = new HeaderGenerator();
            }
        }
    }

    public static CrimeEntity getHeaderEntity() {
        initializeInstance();

        return header;
    }



    public static boolean isHeader(CrimeEntity entity) {
        initializeInstance();
        return entity.equals(header) || entity.getID().equals(header.getID()) && entity.getTitle().equals(header.getTitle());
    }

    private HeaderGenerator() {

        header = new CrimeEntity();
        header.setID(UUID.nameUUIDFromBytes(header_uuid_bytes));
        header.setTitle(header_title);

    }
}
