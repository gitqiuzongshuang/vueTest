package com.qiu.dao;

import com.qiu.pojo.people;

import java.util.List;

public interface testDao {
    public int add(people people);
    public int delete(Integer id);
    public List<people> selectAll();
    public List<people> selectByName(String name);
    public int update(people people);
    public people selectPeople(int id);
}
