package com.xyr.service.impl;

import com.xyr.dao.CreditorDAO;
import com.xyr.dao.FundingNotMatchedDAO;
import com.xyr.domain.CreditorModel;
import com.xyr.domain.FundingNotMatchedModel;
import com.xyr.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by xyr on 2017/9/18.
 */
@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private FundingNotMatchedDAO fundingNotMatchedDAO;
    @Autowired
    private CreditorDAO creditorDAO;


    @Override
    @Transactional
    public void startMatch() {
        // 1.获取所有待匹配资金 状态 10901 待匹配
        List<FundingNotMatchedModel> fnmm = fundingNotMatchedDAO.findByMatch();
        // 2.获取所有待匹配债权 已审核 11302 11401未匹配 11403 部分匹配
        List<CreditorModel> cm = creditorDAO.findAllMatch();

        match(fnmm, cm);
    }

    private void match(List<FundingNotMatchedModel> invests, List<CreditorModel> creditors) {
        // 1.获取待匹配资金总额
        BigDecimal investMoney = getInvest(invests);
        // 2.获取待匹配债权总额
        BigDecimal creditorMoney = getCreditor(creditors);

        LinkedList<FundingNotMatchedModel> fnms = new LinkedList<FundingNotMatchedModel>(invests);// 资金队列
        LinkedList<CreditorModel> cms = new LinkedList<CreditorModel>(creditors);// 债权队列
        //比较总金额大小，金额大的那方肯定在最后会在队列中有剩余
        if (investMoney.compareTo(creditorMoney) > 0) { //资金大于债权
            while (!cms.isEmpty()) { //只要债权队列不为空就一直循环
                FundingNotMatchedModel fnm = fnms.poll(); //从资金队列中获取一个元素
                CreditorModel cm = cms.poll(); //从债权队列中获取一个元素
                BigDecimal sub = (BigDecimal.valueOf(fnm.getfNotMatchedMoney()))
                        .subtract(BigDecimal.valueOf(cm.getAvailableMoney()));
                if (sub.intValue() > 0) { //债权完全被匹配，资金部分匹配 将未匹配完的资金重新放回队列
                    fnm.setfNotMatchedMoney(sub.abs().doubleValue());
                    fnms.offer(fnm);
                } else if (sub.intValue() < 0) { //资金完全被匹配，债权部分匹配 将未匹配完的债权重新放回队列
                    cm.setAvailableMoney(sub.abs().doubleValue());
                    cms.offer(cm);
                }
            }
            // 修改所有债权队列中债权的信息
            updateCreditorStatus(creditors);
            // 移除掉资金队列中的部分，invests中剩下的就是已经完全匹配了的
            invests.removeAll(fnms);
            updateInvestStatus(invests);
            // 修改剩余资金队列中资金信息
            updateNoMatchInvest(fnms);
        } else if (investMoney.compareTo(creditorMoney) < 0) { //资金小于债权
            while (!fnms.isEmpty()) { //只要资金队列不为空就一直循环
                FundingNotMatchedModel fnm = fnms.poll(); //从资金队列中获取一个元素
                CreditorModel cm = cms.poll(); //从债权队列中获取一个元素
                BigDecimal sub = (BigDecimal.valueOf(fnm.getfNotMatchedMoney()))
                        .subtract(BigDecimal.valueOf(cm.getAvailableMoney()));
                if (sub.intValue() > 0) { //债权完全被匹配，资金部分匹配 将未匹配完的资金重新放回队列
                    fnm.setfNotMatchedMoney(sub.abs().doubleValue());
                    fnms.offer(fnm);
                } else if (sub.intValue() < 0) { //资金完全被匹配，债权部分匹配 将未匹配完的债权重新放回队列
                    cm.setAvailableMoney(sub.abs().doubleValue());
                    cms.offer(cm);
                }
            }
            // 修改所有资金队列中资金信息
            updateInvestStatus(invests);
            // 修改剩余债权队列中债权信息
            creditors.removeAll(cms);
            updateCreditorStatus(creditors);
            updateNotMatchCreditor(cms);
        } else { //资金等于债权
            // 修改所有资金队列中资金信息
            updateInvestStatus(invests);
            // 修改所有债权队列中债权的信息
            updateCreditorStatus(creditors);
        }

    }

    /**
     * 更新已完全匹配的债权
     *
     * @param cms
     */
    private void updateCreditorStatus(List<CreditorModel> cms) {
        for (CreditorModel cm: cms) {
            creditorDAO.updateStatus(11402, 0.0, cm.getId());
        }
    }

    /**
     * 更新已完全匹配的资金
     * @param fnms
     */
    private void updateInvestStatus(List<FundingNotMatchedModel> fnms) {
        for (FundingNotMatchedModel fnm: fnms) {
            fundingNotMatchedDAO.updateStatus(0.0, 10905, fnm.getfId());
        }
    }

    /**
     * 修改债权队列中信息
     *
     * @param cms
     */
    private void updateNotMatchCreditor(LinkedList<CreditorModel> cms) {
        //修改剩余数据
        for (CreditorModel cm : cms) {
            CreditorModel _cm = creditorDAO.getOne(cm.getId());
            if(_cm.getAvailableMoney()==cm.getAvailableMoney()) {
                //相等 表示未匹配
                creditorDAO.updateStatus(11401, cm.getAvailableMoney(), cm.getId());
            } else {
                //不等 表示部分匹配
                creditorDAO.updateStatus(11403, cm.getAvailableMoney(), cm.getId());
            }

        }

    }

    /**
     * 修改资金队列中的信息
     *
     * @param fnms
     * @return
     */
    private void updateNoMatchInvest(LinkedList<FundingNotMatchedModel> fnms) {
        for (FundingNotMatchedModel fnm : fnms) {
            FundingNotMatchedModel _fnm = fundingNotMatchedDAO.getOne(fnm.getfId());
            if (_fnm.getfNotMatchedMoney() == fnm.getfNotMatchedMoney()) {
                fundingNotMatchedDAO.updateStatus(fnm.getfNotMatchedMoney(), 10901, fnm.getfId());
            } else {
                fundingNotMatchedDAO.updateStatus(fnm.getfNotMatchedMoney(), 10905, fnm.getfId());
            }
        }
    }

    private BigDecimal getCreditor(List<CreditorModel> creditors) {
        BigDecimal cs = new BigDecimal(0);
        for (CreditorModel cm : creditors) {
            cs = cs.add(BigDecimal.valueOf(cm.getAvailableMoney()));
        }
        return cs;
    }

    private BigDecimal getInvest(List<FundingNotMatchedModel> invests) {
        BigDecimal cs = new BigDecimal(0);
        for (FundingNotMatchedModel cm : invests) {
            cs = cs.add(BigDecimal.valueOf(cm.getfNotMatchedMoney()));
        }
        return cs;
    }

}
