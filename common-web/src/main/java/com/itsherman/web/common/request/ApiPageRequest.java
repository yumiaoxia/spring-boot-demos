package com.itsherman.web.common.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @ApiModelProperty(value = "页码", example = "0")
    private Integer pageNo;

    @Min(0)
    @JsonProperty
    @NotNull
    @ApiModelProperty(value = "每页记录数", example = "20")
    private Integer pageSize;

    @JsonProperty
    @ApiModelProperty(value = "排序类型,1——升序，2——降序。顺序跟sortProperties中的字段的顺序一一对应")
    private Integer[] directions;

    @JsonProperty
    @ApiModelProperty(value = "排序字段,顺序跟directions中的排序类型的顺序一一对应")
    private String[] sortProperties;

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setDirections(Integer[] directions) {
        this.directions = directions;
    }

    public void setSortProperties(String[] sortProperties) {
        this.sortProperties = sortProperties;
    }

    @JsonIgnore
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
            if (directions.length != sortProperties.length) {
                throw new IllegalArgumentException("排序字段必须和排序方式一一对应");
            } else {
                List<Sort.Order> orders = new ArrayList<>(sortProperties.length);
                for (int i = sortProperties.length - 1; i >= 0; i--) {
                    Sort.Order order = null;
                    switch (directions[i]) {
                        case 1:
                            order = Sort.Order.asc(sortProperties[i]);
                            break;
                        case 2:
                            order = Sort.Order.desc(sortProperties[i]);
                            break;
                        default:
                            continue;
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
