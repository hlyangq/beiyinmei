package com.ningpai.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by youzipi on 16/9/7.
 */
public class KCollection {
    /**
     * convert array to list.
     * return emptyList if array is null.
     *
     * @param elements
     * @param <E>
     * @return
     */
    public static <E> List<E> defaultList(E... elements) {
        if (elements != null) {
            return Arrays.asList(elements);
        } else {
            return Collections.emptyList();
        }
    }

//    Collection<? extends E>

    public static <E> List<E> defaultList(List<E> list) {
        if (list != null) {
            return list;
        } else {
            return Collections.emptyList();
        }
    }
}
