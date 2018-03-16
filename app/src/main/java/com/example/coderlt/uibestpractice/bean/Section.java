package com.example.coderlt.uibestpractice.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by coderlt on 2018/3/15.
 */

public class Section implements Serializable {
    private String sectionName;
    private List<Employee> employees;

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
