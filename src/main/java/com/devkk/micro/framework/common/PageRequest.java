package com.devkk.micro.framework.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhongkunming
 */
@Getter
@Setter
public class PageRequest {

    @Min(value = 1, message = "每页条数最小为1")
    @Schema(description = "每页条数")
    private Long size;

    @Min(value = 1, message = "页码最小为1")
    @Schema(description = "页码")
    private Long current;

    public <T> IPage<T> toPage() {
        Page<T> page = new Page<>();
        page.setMaxLimit(100L);
        page.setSize(size);
        page.setCurrent(current);
        return page;
    }
}
