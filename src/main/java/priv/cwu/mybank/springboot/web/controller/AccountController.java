package priv.cwu.mybank.springboot.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import priv.cwu.mybank.springboot.annotation.CheckLogin;
import priv.cwu.mybank.springboot.annotation.CheckOwnership;
import priv.cwu.mybank.springboot.service.AccountService;
import priv.cwu.mybank.springboot.utils.CommonUtils;
import priv.cwu.mybank.springboot.utils.HttpResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Pattern;

@Slf4j
@Validated
@CheckLogin
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;


    @PostMapping("/account")
    public HttpResponse createAccount(HttpServletRequest request, HttpServletResponse response) {
        String userId = CommonUtils.getLoginUserId(request);
        return accountService.openAccount(userId);
    }


    @CheckOwnership
    @RequestMapping("/deposit")
    public HttpResponse deposit(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam("accountId") String accountId,
                                @Validated @Pattern(regexp = "^[0-9]+(.[0-9]{1,2})?$", message = "金钱格式错误") @RequestParam("money") String money
    ) {
        return accountService.plusMoney(accountId, Double.parseDouble(money));
    }

}
