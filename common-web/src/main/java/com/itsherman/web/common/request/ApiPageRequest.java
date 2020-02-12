package com.itsherman.web.common.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-03
 */
public class ApiPageRequest<T> extends ApiRequest<T> {

    @Min(0)
    @JsonProperty
    @ApiModelProperty("页码")
    private Integer pageNo;

    @Min(0)
    @JsonProperty
    @NotNull
    @ApiModelProperty("每页记录数")
    private Integer pageSize;

    @JsonProperty
    private List<Sort.Direction> directions;

    @JsonProperty
    private List<String> sortProperties;

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setDirections(List<Sort.Direction> directions) {
        this.directions = directions;
    }

    public void setSortProperties(List<String> sortProperties) {
        this.sortProperties = sortProperties;
    }

    public Pageable getPageable() {
        Sort sort = getSort();
        Pageable pageable = null;
        if (pageSize != null && pageNo != null) {
            pageable = PageRequest.of(pageNo, pageSize, sort);
        } else {
            pageable = PageRequest.of(0, 0, sort);
        }
        return pageable;
    }


    private Sort getSort() {
        Sort sort = null;
        if (directions != null && sortProperties != null) {
            if (directions.size() != sortProperties.size()) {
                throw new IllegalArgumentException("排序字段必须和排序方式一一对应");
            } else {
                List<Sort.Order> orders = new ArrayList<>(sortProperties.size());
                for (int i = sortProperties.size() - 1; i >= 0; i--) {
                    Sort.Order order = null;
                    if (Sort.Direction.ASC == directions.get(i)) {
                        order = Sort.Order.asc(sortProperties.get(i));
                    } else {
                        order = Sort.Order.desc(sortProperties.get(i));
                    }
                    orders.add(order);
                }
                sort = Sort.by(orders);
            }
        } else {
            sort = Sort.unsorted();
        }
        return sort;
    }

    @Override
    public String toString() {
        return "ApiPageRequest{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", directions=" + directions +
                ", sortProperties=" + sortProperties +
                '}';
    }
}
