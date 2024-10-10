package com.devkk.micro.framework.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.devkk.micro.utils.BeanUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author zhongkunming
 */
@Data
public class PageResult<R> implements Serializable {

    @Serial
    private static final long serialVersionUID = -8586054880473556418L;

    @Schema(title = "总页数")
    private Long pages;

    @Schema(title = "总条数")
    private Long total;

    @Schema(title = "每页条数")
    private Long size;

    @Schema(title = "页码")
    private Long current;

    @Schema(title = "分页数据")
    private List<R> records;

    public static <R> PageResult<R> trans(IPage<R> page) {
        PageResult<R> result = new PageResult<>();
        result.setPages(page.getPages());
        result.setTotal(page.getTotal());
        result.setSize(page.getSize());
        result.setCurrent(page.getCurrent());
        result.setRecords(page.getRecords());
        return result;
    }

    public static <T, R> PageResult<T> trans(IPage<R> page, Class<T> clazz) {
        List<R> records = page.getRecords();
        PageResult<T> result = new PageResult<>();
        result.setPages(page.getPages());
        result.setTotal(page.getTotal());
        result.setSize(page.getSize());
        result.setCurrent(page.getCurrent());
        result.setRecords(BeanUtils.copyToList(records, clazz));
        return result;
    }
}
