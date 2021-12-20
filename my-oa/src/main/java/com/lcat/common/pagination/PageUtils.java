package com.lcat.common.pagination;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.List;

public class PageUtils extends Page {

    /**
     * 分页查询
     * @param current
     * @param size
     * @param isSearchCount
     * @param sourceList
     * @param <T>
     * @return
     */
    public <T> Page<T> doPages(long current, long size, boolean isSearchCount, List<T> sourceList) {
        Page<T> resultList = new Page<>();
        resultList.setTotal(0);
        List<T> pageList = new ArrayList<>();
        if (sourceList != null && !sourceList.isEmpty()) {
            int sourceSize = sourceList.size();
            if (isSearchCount) {
                current = ("".equals(current) || current <= 0) ? 1 : current;
                size = ("".equals(size) || size <= 0) ? 1 : size;

                int fromIndex = (int) Math.min((current - 1) * size, sourceSize);
                int toIndex = (int) Math.min(current * size, sourceSize);
                pageList = sourceList.subList(fromIndex, toIndex);
            }
            resultList.setRecords(pageList);
            resultList.setTotal(pageList.size());
        }
        resultList.setSize(size);
        return resultList;
    }

}
