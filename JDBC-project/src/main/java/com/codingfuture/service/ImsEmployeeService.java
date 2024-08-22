package com.codingfuture.service;

import com.codingfuture.entity.ImsEmployee;

import java.util.ArrayList;
import java.util.List;

public interface ImsEmployeeService {
     boolean insert(ImsEmployee imsEmployee);
     boolean delete(ImsEmployee imsEmployee);
     boolean update(ImsEmployee imsEmployee);
     ArrayList<ImsEmployee> query();
}
