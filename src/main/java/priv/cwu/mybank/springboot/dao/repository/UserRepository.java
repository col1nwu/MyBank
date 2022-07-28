package priv.cwu.mybank.springboot.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import priv.cwu.mybank.springboot.dao.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(String userId);

    Boolean existsByUserIdAndIsDeleted(String userId, Boolean isDeleted);

    User findByUserIdAndIsDeleted(String userId, Boolean isDeleted);

    User findByEmailAndIsDeleted(String email, Boolean isDeleted);

    Boolean existsByEmailAndIsDeleted(String email, Boolean isDeleted);

}
