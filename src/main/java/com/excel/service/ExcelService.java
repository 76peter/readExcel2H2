package com.excel.service;

import cn.hutool.json.JSONObject;
import com.excel.model.Quotation;

import java.util.List;

public interface ExcelService {

    /**
     * 查询全部
     * @return
     */
    List<Quotation> findByGysContains(String name);

    /**
     * 根据公司名称统计中标数量和陪标公司
     * @param name
     * @return
     */
    JSONObject findByGys(String name);
}
