package com.xyr.dao;

import com.xyr.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by xyr on 2017/9/1.
 */
public interface UserAccountDAO extends JpaRepository<UserAccount, Integer> {

    UserAccount findByUserId(int userId);

    @Modifying
    @Query("update UserAccount set total = ?1, balance = ?2 where userId = ?3")
    void updateBalanceAndTotal(double total, double balance, int userId);

    @Modifying
    @Query("update UserAccount set balance = ?1, inverstmentW = ?2, interestTotal = ?3, recyclingInterest = ?4, inverstmentA = ?5 where id = ?6")
    void updateNewInvestmentUserAccount(double balance, double inverstmentW, double interestTotal, double recyclingInterest, double inverstmentA, int userId);

}
