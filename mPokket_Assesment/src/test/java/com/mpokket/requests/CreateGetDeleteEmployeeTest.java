package com.mpokket.requests;

import org.testng.annotations.Test;

import com.mpokket.pojos.CreateEmployeePOJO;
import com.mpokket.util.APIBaseClass;
import com.mpokket.util.ResourcePath;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;

public class CreateGetDeleteEmployeeTest extends APIBaseClass {
	
	@Test
	public void createGetDeleteEmployeeTest() {
		
		CreateEmployeePOJO createEmployeePOJO = new CreateEmployeePOJO("abc", "2000", "121");
		
		Response createEmployeeResponse = RestAssured.given()
		.contentType(ContentType.JSON)
		.body(createEmployeePOJO, ObjectMapperType.JACKSON_1)
		.when()
		.post(ResourcePath.CREATE_EMPLOYEE);
		createEmployeeResponse.then()
		.assertThat().statusCode(200)
		.assertThat().contentType(ContentType.JSON)
		.log().all();
		
		int empId = createEmployeeResponse.jsonPath().get("data.id");
		
		RestAssured.given()
		.pathParam("empid", empId)
		.get(ResourcePath.GET_SINGLE_EMPLOYEE)
		.then()
		.assertThat().statusCode(200)
		.assertThat().contentType(ContentType.JSON)
		.log().all();
		
		RestAssured.given()
		.pathParam("empid", empId)
		.delete(ResourcePath.DELETE_SINGLE_EMPLOYEE)
		.then()
		.assertThat().statusCode(204)
		.log().all();
	}
	

}
