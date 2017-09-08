package com.xyr.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xyr.domain.ProductEarningRate;
import com.xyr.service.ProductRateService;
import com.xyr.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by xyr on 2017/9/8.
 */
@Controller
@RequestMapping("/productRate")
public class ProductRateController {

    @Autowired
    private ProductRateService productRateService;

    /**
     * 获取产品利率信息
     *
     * @param pid
     * @return
     */
    @RequestMapping("/yearInterest")
    @ResponseBody
    public ServerResponse yearInterest(String pid) {
        List<ProductEarningRate> productEarningRates = productRateService.findByProId(pid);

        List<Map<String, Object>> listMap = Lists.newArrayList();
        for (ProductEarningRate rate : productEarningRates) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("endMonth", rate.getMonth());
            map.put("incomeRate", rate.getIncomeRate());
            listMap.add(map);
        }

        return ServerResponse.createBySuccess(listMap);
    }

}
