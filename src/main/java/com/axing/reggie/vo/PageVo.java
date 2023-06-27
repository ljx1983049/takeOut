package com.axing.reggie.vo;

import java.util.List;

/**
 * 返回界面信息
 */
public class PageVo<T> {
    //界面列表信息
    private List<T> records;
    //界面总记录条数
    private int total;

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
