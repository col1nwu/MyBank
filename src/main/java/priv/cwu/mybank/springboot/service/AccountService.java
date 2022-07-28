package priv.cwu.mybank.springboot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.cwu.mybank.springboot.constant.ResponseCode;
import priv.cwu.mybank.springboot.dao.entity.Account;
import priv.cwu.mybank.springboot.dao.entity.User;
import priv.cwu.mybank.springboot.dao.repository.AccountRepository;
import priv.cwu.mybank.springboot.dao.repository.UserRepository;
import priv.cwu.mybank.springboot.utils.CommonUtils;
import priv.cwu.mybank.springboot.utils.HttpResponse;
import priv.cwu.mybank.springboot.utils.ResponseUtils;

@Slf4j
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;


    @Transactional(rollbackFor = Exception.class)
    public HttpResponse openAccount(String userId) {
        User user = userRepository.findByUserId(userId);
        int accountNum = accountRepository.countByAccountHolderIdAndIsDeleted(user.getId(), false);
        if (accountNum >= 5) {
            return ResponseUtils.newResult(ResponseCode.TOO_MANY_ACCOUNTS);
        }

        String accountId;
        do {
            accountId = "AC_" + CommonUtils.generateUUID();
        } while (accountRepository.existsByAccountIdAndIsDeleted(accountId, false));

        Account account = new Account(accountId, user.getId());
        account = accountRepository.save(account);

        return ResponseUtils.newSuccessResult(account);
    }


    @Transactional(rollbackFor = Exception.class)
    public HttpResponse plusMoney(String accountId, Double amount) {
        Account account = accountRepository.findByAccountIdAndIsDeleted(accountId, false);
        if (account == null) {
            return ResponseUtils.newResult(ResponseCode.INVALID_ACCOUNT);
        } else if (account.getIsLocked()) {
            return ResponseUtils.newResult(ResponseCode.ACCOUNT_LOCKED);
        }

        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);

        account = accountRepository.save(account);
        return ResponseUtils.newSuccessResult(account);
    }


    @Transactional(rollbackFor = Exception.class)
    public HttpResponse minusMoney(String accountId, Double amount) {
        Account account = accountRepository.findByAccountIdAndIsDeleted(accountId, false);
        if (account == null) {
            return ResponseUtils.newResult(ResponseCode.INVALID_ACCOUNT);
        } else if (account.getIsLocked()) {
            return ResponseUtils.newResult(ResponseCode.ACCOUNT_LOCKED);
        } else if (account.getBalance() < amount) {
            return ResponseUtils.newResult(ResponseCode.NOT_ENOUGH_MONEY);
        }

        double newBalance = account.getBalance() - amount;
        account.setBalance(newBalance);

        account = accountRepository.save(account);
        return ResponseUtils.newSuccessResult(account);
    }


    @Transactional(rollbackFor = Exception.class)
    public HttpResponse transfer(String source, String destination, Double amount) {
        HttpResponse first = minusMoney(source, amount);
        if (first.getCode() == HttpStatus.OK.value()) {
            HttpResponse second = plusMoney(destination, amount);
            if (second.getCode() == HttpStatus.OK.value()) {
                return ResponseUtils.newSuccessResult();
            } else {
                return second;
            }
        }
        return first;
    }


    @Transactional(rollbackFor = Exception.class)
    public HttpResponse changeAccountStatus(String accountId, Boolean isLocked) {
        Account account = accountRepository.findByAccountIdAndIsDeleted(accountId, false);
        if (account == null)  {
            return ResponseUtils.newResult(ResponseCode.INVALID_ACCOUNT);
        }

        account.setIsLocked(isLocked);
        accountRepository.save(account);
        return ResponseUtils.newSuccessResult();
    }


    public HttpResponse lockAccount(String accountId) {
        return changeAccountStatus(accountId, true);
    }


    public HttpResponse unlockAccount(String accountId) {
        return changeAccountStatus(accountId, false);
    }
}
