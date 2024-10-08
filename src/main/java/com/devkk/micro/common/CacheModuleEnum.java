package com.devkk.micro.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhongkunming
 */
@Getter
@AllArgsConstructor
public enum CacheModuleEnum {

    DEFAULT("default"),

    TOKEN("token"),

    DICT("dict");

    private final String value;
}
