package com.seanchenxi.gwt.storage.client;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.storage.client.Storage;
import com.wallissoftware.zip.client.Inflate;

public class PriorityStorage {

    private static final String VALUE_KEY = "v";
    private static final String PRIORITY_KEY = "p";

    private final Storage storage;

    private final static Logger logger = Logger.getLogger(PriorityStorage.class.getName());

    PriorityStorage(final Storage storage) {
        this.storage = storage;
    }

    private boolean cleanByPriority() {
        double maxPriority = -1;
        final Set<String> keysToDelete = new HashSet<>();
        for (int i = 0; i < getLength(); i++) {

            final double priority = getJsonItem(key(i)).get(PRIORITY_KEY).isNumber().doubleValue();
            if (priority > -1) {
                if (priority > maxPriority) {
                    maxPriority = priority;
                    keysToDelete.clear();
                }

                if (maxPriority == priority) {
                    keysToDelete.add(key(i));
                }
            }
        }
        if (!keysToDelete.isEmpty()) {
            for (final String key : keysToDelete) {
                removeItem(key);
            }
            return true;
        } else {
            return false;
        }
    }

    public void clear() {
        storage.clear();
    }

    public String getItem(final String key) {
        final JSONObject jsonItem = getJsonItem(key);
        if (jsonItem.containsKey(VALUE_KEY) && jsonItem.get(VALUE_KEY).isString() != null) {
            return jsonItem.get(VALUE_KEY).isString().stringValue();
        } else {
            return null;
        }
    }

    private JSONObject getJsonItem(final String key) {
        final String storageItem = storage.getItem(key);
        if (storageItem == null) {
            final JSONObject result = new JSONObject();
            result.put(PRIORITY_KEY, new JSONNumber(-1));
            return result;
        }
        return JSONParser.parseStrict(Inflate.inflate(storageItem)).isObject();
    }

    public int getLength() {
        return storage.getLength();
    }


    public String key(final int index) {
        return storage.key(index);
    }

    public void removeItem(final String key) {
        storage.removeItem(key);
    }

    public void setItem(final String key, final String data, final int priority) {
        try {
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put(PRIORITY_KEY, new JSONNumber(priority));
            jsonObject.put(VALUE_KEY, new JSONString(data));
            storage.setItem(key, Inflate.deflate(jsonObject.toString()));
        } catch (final JavaScriptException e) {
            final String msg = e.getMessage();
            if (msg != null && msg.contains("QUOTA") && msg.contains("DOM")) {
                if (cleanByPriority()) {
                    setItem(key, data, priority);
                    return;
                }
                throw e;
            }
        }
    }



}
