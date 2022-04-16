package io.supabase;

public class SupabaseClient {

    private SupabaseRestClient restClient;

    private SupabaseAuthClient authClient;

    public SupabaseClient(String supabaseUrl, String supabaseKey) {
        restClient = new SupabaseRestClient(supabaseUrl, supabaseKey);
        authClient = new SupabaseAuthClient(supabaseUrl, supabaseKey);
    }

}
