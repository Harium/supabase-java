package io.supabase.database;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConditionTest {

    @Test
    public void testSingleCondition() {
        assertEquals("eq.10", Condition.eq("age", 10).getQueryValue());
        assertEquals("is.true", Condition.is("student", true).getQueryValue());
    }

    @Test
    public void testRangeOperators() {
        assertEquals("in.(1,2,3)", Condition.in("a", 1, 2, 3).getQueryValue());
        assertEquals("in.(\"hi,there\",\"yes,you\")", Condition.in("a", "hi,there", "yes,you").getQueryValue());
        assertEquals("sl.(1,10)", Condition.sl("range", 1, 10).getQueryValue());
        assertEquals("sr.(1,10)", Condition.sr("range", 1, 10).getQueryValue());
        assertEquals("nxr.(1,10)", Condition.nxr("range", 1, 10).getQueryValue());
        assertEquals("nxl.(1,10)", Condition.nxl("range", 1, 10).getQueryValue());
        assertEquals("adj.(1,10)", Condition.adj("range", 1, 10).getQueryValue());
    }

    @Test
    public void testGroupOperators() {
        assertEquals("cd.{1,2,3}", Condition.cd("values", 1, 2, 3).getQueryValue());
        assertEquals("cs.{example,new}", Condition.cs("tags", "example", "new").getQueryValue());
        assertEquals("sl.(1,10)", Condition.sl("range", 1, 10).getQueryValue());
        assertEquals("sr.(1,10)", Condition.sr("range", 1, 10).getQueryValue());
        assertEquals("nxr.(1,10)", Condition.nxr("range", 1, 10).getQueryValue());
        assertEquals("nxl.(1,10)", Condition.nxl("range", 1, 10).getQueryValue());
        assertEquals("adj.(1,10)", Condition.adj("range", 1, 10).getQueryValue());


        assertEquals("ov.[2017-01-01,2017-06-30]", Condition.ov("period", "2017-01-01", "2017-06-30").getQueryValue());
        assertEquals("ov.{1,3}", Condition.ov("period", true, 1, 3).getQueryValue());
    }

    @Test
    public void testLogicalOperators() {
        assertEquals("not.eq.10", Condition.not(Condition.eq("age", 10)).getQueryValue());
        assertEquals("(age.gte.18,student.is.true)", Condition.or(Condition.gte("age", 18), Condition.is("student", true)).getQueryValue());
        assertEquals("(age.eq.14,not.and(age.gte.11,age.lte.17))",Condition.or(Condition.eq("age", 14), Condition.not(Condition.and(Condition.gte("age", 11), Condition.lte("age", 17)))).getQueryValue());
    }

    @Test
    public void testSelect() {
        assertEquals("first_name,age", Condition.select("first_name","age").getQueryValue());
        assertEquals("fullName:full_name,birthDate:birth_date", Condition.select("full_name","birth_date").as("fullName","birthDate").getQueryValue());
    }
}
