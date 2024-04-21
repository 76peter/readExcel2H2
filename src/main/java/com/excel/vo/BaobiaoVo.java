package com.excel.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("公司中标统计")
public class BaobiaoVo {
    @ApiModelProperty("序号")
    private String xh;
    @ApiModelProperty("询价单")
    private String dh;
    @ApiModelProperty("名称")
    private String mc;
    @ApiModelProperty("产品名称")
    private String cpmc;
    @ApiModelProperty("中标金额")
    private String zbj;
    @ApiModelProperty("是否中标")
    private String sfzb;
    @ApiModelProperty("陪标单位")
    private List<String> pbdw;
}
