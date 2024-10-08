package com.devkk.micro.framework.common;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author zhongkunming
 */
@Getter
@Setter
public abstract class BaseEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Schema(description = "主键")
    private String id;

    @TableField(value = "create_dt", fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createDt;

    @TableField(value = "update_dt", fill = FieldFill.UPDATE)
    @Schema(description = "更新时间")
    private LocalDateTime updateDt;
}
