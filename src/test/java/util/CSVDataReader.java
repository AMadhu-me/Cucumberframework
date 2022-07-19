package util;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CSVDataReader {
	
	public static HashMap<String,String> commonCSVConfig;
	
	public CSVReader readCsvSkipHeader(String csvFilePath)
	{
		CSVReader reader = null;
		
		InputStream filestream = Thread.currentThread().getContextClassLoader().getResourceAsStream(csvFilePath);
		reader=new CSVReader(new InputStreamReader(filestream),',','\"','\\',1);
		
		return reader;
		
	}
	public CSVReader readCsvAllLines(String csvFilePath)
	{
		CSVReader reader = null;
		
		InputStream filestream = Thread.currentThread().getContextClassLoader().getResourceAsStream(csvFilePath);
		reader=new CSVReader(new InputStreamReader(filestream));
		
		return reader;
		
	}
	
	public void csvConfigData(String filename) throws IOException
	{
		String[] header = null;
		String[] line = null;
		
		FileReader filereader = new FileReader(filename);
		
		HashMap<String,String> csvConfigCollection = new LinkedHashMap<String, String>();
		
		CSVReader reader=new CSVReader(filereader);
		header = reader.readNext();
		while((line= reader.readNext()) != null)
		{
			csvConfigCollection.put(line[0], line[1]);
		}
		commonCSVConfig=csvConfigCollection;
	}

	public String getCSVValue(String key)
	{
		String value=null;
		try
		{
			value=commonCSVConfig.get(key);
		}catch(Exception E)
		{
			
		}
		return value;
	}
}
