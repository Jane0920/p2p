package com.xyr.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by xyr on 2017/8/25.
 */
@Entity // 当前类是一个实体
@Table(name = "t_admin") // 与数据库中哪一个表映射
public class Admin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "t_id", nullable = false)
    private int id;
    @Column(name = "t_username")
    private String username;
    @Column(name = "t_password")
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
