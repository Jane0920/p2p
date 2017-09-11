package com.xyr.dao;

import com.xyr.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by xyr on 2017/9/8.
 */
public interface CityDAO extends JpaRepository<City, Integer> {

    List<City> findByParentCityAreaNumIsNull();

    List<City> findByParentCityAreaNum(String parentCityAreaNum);

}
