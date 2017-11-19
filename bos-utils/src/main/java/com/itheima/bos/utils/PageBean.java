package com.itheima.bos.utils;

import java.util.ArrayList;
import java.util.List;

public class PageBean<T> {
	//当前
	// 显示当前页数
	private int currentPage;
	// 显示当前页数显示的条数
	private int pageSize;
	  // 总页数
    private int totalPage;
    // 总条数
    private int total;
    //每页显示的商品集合
    private List<T> rows;

	
	public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PageBean [currentPage=" + currentPage + ", pageSize=" + pageSize
                + ", totalPage=" + totalPage + ", total=" + total + ", rows="
                + rows + "]";
    }


	
}
