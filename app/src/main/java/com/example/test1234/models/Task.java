package com.example.test1234.models;

import java.security.PublicKey;
import java.util.Date;

public class Task {
    public Integer Id;
    public Integer ManagerId;
    public String ExName;
    public String StFullName;
    public String Title;
    public String Description;
    public String StartDate;
    public String EndDate;
    public Date CompleteDate;
    public boolean IsDeleted;
    public Integer StatusId;
    public Integer NumberOfCom;
    public Paging paging;
    public Executor executor;
    public Comment comments;
}
