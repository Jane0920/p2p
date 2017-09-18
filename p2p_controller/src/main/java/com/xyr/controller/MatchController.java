package com.xyr.controller;

import com.xyr.service.MatchService;
import com.xyr.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xyr on 2017/9/18.
 */
@Controller
@RequestMapping("/match")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @RequestMapping("/startMatchByManually")
    @ResponseBody
    public ServerResponse startMatchByManually() {
        matchService.startMatch();
        return ServerResponse.createBySuccess();
    }

}
