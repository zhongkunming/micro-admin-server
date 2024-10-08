package com.devkk.micro.utils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author zhongkunming
 */
public class TreeUtils {

    private TreeUtils() {
    }

    public static <K, T> List<T> build(List<T> sources,
                                       Function<T, K> idOp,
                                       Function<T, K> pidOp,
                                       Function<T, List<T>> childGetOp,
                                       BiConsumer<T, List<T>> childSetOp) {
        if (Objects.isNull(sources) || sources.isEmpty()) {
            return new ArrayList<>(0);
        }
        Map<K, T> cache = new HashMap<>();
        for (T source : sources) {
            K id = idOp.apply(source);
            cache.put(id, source);
        }
        List<T> list = new ArrayList<>();
        for (T source : sources) {
            K pid = pidOp.apply(source);

            if (Objects.isNull(pid)) {
                list.add(source);
                continue;
            }
            T parent = cache.get(pid);
            if (Objects.isNull(parent)) {
                continue;
            }
            List<T> children = Optional.ofNullable(childGetOp.apply(parent)).orElseGet(ArrayList::new);
            children.add(source);
            childSetOp.accept(parent, children);
        }
        return list;
    }
}
