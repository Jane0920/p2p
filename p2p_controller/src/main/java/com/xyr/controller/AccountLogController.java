package com.xyr.controller;

import com.google.common.collect.Maps;
import com.xyr.domain.WaitMatchMoneyModel;
import com.xyr.service.AccountLogService;
import com.xyr.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by xyr on 2017/9/18.
 */
@Controller
@RequestMapping("/accountLog")
public class AccountLogController {

    @Autowired
    private AccountLogService accountLogService;

    /**
     * 查询待匹配资金队列
     *
     * @return
     */
    @RequestMapping("/selectWaitMoney")
    @ResponseBody
    public ServerResponse selectWaitMoney(String page, String userName, String pSerialNo, String endDate,
                                          String productName, String investType) {
        // 3.调用service完成操作
        // 3.1 查询所有待匹配资金队列
        List<WaitMatchMoneyModel> waitMatchMoneys = accountLogService.selectWaitMatch();
        // 3.2查询待匹配资金队列统计信息
        WaitMatchMoneyModel waitMatchMoneyCount = accountLogService.selectWaitMatchCount();

        // 4.封装数据，并响应到浏览器端
        Map<String, Object> returnMap = Maps.newHashMap();
        returnMap.put("listMatch", waitMatchMoneys);
        returnMap.put("waitMatchCount", waitMatchMoneyCount);
        return ServerResponse.createBySuccess(returnMap);
    }

}
