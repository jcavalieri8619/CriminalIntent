package com.bignerdranch.android.criminalintent.database.converter;

import android.arch.persistence.room.TypeConverter;
import android.util.Log;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Converters {



    private static final String TAG = Converters.class.getSimpleName();




    @TypeConverter
    public static UUID fromHashUUID( String value) {

        Log.d(TAG, "fromHashUUID: ");

        return value==null?null: UUID.fromString(value);
    }

    @TypeConverter
    public static String UUIDToHash(UUID uuid) {
        Log.d(TAG, "UUIDToHash: ");

        if (uuid == null) {

            return null;
        }


        return uuid.toString();

    }


    @TypeConverter
    public static Date fromTimestamp(Long value) {
        Log.d(TAG, "fromTimestamp: ");
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        Log.d(TAG, "dateToTimestamp: ");

        return date == null ? null : date.getTime();
    }



    //TODO didn't need this but its cool so I'll leave it here for now
    private static <V, K> Map<V, K> invertMap(Map<K, V> map) {
        return map.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        new Function<Map.Entry<K, V>, V>() {
                            @Override
                            public V apply(final Map.Entry<K, V> kvEntry) {

                                return kvEntry.getValue();

                            }
                        }, new Function<Map.Entry<K,V>,K>() {
                            @Override
                            public K apply(final Map.Entry<K, V> kvEntry) {
                                return kvEntry.getKey();
                            }
                        }));
    }

}
