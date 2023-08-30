package org.carl.common.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Written by Mr. Carl
 * @description: TODO 分页请求参数
 * @version: 1.0
 */
@Data
@NoArgsConstructor
public class PageQuery {

    public PageQuery(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    /**
     * 当前页号
     */
    @Min(0)
    private Integer pageNumber;

    public Integer getPageNumber() {
        return pageNumber != null ? pageNumber : 0;
    }

    /**
     * 一页数量
     */
    @Min(1)
    @Max(200)
    private Integer pageSize;

    public Integer getPageSize() {
        return pageSize != null ? pageSize : 20;
    }

    /**
     * 总数量
     */
    private Long pageCount;

    /**
     * 总页数
     */
    private Long pageLine;

    /**
     * 偏移量
     */
    @JSONField(serialize = false)
    public Long getOffset() {
        return (long) getPageNumber() * getPageSize();
    }

    /**
     * 赋值总数量、总页数
     *
     * @param count 总数量
     */
    public void setPageCount(Long count) {
        if (count == null) {
            count = 0L;
        }
        this.pageCount = count;
        this.pageLine = this.pageCount % getPageSize() == 0
                ? this.pageCount / getPageSize()
                : this.pageCount / getPageSize() + 1;
    }

}
