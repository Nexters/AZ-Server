package org.nexters.az.user.repository;

import org.nexters.az.common.repository.ExtendRepository;
import org.nexters.az.user.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ExtendRepository<User> {
}
