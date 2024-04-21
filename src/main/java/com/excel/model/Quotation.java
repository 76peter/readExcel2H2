package com.excel.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@ApiModel("询价单")
@Table(name = "quotation")
public class Quotation  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "dh")
    @ApiModelProperty("单号")
    private String dh;

    @Column(name = "biaoti")
    @ApiModelProperty("标题")
    private String biaoti;

    @Column(name = "chanpin")
    @ApiModelProperty("产品")
    private String chanpin;

    @Column(name = "cjj")
    @ApiModelProperty("成交金额")
    private String cjj;

    @Column(name = "gys")
    @ApiModelProperty("供应商")
    private String gys;

    @Column(name = "sfcj")
    @ApiModelProperty("是否成交")
    private String sfcj;

}
