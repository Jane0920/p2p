package com.xyr.controller;

import com.xyr.domain.Product;
import com.xyr.domain.ProductEarningRate;
import com.xyr.service.ProductService;
import com.xyr.utils.JsonUtils;
import com.xyr.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    @RequestMapping(value = "/findAllProduct")
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
    @RequestMapping(value = "/findProductById")
    @ResponseBody
    public ServerResponse findProductById(long proId) {
        return productService.findProductById(proId);
    }

    /**
     * 修改产品信息
     *
     * @param product         产品信息
     * @param proEarningRates 产品利率信息
     * @return
     */
    @RequestMapping(value = "/modifyProduct")
    @ResponseBody
    public ServerResponse modifyProduct(Product product, String proEarningRates) {
        List<ProductEarningRate> productEarningRates = null;
        if (proEarningRates != null && !"".equals(proEarningRates.trim()))
            productEarningRates = JsonUtils.jsonToList(proEarningRates, ProductEarningRate.class);
        product.setProEarningRate(productEarningRates);

        return productService.updateProduct(product);
    }

    /**
     * 获取产品月利率信息
     *
     * @param proId
     * @return
     */
    @RequestMapping(value = "/findRates")
    @ResponseBody
    public ServerResponse findRates(String proId) {
        return productService.findRates(proId);
    }

    /**
     * 新增产品
     *
     * @param product
     * @param proEarningRates
     * @return
     */
    @RequestMapping(value = "/addProduct")
    @ResponseBody
    public ServerResponse addProduct(Product product, String proEarningRates) {
        List<ProductEarningRate> productEarningRates = null;
        if (proEarningRates != null && !"".equals(proEarningRates.trim()))
            productEarningRates = JsonUtils.jsonToList(proEarningRates, ProductEarningRate.class);

        product.setProEarningRate(productEarningRates);
        return productService.addProduct(product);
    }

    /**
     * 删除产品
     *
     * @param proId 产品id
     * @return
     */
    @RequestMapping("/delProduct")
    @ResponseBody
    public ServerResponse productDel(String proId) {
        return productService.productDel(proId);
    }

}
