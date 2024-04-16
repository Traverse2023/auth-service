package com.traverse.authservice.auth;

import com.traverse.authservice.models.AppUserDetails;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface AppUserRepository extends Neo4jRepository<AppUserDetails, String> {
    @Query("MATCH (n:User {username: $email}) RETURN n{.*, id: elementId(n)}")
    AppUserDetails findByEmailWithId(String email);

}
