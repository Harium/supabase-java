package com.harium.supabase.storage;

import com.harium.dotenv.Env;
import com.harium.supabase.SupabaseClient;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class StorageTest {

    private String supabaseUrl;
    private String supabaseKey;
    private SupabaseClient supabaseClient;

    @Before
    public void setUp() {
        supabaseUrl = Env.get("SUPABASE_URL");
        supabaseKey = Env.get("SUPABASE_KEY");

        supabaseClient = new SupabaseClient(supabaseUrl, supabaseKey);
    }

    @Test
    @Ignore
    public void testListBucket() throws IOException {
        List<Bucket> bucketList = supabaseClient.storage().listBuckets();
        assertTrue(bucketList.isEmpty());
    }

}
