package com.walmart.Utils;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;


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
