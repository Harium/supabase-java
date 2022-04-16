package io.supabase.database;

public class Pair {

    public final String key;
    public final String value;

    public Pair(String key, boolean value) {
        this.key = escape(key);
        this.value = String.valueOf(value);
    }

    public Pair(String key, int value) {
        this.key = escape(key);
        this.value = String.valueOf(value);
    }

    public Pair(String key, double value) {
        this.key = escape(key);
        this.value = String.valueOf(value);
    }

    public Pair(String key, String value) {
        this.key = escape(key);
        this.value = escape(value);
    }

    private String escape(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        return "\"" + text + "\"";
    }
}
