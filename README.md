# supabase-java
[![CircleCI](https://circleci.com/gh/Harium/supabase-java.svg?style=svg)](https://circleci.com/gh/Harium/supabase-java)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.harium.supabase/core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.harium.supabase/core/)

An unofficial Java client for [Supabase](https://supabase.com/).

### Features

Currently, this library has basic support to database operations and storage.


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
- ...