package com.xyr.controller;

import com.xyr.domain.Product;
import com.xyr.service.ProductService;
import com.xyr.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xyr on 2017/8/29.
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 产品列表显示
     *
     * @return
     */
    @RequestMapping(value = "/findAllProduct", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse findAllProduct() {
        return productService.findAllProduct();
    }

    /**
     * 根据产品id查看产品详情
     *
     * @param proId 产品id
     * @return
     */
    @RequestMapping(value = "/findProductById", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse findProductById(long proId) {
        return productService.findProductById(proId);
    }

    @RequestMapping(value = "/modifyProduct", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse modifyProduct(Product product) {
        System.out.println(product.getModifyTime());
        return ServerResponse.createBySuccess();
    }

    /**
     * 获取产品月利率信息
     *
     * @param proId
     * @return
     */
    @RequestMapping(value = "/findRates", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse findRates(String proId) {
        return productService.findRates(proId);
    }

}
