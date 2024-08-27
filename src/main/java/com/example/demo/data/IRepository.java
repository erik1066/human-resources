package com.example.demo.data;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import com.example.demo.model.SearchResults;

public interface IRepository<T extends IEntity>
{
    T get(String id);
    SearchResults<T> getCollection(int from, int size, String sortField, String payload, boolean sortDescending);
    T update(String id, T entity);
    int getCount(String payload);
    boolean delete(String id);
    T insert(T entity);
    List<String> listTables();
}