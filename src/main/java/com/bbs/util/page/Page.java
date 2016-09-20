package com.bbs.util.page;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页容器
 * Created by lihongde on 2016/7/5 21:31
 */
public class Page<T> implements Serializable {
    private int total;
    private int page;
    private int pageSize;
    List<T> items;

    public Page() {
    }

    public Page(int currPage, int pageSize, int total, List<T> items) {
        this.page = currPage;
        this.pageSize = pageSize;
        this.total = total;
        this.items = items;
    }

    @SuppressWarnings("unchecked")
    public List<T> getItems() {
        return items != null ? items : Collections.EMPTY_LIST;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    /** 总记录数 */
    public int getTotal() {
        return total;
    }

    /** 总记录数 */
    public void setTotal(int total) {
        this.total = total;
    }

    /** 当前页码 */
    public int getPage() {
        return page;
    }

    /** 当前页码 */
    public void setPage(int currPage) {
        this.page = currPage;
    }

    /** 页大小，每页最大记录数 */
    public int getPageSize() {
        return pageSize;
    }

    /** 页大小，每页最大记录数 */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /** 总页数 */
    public int getTotalPage() {
        return (int) Math.ceil(total * 1.0 / pageSize);
    }
}
