package com.harium.supabase.storage;

import com.harium.dotenv.Env;
import com.harium.supabase.SupabaseClient;
import com.harium.supabase.common.MessageResponse;
import com.harium.supabase.storage.payload.UploadResponse;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class StorageTest {

    private String supabaseUrl;
    private String supabaseKey;
    private SupabaseClient supabase;

    @Before
    public void setUp() {
        supabaseUrl = Env.get("SUPABASE_URL");
        supabaseKey = Env.get("SUPABASE_KEY");

        supabase = new SupabaseClient(supabaseUrl, supabaseKey);
    }

    @Test
    @Ignore
    public void testListBucket() throws IOException {
        List<Bucket> bucketList = supabase.storage().listBuckets();
        assertTrue(bucketList.isEmpty());
    }

    @Test
    @Ignore
    public void testGetBucket() throws IOException {
        Bucket bucket = supabase.storage().getBucket("hello");
        assertTrue(bucket.isPublic);
    }

    @Test
    @Ignore
    public void testCreateBucket() throws IOException {
        Bucket bucket = supabase.storage().createBucket("hello", true);
        assertTrue(bucket.isPublic);
    }

    @Test
    @Ignore
    public void testEmptyBucket() throws IOException {
        MessageResponse response = supabase.storage().emptyBucket("hello");
        assertEquals("Successfully emptied", response.message);
    }

    @Test
    @Ignore
    public void testDeleteBucket() throws IOException {
        MessageResponse response = supabase.storage().deleteBucket("hello");
        assertEquals("Successfully deleted", response.message);
    }

    @Test
    @Ignore
    public void testListFiles() throws IOException {
        List<FileObject> response = supabase.storage().listFiles("example-bucket");
        assertFalse(response.isEmpty());
    }

    @Test
    @Ignore
    public void testRemoveFile() throws IOException {
        List<FileObject> response = supabase.storage().remove("bucket", "test.txt", "test1.txt");
        assertFalse(response.isEmpty());
    }

    @Test
    @Ignore
    public void testCreateFile() throws IOException {
        UploadResponse response = supabase.storage().upload("bucket/test.txt", new byte[]{1,2,3,4,5});
        assertNotNull(response.key);
    }

    @Test
    @Ignore
    public void testDownloadFile() throws IOException {
        byte[] response = supabase.storage().download("bucket", "test.txt");
        assertNotNull(response);
    }

}
