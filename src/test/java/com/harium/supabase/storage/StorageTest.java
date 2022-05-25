package com.harium.supabase.storage;

import com.harium.dotenv.Env;
import com.harium.supabase.SupabaseClient;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

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

    @Test
    @Ignore
    public void testGetBucket() throws IOException {
        Bucket bucket = supabaseClient.storage().getBucket("hello");
        assertTrue(bucket.isPublic);
    }

    @Test
    @Ignore
    public void testCreateBucket() throws IOException {
        Bucket bucket = supabaseClient.storage().createBucket("hello", true);
        assertTrue(bucket.isPublic);
    }

    @Test
    @Ignore
    public void testEmptyBucket() throws IOException {
        MessageResponse response = supabaseClient.storage().emptyBucket("hello");
        assertEquals("Successfully emptied", response.message);
    }

    @Test
    @Ignore
    public void testDeleteBucket() throws IOException {
        MessageResponse response = supabaseClient.storage().deleteBucket("hello");
        assertEquals("Successfully deleted", response.message);
    }

    @Test
    //@Ignore
    public void testListFiles() throws IOException {
        List<FileObject> response = supabaseClient.storage().listFiles("example-bucket");
        assertFalse(response.isEmpty());
    }

}
