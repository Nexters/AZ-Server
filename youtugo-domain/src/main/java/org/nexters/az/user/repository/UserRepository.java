package org.nexters.az.user.repository;

import org.nexters.az.common.repository.ExtendRepository;
import org.nexters.az.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends ExtendRepository<User> {
    Optional<User> findByIdentification(String identification);

    boolean existsAllByNickname(String nickname);

    boolean existsAllByIdentification(String identification);
}