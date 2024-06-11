package com.payable.ttt.dto;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;


public class BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getJson() {
		final GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
		builder.excludeFieldsWithoutExposeAnnotation();
		
		builder.setDateFormat("MMM dd, yyyy hh:mm:ss aa");
		final Gson gson = builder.create();
		return gson.toJson(this);
	}
	
	
    public String getJsonNew() {
        final GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();

        // Add a custom TypeAdapter for Date fields
        builder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
            private final DateFormat df = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");

            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                if (src == null) {
                    return JsonNull.INSTANCE; // return a null JSON value for null dates
                } else {
                    return context.serialize(df.format(src)); // serialize the date as a formatted string
                }
            }
        });
        
        builder.registerTypeAdapter(String.class, new JsonSerializer<String>() {
            @Override
            public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
                if (src == null) {
                    return context.serialize(""); // return an empty string for null strings
                } else {
                    return context.serialize(src); // serialize non-null strings as-is
                }
            }
        });

        final Gson gson = builder.create();
        return gson.toJson(this);
    }

}
