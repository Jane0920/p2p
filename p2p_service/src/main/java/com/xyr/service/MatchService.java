package com.xyr.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xyr on 2017/9/18.
 */
public interface MatchService {

    @Transactional
    void startMatch();

}
