package com.xyr.dao;

import com.xyr.domain.AccountLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by xyr on 2017/9/12.
 */
public interface AccountLogDAO extends JpaRepository<AccountLog, Integer> {

    @Query(value = "select tpa.pId as id, tpa.pUid as userId, tpa.pStatus as status, tpa.pCurrentMonth as currentMonth" +
            ", tpa.pDeadline as deadline, tpa.pSerialNo as pSerialNo, tm.fNotMatchedMoney as amountWait, " +
            "tm.fFoundingType as investType, tm.fId as investRecordId, tm.fInvestRecordId as recordedId," +
            " tm.fCreateDate as confirmDate, tm.fFoundingWeight as fundWeight, tu.username as userName," +
            " tp.productName as productName from FundingNotMatchedModel tm, ProductAccount tpa, User tu, Product tp" +
            " where tm.fInvestRecordId = tpa.pId and tu.id = tpa.pUid and tpa.pProductId = tp.proId and  tm.fIsLocked=10901 and tm.fFoundingType in (124,125,126,127) ")
    List<Object[]> selectWaitMatch();

    @Query("select  count(fnmm.fId),sum(fnmm.fNotMatchedMoney) " +
            "from     FundingNotMatchedModel fnmm,    ProductAccount pa,User um, Product p " +
            "where fnmm.fInvestRecordId=pa.pId and pa.pUid=um.id   and p.proId=pa.pProductId   and fnmm.fIsLocked=10901    and fnmm.fFoundingType in (124,125,126,127)")
    List<Object[]> selectWatchMatchCount();

}
