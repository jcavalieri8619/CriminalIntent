package com.bignerdranch.android.criminalintent.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;

    private  List<Crime> mCrimes;

    private Map<UUID, Crime> mUUIDCrimeMap;

    private Context ctx;


    public void addCrime(Crime crime) {
        mUUIDCrimeMap.put(crime.getID(), crime);
    }

    public static CrimeLab getInstance(Context ctx) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(ctx);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context ctx) {

        mUUIDCrimeMap = new LinkedHashMap<>();
        this.ctx = ctx;


    }

    public void TESTING_addMoreCrimes(int numCrimes) {
        //TESTING populate CrimeLab with test crimes for now
        int start = mCrimes == null ? 0 : mCrimes.size();
        for (int i = start; i < start+numCrimes; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime # " + i);
            crime.setSolved(i % 2 == 0);
            crime.setSeriousCrime((i + 1) % 13 == 0);

            mUUIDCrimeMap.put(crime.getID(), crime);
        }
        //TESTING
    }



    //FIXME mCrimes should be kept syncrhonized with the hashMap so getCrimes will probably need to recreate arraylist each invocation
    public List<Crime> getCrimes() {

//        if (mCrimes == null) {
//            mCrimes = new ArrayList<>(mUUIDCrimeMap.values());
//
//            // doesn't work
////            mCrimes = (List<Crime>) (List<?>) mUUIDCrimeMap.values();
//
//        }

        mCrimes = new ArrayList<>(mUUIDCrimeMap.values());
        return mCrimes;
    }

    public Crime getCrime(UUID ID) {
        return mUUIDCrimeMap.get(ID);
    }


}
