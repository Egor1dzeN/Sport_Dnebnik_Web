package com.example.main.Repository;

import com.example.main.domain.Entity.VerifyAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerifyAccountRepository extends JpaRepository<VerifyAccount, Long> {
    @Query("select a.user.id from VerifyAccount as a where a.access_token=:token")
    Optional<Long> findUserIdByAccessToken(@Param("token") String token);
}
