package com.example.demo.model;

import java.util.Date;
import java.util.ArrayList;

public class Request
{
    public String id;
    public Date dateCreated;
    public RequestStatus status;
    public RequestType type;
    public String submitterId;
    public Role submitterRole;
    public String submitterComments;
    public ArrayList<RequestAction> actions = new ArrayList<RequestAction>();
    public TimeOff timeOff;
}