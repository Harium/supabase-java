# supabase-java
A Java client for [Supabase](https://supabase.com/).

### Features

Currently, this library supports database operations only

### Usage
```java
// Starting the client
SupabaseRestClient supabase = new SupabaseRestClient(YOUR_SUPABASE_URL, YOUR_SUPABASE_KEY);
// Inserting a new user to the table users
supabase.databse().insert("users", Insert.row().column("email", "user@email.com")
                                     .column("username", "user123"));

// Using storage (uploading a file)
supabase.storage().upload("mybucket/test.txt", new byte[]{1,2,3,4,5});
```

#### Not implemented yet
- Authentication
- Storage
- ...