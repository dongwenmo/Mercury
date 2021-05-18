package com.cn.momo.util.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具
 * dongwenmo 2021-04-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {
    private int pageNum;// 当前页
    private int pageSize;// 页大小
    private int startRow;// 开始行数
    private int endRow;// 结束行数
    private int total;// 总行数
    private List<T> list;// 查询内容
    private boolean isLastPage;// 是否最后一页

    public Page(PageDTO pageDTO) {
        if (pageDTO.getPageNum() == null || pageDTO.getPageNum() <= 0) {
            this.pageNum = 1;
            this.pageSize = 1000000;
        } else {
            this.pageNum = pageDTO.getPageNum();
            if (pageDTO.getPageSize() == null || pageDTO.getPageSize() <= 0) {
                this.pageSize = 20;
            } else {
                this.pageSize = pageDTO.getPageSize();
            }
        }
        this.startRow = (this.pageNum - 1) * this.pageSize + 1;
        this.endRow = this.pageNum * this.pageSize;
        this.list = new ArrayList<>();
        this.isLastPage = false;
    }

    public void checkIsLastPage() {
        if (total - (pageNum - 1) * pageSize - list.size() <= 0) {
            this.isLastPage = true;
        }
    }
}
