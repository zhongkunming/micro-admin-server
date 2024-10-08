package com.devkk.micro.web.system.controller;

import com.devkk.micro.framework.common.PageResult;
import com.devkk.micro.framework.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhongkunming
 */
@Tag(name = "示例")
@Validated
@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {

    @PostMapping("/list")
    @Operation(summary = "查询")
    public Result<PageResult<Void>> list() {
        return Result.success();
    }

    @PostMapping("/delete")
    @Operation(summary = "删除")
    public Result<Void> delete() {
        return Result.success();
    }

    @PostMapping("/create")
    @Operation(summary = "新增")
    public Result<Void> create() {
        return Result.success();
    }

    @PostMapping("/update")
    @Operation(summary = "修改")
    public Result<Void> update() {
        return Result.success();
    }
}
