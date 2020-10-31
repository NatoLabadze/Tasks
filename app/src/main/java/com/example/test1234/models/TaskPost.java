package com.example.test1234.models;

import java.util.Date;

public class TaskPost {
    public Integer Id;
    public Integer ManagerId;
    public String ExName;
    public String StFullName;
    public String title;
    public String description;
    public String startDate;
    public String endDate;
    public Date CompleteDate;
    public boolean IsDeleted;
    public Integer StatusId;
    public Integer NumberOfCom;
    public Paging paging;
    public Executor executor;
    public Comment comments;
}
