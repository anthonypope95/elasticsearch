package com.javacodegeeks.example;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.RequestLine;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



public class App{
	
	//The config parameters for the connection
	private static final String HOST = "10.10.10.52";
	private static final int PORT_ONE = 9200;
	private static final String SCHEME = "http";
	private static final int SIZE = 9100;
	private static RestHighLevelClient restHighLevelClient;
	private static ObjectMapper objectMapper = new ObjectMapper();
	private static final String INDEX = "main";
	private static final String queryAggr = "{\"aggs\": {  \"3\": {       \"terms\": { \"field\": \"protocol.keyword\",\"order\": {  \"_count\": \"desc\" }, \"size\": 24}}},\"size\": 0,\"stored_fields\": [  \"*\"  ],  \"script_fields\": {},  \"docvalue_fields\": [    {     \"field\": \"@timestamp\",      \"format\":\"date_time\"    }  ],  \"_source\": {    \"excludes\": []  },  \"query\": {    \"bool\": {      \"must\": [],      \"filter\": [        {\"bool\": {            \"should\": [             {                \"match_phrase\": {                  \"_index\": \"main\"}           }],            \"minimum_should_match\": 1          }        },        {          \"bool\": {            \"should\": [              {\"match_phrase\": {                  \"_index\": \"main\"                }              }            ],    \"minimum_should_match\": 1 }       }      ]    }  }}";

	public static void main(String[] args) throws IOException{
    	
    	makeConnection();
    	Map<String,Object> searchParams = new HashMap<String,Object>();
    	
    	
    	searchParams.put("protocol","UDP");
    	searchParams.put("connectorname","TenableAlfa");

    	Response res = searchQuery(searchParams);
    
    	JsonNode content = objectMapper.readTree(res.getEntity().getContent());
    	//System.out.print(content);
    	parseResponse(content);
    
       	closeConnection();
    	
    }
    
    private static synchronized RestHighLevelClient makeConnection() {
    	 
        if(restHighLevelClient == null) {
            restHighLevelClient = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost(HOST, PORT_ONE, SCHEME)));
        }
  
        return restHighLevelClient;
    }
    
      
    private static Response searchQuery(Map<String,Object> searchParams) throws IOException {
    	
    	String queryString = createQuery(searchParams);    	
    	// OK    String queryString = "{\"query\": { \"bool\": { \"must\": [  {\"match\": {\"protocol\":\"UDP\"}}, {\"match\": {\"connectorname\":\"TenableAlfa\"}} ]  }   }}";
    	    
    	//Request req = new Request("GET","/" + INDEX + "/_search?size=" + SIZE);
    	Request req = new Request("GET","/" + INDEX + "/_search");
        req.setJsonEntity(queryAggr); 
    
        long start = System.currentTimeMillis();
        Response response = restHighLevelClient.getLowLevelClient().performRequest(req); 
	    long end = System.currentTimeMillis();
	    long elapsedTime = end - start;
	    System.out.println("Elapsed time: " + elapsedTime);
	    

    	return response;
    }
    
    
    private static String createQuery(Map<String,Object> searchParams) {
		// TODO Auto-generated method stub
    	String middlePartQuery = null;
    	String firstPartQuery = "{\"query\": { \"bool\": { \"must\": [";
    	String endPartQuery = "]}}}";
    	
    	for(String key: searchParams.keySet()) {
    		
    	  Object obj = searchParams.get(key);
    	 
    	  if(obj instanceof String) {
    		  if(middlePartQuery == null) middlePartQuery =  "{\"match\" : {\"" + key + "\":" + "\"" + obj + "\"}},";
    		
    		  else {
    		  
    			  middlePartQuery = middlePartQuery + "{\"match\" : {\"" + key + "\":" + 
						
						"\"" + obj + "\"}},";
    		  }
    	  }																
    	  else if(obj instanceof Integer) {
    		  if(middlePartQuery == null) middlePartQuery = "{\"match\" : {\"" + key + "\":" + Integer.toString((Integer) obj) + "}},";
    		  
    		  else {
    		  
    			  middlePartQuery = middlePartQuery + "{\"match\" : {\"" + key + "\":"
						
						 + Integer.toString((Integer) obj) + "}},";
    		  }
    	  }	
    	  
    	  else if(obj instanceof Double) {
    		  
    		  if(middlePartQuery == null) middlePartQuery = "{\"match\" : {\"" + key + "\":" + Double.toString((Double) obj) + "}},";
    		  
    		  else {
    		  
    			  middlePartQuery = middlePartQuery + "{\"match\" : {\"" + key + "\":"
						
						 + Double.toString((Double) obj) + "}},";
    		  }
    	  }
    	}
    	
    
    	middlePartQuery = middlePartQuery.substring(0, middlePartQuery.length()-1);
    	return firstPartQuery + middlePartQuery + endPartQuery;
		
	}



    private static void parseResponse(JsonNode content) {
    	int i = 0;
    	for (JsonNode node : content.get("hits").get("hits")) {
    		
    		JsonNode source = node.get("_source");
    		Map<String, Object> result = objectMapper.convertValue(source, new TypeReference<Map<String, Object>>(){});
    		String t =  (String) result.get("@timestamp");
            result.remove("@timestamp");
            result.put("timestamp", t);
            String versStr =  (String) result.get("@version");
            int vers = Integer.parseInt(versStr);
            result.remove("@version");
            result.put("version", vers);
            i++;
           // System.out.println(objectMapper.convertValue(result, LogRecord.class));
           
    	}
    	
  
    }
    
    
    private static synchronized void closeConnection() throws IOException {
        restHighLevelClient.close();
        restHighLevelClient = null;
    }
}
