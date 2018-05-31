package com.bignerdranch.android.criminalintent.database;

import android.arch.persistence.room.TypeConverter;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Converters {


    private static Map<Long, String> sLongStringMap = new Hashtable<>();
    private static Map<String, Long> sStringLongMap = new Hashtable<>();






    @TypeConverter
    public static UUID fromHashUUID( Long value) {
        return value==null?null: UUID.fromString(sLongStringMap.get(value));
    }

    @TypeConverter
    public static Long UUIDToHash(UUID uuid) {
        if (uuid == null) {

            return null;
        }
        String key = addUUID(uuid);

        return sStringLongMap.get(key);

    }


    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    private static String addUUID(UUID uuid) {
        String uuid_str = uuid.toString();

        if (sStringLongMap.containsKey(uuid_str)) {
            return uuid_str;
        }


        Random rng = new Random(uuid.getMostSignificantBits());

        Long uuid_long = rng.nextLong();
        if (sLongStringMap.containsKey(uuid_long)) {
            uuid_long = rng.nextLong();
        }

        sLongStringMap.put(uuid_long, uuid_str);
        sStringLongMap.put(uuid_str, uuid_long);

        return uuid_str;

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
