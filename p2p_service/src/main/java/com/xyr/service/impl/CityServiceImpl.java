package com.xyr.service.impl;

import com.xyr.dao.CityDAO;
import com.xyr.domain.City;
import com.xyr.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xyr on 2017/9/8.
 */
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDAO cityDAO;

    @Override
    public List<City> findProvince() {
        return cityDAO.findByParentCityAreaNumIsNull();
    }

    @Override
    public List<City> findCity(String parentCityAreaNum) {
        return cityDAO.findByParentCityAreaNum(parentCityAreaNum);
    }
}
