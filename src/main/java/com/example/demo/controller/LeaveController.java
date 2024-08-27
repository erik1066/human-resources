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
@RequestMapping("/api/leave")
public class LeaveController {

	private IRepository<Employee> _repository = new Repository<Employee>("employee", new TypeToken<Employee>() {} );

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String index() throws IOException {
		return IOUtils.toString(EmployeeController.class.getResourceAsStream("/index.html"), Charset.defaultCharset());
    }
    
	
}