package com.itsherman.commonweb.response;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-03
 */
public class ApiPageResponse<T> extends ApiResponse<List<T>> {

    private Long totalSize;

    private Integer totalPage;

    private Integer pageNo;

    private Integer pageSize;

    public static <T> ApiPageResponse createPageSuccess(Page<T> t) {
        return createPageSuccess("0", "", t);
    }

    public static <T> ApiPageResponse createPageSuccess(String code, String message, Page<T> t) {
        if (t == null) {
            throw new NullPointerException("t must be not null!");
        }
        ApiPageResponse<T> apiPageResponse = new ApiPageResponse<>();
        apiPageResponse.setSuccess(true);
        apiPageResponse.setCode(code);
        apiPageResponse.setMessage(message);
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
}
