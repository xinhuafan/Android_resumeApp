package com.android.alice.resume.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by xinhuafan on 12/19/16.
 */

public class ModelUtils {

    private static Gson gsonForSerialization = new GsonBuilder().registerTypeAdapter(Uri.class, new UriSerializer()).create();

    private static Gson gsonForDeserialization = new GsonBuilder().registerTypeAdapter(Uri.class, new UriDeserializer()).create();

    private static String PREF_NAME = "models";

    public static void save(Context context, String key, Object object) {

        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String jsonString = gsonForSerialization.toJson(object);
        sp.edit().putString(key, jsonString).apply();
    }

    public static <T> T read(Context context, String key, TypeToken<T> typeToken) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        try {
            return gsonForDeserialization.fromJson(sp.getString(key, ""), typeToken.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class UriSerializer implements JsonSerializer<Uri> {

        @Override
        public JsonElement serialize(Uri src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }
    }

    private static class UriDeserializer implements JsonDeserializer<Uri> {

        @Override
        public Uri deserialize(JsonElement src, Type srcType, JsonDeserializationContext context) {
            return Uri.parse(src.getAsString());
        }
    }
}
