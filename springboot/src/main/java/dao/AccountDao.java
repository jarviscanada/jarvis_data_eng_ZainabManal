package dao;

import model.Account;
import model.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountDao extends JpaRepository<Account, Integer>
{
    Optional<Account> findByTraderID(Integer traderId);
}