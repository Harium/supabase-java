package io.supabase.database;

/**
 * Condition class that represents queries based on PostgREST
 * https://postgrest.org/en/stable/api.html#
 * <p>
 * TODO MOVE THIS CLASS TO AN EXTERNAL REPO
 */
public class Condition {

    private static final String OPERATOR_ADJ = "adj";
    private static final String OPERATOR_CS = "cs";
    private static final String OPERATOR_CD = "cd";
    private static final String OPERATOR_EQUALS = "eq";
    private static final String OPERATOR_GREATER_THAN = "gt";
    private static final String OPERATOR_GREATER_THAN_OR_EQUAL = "gte";
    private static final String OPERATOR_LESS_THAN = "lt";
    private static final String OPERATOR_LESS_THAN_OR_EQUAL = "lte";
    private static final String OPERATOR_LIKE = "like";
    private static final String OPERATOR_ILIKE = "ilike";
    private static final String OPERATOR_IN = "in";
    private static final String OPERATOR_IS = "is";
    private static final String OPERATOR_NOT = "not";
    private static final String OPERATOR_NOT_EQUAL = "neq";
    private static final String OPERATOR_NXR = "nxr";
    private static final String OPERATOR_NXL = "nxl";
    private static final String OPERATOR_OVERLAP = "ov";
    private static final String OPERATOR_SL = "sl";
    private static final String OPERATOR_SR = "sr";
    private static final String OPERATOR_OR = "or";
    private static final String OPERATOR_AND = "and";

    protected final String column;
    protected final String operator;
    protected String value;

    Condition(String column, String operator, boolean value) {
        this.column = column;
        this.operator = operator;
        this.value = String.valueOf(value);
    }

    Condition(String column, String operator, int value) {
        this.column = column;
        this.operator = operator;
        this.value = String.valueOf(value);
    }

    Condition(String column, String operator, double value) {
        this.column = column;
        this.operator = operator;
        this.value = String.valueOf(value);
    }

    Condition(String column, String operator, String value) {
        this.column = column;
        this.operator = operator;
        this.value = escapeValue(value);
    }

    protected String escapeValue(String value) {
        if (value == null) {
            return "";
        }

        return "\"" + value + "\"".replaceAll(" ", "%20");
    }

    public static Condition eq(String column, boolean value) {
        return new Condition(column, OPERATOR_EQUALS, value);
    }

    public static Condition eq(String column, double value) {
        return new Condition(column, OPERATOR_EQUALS, value);
    }

    public static Condition eq(String column, int value) {
        return new Condition(column, OPERATOR_EQUALS, value);
    }

    public static Condition eq(String column, String value) {
        return new Condition(column, OPERATOR_EQUALS, value);
    }

    public static Condition gt(String column, double value) {
        return new Condition(column, OPERATOR_GREATER_THAN, value);
    }

    public static Condition gt(String column, int value) {
        return new Condition(column, OPERATOR_GREATER_THAN, value);
    }

    public static Condition gt(String column, String value) {
        return new Condition(column, OPERATOR_GREATER_THAN, value);
    }

    public static Condition gte(String column, double value) {
        return new Condition(column, OPERATOR_GREATER_THAN_OR_EQUAL, value);
    }

    public static Condition gte(String column, int value) {
        return new Condition(column, OPERATOR_GREATER_THAN_OR_EQUAL, value);
    }

    public static Condition gte(String column, String value) {
        return new Condition(column, OPERATOR_GREATER_THAN_OR_EQUAL, value);
    }

    public static Condition lt(String column, double value) {
        return new Condition(column, OPERATOR_LESS_THAN, value);
    }

    public static Condition lt(String column, int value) {
        return new Condition(column, OPERATOR_LESS_THAN, value);
    }

    public static Condition lt(String column, String value) {
        return new Condition(column, OPERATOR_LESS_THAN, value);
    }

    public static Condition lte(String column, double value) {
        return new Condition(column, OPERATOR_LESS_THAN_OR_EQUAL, value);
    }

    public static Condition lte(String column, int value) {
        return new Condition(column, OPERATOR_LESS_THAN_OR_EQUAL, value);
    }

    public static Condition lte(String column, String value) {
        return new Condition(column, OPERATOR_LESS_THAN_OR_EQUAL, value);
    }

