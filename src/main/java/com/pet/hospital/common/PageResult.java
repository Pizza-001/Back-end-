package com.pet.hospital.common;

import io.swagger.v3.oas.annotations.media.Schema;
import com.github.pagehelper.PageInfo;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "通用查询分页结果包装")
public class PageResult<T> {
    @Schema(description = "满足查询条件的总数据条目数")
    private long total;
    @Schema(description = "当前分页请求下的明细数据列表")
    private List<T> list;

    public PageResult() {}

    public PageResult(long total, List<T> list) {
        this.total = total;
        this.list = list;
    }

    public static <T> PageResult<T> of(PageInfo<T> pageInfo) {
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }
}
