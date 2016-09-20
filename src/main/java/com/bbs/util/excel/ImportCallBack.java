package com.bbs.util.excel;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 导入回调接口 <br />
 *
 * @author Guoyan
 */
@Transactional
public interface ImportCallBack {

	/**
	 * 导入之前需要进行的数据库操作
	 * 
	 * @param sqlSessionTemplate
	 *            {@link org.mybatis.spring.SqlSessionTemplate}
	 * @param data
	 *            原始Excel中的数据，按行进行分割
     * @return
	 */
	void preOperation(SqlSessionTemplate sqlSessionTemplate, List<Object[]> data);

	/**
	 * 导入之后需要进行的数据库操作
	 * 
	 * @param sqlSessionTemplate
	 *            {@link org.mybatis.spring.SqlSessionTemplate}
	 * @param data
	 *            进行转换后的数据
	 */
	void postOperation(SqlSessionTemplate sqlSessionTemplate, List<Object[]> data);
}
