package com.bbs.util.page;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lihongde on 2016/7/6 19:36
 */
public class PageModel {
    private long recordCount;
    private long pageCount;
    private long pageNum;
    private long pageSize;
    private Map<String, String> pageParams = new HashMap<String, String>();
    private String requestUrl;

    public PageModel(long recordCount, long pageCount, long pageNum, long pageSize) {
        this.recordCount = recordCount;
        this.pageCount = pageCount;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.requestUrl = "";
    }

    public PageModel() {
    }

    public PageModel(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public void set(Page result) {
        this.pageNum = result.getPage();
        this.pageSize = result.getPageSize();
        this.recordCount = result.getTotal();
        this.pageCount = result.getTotalPage();
    }

    public long getRecordCount() {
        return this.recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public long getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public long getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public long getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public Map<String, String> getPageParams() {
        return this.pageParams;
    }

    public void setPageParams(Map<String, String> pageParams) {
        this.pageParams = pageParams;
    }

    public String getRequestUrl() {
        return this.requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
}
