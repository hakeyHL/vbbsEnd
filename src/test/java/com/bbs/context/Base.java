package com.bbs.context;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/** 
 * @author lihongde
 */
@ContextConfiguration(locations = {
		"classpath:applicationContext.xml",
		"classpath:spring-mvc.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class Base extends Assert{
	protected Logger logger = Logger.getLogger(getClass());
	
	@Resource
	protected SqlSessionTemplate sqlSessionTemplate;

    @Resource
    protected JdbcTemplate jdbcTemplate;
	
}
