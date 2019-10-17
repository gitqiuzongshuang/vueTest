package com.qiu.controller;

import com.qiu.pojo.people;
import com.qiu.service.testService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class testController {
@Autowired
testService testService;
    @RequestMapping(value = "/test.do",produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String tes(String name,HttpServletRequest request,HttpServletResponse response)throws IOException{
        String name1=new String(name.getBytes("iso-8859-1"),"utf-8");
        return name;
    }
    @RequestMapping(value = "/add.do",produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String add(people people){
        int r=testService.insert(people);
        System.out.println(people.getName()+"+++"+people.getTel());
        if(r>0){
            return "success";
        }
        return "failed";
}
    @RequestMapping(value = "/del.do",produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String remove(Integer id, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        if(id!=null){
            int r = testService.remove(id);
            if (r > 0) {
                return "success";
            }
        }
        return "failed";
    }
    @RequestMapping("/getPeople.do")
    @ResponseBody
    public Map<String,Object> getPeople(String name, Integer currentPage,Integer pageSize,HttpServletResponse response) {

        if(name!=null&&!"".equals(name)){
            name="%"+ name +"%";
           return  testService.getPeopleByName(currentPage,pageSize,name);
        }
        return testService.getPeople(currentPage,pageSize);
    }
    @RequestMapping(value = "/getPeopleInfo",produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public people getPeopleInfo(Integer id){
        System.out.println(id);
        List<people> list=new ArrayList<>();
        people people=testService.getPeopleInfo(id);
        System.out.println(people.getName());
        return people;
    }
    @RequestMapping(value = "/edit.do",produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String edit(people people){
        int r=testService.edit(people);
        if(r>0){
            return "success";
        }
        return "failed";
    }
    @RequestMapping(value = "/export.do",produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String exp(){
        return testService.exportInfo();
    }
    @RequestMapping(value = "/import.do",produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String imp(){
        List<people> listExcel=testService.getAllFromExcel("D://export//peopleImport.xls");
        for (people p:listExcel) {
            System.out.println("id:"+p.getId()+"name"+p.getName());
            if (testService.getPeopleInfo(p.getId())==null){
                int r=testService.insert(p);

            }else {
                int r=testService.edit(p);
            }
        }
        return "success";
    }
    @RequestMapping(value = "/getFile.do",produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String file(MultipartFile file,HttpServletRequest request)throws IOException{
        List<people> listExcel=testService.getAllFromExcel2(file);
        for (people p:listExcel) {
            System.out.println("id:"+p.getId()+"name"+p.getName());
            if (testService.getPeopleInfo(p.getId())==null){
                int r=testService.insert(p);

            }else {
                int r=testService.edit(p);
            }
        }
        return "success";
    }

}
