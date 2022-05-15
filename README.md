# supabase-java
A Java client for [Supabase](https://supabase.com/).

### Features

Currently, this library supports database operations only

### Usage
```java
// Starting the client
SupabaseRestClient supabase = new SupabaseRestClient(YOUR_SUPABASE_URL, YOUR_SUPABASE_KEY);
// Inserting a new user to the table users
supabase.insert("users", Insert.row().column("email", "user@email.com")
                                     .column("username", "user123"));
```

#### Not implemented yet
- Authentication
- Storage
- ...