package io.supabase.database;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonHelperTest {

    @Test
    public void testSimpleJsonFromPairs() {
        assertEquals("{\"col1\":}", JsonHelper.buildJsonFromInsert(new Insert().append("col1", "")));
        assertEquals("{\"col1\":\"value1\"}", JsonHelper.buildJsonFromInsert(new Insert().append("col1", "value1")));
        assertEquals("{\"col1\":\"value1\",\"col2\":\"value2\"}", JsonHelper.buildJsonFromInsert(new Insert().append("col1", "value1").append("col2", "value2")));
    }

}
