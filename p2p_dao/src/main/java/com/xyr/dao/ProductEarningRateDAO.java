package com.xyr.dao;

import com.xyr.domain.ProductEarningRate;
import org.omg.CORBA.PERSIST_STORE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by xyr on 2017/8/29.
 */
public interface ProductEarningRateDAO extends JpaRepository<ProductEarningRate, Integer> {

    List<ProductEarningRate> findByProductIdOrderByMonth(int productId);

    List<ProductEarningRate> findByProductId(int productId);

    /**
     * 使用 @Modifying 进行修饰. 以通知 SpringData， 这是一个 UPDATE 或 DELETE 操作
     * UPDATE 或 DELETE 操作需要使用事务，此时需要定义 Service 层，在 Service 层的方法上添加事务操作
     *
     * @param productId
     */
    @Modifying
    @Query("delete from ProductEarningRate pro where pro.productId = ?1")
    void deleteByProductId(int productId);

}
