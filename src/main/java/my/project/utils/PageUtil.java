package my.project.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author : Kevin Chang
 * @create 2023/10/4 上午10:36
 */
public class PageUtil {

    public static <T> Page<T> createPageFromList(List<T> list, Pageable pageable){
        if(list == null){
            throw new IllegalArgumentException("thie list must not be null");
        }

        return null;
    }
}
