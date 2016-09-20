package com.bbs.util.excel;

import com.bbs.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by lihongde
 * excel导入入口
 */
@Component
public class ExcelImporter {

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Resource
	private SqlSessionTemplate sqlSessionTemplate;

	private ImportConfig importConfig;

	private ImportConfig getImportConfig() {
		return importConfig;
	}

	public ExcelImporter setImportConfig(ImportConfig importConfig) {
		this.importConfig = importConfig;
		return this;
	}


	public Map<String, Object> importExcelFile(MultipartFile multipartFile, Integer voteId) throws Exception {
        Map<String, Object> importMap = new HashMap<String, Object>();

		ImportConfig importConfig = this.getImportConfig();
		if (importConfig == null) throw new RuntimeException("无参数配置，无法执行导入操作");
		// 根据文件后缀名创建不同版本的excel
		Workbook xwb = multipartFile.getOriginalFilename().contains("xlsx") ? new XSSFWorkbook(multipartFile.getInputStream()) : new HSSFWorkbook(multipartFile.getInputStream());
		// 进行excel模版校验
		String error = importConfig.validation(xwb);
		if (null != error && !"".equals(error)) throw new RuntimeException(error);
		// 获取表格中的数据，按行形成List<Object[]>
		List<Object[]> preExecution = new LinkedList<Object[]>();
		Sheet sheet = xwb.getSheetAt(0);
        //处理图片


		//用第2行的列数当做总的数据列数
		int cells = sheet.getRow(0).getLastCellNum();
		for (int i = 1, length = sheet.getLastRowNum(); i <= length; i++) {
			Row row = sheet.getRow(i);
			Object[] tempData = new Object[cells+1];
			//判断是否为空，如果为空则不处理
			if (row != null && row.getCell(0) != null && StringUtils.isNotEmpty(getCellFormatValue(row.getCell(0)).trim())) {
				for (int j = 0; j < tempData.length; j++) {
                    if(j == 0){
                        tempData[0] = voteId;
                    }else{
                        tempData[j] = getCellFormatValue(row.getCell(j-1));
                    }
				}
				preExecution.add(tempData);
			}
			else{
				break;
			}
		}

        importMap.put("totalRows", preExecution.size());  //总有效行数

		// 导入之前的一些必要操作
		importConfig.getImportCallBack().preOperation(sqlSessionTemplate, preExecution);

		// 处理Excel中的数据
		final List<Object[]> batchArgs = importConfig.getImportData(xwb, preExecution);
		
		//判断结果是否包含错误，如果出错，抛出错误信息
		if(batchArgs.size()==1 && batchArgs.get(0)[0] == "error" ){
			throw new RuntimeException(batchArgs.get(0)[1].toString());
		}
		
		// 执行导入过程
		int[] success = jdbcTemplate.batchUpdate(importConfig.getImportSQL(), new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
				Object[] objects = batchArgs.get(i);
				for (int j = 0, length = objects.length; j < length; j++)
					preparedStatement.setObject(j + 1, objects[j]);
			}

			@Override
			public int getBatchSize() {
				return batchArgs.size();
			}
		});
		// 导入成功，执行导入之后的操作
		if (success.length > 0) {
			importConfig.getImportCallBack().postOperation(sqlSessionTemplate, batchArgs);
		}
        importMap.put("successRows", success.length);
        importMap.put("failRows", preExecution.size() - success.length);
        return importMap;
	}

	private String getCellFormatValue(Cell cell) {
		String cellvalue = "";
		if (cell != null) {
			switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_NUMERIC: {
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						Date date = cell.getDateCellValue();
						cellvalue = DateUtil.formatDate(date, "yyyy-MM-dd");
                    } else {
						cellvalue = String.valueOf(cell.getNumericCellValue());
					}
					break;
				}
				case HSSFCell.CELL_TYPE_FORMULA: {
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						Date date = cell.getDateCellValue();
						cellvalue = DateUtil.formatDate(date, "yyyy-MM-dd");
					} else {
						cellvalue = String.valueOf(cell.getNumericCellValue());
					}
					break;
				}
				case HSSFCell.CELL_TYPE_STRING:
					cellvalue = cell.getRichStringCellValue().getString();
					break;
				default:
					cellvalue = " ";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue.trim();
	}

}
