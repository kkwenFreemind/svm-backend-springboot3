package com.svm.backend.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @Author : Kevin Chang
 * @create 2023/10/4 上午10:36
 */
public class PageUtil {

    public static <T> Page<T> listToPage(List<T> list, int page, int size) {
        page = page - 1;
        int start = page * size;
        int end = Math.min(start + size, list.size());

        // 截取指定页的数据
        List<T> sublist = list.subList(start, end);

        // 创建一个包含分页信息的 Page 对象
        Page<T> pageResult = new PageImpl<>(sublist, PageRequest.of(page, size), list.size());

        return pageResult;
    }


}
