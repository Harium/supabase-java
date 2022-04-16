import com.harium.dotenv.Env;
import com.harium.postgrest.Pair;
import io.supabase.SupabaseRestClient;

import java.io.IOException;

public class Example {

    private static final String SUPABASE_URL = Env.get("SUPABASE_URL");
    private static final String SUPABASE_KEY = Env.get("SUPABASE_KEY");

    public static void main(String[] args) throws IOException {

        SupabaseRestClient supabase = new SupabaseRestClient(SUPABASE_URL, SUPABASE_KEY);
        supabase.insert("users", new Pair("email", "b@example.com"), new Pair("name", "Just b User"), new Pair("newsletter", true));
        //supabase.delete("users", Condition.eq("id", 1));

        System.out.println(supabase.findAll("users"));

        //https://postgrest.org/en/stable/api.html#insertions-updates

        //POST /table_name HTTP/1.1

        //{ "col1": "value1", "col2": "value2" }
    }


}
