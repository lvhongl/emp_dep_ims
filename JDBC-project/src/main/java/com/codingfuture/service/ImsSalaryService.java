package com.codingfuture.service;

import com.codingfuture.entity.ImsSalary;

import java.util.ArrayList;

public interface ImsSalaryService {
    boolean insert(ImsSalary imsSalary);
    boolean delete(ImsSalary imsSalary);
    boolean update(ImsSalary imsSalary);
    ArrayList<ImsSalary> query();
}
