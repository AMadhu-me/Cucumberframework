package util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ExcelDataCollection {

	CSVDataReader csvData = new CSVDataReader();
	public static Map<String,Map<String,Map<String,String>>> globalExcelData;
	public static Map<String,Map<String,String>> scenarioSheetData;
	public static Map<String,Map<String,String>> accountsSheetData;
	public static Map<String,Map<String,String>> usersSheetData;
	public static Map<String,Map<String,Map<String,String>>> locatorsExcelData;


	public static Workbook getWorkBook(String filepath) throws IOException
	{
		Workbook workbook=null;
		//FileInputStream fis = Thread.currentThread().getContextClassLoader().getResourceAsStream(filepath);
		FileInputStream fis = new FileInputStream(filepath);
		ZipSecureFile.setMinInflateRatio(0);
		workbook=new XSSFWorkbook(fis);
		return workbook;

	}

	public static Map<String,Map<String,String>> sheetData(Workbook workbook , String sheetName)
	{

		Map<String,Map<String,String>> sheetDataByName = new HashMap();

		Sheet sheetData = workbook.getSheet(sheetName);

		Row firstRow = sheetData.getRow(0);

		for(int i =0;i<=sheetData.getLastRowNum();i++)
		{
			Map<String,String> valuePairs = new HashMap();

			try
			{
				Row row = sheetData.getRow(i);
				String rowFirstValue = row.getCell(0).getStringCellValue().trim();

				for(int j=0;j<=row.getLastCellNum()-1;j++)
				{
					try {
						String headerData = firstRow.getCell(j).getStringCellValue().trim();
						String rowValueData =row.getCell(j).getStringCellValue().trim();

						valuePairs.put(headerData, rowValueData);
					}catch(Exception E)
					{

					}
					sheetDataByName.put(rowFirstValue, valuePairs);
				}


			}catch(Exception e)
			{

			}

		}
		return sheetDataByName;

	}

	public static Map<String,Map<String,Map<String,String>>> sheetData(String filePath) throws IOException
	{
		Map<String,Map<String,Map<String,String>>> sheetDataCollection = new HashMap();

		Workbook workbook = getWorkBook(filePath);

		for(int i=0;i<=workbook.getNumberOfSheets()-1;i++)
		{
			String currentSheetName=workbook.getSheetName(i);
			Map<String,Map<String,String>> shhetDataMap = sheetData(workbook,currentSheetName);
			sheetDataCollection.put(currentSheetName, shhetDataMap);
		}
		return sheetDataCollection;

	}

	public static Map<String,Map<String,String>> getSheettoData(String sheetName)
	{
		Map<String,Map<String,String>> sheetData = globalExcelData.get(sheetName);
		return sheetData;

	}


	public static String getSpecificKeyValue(Map<String,Map<String,String>> dataCollection , String rowName,String key)
	{
		String value = null;
		Map<String,String> rawData = dataCollection.get(rowName);
		if(rawData!=null)
		{
			value=rawData.get(key);
		}
		return value;

	}
	public static  Map<String,Map<String,String>>putSpecificKeyValue(Map<String,Map<String,String>> dataCollection , String rowName,String key,String value)
	{

		Map<String,String> rawData = dataCollection.get(rowName);
		rawData.put(key,value);
		dataCollection.put(rowName,rawData);

		return dataCollection;

	}

	public static XSSFWorkbook readProtectedFile(String protectedFilePath,String encoadedPassword)
	{
		XSSFWorkbook workbook =null;
		try
		{
			NPOIFSFileSystem fs = new NPOIFSFileSystem(new File(protectedFilePath));
			EncryptionInfo info = new EncryptionInfo(fs);
			Decryptor d = Decryptor.getInstance(info);

			if(d.verifyPassword(encoadedPassword))
			{
				workbook = new XSSFWorkbook(d.getDataStream(fs));
			}else
			{
				System.out.println("incorrect password for Secrets");
			}

		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return workbook;
	}

	public void writeToExcel(String sheetName, String rowName, String key, String value) throws IOException, InvalidFormatException {
		try
		{
			String filePath = csvData.getCSVValue("excelInputFile");
			File file = new File(filePath);
			FileInputStream inputStream = new FileInputStream(file);
			Workbook workbook = WorkbookFactory.create(inputStream);
			Sheet sheetData = workbook.getSheet(sheetName);

			Row firstRow = sheetData.getRow(0);

			for(int i=1;i<=sheetData.getLastRowNum();i++)
			{
				Map<String,String> valuePairs = new HashMap<>();
				Row row = sheetData.getRow(i);
				String rowFirstValue = row.getCell(0).getStringCellValue();
				if(rowFirstValue.contentEquals(rowName))
				{
					for(int j=1;j<=row.getLastCellNum()-1;j++)
					{
						String headerData = firstRow.getCell(j).getStringCellValue().trim();
						if(headerData.contentEquals(key))
						{
							Cell cell2Update = sheetData.getRow(i).getCell(j);
							cell2Update.setCellValue((String) value);
						}
					}
				}
				inputStream.close();
				FileOutputStream outputStream = new FileOutputStream(file);
				workbook.write(outputStream);
				workbook.close();
				outputStream.close();
			}

		}catch(Exception E)
		{
			E.printStackTrace();
		}
	}

	public static void writeToExcelInCollection(Map<String,Map<String,String>>dataCollection , String filePath, String sheetName)
	{
		try
		{

			File file=new File(filePath);
			FileInputStream inputStream = new FileInputStream(file);
			Workbook workbook = WorkbookFactory.create(inputStream);
			Sheet sheetData = workbook.getSheet(sheetName);
			Row firstRow = sheetData.getRow(0);
			for(String rowName: dataCollection.keySet())
			{
				Map<String,String> rawData = dataCollection.get(rowName);
				if(rawData!=null)
				{
					for(String key : rawData.keySet())
					{
						String value =rawData.get(key);

						for(int i=1;i<=sheetData.getLastRowNum();i++)
						{
							Row row = sheetData.getRow(i);
							String rowFirstValue = row.getCell(0).getStringCellValue();
							if(rowFirstValue.equalsIgnoreCase(rowName))
							{
								for(int j=1;j<=row.getPhysicalNumberOfCells();j++)
								{
									String headerData = firstRow.getCell(j).getStringCellValue().trim();
									if(headerData.equalsIgnoreCase(key))
									{
										Cell cellToUpdate;
										cellToUpdate=row.createCell(j);
										cellToUpdate.setCellValue(value);
									}
								}
							}
						}
					}
				}
				inputStream.close();
			}
			FileOutputStream outputStream = new FileOutputStream(file);
			workbook.write(outputStream);
			outputStream.close();

		}catch(Exception E)
		{
			E.printStackTrace();
		}
	}

	public static LinkedList<String> getLocatorsData(String locator)
	{
		String locatorName = StringUtils.substringAfter(locator,".");
		String objectPage = StringUtils.substringBefore(locator,".");
		Map<String,Map<String,String>> sheetData = locatorsExcelData.get(objectPage);

		String objectIdentifier = ExcelDataCollection.getSpecificKeyValue(sheetData,locatorName,"ObjectIdentifier");
		String objectLocator = ExcelDataCollection.getSpecificKeyValue(sheetData,locatorName,"ObjectLocator");
		LinkedList<String> objectData = new LinkedList<>();
		objectData.add(objectIdentifier);
		objectData.add(objectLocator);

		return objectData;
	}
}
