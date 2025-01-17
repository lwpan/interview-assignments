package com.liuwangpan.urlconvert.model.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wp_li
 * @Deacription baseResponse
 **/
@Data
@AllArgsConstructor
@ApiModel("统一 Response 返回值")
public class BaseResponse<T> implements Serializable {

    /**
     * 返回成功的状态码
     */
    public static final Long SUCCESS_CODE = 200L;
    /**
     * 默认成功msg
     */
    public static final String DEFAULT_SUCCESS_MESSAGE = "success";

    @ApiModelProperty(name = "结果码", notes = "正确响应时该值为 Result#SUCCESS_CODE，错误响应时为错误代码")
    private long code;

    @ApiModelProperty(name = "人工可读的消息", notes = "正确响应时该值为 Result#DEFAULT_SUCCESS_MESSAGE，错误响应时为错误信息")
    private String msg;

    @ApiModelProperty(name = "响应体", notes = "正确响应时该值会被使用")
    private T data;

    public BaseResponse(T data) {
        this.setData(data);
        this.setCode(SUCCESS_CODE);
        this.setMsg(DEFAULT_SUCCESS_MESSAGE);
    }

    public BaseResponse() {
        this.setCode(SUCCESS_CODE);
        this.setMsg(DEFAULT_SUCCESS_MESSAGE);
    }


}