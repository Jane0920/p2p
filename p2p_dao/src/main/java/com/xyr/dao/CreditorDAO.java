package com.xyr.dao;

import com.xyr.domain.CreditorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by xyr on 2017/9/13.
 */
public interface CreditorDAO extends JpaRepository<CreditorModel, Integer> {

    @Query("select cm from CreditorModel cm where cm.debtStatus = 11302 and cm.matchedStatus in (11401, 11403)")
    List<CreditorModel> findAllMatch();

    @Modifying
    @Query("update CreditorModel set matchedStatus = 11402 where debtStatus = 11302")
    void updateAllMatch();

    @Modifying
    @Query("update CreditorModel set matchedStatus = 11402 where id = ?1")
    void updateStatus(int id);

}