    public static Condition neq(String column, boolean value) {
        return new Condition(column, OPERATOR_NOT_EQUAL, value);
    }

    public static Condition neq(String column, double value) {
        return new Condition(column, OPERATOR_NOT_EQUAL, value);
    }

    public static Condition neq(String column, int value) {
        return new Condition(column, OPERATOR_NOT_EQUAL, value);
    }

    public static Condition neq(String column, String value) {
        return new Condition(column, OPERATOR_NOT_EQUAL, value);
    }

    /*
     * Note: use * in place of %
     */
    public static Condition like(String column, String value) {
        return new Condition(column, OPERATOR_LIKE, value);
    }

    /*
     * Note: use * in place of %
     */
    public static Condition ilike(String column, String value) {
        return new Condition(column, OPERATOR_ILIKE, value);
    }

    public static Condition in(String column, int... values) {
        return new RangedCondition(column, OPERATOR_IN, values);
    }

    public static Condition in(String column, String... values) {
        return new RangedCondition(column, OPERATOR_IN, values);
    }

    public static Condition is(String column, boolean value) {
        return new Condition(column, OPERATOR_IS, value);
    }

    public static Condition is(String column, String value) {
        return new Condition(column, OPERATOR_IS, value);
    }

    public static Condition cs(String column, int... values) {
        return new GroupCondition(column, OPERATOR_CS, values);
    }

    public static Condition cs(String column, String... values) {
        return new GroupCondition(column, OPERATOR_CS, values);
    }

    public static Condition cd(String column, int... values) {
        return new GroupCondition(column, OPERATOR_CD, values);
    }

    public static Condition cd(String column, String... values) {
        return new GroupCondition(column, OPERATOR_CD, values);
    }

    public static Condition ov(String column, int... values) {
        return ov(column, false, values);
    }

    public static Condition ov(String column, String... values) {
        return ov(column, false, values);
    }

    public static Condition ov(String column, boolean asArray, int... values) {
        if (!asArray) {
            return new PeriodCondition(column, OPERATOR_OVERLAP, values);
        }
        return new GroupCondition(column, OPERATOR_OVERLAP, values);
    }

    public static Condition ov(String column, boolean asArray, String... values) {
        if (!asArray) {
            return new PeriodCondition(column, OPERATOR_OVERLAP, values);
        }
        return new GroupCondition(column, OPERATOR_OVERLAP, values);
    }

    public static Condition sl(String column, int... values) {
        return new RangedCondition(column, OPERATOR_SL, values);
    }

    public static Condition sl(String column, String... values) {
        return new RangedCondition(column, OPERATOR_SL, values);
    }

    public static Condition sr(String column, int... values) {
        return new RangedCondition(column, OPERATOR_SR, values);
    }

    public static Condition sr(String column, String... values) {
        return new RangedCondition(column, OPERATOR_SR, values);
    }

    public static Condition nxl(String column, int... values) {
        return new RangedCondition(column, OPERATOR_NXL, values);
    }

    public static Condition nxl(String column, String... values) {
        return new RangedCondition(column, OPERATOR_NXL, values);
    }

    public static Condition nxr(String column, int... values) {
        return new RangedCondition(column, OPERATOR_NXR, values);
    }

    public static Condition nxr(String column, String... values) {
        return new RangedCondition(column, OPERATOR_NXR, values);
    }

    public static Condition adj(String column, int... values) {
        return new RangedCondition(column, OPERATOR_ADJ, values);
    }

    public static Condition adj(String column, String... values) {
        return new RangedCondition(column, OPERATOR_ADJ, values);
    }

    public static Condition not(Condition condition) {
        // If is logical operator
        if (condition.operator.isEmpty()) {
            return new LogicalCondition(OPERATOR_NOT + "." + condition.column, condition.operator, condition.value);
        }
        return new NestedCondition(condition.column, OPERATOR_NOT + "." + condition.operator, condition.value);
    }

    public static Condition or(Condition... conditions) {
        String value = LogicalCondition.buildQueryValue(conditions);
        return new LogicalCondition(OPERATOR_OR, "", value);
    }

    public static Condition and(Condition... conditions) {
        String value = LogicalCondition.buildQueryValue(conditions);
        return new LogicalCondition(OPERATOR_AND, "", value);
    }

