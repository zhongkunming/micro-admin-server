package com.devkk.micro.utils;

import org.dromara.hutool.core.bean.BeanUtil;

import java.util.Collection;
import java.util.List;

/**
 * @author zhongkunming
 */
public class BeanUtils {

    private BeanUtils() {
    }

    public static <R> R copy(Object source, Class<R> clazz) {
        return BeanUtil.copyProperties(source, clazz);
    }

    public static <S, R> List<R> copyToList(Collection<S> sources, Class<R> clazz) {
        return BeanUtil.copyToList(sources, clazz);
    }
}
