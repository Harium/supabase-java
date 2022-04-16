package io.supabase.database;

import java.util.Arrays;
import java.util.List;

public class JsonHelper {

    public static String buildJsonFromInsert(Insert row) {
        return buildJsonFromPairs(row.pairs);
    }

    public static String buildJsonFromInsert(Insert... rows) {
        return buildJsonFromInsert(Arrays.asList(rows));
    }

    public static String buildJsonFromInsert(List<Insert> rows) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < rows.size(); i++) {
            Insert row = rows.get(i);
            builder.append(buildJsonFromInsert(row));
            if (i < rows.size() - 1) {
                builder.append(",");
            }
        }
        builder.append("]");
        return builder.toString();
    }

    static String buildJsonFromPairs(List<Pair> pairs) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (int i = 0; i < pairs.size(); i++) {
            Pair pair = pairs.get(i);
            builder.append(pair.key);
            builder.append(":");
            builder.append(pair.value);
            if (i < pairs.size() - 1) {
                builder.append(",");
            }
        }
        builder.append("}");
        return builder.toString();
    }
}
