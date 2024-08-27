package com.example.demo.model;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.example.demo.data.IEntity;

public class Employee implements IEntity
{
    public String id;
    public String firstName;
    public String lastName;
    public Date startDate = new GregorianCalendar(2000, Calendar.JANUARY, 1).getTime();
    public String title;
    public String email;
    public String linkedin;
    public String photoUrl;
    public String streetAddress1;
    public String streetAddress2;
    public String city;
    public String zip;
    public String state;

    public String department;
    public String managerId;
    public String managerName;

    public String bio;

    public String workPhone;
    public String cellPhone;
    public String homePhone;

    public double annualLeaveBalance = 0.0;
    public double sickLeaveBalance = 0.0;

    public boolean isActive = false;
    public int salary = 0;
    public ArrayList<Request> requests = new ArrayList<Request>();
    public ArrayList<Review> reviews = new ArrayList<Review>();
    public ArrayList<Employee> reports = new ArrayList<Employee>();

    public String getId() { return id; }
    public void setId(String newId) { id = newId; }
}