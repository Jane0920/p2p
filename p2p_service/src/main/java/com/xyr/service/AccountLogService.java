package com.xyr.service;

import com.xyr.domain.WaitMatchMoneyModel;

import java.util.List;

/**
 * Created by xyr on 2017/9/18.
 */
public interface AccountLogService {

    /**
     * 查询所有待匹配资金队列
     *
     * @return
     */
    List<WaitMatchMoneyModel> selectWaitMatch();

    /**
     * 查询所有待匹配资金队列统计信息
     *
     * @return
     */
    WaitMatchMoneyModel selectWaitMatchCount();
}
