package com.example.genshinwiki.infrastructure.util;

import com.example.genshinwiki.infrastructure.model.PageInput;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 构造Spring-Data分页参数
 *
 * @author ankelen
 * @date 2020-05-23 14:38
 */
public abstract class PageInputHelper {
    //region private

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int DEFAULT_MAX_PAGE_SIZE = 2000;
    private static final Pageable DEFAULT_PAGE_REQUEST = PageRequest.of(DEFAULT_PAGE, DEFAULT_PAGE_SIZE);

    /**
     * @param sortArg 排序参数 eg: "id,desc&createAt,asc" or "id,createAt,desc"
     * @return Sort
     */
    private static Sort resolveSortArg(String sortArg) {
        if (StringUtil.isBlank(sortArg)) {
            return Sort.unsorted();
        }
        String[] args = sortArg.split(StringPool.AMPERSAND);
        List<Order> allOrders = new ArrayList<>();

        for (String arg : args) {
            // eg: ["id,desc"] or ["id"] or ["id,createAt,desc"]
            String[] elements = arg.split(StringPool.COMMA);

            // 将 [,] 最后一个的英文转为 Sort.Direction [ASC or DESC]
            Optional<Direction> direction = elements.length == 0
                    ? Optional.empty()
                    : Direction.fromOptionalString(elements[elements.length - 1]);

            // 若带了排序标识, 返回elements.length-1[前n-1个元素], 否则返回elements.length
            int lastIndex = direction.map(it -> elements.length - 1).orElseGet(() -> elements.length);
            // 将Direction前面的字段加入排序集合
            for (int i = 0; i < lastIndex; i++) {
                String property = elements[i];
                if (StringUtil.isBlank(property)) {
                    continue;
                }
                // 当direction为null时,构造升序排序 否则根据具体的direction进行构造
                Optional.of(
                        direction.map(it -> new Order(it, property))
                                .orElseGet(() -> Order.by(property))
                ).ifPresent(allOrders::add);
            }
        }
        return allOrders.isEmpty() ? Sort.unsorted() : Sort.by(allOrders);
    }

    /**
     * @param pageArg page参数
     * @param sizeArg size参数
     * @return Pageable
     */
    private static Pageable getPageable(Integer pageArg, Integer sizeArg) {
        Optional<Integer> page = Optional.ofNullable(pageArg);
        Optional<Integer> pageSize = Optional.ofNullable(sizeArg);

        // page和size均传了空值 返回默认分页 (Sort 单独做的解析)
        if (!page.isPresent() && !pageSize.isPresent()) {
            return DEFAULT_PAGE_REQUEST;
        }

        Optional<Pageable> defaultPage = DEFAULT_PAGE_REQUEST.toOptional();
        // 解析 page 和 pageSize 参数，其中一个为null的话使用 默认分页结果
        int p = page.orElseGet(() -> defaultPage.map(Pageable::getPageNumber).orElseThrow(IllegalStateException::new));
        int ps = pageSize.orElseGet(() -> defaultPage.map(Pageable::getPageSize).orElseThrow(IllegalStateException::new));

        // 定界
        ps = Math.max(1, ps);
        ps = Math.min(ps, DEFAULT_MAX_PAGE_SIZE);
        return PageRequest.of(p, ps, defaultPage.map(Pageable::getSort).orElseGet(Sort::unsorted));
    }

    //endregion

    /**
     * 通过 PageInput 构造 Pageable{@link Pageable}
     *
     * @param in PageInput
     * @return Pageable
     */
    public static Pageable toPageable(PageInput in) {
        // 空值 返回无分页[全部做一页]
        if (in == null) {
            return Pageable.unpaged();
        }

        return getPageable(in.getPage(), in.getSize());
    }
}
