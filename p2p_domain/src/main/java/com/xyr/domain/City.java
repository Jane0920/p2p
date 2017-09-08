package com.xyr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by xyr on 2017/9/8.
 */
//@Entity 指明了Spring注入的Bean实体
//@Table 将该bean映射到数据库表中
@Entity
@Table(name = "T_CITY")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "T_ID")
    private Integer cityId;//城市表 主键

    @Column(name = "T_CITY_AREA_NUM")
    private String cityAreaNum;//城市编号

    @Column(name = "T_CITY_NAME")
    private String cityName;//城市名称

    @Column(name = "T_CITY_LEVEL")
    @JsonIgnore
    private int cityLevel;//城市级别 (0：省 ；1：市；2：县)

    @Column(name = "T_PARENT_CITY_NUM")
    @JsonIgnore
    private String parentCityAreaNum;//父级城市编号

    @Transient
    @JsonIgnore
    private City parent;

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityAreaNum() {
        return cityAreaNum;
    }

    public void setCityAreaNum(String cityAreaNum) {
        this.cityAreaNum = cityAreaNum;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityLevel() {
        return cityLevel;
    }

    public void setCityLevel(int cityLevel) {
        this.cityLevel = cityLevel;
    }

    public String getParentCityAreaNum() {
        return parentCityAreaNum;
    }

    public void setParentCityAreaNum(String parentCityAreaNum) {
        this.parentCityAreaNum = parentCityAreaNum;
    }

    public City getParent() {
        return parent;
    }

    public void setParent(City parent) {
        this.parent = parent;
    }

}
