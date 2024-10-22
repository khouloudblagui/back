package org.example.authentification.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token,Long> {
    @Query(value = """
      select t from Token t inner join User u\s
      on t.user.user_ky = u.user_ky\s
      where u.user_ky = :user_ky and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByUser(Integer user_ky);

    Optional<Token> findByToken(String token);
}
