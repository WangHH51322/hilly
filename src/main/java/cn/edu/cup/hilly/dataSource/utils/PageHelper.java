package cn.edu.cup.hilly.dataSource.utils;

import lombok.Data;

import java.util.List;

/**
 * @author CodeChap
 * @date 2021-08-09 10:17
 * @description PageHelper
 */
@Data
public class PageHelper<T> {
    private long currentPage;
    private long total;
    private long pageSize;
    private List<T> list;

    public PageHelper(long pageNum, long total, long pageSize, List<T> list) {
        this.currentPage = pageNum;
        this.total = total;
        this.pageSize = pageSize;
        this.list = list;
    }

    public PageHelper(long pageNum, long pageSize, List<T> list) {
        this.currentPage = pageNum;
        this.pageSize = pageSize;
        this.list = list;
    }
}
