package com.xyr.dao;

import com.xyr.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by xyr on 2017/8/29.
 */
public interface ProductDAO extends JpaRepository<Product, Long> {

    @Modifying
    @Query("delete from Product pro where pro.proId = ?1")
    void productDel(long proId);

}
