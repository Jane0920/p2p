package com.xyr.dao;

import com.xyr.domain.CreditorModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xyr on 2017/9/13.
 */
public interface CreditorDAO extends JpaRepository<CreditorModel, Integer> {
}
