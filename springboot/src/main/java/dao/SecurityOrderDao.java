package dao;

import model.SecurityOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityOrderDao extends JpaRepository<SecurityOrder, String> {
}