    public static SelectCondition select(String... values) {
        return new SelectCondition("select", "", values);
    }

    public String getQueryParam() {
        return column;
    }

    public String getQueryValue() {
        return operator + "." + value;
    }

    static class NestedCondition extends Condition {
        public NestedCondition(String column, String operator, String value) {
            super(column, operator, value);
        }

        protected String escapeValue(String value) {
            return value;
        }
    }

    static class GroupCondition extends Condition {
        private static final String START_TOKEN = "{";
        private static final String END_TOKEN = "}";

        public GroupCondition(String column, String operator, String... values) {
            super(column, operator, buildValues(START_TOKEN, END_TOKEN, values));
        }

        public GroupCondition(String column, String operator, int... values) {
            super(column, operator, buildValues(START_TOKEN, END_TOKEN, values));
        }

        protected GroupCondition(String startToken, String endToken, String column, String operator, String... values) {
            super(column, operator, buildValues(startToken, endToken, values));
        }

        protected GroupCondition(String startToken, String endToken, String column, String operator, int... values) {
            super(column, operator, buildValues(startToken, endToken, values));
        }

        protected String escapeValue(String value) {
            return value;
        }

        private static String buildValues(String startToken, String endToken, int[] values) {
            StringBuilder builder = new StringBuilder();
            builder.append(startToken);

            for (int i = 0; i < values.length; i++) {
                appendValue(builder, values[i]);
                if (i < values.length - 1) {
                    builder.append(",");
                }
            }

            builder.append(endToken);
            return builder.toString();
        }

        private static String buildValues(String startToken, String endToken, String[] values) {
            StringBuilder builder = new StringBuilder();
            builder.append(startToken);

            for (int i = 0; i < values.length; i++) {
                appendValue(builder, values[i]);
                if (i < values.length - 1) {
                    builder.append(",");
                }
            }

            builder.append(endToken);
            return builder.toString();
        }

        private static void appendValue(StringBuilder builder, int value) {
            builder.append(value);
        }

        static void appendValue(StringBuilder builder, String value) {
            if (value.contains(",") || value.contains(" ")) {
                builder.append("\"");
                builder.append(value);
                builder.append("\"");
            } else {
                builder.append(value);
            }
        }
    }

    public static class SelectCondition extends GroupCondition {

        private String[] values;

        public SelectCondition(String column, String operator, String... values) {
            super("", "", column, operator, values);
            this.values = values;
        }

        public SelectCondition as(String... names) {
            // Reprocess value
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < values.length; i++) {
                appendValue(builder, names[i]);
                builder.append(":");
                appendValue(builder, values[i]);
                if (i < values.length - 1) {
                    builder.append(",");
                }
            }
            value = builder.toString();

            return this;
        }

        @Override
        public String getQueryValue() {
            return value;
        }
    }

    static class PeriodCondition extends GroupCondition {
        private static final String START_TOKEN = "[";
        private static final String END_TOKEN = "]";

        public PeriodCondition(String column, String operator, String... values) {
            super(START_TOKEN, END_TOKEN, column, operator, values);
        }

        public PeriodCondition(String column, String operator, int... values) {
            super(START_TOKEN, END_TOKEN, column, operator, values);
        }
    }

    static class RangedCondition extends GroupCondition {
        private static final String START_TOKEN = "(";
        private static final String END_TOKEN = ")";

        public RangedCondition(String column, String operator, String... values) {
            super(START_TOKEN, END_TOKEN, column, operator, values);
        }

        public RangedCondition(String column, String operator, int... values) {
            super(START_TOKEN, END_TOKEN, column, operator, values);
        }
    }

    static class LogicalCondition extends NestedCondition {
        public LogicalCondition(String column, String operator, String value) {
            super(column, operator, value);
        }

        private static String buildQueryValue(Condition[] conditions) {
            StringBuilder builder = new StringBuilder();
            builder.append("(");

            for (int i = 0; i < conditions.length; i++) {
                builder.append(conditions[i].column);
                if (!conditions[i].operator.isEmpty()) {
                    builder.append(".");
                }
                builder.append(conditions[i].getQueryValue());
                if (i < conditions.length - 1) {
                    builder.append(",");
                }
            }

            builder.append(")");
            return builder.toString();
        }

        public String getQueryValue() {
            return operator + value;
        }
    }

}
