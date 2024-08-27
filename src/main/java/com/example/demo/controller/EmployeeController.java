package com.example.demo.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.google.gson.*;

import com.example.demo.model.*;
import com.example.demo.data.IRepository;
import com.example.demo.data.Repository;
import com.google.gson.reflect.TypeToken;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonClientException;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api/employee")
public class EmployeeController {

	private IRepository<Employee> _repository = new Repository<Employee>("employee", new TypeToken<Employee>() {} );

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String index() throws IOException {
		return IOUtils.toString(EmployeeController.class.getResourceAsStream("/index.html"), Charset.defaultCharset());
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Creates a new employee record", notes = "Creates a new employee record")
	@ResponseBody
	public ResponseEntity<JsonNode> createEmployee(@RequestBody(required = true) Employee employee) 
	{
		ObjectMapper mapper = new ObjectMapper();

		if(employee.getId() == null || employee.getId().isEmpty()) {
			employee.setId(java.util.UUID.randomUUID().toString());
		}
		else {

			try {
				_repository.get(employee.getId());
				throw new IllegalStateException("Employee with id " + employee.getId() + " already exists");
			}
			catch (Exception ex) {				
			}
		}

		try {
			Employee newEmployee = _repository.insert(employee);			
			Gson gson = new Gson();
			String json = gson.toJson(newEmployee);
			return new ResponseEntity<>(mapper.readTree(json), HttpStatus.CREATED);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Updates an employee record", notes = "Updates an employee record")
	@ResponseBody
	public ResponseEntity<JsonNode> updateEmployee(@RequestBody(required = true) Employee employee) 
	{
		ObjectMapper mapper = new ObjectMapper();

		try {
			Employee updatedEmployee = _repository.update(employee.getId(), employee);			
			Gson gson = new Gson();
			String json = gson.toJson(updatedEmployee);
			return new ResponseEntity<>(mapper.readTree(json), HttpStatus.OK);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@RequestMapping(value = "timeoff", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Submits a time off request for the logged-in employee", notes = "Submits a time off request for the logged-in employee")
	@ResponseBody
	public ResponseEntity<JsonNode> submitNewTimeOffRequest(@RequestBody(required = true) String payload) 
	{
		JSONObject request = new JSONObject(payload);
		// parse employee, send to DAL

		ObjectMapper mapper = new ObjectMapper();
		
		try {

			request.put("dateCreated", Calendar.getInstance());
			request.put("status", "PendingManager");
			request.put("submitterRole", "Employee");
			
			JSONObject out = new JSONObject();
			out.put("success", true);
			return new ResponseEntity<>(mapper.readTree(out.toString()), HttpStatus.OK);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	// @RequestMapping(method = RequestMethod.GET, value="/tables", produces = MediaType.APPLICATION_JSON_VALUE)
	// @ApiOperation(value = "Lists all DB tables", notes = "Lists all DB tables")
	// @ResponseBody
	// public ResponseEntity<JsonNode> listTables(@RequestBody(required = false) String payload) 
	// {
	// 	ObjectMapper mapper = new ObjectMapper();

	// 	try {
			
	// 		List<String> tables = _repository.listTables();

	// 		Gson gson = new Gson();
	// 		String json = gson.toJson(tables);  
	// 		return new ResponseEntity<>(mapper.readTree(json), HttpStatus.OK);


	// 	} catch (Exception e) {
	// 		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	// 	}
	// }

	@RequestMapping(method = RequestMethod.GET, value="/current", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Gets the current employee", notes = "Gets the current employee")
	@ResponseBody
	public ResponseEntity<JsonNode> getCurrentEmployee(@RequestBody(required = false) String payload) 
	{
		ObjectMapper mapper = new ObjectMapper();

		try {

			Employee employee = _repository.get("7dd608b5-7a12-43e6-805c-df20d4da112a");
			Employee manager = _repository.get(employee.managerId);			
			employee.managerName = manager.firstName + " " + manager.lastName;

			Gson gson = new Gson();
			String json = gson.toJson(employee);
			return new ResponseEntity<>(mapper.readTree(json), HttpStatus.OK);

		} catch (AmazonServiceException e) {

			JsonNode error = null;
			try {
				error = mapper.readTree("{ \"errorMessage\" : \"" + e.getMessage() + "\", \"statusCode\" : \"" + e.getStatusCode() + "\", \"awsErrorCode\" : \"" + e.getErrorCode() + "\", \"errorType\" : \"" + e.getErrorType() + "\" }");				
			}
			catch (Exception ex) {}

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

		} catch (AmazonClientException e) {

			JsonNode error = null;
			try {
				error = mapper.readTree("{ \"errorMessage\" : \"" + e.getMessage() + "\" }");
			}
			catch (Exception ex) {}

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

		} catch (NullPointerException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

		} catch (Exception e) {

			JsonNode error = null;
			try {
				error = mapper.readTree("{ \"errorMessage\" : \"" + e.getMessage() + "\" }");
			}
			catch (Exception ex) {}

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

		}
	}

	@RequestMapping(method = RequestMethod.GET, value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Gets an employee record", notes = "Gets an employee record")
	@ResponseBody
	public ResponseEntity<JsonNode> getEmployee(
		@RequestBody(required = false) String payload, 
		@ApiParam(value = "Employee identifier") @PathVariable(value = "id") String id)
	{
		ObjectMapper mapper = new ObjectMapper();

		try {

			Employee employee = _repository.get(id);
			Employee manager = _repository.get(employee.managerId);			
			employee.managerName = manager.firstName + " " + manager.lastName;

			Gson gson = new Gson();
			String json = gson.toJson(employee);
			return new ResponseEntity<>(mapper.readTree(json), HttpStatus.OK);
			
		} catch (AmazonServiceException e) {
			JsonNode error = null;
			try {
				error = mapper.readTree("{ \"errorMessage\" : \"" + e.getMessage() + "\", \"statusCode\" : \"" + e.getStatusCode() + "\", \"awsErrorCode\" : \"" + e.getErrorCode() + "\", \"errorType\" : \"" + e.getErrorType() + "\" }");				
			}
			catch (Exception ex) {}

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
		} catch (AmazonClientException e) {
			JsonNode error = null;
			try {
				error = mapper.readTree("{ \"errorMessage\" : \"" + e.getMessage() + "\" }");
			}
			catch (Exception ex) {}

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
		} catch (NullPointerException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Gets a list of employees", notes = "Gets a list of employees")
	@ResponseBody
	public ResponseEntity<JsonNode> getEmployees(
		@RequestBody(required = false) String payload,
		@ApiParam(value = "Set the starting point of the result set") @RequestParam(value = "start", defaultValue = "0") int start,
		@ApiParam(value = "Limit the number of objects to return") @RequestParam(value = "end", defaultValue = "-1") int end) 
	{
		ObjectMapper mapper = new ObjectMapper();

		try {

			SearchResults<Employee> results = _repository.getCollection(start, end, "name", "{}", false);

			Gson gson = new Gson();
			String json = gson.toJson(results);
			return new ResponseEntity<>(mapper.readTree(json), HttpStatus.OK);

			// JSONObject out = new JSONObject();
			// out.put("inserted", 4);
			// JSONArray elementIds = new JSONArray();
			// // for (Document document : documents) {
			// // 	elementIds.put(document.getObjectId("_id"));
			// // }
			// out.put("ids", elementIds);
		
			// return new ResponseEntity<>(mapper.readTree(out.toString()), HttpStatus.OK);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
}