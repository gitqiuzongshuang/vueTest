package com.qiu.service;

import com.alibaba.druid.pool.vendor.SybaseExceptionSorter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qiu.dao.testDao;
import com.qiu.pojo.people;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class testService {
@Autowired
    testDao testDao;
    public int insert(people people){
    return  testDao.add(people);
}
    public int remove(int id){
        return  testDao.delete(id);
    }
    public Map<String,Object> getPeople(int current,int pageSize){
        Map<String,Object> map=new HashMap<String,Object>();
        PageHelper.startPage(current,pageSize);
        List<people> list=testDao.selectAll();
        PageInfo<people> page = new PageInfo<people>(list);
        System.out.println("总数量：" + page.getTotal());
        System.out.println("每页显示数量：" + page.getPageSize());
        System.out.println("当前页码：" + page.getPageNum());
        int totalpages= (int) (page.getTotal()/page.getPageSize());
        if (page.getTotal()%page.getPageSize()>0){
            totalpages+=1;
        }
        System.out.println("总页数：" + page.getPageNum());
        map.put("people",list);
        map.put("total",page.getTotal());
        map.put("currentPage",page.getPageNum());
        return map;
    }
    public Map<String,Object> getPeopleByName(int current,int pageSize,String name)
    {
        Map<String,Object> map=new HashMap<String,Object>();
        PageHelper.startPage(1,pageSize);
        List<people> list=testDao.selectByName(name);
        PageInfo<people> page = new PageInfo<people>(list);
        System.out.println("总数量：" + page.getTotal());
        System.out.println("每页显示数量：" + page.getPageSize());
        System.out.println("当前页码：" + page.getPageNum());
        map.put("people",list);
        map.put("total",page.getTotal());
        map.put("currentPage",page.getPageNum());
        return map;
    }
    public int edit(people people){return testDao.update(people);}
    public people getPeopleInfo(int id){return testDao.selectPeople(id);}
    public String exportInfo(){
        List<people> list=testDao.selectAll();
        try {
            WritableWorkbook wwb = null;

            // 创建可写入的Excel工作簿
            String fileName = "D://export//people.xls";
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            wwb= Workbook.createWorkbook(file);
            // 创建工作表
            WritableSheet ws = wwb.createSheet("Test Shee 1", 0);
            Label lableId=new Label(0, 0, "编号(id)");
            Label labelName=new Label(1, 0, "姓名(name)");
            Label labelTel=new Label(2, 0, "电话(tel)");
            Label labelSex=new Label(3, 0, "性别(sex)");
            ws.addCell(lableId);
            ws.addCell(labelTel);
            ws.addCell(labelName);
            ws.addCell(labelSex);
            for(int i=0;i<list.size();i++){
                Label lableId_i=new Label(0, i+1,list.get(i).getId()+"");
                Label labelName_i=new Label(1, i+1, list.get(i).getName());
                Label labelTel_i=new Label(2, i+1, list.get(i).getTel());
                Label labelSex_i=new Label(3, i+1, list.get(i).getSex()+"");
                ws.addCell(lableId_i);
                ws.addCell(labelTel_i);
                ws.addCell(labelName_i);
                ws.addCell(labelSex_i);
            }
            wwb.write();//写进文档
            System.out.println("数据导出成功！");
            wwb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "success";
    }
    public people getPeopleById(int id){ return testDao.selectPeople(id); }
    public List<people> getAllFromExcel(String file){
        List<people> list=new ArrayList<people>();
        try{
            Workbook wb=Workbook.getWorkbook(new File(file));
            Sheet sheet=wb.getSheet(0);
            int rows=sheet.getRows();
            int cols=sheet.getColumns();
            for(int i=1;i<rows;i++){
                int j=0;
                String id=sheet.getCell(j++,i).getContents();
                String name=sheet.getCell(j++,i).getContents();
                String tel=sheet.getCell(j++,i).getContents();
                String sex=sheet.getCell(j++,i).getContents();
                people people=new people();
                people.setId(Integer.parseInt(id));
                people.setName(name);
                people.setTel(tel);
                people.setSex(Integer.parseInt(sex));
                list.add(people);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public List<people> getAllFromExcel2(MultipartFile file) throws IOException {
        List<people> list=new ArrayList<people>();
        CommonsMultipartFile cFile = (CommonsMultipartFile) file;
        DiskFileItem fileItem = (DiskFileItem) cFile.getFileItem();
        InputStream inputStream = fileItem.getInputStream();
        try{
            Workbook wb=Workbook.getWorkbook(inputStream);
            Sheet sheet=wb.getSheet(0);
            int rows=sheet.getRows();
            int cols=sheet.getColumns();
            for(int i=1;i<rows;i++){
                int j=0;
                String id=sheet.getCell(j++,i).getContents();
                String name=sheet.getCell(j++,i).getContents();
                String tel=sheet.getCell(j++,i).getContents();
                String sex=sheet.getCell(j++,i).getContents();
                people people=new people();
                people.setId(Integer.parseInt(id));
                people.setName(name);
                people.setTel(tel);
                people.setSex(Integer.parseInt(sex));
                list.add(people);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
