package com.muggle.learn;


import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: muggle
 * @Date: 2020/4/26
 **/
public class PoseidonQuery<T> {
    private T search;

    private Map<String, PoseidonQueryEnum> operator;

    private int pageSize;

    private int pageNo;

    private List<String> orderBy;

    public List<String> getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(List<String> orderBy) {
        this.orderBy = orderBy;
    }

    public T getSearch() {
        return search;
    }

    public void setSearch(T search) {
        this.search = search;
    }

    public Map<String, PoseidonQueryEnum> getOperator() {
        return operator;
    }

    public void setOperator(Map<String, PoseidonQueryEnum> operator) {
        this.operator = operator;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getSart(){
        return pageSize*(pageNo-1);
    }

    public int getEnd(){
        return pageSize;
    }
}
