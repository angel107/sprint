package com.example.demo.weatherApi.service;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
//import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Body {
	public String dataType;
	public Items items;
	public int pageNo;
	public int numOfRows;
	public int totalCount;
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
class Header {
	public String resultCode;
	public String resultMsg;
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
class Item {
	public Object announceTime;
	public int numEf;
	public String regId;
	public int rnSt;
	public int rnYn;
	public int ta;
	public String wd1;
	public String wd2;
	public String wdTnd;
	public String wf;
	public String wfCd;
	public String wsIt;
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
class Items {
	public ArrayList<Item> item;
	
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

class Response {
	public Header header;
	public Body body;
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Root {
	Response response;
}