package com.javacodegeeks.example;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;




public class App{
	
	//The config parameters for the connection
	private static final String HOST = "10.10.10.52";
	private static final int PORT_ONE = 9200;
	private static final String SCHEME = "http";
	private static RestHighLevelClient restHighLevelClient;
	private static ObjectMapper objectMapper = new ObjectMapper();
	private static final String INDEX = "main";
	
	
    public static void main(String[] args) throws IOException{
    	
    	makeConnection();
    	Map<String,Object> searchParams = new HashMap<String,Object>();
    	//searchParams.put("protocol", "TCP");
    	searchParams.put("port", 8080);
    	parseResponse(searchQuery(searchParams));
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
    
    
    private static SearchResponse searchQuery(Map<String,Object> searchParams) throws IOException {
    	
    	SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder qb = QueryBuilders.boolQuery();
    
		for (String s : searchParams.keySet()){
    		qb.must(QueryBuilders.matchQuery(s,searchParams.get(s)));
    	}
		searchSourceBuilder.query(qb);
		searchSourceBuilder.size(3875);
		searchRequest.indices(INDEX);
		searchRequest.source(searchSourceBuilder);
		
		// test tempi di risposta della search 
		long start = System.currentTimeMillis();
		SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		long end = System.currentTimeMillis();
		long elapsedTime = end - start;
		
		System.out.println("Elapsed time: " + elapsedTime);
		
    	return searchResponse;
    	
    }
    
    
    private static void parseResponse(SearchResponse searchResponse) {
    	int i = 0;
    	for (SearchHit hit : searchResponse.getHits()) {
    	
    		Map<String,Object> map = hit.getSourceAsMap();
    		String t = (String) map.get("@timestamp");
            map.remove("@timestamp");
            map.put("timestamp", t);
            String versStr =  (String) map.get("@version");
            int vers = Integer.parseInt(versStr);
            map.remove("@version");
            map.put("version", vers);
            i++;
           // System.out.println(objectMapper.convertValue(map, LogRecord.class));
           
    	}
    	System.out.println("Elements: " + i);
    } 
    
  
    
    private static synchronized void closeConnection() throws IOException {
        restHighLevelClient.close();
        restHighLevelClient = null;
    }
}
