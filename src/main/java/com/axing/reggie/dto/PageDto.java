package com.axing.reggie.dto;

import java.time.LocalDateTime;

/**
 * 接收分页信息
 */
public class PageDto {
    /**
     * 当前页码
     */
    private Integer page;
    /**
     * 每页显示条数
     */
    private Integer pageSize;
    /**
     * 从第几条开始显示
     */
    private Integer startNum;
    /**
     * 条件:名称
     */
    private String name;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 订单号
     */
    private String number;

    /**
     * 开始日期
     */
    private String beginTime;

    /**
     * 结束日期
     */
    private String endTime;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // public void setStartNum(Integer page,Integer pageSize){
    //     this.startNum = (page-1)*pageSize;
    // }

    public Integer getStartNum() {
        Integer startNum = (getPage()-1)*getPageSize();
        return startNum;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PageDto{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", startNum=" + startNum +
                ", name='" + name + '\'' +
                '}';
    }

}
