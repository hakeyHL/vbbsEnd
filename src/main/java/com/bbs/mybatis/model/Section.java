package com.bbs.mybatis.model;

import java.io.Serializable;

/**
 * 版块表
 */
public class Section implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4232866490561026673L;

	private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}