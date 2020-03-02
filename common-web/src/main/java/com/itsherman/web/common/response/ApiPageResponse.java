package com.itsherman.web.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-03
 */
@ApiModel
public class ApiPageResponse<T> extends ApiResponse<T> {

    @ApiModelProperty("总记录数")
    private Long totalSize;

    @ApiModelProperty("总页数")
    private Integer totalPage;

    @ApiModelProperty("页码")
    private Integer pageNo;

    @ApiModelProperty("每页记录记录数")
    private Integer pageSize;

    public static <T> ApiPageResponse<List<T>> createPageSuccess(Page<T> t) {
        return createPageSuccess("0", t);
    }

    public static <T> ApiPageResponse<List<T>> createPageSuccess(String code, Page<T> t) {
        if (t == null) {
            throw new NullPointerException("t must be not null!");
        }
        ApiPageResponse<List<T>> apiPageResponse = new ApiPageResponse<>();
        apiPageResponse.setSuccess(true);
        apiPageResponse.setCode(code);
        apiPageResponse.setTotalSize(t.getTotalElements());
        apiPageResponse.setTotalPage(t.getTotalPages());
        apiPageResponse.setPageNo(t.getNumber());
        apiPageResponse.setPageSize(t.getSize());
        apiPageResponse.setData(t.getContent());
        return apiPageResponse;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "ApiPageResponse{" +
                "totalSize=" + totalSize +
                ", totalPage=" + totalPage +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }
}
