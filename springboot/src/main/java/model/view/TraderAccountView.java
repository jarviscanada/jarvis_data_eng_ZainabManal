package model.view;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import model.Account;
import model.Trader;
import org.springframework.data.annotation.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TraderAccountView {
        @Id
        private int traderId;
        private String firstName;
        private String lastName;
        private double amount;

        public int getTraderId()
        {
            return traderId;
        }

        public void setTraderId(int traderId)
        {
            this.traderId = traderId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
    }


