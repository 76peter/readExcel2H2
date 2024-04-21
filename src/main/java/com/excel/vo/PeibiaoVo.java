package com.excel.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("陪标单位统计")
public class PeibiaoVo {
    @ApiModelProperty("序号")
    private String xh;
    @ApiModelProperty("陪标单位名称")
    private String dwmc;
    @ApiModelProperty("陪标次数")
    private String cs;
}
