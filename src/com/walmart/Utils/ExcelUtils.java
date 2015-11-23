package com.walmart.Utils;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;


public class ExcelUtils {

    private Logger _logger;
    Object[][] data = null;
    ArrayList<Object> topRowList = new ArrayList<>();

    public ExcelUtils() {
        _logger = Logger.getLogger(ExcelUtils.class);
    }

    public Object[][] getSimpleExcelData(String filePath)
            throws Exception {
        XSSFWorkbook workbook = getWorkBook(filePath);
        Sheet sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
        int numberOfRows = sheet.getLastRowNum() + 1;
        int numberOfColumns = countNonEmptyColumns(sheet);
        data = new Object[numberOfRows-1][numberOfColumns];
        for(int rowNum = 1;rowNum < numberOfRows;rowNum++)
        {
            Row row = sheet.getRow(rowNum);
            if (isEmpty(row)) {
                break;
            } else {
                for (int colNum = 0; colNum < numberOfColumns; colNum++) {
                    Cell cell = row.getCell(colNum);
                    if (cell != null
                            && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                        data[rowNum - 1][colNum] = objectFrom(workbook,
                                cell);
                    }
                }
            }
        }

        return data;
    }


    public Object[][] getComplexExcelData(String filePath)
            throws Exception {
        XSSFWorkbook workbook = getWorkBook(filePath);
        Sheet sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
        int numberOfRows = sheet.getLastRowNum() + 1;
        int numberOfColumns = countNonEmptyColumns(sheet);
        data = new Object[numberOfRows-1][3];
        Iterator<Row> rows = sheet.iterator();
        topRowList = getFirstRowContent(sheet.getRow(0),workbook);
        Map<Object,Object> dataMap = new HashMap<Object,Object>();
        for (int rowNum = 1; rowNum < numberOfRows; rowNum++) {
            Row row = sheet.getRow(rowNum);
            ArrayList<Object> cellList =  new ArrayList<Object>();
            if (isEmpty(row)) {
                break;
            } else {
                for (int column = 0; column < numberOfColumns; column++) {
                    Cell cell = row.getCell(column);
                    cellList.add(objectFrom(workbook,cell));
                }
            }
            dataMap = setDataMap(cellList);
            setDataObject(dataMap,rowNum-1);
        }

        return data;
    }

    public ArrayList<Object> getFirstRowContent(Row row,XSSFWorkbook workbook)
    {
        ArrayList<Object> topRow = new ArrayList<>();
        Iterator<Cell> cells = row.cellIterator();
        while(cells.hasNext())
        {
            Cell cell = cells.next();
            topRow.add(objectFrom(workbook,cell));
        }

        return topRow;
    }

    public Map<Object,Object> setDataMap(ArrayList<Object> cellDataList)
    {
        Map<Object,Object> dataMap = new HashMap<>();
        for(int i=0;i<topRowList.size();i++)
        {
            dataMap.put(topRowList.get(i),cellDataList.get(i));
        }

        return dataMap;
    }

    public void setDataObject(Map<Object,Object> dataMap,int rowNum)
    {

        Iterator<Object> values = dataMap.values().iterator();
        String firstElement = (String)dataMap.get("Resource");
        String api = (String)dataMap.get("API-Key");
        dataMap.remove(dataMap.get(0));
        data[rowNum][0] = firstElement;
        data[rowNum][1] = api;
        data[rowNum][2] = dataMap;
    }

    public XSSFWorkbook getWorkBook(String filePath)
    {
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));
        }
        catch (Exception e)
        {
            _logger.error(e);
        }
        return workbook;
    }

    private boolean isEmpty(Row row) {
        Cell firstCell = row.getCell(0);
        boolean rowIsEmpty = (firstCell == null)
                || (firstCell.getCellType() == Cell.CELL_TYPE_BLANK);
        return rowIsEmpty;
    }

    private int countNonEmptyColumns(Sheet sheet) {
        Row firstRow = sheet.getRow(0);
        return firstEmptyCellPosition(firstRow);
    }

    private int firstEmptyCellPosition(Row cells) {
        int columnCount = 0;
        for (Cell cell : cells) {
            if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                break;
            }
            columnCount++;
        }
        return columnCount;

    }

    private Object objectFrom(XSSFWorkbook workbook, Cell cell) {
        Object cellValue = null;
        if (cell.getCellType() != Cell.CELL_TYPE_BLANK) {
            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                cellValue = getNumericCellValue(cell);
            } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                cellValue = cell.getBooleanCellValue();
            }
            else {
                cellValue = cell.getStringCellValue();
            }
        }

    return cellValue;
    }

    private Object getNumericCellValue(final Cell cell) {
        Object cellValue;
        if (DateUtil.isCellDateFormatted(cell)) {
        cellValue = new Date(cell.getDateCellValue().getTime());
        } else {
        cellValue = cell.getNumericCellValue();
        }
        return cellValue;
        }
}
