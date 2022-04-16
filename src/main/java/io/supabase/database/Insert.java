package io.supabase.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Insert {

    public List<Pair> pairs = new ArrayList<>();

    public Insert(Pair... pairs) {
        this.pairs.addAll(Arrays.asList(pairs));
    }

    public Insert append(String column, boolean value) {
        this.pairs.add(new Pair(column, value));
        return this;
    }

    public Insert append(String column, int value) {
        this.pairs.add(new Pair(column, value));
        return this;
    }

    public Insert append(String column, double value) {
        this.pairs.add(new Pair(column, value));
        return this;
    }

    public Insert append(String column, String value) {
        this.pairs.add(new Pair(column, value));
        return this;
    }

}
