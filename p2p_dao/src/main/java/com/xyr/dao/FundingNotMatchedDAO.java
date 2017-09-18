package com.xyr.dao;

import com.xyr.domain.FundingNotMatchedModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by xyr on 2017/9/12.
 */
public interface FundingNotMatchedDAO extends JpaRepository<FundingNotMatchedModel, Integer> {

    @Query("select fm from FundingNotMatchedModel fm where fm.fIsLocked = 10901")
    List<FundingNotMatchedModel> findByMatch();

}
