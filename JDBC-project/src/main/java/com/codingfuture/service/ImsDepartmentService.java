package com.codingfuture.service;

import com.codingfuture.entity.ImsDepartment;

import java.util.ArrayList;

public interface ImsDepartmentService {
    boolean insert(ImsDepartment imsDepartment);

    boolean delete(ImsDepartment imsDepartment);

    ArrayList<ImsDepartment> query();

    boolean updata(ImsDepartment imsDepartment);
    boolean queryByName(ImsDepartment imsDepartment);

}
