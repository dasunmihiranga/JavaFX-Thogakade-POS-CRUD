package edu.icet.db;

import edu.icet.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class DBConnection {

    private static DBConnection instance;
    private List<Customer>customerList;
    private DBConnection(){
        this.customerList=new ArrayList<>();
    }
    public List<Customer>getConnection(){
        return customerList;
    }
    public static DBConnection getInstance(){
        return null==instance? instance=new DBConnection():instance;
    }

}
