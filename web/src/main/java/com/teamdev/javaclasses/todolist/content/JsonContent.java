package com.teamdev.javaclasses.todolist.content;

import java.util.LinkedHashMap;
import java.util.Map;

public class JsonContent implements ResponseContent<String> {

    private final Map<String, String> content = new LinkedHashMap<>();

    public void put(String key, String value) {
        content.put("\"" + key + "\"", "\"" + value + "\"");
    }
    public void put(String key, String[] values) {
        StringBuilder resultString = new StringBuilder("[");
        for (String value : values) {
            if (value.charAt(0) != '{') {
                resultString.append(String.format("\"%s\",", value));
            } else {
                resultString.append(String.format("%s,", value));
            }
        }
        if (resultString.length() > 1) {
            resultString.deleteCharAt(resultString.length() - 1);
        }
        resultString.append("]");
        content.put("\"" + key + "\"", resultString.toString());
    }

    @Override
    public String getResultContent() {
        final StringBuilder resultJson = new StringBuilder("{");
        content.forEach((key, value) ->
                resultJson.append(String.format("%s: %s,", key, value)));
        if (resultJson.length() > 1) {
            resultJson.deleteCharAt(resultJson.length() - 1);
        }
        resultJson.append("}");
        return resultJson.toString();
    }
}