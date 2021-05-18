package com.cn.momo.util.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * dongwenmo 2021-04-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO {
    private Integer pageSize;
    private Integer pageNum;
}
