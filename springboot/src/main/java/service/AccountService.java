package service;

import dao.AccountJpaRepository;
import model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    private AccountJpaRepository accountRepo;

    @Autowired
    public AccountService(AccountJpaRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Transactional
    public void deleteAccountByTraderId(Integer traderId) {
        Account account = accountRepo.getAccountByTraderId(traderId);
        if (account.getAmount() != 0) {
            throw new IllegalArgumentException("Balance not 0");
        }
        accountRepo.deleteById(account.getId());
    }

}