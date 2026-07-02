package rahulshettyacademy.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataReader { //not used in this class we moved this method to base test class so that we can directly call this method from SubmitOrderPOMTest's dataprovider
	
	public List<HashMap<String,String>> getJsonDatatoMap(String filePath) throws IOException { //method to convert json data file to a hashmap
		
		//step1: convert the json file to a String
		//String jsonContent=FileUtils.readFileToString(new File(System.getProperty("user.dir")+"\\src\\test\\java\\rahulshettyacademy\\data\\PurchaseOrder.json"),StandardCharsets.UTF_8);
		String jsonContent=FileUtils.readFileToString(new File(System.getProperty("user.dir")+"\\src\\test\\java\\rahulshettyacademy\\data\\PurchaseOrder.json"),StandardCharsets.UTF_8);
		
		//Step 2:Add Jackson Data bind dependency and convert the String from step1(jsonContent) to a HashMap or List<HashMap<String,String>>
		ObjectMapper mapper=new ObjectMapper();
		List<HashMap<String,String>> data=mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>() {});//write correct syntax
		
		return data;  //output will be [ {map1,map2} ] list of jsons
		
		
	}

}
