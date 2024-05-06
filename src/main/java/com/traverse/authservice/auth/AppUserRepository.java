package com.traverse.authservice.auth;

import com.traverse.authservice.models.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface AppUserRepository extends Neo4jRepository<User, String> {
    @Query("MATCH (n:User {username: $email}) RETURN n{.*, id: elementId(n)}")
    User findByEmailWithId(String email);

    // Create
    @Query("CREATE (n: User { username: :#{#user.username}, firstName: :#{#user.firstName}, lastName: :#{#user.lastName}, password: :#{#user.password}, pfpUrl: :#{#user.pfpUrl}}) " +
            "RETURN {id:elementId(n), username:n.username, firstName:n.firstName, lastName:n.lastName, pfpUrl:n.pfpURL}")
    User createUser(User user);



}
