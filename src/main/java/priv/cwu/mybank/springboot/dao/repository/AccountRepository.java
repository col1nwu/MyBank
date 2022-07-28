package priv.cwu.mybank.springboot.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import priv.cwu.mybank.springboot.dao.entity.Account;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Integer countByAccountHolderIdAndIsDeleted(Long accountHolderId, Boolean isDeleted);

    Boolean existsByAccountIdAndIsDeleted(String accountId, Boolean isDeleted);

    Account findByAccountIdAndIsDeleted(String accountId, Boolean isDeleted);

    List<Account> findAllByAccountHolderIdAndIsDeleted(Long accountHolderId, Boolean isDeleted);

}
