package com.excel.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.excel.model.Quotation;
import com.excel.repository.QuotationRepository;
import com.excel.service.ExcelService;
import com.excel.vo.BaobiaoVo;
import com.excel.vo.PeibiaoVo;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExcelServiceImpl implements ExcelService {


    @Resource
    private QuotationRepository quotationRepository;

    /**
     * 项目启动时，初始化导入Excel数据
     */
    @PostConstruct
    public void init(){
        try {
            InputStream resourceAsStream = new FileInputStream(System.getProperty("user.dir")+"/data.xls");
            ExcelReader reader = ExcelUtil.getReader(resourceAsStream);
            // 忽略表头
            reader.addHeaderAlias("单号","dh");
            reader.addHeaderAlias("标题","biaoti");
            reader.addHeaderAlias("产品","chanpin");
            reader.addHeaderAlias("供应商","gys");
            reader.addHeaderAlias("是否通过（成交）","sfcj");
            reader.addHeaderAlias("成交金额","cjj");
            List<Quotation> quotations = reader.readAll(Quotation.class);
            ArrayList<Quotation> guolv = quotations.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(s -> s.getDh() + ";" + s.getGys()))), ArrayList::new));
            quotationRepository.saveAllAndFlush(guolv);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Quotation> findByGysContains(){
        return quotationRepository.findBySfcj("通过");
    }

    public JSONObject findByGys(String name){
        List<Quotation> byGysContains = quotationRepository.findByGys(name);
        List<BaobiaoVo> baobiaoVos = new ArrayList<>();
        List<PeibiaoVo> peibiaoVos = new ArrayList<>();
        List<String> peibiaoCount = new ArrayList<>();
        for (int i = 0; i < byGysContains.size(); i++) {
            Quotation quotation = byGysContains.get(i);
            BaobiaoVo baobiaoVo = new BaobiaoVo();
            baobiaoVo.setXh(String.valueOf(i+1));
            baobiaoVo.setDh(quotation.getDh());
            baobiaoVo.setMc(quotation.getBiaoti());
            baobiaoVo.setCpmc(quotation.getChanpin());
            baobiaoVo.setZbj(quotation.getCjj());
            if("通过".equals(quotation.getSfcj())){
                baobiaoVo.setSfzb("是");
                List<Quotation> byDh = quotationRepository.findByDhAndAndSfcj(quotation.getDh(),"不通过");
                List<String> peiBiaoList = byDh.stream().map(Quotation::getGys).collect(Collectors.toList());
                baobiaoVo.setPbdw(peiBiaoList);
                peibiaoCount.addAll(peiBiaoList);
            }else{
                baobiaoVo.setSfzb("否");
            }
            baobiaoVos.add(baobiaoVo);
        }
        Map<String, List<String>> collect = peibiaoCount.stream().collect(Collectors.groupingBy(s -> s));
        Integer i = 1;
        for (String s : collect.keySet()) {
            List<String> strings = collect.get(s);
            PeibiaoVo peibiaoVo = new PeibiaoVo();
            peibiaoVo.setXh(String.valueOf(i));
            peibiaoVo.setDwmc(s);
            peibiaoVo.setCs(String.valueOf(strings.size()));
            peibiaoVos.add(peibiaoVo);
            i++;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.putOpt("baobiaoVos",baobiaoVos);
        jsonObject.putOpt("peibiaoVos",peibiaoVos);
        return jsonObject;
    }
}
