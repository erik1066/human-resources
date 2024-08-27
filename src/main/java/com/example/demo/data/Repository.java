package com.example.demo.data;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.example.demo.model.SearchResults;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;


import com.amazonaws.services.dynamodbv2.document.Table;

import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;

import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;

import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;




import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.DeleteItemResult;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class Repository<T extends IEntity> implements IRepository<T>
{
    private AmazonDynamoDB _ddb;
    private DynamoDB _dynamoDB;
    private String _tableName = "";
    private TypeToken<T> _type;

    public Repository(String tableName, TypeToken<T> type) {
        _tableName = tableName;
        _type = type;        
        _ddb = AmazonDynamoDBClientBuilder.defaultClient();
        _dynamoDB = new DynamoDB(_ddb);        
    }

    public T get(String id) {

        Table table = _dynamoDB.getTable(_tableName);
        
        GetItemSpec spec = new GetItemSpec()
            .withPrimaryKey("id", id)
            .withProjectionExpression(_tableName);

        String json = table.getItem(spec).toJSON();

        json = json.substring(_tableName.length() + 4);
        int length = json.length();
        json = json.substring(0, length - 1);

        T entity = new Gson().fromJson(json, _type.getType());
        return entity;
    }

    public SearchResults<T> getCollection(int from, int size, String sortField, String payload, boolean sortDescending) {

        // Table table = _dynamoDB.getTable(_tableName);
        
        // QuerySpec spec = new QuerySpec()
        //     .withKeyConditionExpression("Id = :v_id");
        //     // .withValueMap(new ValueMap()
        //     //     .withString(":v_id", "Amazon DynamoDB#DynamoDB Thread 1"));
        
        // ItemCollection<QueryOutcome> items = table.query(spec);

        QueryRequest queryRequest = new QueryRequest()
            .withTableName(_tableName);

        QueryResult queryResult = _ddb.query(queryRequest);
        List<Map<String,AttributeValue>> attributeValues = queryResult.getItems();

        // Iterator<Item> iterator = items.iterator();
        // Item item = null;

        // SearchResults<T> results = new SearchResults<T>();
        // results.items = new ArrayList<T>();

        // int i = 0;

        // while (iterator.hasNext()) {
            
        //     item = iterator.next();

        //     if (i >= from && i <= size) 
        //     {
        //         String json = item.toJSON();
        //         json = json.substring(_tableName.length() + 4);
        //         int length = json.length();
        //         json = json.substring(0, length - 1);
        //         T entity = new Gson().fromJson(json, _type.getType());

        //         results.items.add(entity);
        //     }

        //     i++;
            
        //     // System.out.println(item.toJSONPretty());
        // }

        return null;

        // HashMap<String, Condition> scanFilter = new HashMap<String, Condition>();
        
        // Boolean lastEval = true;
        // int count = 0;
        // ScanRequest scanRequest = new ScanRequest(_tableName).withScanFilter(scanFilter);
        // while(lastEval) {
        //     ScanResult scanResult = _ddb.scan(scanRequest);
        //     count += scanResult.getCount();
        //     System.out.println("Page Size: " + scanResult.getCount());
        //     System.out.println("Total count = " + count);
        //     if (scanResult.getLastEvaluatedKey() != null)
        //         lastEval = scanResult.getLastEvaluatedKey().isEmpty() == false;
        //     else
        //         lastEval = false;
        //     if (lastEval) {
        //         scanRequest.setExclusiveStartKey(scanResult.getLastEvaluatedKey());
        //     }
        // }


        // return null;
    }

    public T update(String id, T entity) {
        Table table = _dynamoDB.getTable(_tableName);
        
        Gson gson = new Gson();
        String json = gson.toJson(entity);

        Item item = new Item()
            .withPrimaryKey("id", entity.getId())
            .withJSON(_tableName, json);

        PutItemOutcome outcome = table.putItem(item);

        if (outcome.getPutItemResult() != null) {
            return get(entity.getId());
        }
        throw new IllegalStateException("Update failed");
    }

    public int getCount(String payload) {
        return 0;
    }

    public boolean delete(String id) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable(_tableName);

        DeleteItemOutcome outcome = table.deleteItem("id", id);

        DeleteItemResult result = outcome.getDeleteItemResult();

        if (result != null) {
            return true;
        }

        return false;
    }

    public T insert(T entity) {
        
        Table table = _dynamoDB.getTable(_tableName);

        Gson gson = new Gson();
        String json = gson.toJson(entity);

        Item item = new Item()
            .withPrimaryKey("id", entity.getId())
            .withJSON(_tableName, json);

        PutItemOutcome outcome = table.putItem(item);

        return get(entity.getId());
    }

    public List<String> listTables() {

        ArrayList<String> tableNames = new ArrayList<String>();
        
        boolean more_tables = true;
        while(more_tables) {
            String last_name = null;
            try {
                ListTablesResult table_list = null;
                if (last_name == null) {
                    table_list = _ddb.listTables();
                }
        
                List<String> table_names = table_list.getTableNames();
        
                if (table_names.size() > 0) {
                    for (String cur_name : table_names) {
                        tableNames.add(cur_name); // System.out.format("* %s\n", cur_name);
                    }
                } 
        
                last_name = table_list.getLastEvaluatedTableName();
                if (last_name == null) {
                    more_tables = false;
                }
            } catch (AmazonServiceException e) {
                System.out.println(e);
                more_tables = false;
                // TODO: handle error??
            } catch (Exception e) {
                System.out.println(e);
                more_tables = false;
                // TODO: ??
            }

            
        }
        return tableNames;
    }
}