package priv.cwu.mybank.springboot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.cwu.mybank.springboot.constant.ResponseCode;
import priv.cwu.mybank.springboot.dao.entity.User;
import priv.cwu.mybank.springboot.dao.repository.UserRepository;
import priv.cwu.mybank.springboot.utils.CommonUtils;
import priv.cwu.mybank.springboot.utils.HttpResponse;
import priv.cwu.mybank.springboot.utils.ResponseUtils;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public HttpResponse login(String email, String password) {
        User user = userRepository.findByEmailAndIsDeleted(email, false);
        if (user == null) {
            return ResponseUtils.newResult(ResponseCode.INVALID_USER);
        }
        String correctPassword = user.getPassword();
        if (correctPassword.equals(password)) {
            return ResponseUtils.newSuccessResult(user);
        } else {
            return ResponseUtils.newResult(ResponseCode.INVALID_PASSWORD);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public HttpResponse insertUser(String username, String password, String email) {
        if (userRepository.existsByEmailAndIsDeleted(email, false)) {
            return ResponseUtils.newResult(ResponseCode.DUPLICATE_EMAIL);
        }

        String userId;
        do {
            userId = "USER_" + CommonUtils.generateUUID();
        } while (userRepository.existsByUserIdAndIsDeleted(userId, false));

        User user = new User(userId, username, password, email);
        user = userRepository.save(user);

        return ResponseUtils.newSuccessResult(user);
    }


    @Transactional(rollbackFor = Exception.class)
    public HttpResponse deleteUser(String userId) {
        User user = userRepository.findByUserIdAndIsDeleted(userId, false);
        if (user == null) {
            return ResponseUtils.newResult(ResponseCode.INVALID_USER);
        }

        userRepository.deleteById(user.getId());
        return ResponseUtils.newSuccessResult();
    }


    @Transactional(rollbackFor = Exception.class)
    public HttpResponse changePassword(String userId, String password) {
        User user = userRepository.findByUserIdAndIsDeleted(userId, false);
        if (user == null) {
            return ResponseUtils.newResult(ResponseCode.INVALID_USER);
        }

        user.setPassword(password);
        return ResponseUtils.newSuccessResult();
    }

}
