package com.xyr.service;

import com.xyr.domain.City;

import java.util.List;

/**
 * Created by xyr on 2017/9/8.
 */
public interface CityService {

    /**
     * 查询省
     *
     * @return
     */
    List<City> findProvince();

    /**
     * 查找市/区
     *
     * @return
     * @param parentCityAreaNum
     */
    List<City> findCity(String parentCityAreaNum);

}
