package com.excel.controller;

import cn.hutool.json.JSONObject;
import com.excel.model.Quotation;
import com.excel.service.ExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/excel")
@Api(tags="excel处理接口")
public class ExExcelController {

    @Resource
    private ExcelService excelService;

    @GetMapping("/doQuery")
    @ApiOperation("查询接口")
    @ResponseBody
    public List<Quotation> doQuery(String name){
        return excelService.findByGysContains(name);
    }

    @GetMapping("/findByGys")
    @ApiOperation("通过公司过滤报表")
    @ResponseBody
    public JSONObject findByGys(String name){
        return excelService.findByGys(name);
    }


}
