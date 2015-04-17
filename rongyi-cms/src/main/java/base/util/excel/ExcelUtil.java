package base.util.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public abstract class ExcelUtil {
	private static final Log LOG = LogFactory.getLog(ExcelUtil.class);

	public static final String TEMPLATE_RESOURCE_PATH = "config/template/excel";

	public static final int OUTPUT_SUCCESS = 0;

	public static final int OUTPUT_FAILURE = 1;

	/** Excel Sheetの操作 */
	public final SheetOperation SheetOp = new SheetOperation();

	/** Excel Rowの操作 */
	public final RowOperation RowOp = new RowOperation();

	/** Excel Columnの操作 */
	public final ColumnOperation ColumnOp = new ColumnOperation();

	/** Excel Cellの操作 */
	public final CellOperation CellOp = new CellOperation();

	/** Excel CellFormatの操作 */
	public final CellFormatOperation CellFormatOp = new CellFormatOperation();

	/** Excel CellStyleの操作 */
	public final CellStyleOperation CellStyleOp = new CellStyleOperation();

	/** Excel Header Footer の操作 */
	public final HeaderFooterOperation HeaderFooterOp = new HeaderFooterOperation();

	/** Excel Print の操作 */
	public final PrintOperation PrintOp = new PrintOperation();

	public abstract int output(HSSFWorkbook workbook);

	protected void prepare(HSSFWorkbook workbook) {

		HSSFPalette palette = workbook.getCustomPalette();

		palette.setColorAtIndex(HSSFColor.BRIGHT_GREEN.index, (byte) 0,
				(byte) 255, (byte) 0);

		palette.setColorAtIndex(HSSFColor.MAROON.index, (byte) 127, (byte) 0,
				(byte) 0);

		palette.setColorAtIndex(HSSFColor.TURQUOISE.index, (byte) 0,
				(byte) 255, (byte) 255);

		palette.setColorAtIndex(HSSFColor.VIOLET.index, (byte) 128, (byte) 0,
				(byte) 128);

		palette.setColorAtIndex(HSSFColor.DARK_YELLOW.index, (byte) 128,
				(byte) 128, (byte) 0);

		palette.setColorAtIndex(HSSFColor.RED.index, (byte) 255, (byte) 0,
				(byte) 0);

		palette.setColorAtIndex(HSSFColor.PINK.index, (byte) 255, (byte) 0,
				(byte) 255);

		clearExcelSummary(workbook);
	}

	protected void cleanup(HSSFWorkbook workbook) {

	}

	public HSSFRichTextString createHSSFRichTextString(String str) {
		return new HSSFRichTextString(str);
	}

	/**
	 * 新しいWorkBookを作る
	 * 
	 * @param templateName
	 * @return 作ったWorkBook
	 * @throws Exception
	 */
	public static HSSFWorkbook createWorkBook(String templateName)
			throws Exception {
		Resource resource = new ClassPathResource(TEMPLATE_RESOURCE_PATH + "/"
				+ templateName);

		return new HSSFWorkbook(resource.getInputStream());
	}

	/**
	 * 
	 * エクセルの行を作り出す
	 * 
	 * @param sheet
	 *            シート
	 * @param rowCount
	 *            作り出す行数
	 * @param colCount
	 *            作り出すコラム数
	 */
	public static void createRows(HSSFSheet sheet, int rowCount, int colCount) {
		for (int rownum = 0; rownum < rowCount; rownum++) {
			Row row = sheet.createRow(rownum);
			for (int cellnum = 0; cellnum < colCount; cellnum++) {
				row.createCell(cellnum);
			}
		}
	}

	public static void copyRowsWithCells(HSSFSheet sourceSheet,
			HSSFSheet targetSheet, int rowFrom, int rowTo, int colFrom,
			int colTo, int offsetR) {
		// cell copy 処理
		for (int i = rowFrom; i < rowTo; i++) {
			Row sourceRow = sourceSheet.getRow(i);
			if (null == sourceRow) {
				continue;
			}

			Row targetRow = targetSheet.getRow(offsetR + i);
			if (null == targetRow) {
				targetRow = targetSheet.createRow(offsetR + i);
			}
			targetRow.setHeight(sourceRow.getHeight());

			for (int j = colFrom; j < colTo; j++) {
				Cell sourceCell = sourceRow.getCell(j);
				// コラムの幅を設定する
				targetSheet.setColumnWidth(j, sourceSheet.getColumnWidth(j));

				if (null == sourceCell) {
					continue;
				}
				Cell targetCell = targetRow.getCell(j);
				if (null == targetCell) {
					targetCell = targetRow.createCell(offsetR + i);
				}
				targetCell.setCellStyle(sourceCell.getCellStyle());

				int cType = sourceCell.getCellType();
				switch (cType) {
				case HSSFCell.CELL_TYPE_BOOLEAN:
					targetCell.setCellValue(sourceCell.getBooleanCellValue());
					break;
				case HSSFCell.CELL_TYPE_ERROR:
					targetCell
							.setCellErrorValue(sourceCell.getErrorCellValue());
					break;
				case HSSFCell.CELL_TYPE_NUMERIC:
					targetCell.setCellValue(sourceCell.getNumericCellValue());
					break;
				case HSSFCell.CELL_TYPE_STRING:
					targetCell
							.setCellValue(sourceCell.getRichStringCellValue());
					break;
				}
			}
		}
	}

	/**
	 * Template内容をCopy
	 * 
	 * @param destRowIndex
	 * @param destSheet
	 * @param tempRowIndex
	 * @param tempSheet
	 * @param maxRow
	 * @param maxColumn
	 * @return row index
	 */
	public int copyTemplateContent(int destRowIndex, HSSFSheet destSheet,
			int tempRowIndex, HSSFSheet tempSheet, int maxRow, int maxColumn) {

		// for the max row which content need be copied
		for (int row = tempRowIndex; row < maxRow; row++) {

			HSSFRow destRow = RowOp.createRow(destSheet, destRowIndex++);
			HSSFRow templateRow = RowOp.getRow(tempSheet, row);

			// adjust the height of new row and make it the same with template
			// row
			RowOp.copyRowHeight(destRow, templateRow);

			// the max column which need be cloned
			for (int col = 0; col < maxColumn; col++) {

				HSSFCell templateCell = CellOp.getCell(templateRow, col);

				if (templateCell != null) {

					HSSFCell destCell = RowOp.createCell(destRow, col);
					destCell.setCellStyle(templateCell.getCellStyle());

					int celltype = templateCell.getCellType();
					if (celltype == Cell.CELL_TYPE_NUMERIC) {
						destCell.setCellValue(templateCell
								.getNumericCellValue());
					} else {
						destCell.setCellValue(templateCell.getStringCellValue());
					}
				}
			}
		}

		int offsetRow = destRowIndex - maxRow + tempRowIndex;

		CellFormatOp.copyMergeFormat(destSheet, tempSheet, offsetRow);

		return offsetRow;
	}

	/**
	 * do remove merge data before remove row
	 * 
	 * @param sheet
	 * @param rowIndex
	 */
	public void removeRowDeep(HSSFSheet sheet, int rowIndex) {
		HSSFRow unusedRow = RowOp.getRow(sheet, rowIndex);

		if (unusedRow != null) {

			int colSize = unusedRow.getPhysicalNumberOfCells();
			for (int i = 0; i < colSize; i++) {
				HSSFCell celldata = CellOp.getCell(unusedRow, i);
				if (celldata != null)
					CellFormatOp.revmoeMergedCell(sheet, celldata);
			}

			RowOp.removeRow(sheet, unusedRow);
		}
	}

	public void clearExcelSummary(HSSFWorkbook workbook) {

		workbook.createInformationProperties();

		SummaryInformation sInfo = workbook.getSummaryInformation();
		sInfo.setTitle("");
		sInfo.setSubject("");
		sInfo.setAuthor("");
		sInfo.setKeywords("");
		sInfo.setComments("");

		DocumentSummaryInformation docSInfo = workbook
				.getDocumentSummaryInformation();
		docSInfo.setManager("");
		docSInfo.setCompany("");
		docSInfo.setCategory("");

	}

	static class ExcelOperation {

		/** define the index　of template is zero (first sheet) */
		static final int TEMPLATE_SHEET_INDEX = 0;

		static final int TEMPLATE_SHEET_NO_AUTO_PAGEBREAK = 1;

	}

	/**
	 * ExcelのSheetの処理
	 * 
	 */
	public static class SheetOperation extends ExcelOperation {

		/**
		 * set Default Active Cell (0, 0)
		 * 
		 * @param sheet
		 */
		public void setSelectedCellDefault(HSSFWorkbook workbook) {
			setSelectedCell(workbook, 0, 0, 0);
		}

		/**
		 * Sets this cell as the active cell for the worksheet
		 * 
		 * @param sheet
		 * @param row
		 * @param col
		 */
		public void setSelectedCell(HSSFWorkbook workbook, int sheetIndex,
				int row, int col) {
			workbook.setActiveSheet(sheetIndex);

			HSSFSheet sheet = getSheet(workbook, sheetIndex);
			sheet.showInPane((short) row, (short) col);

			HSSFRow activeRow = sheet.getRow(row);
			HSSFCell activeCell = activeRow.getCell(col);
			activeCell.setAsActiveCell();
		}

		/**
		 * Returns the index of the given sheet
		 * 
		 * @param workbook
		 * @param sheet
		 * @return int
		 */
		public int getSheetIndex(HSSFWorkbook workbook, HSSFSheet sheet) {
			return workbook.getSheetIndex(sheet);
		}

		/**
		 * create an HSSFSheet for this HSSFWorkbook, adds it to the sheets and
		 * returns the high level representation. Use this to create new sheets.
		 * 
		 * @param workbook
		 * @param sheetName
		 * @return HSSFSheet
		 */
		public HSSFSheet createSheet(HSSFWorkbook workbook, String sheetName) {
			return workbook.createSheet(sheetName);
		}

		/**
		 * Get the HSSFSheet object at the given index.
		 * 
		 * @param wrokbook
		 * @param index
		 * @return HSSFSheet
		 */
		public HSSFSheet getSheet(HSSFWorkbook wrokbook, int index) {
			return wrokbook.getSheetAt(index);
		}

		/**
		 * return the name of this sheet
		 * 
		 * @param sheet
		 * @return String
		 */
		public String getSheetName(HSSFSheet sheet) {
			return sheet.getSheetName();
		}

		/**
		 * remove the given index sheet
		 * 
		 * @param wrokbook
		 * @param sheetIndex
		 */
		public void removeSheet(HSSFWorkbook wrokbook, int sheetIndex) {
			wrokbook.removeSheetAt(sheetIndex);
		}

		/**
		 * clone the template sheet
		 * 
		 * @param workbook
		 * @param sheetName
		 * @return HSSFSheet
		 */
		public HSSFSheet cloneTemplateSheet(HSSFWorkbook workbook,
				String sheetName) {
			return cloneSheet(workbook, sheetName, TEMPLATE_SHEET_INDEX);
		}

		/**
		 * 
		 * @param workbook
		 * @param sheetName
		 * @param tempalteSheetIndex
		 * @return HSSFSheet
		 */
		private HSSFSheet cloneSheet(HSSFWorkbook workbook, String sheetName,
				int tempalteSheetIndex) {

			HSSFSheet cloneSheet = workbook.cloneSheet(tempalteSheetIndex);
			workbook.setSheetName(workbook.getNumberOfSheets() - 1, sheetName);

			return cloneSheet;
		}

		/**
		 * get the template sheet(first sheet)
		 * 
		 * @param workbook
		 * @return HSSFSheet
		 */
		public HSSFSheet getTemplateSheet(HSSFWorkbook workbook) {
			return workbook.getSheetAt(TEMPLATE_SHEET_INDEX);
		}

		public HSSFSheet getTemplateSheet(String tempalteFilePath) {

			InputStream excelStream = null;
			HSSFSheet templateSheet = null;

			try {

				Resource templateExcelResource = new ClassPathResource(
						tempalteFilePath);
				excelStream = templateExcelResource.getInputStream();

				HSSFWorkbook templateWrokbook = new HSSFWorkbook(excelStream);

				templateSheet = templateWrokbook
						.getSheetAt(TEMPLATE_SHEET_INDEX);

			} catch (Exception e) {
				LOG.error(this, e);
			} finally {
				if (excelStream != null) {
					try {
						excelStream.close();
					} catch (IOException e) {
						// fail to close stream
					}
				}
			}

			return templateSheet;
		}

		/**
		 * remove the template sheet, we defined the first sheet that is the
		 * template sheet.
		 * 
		 * @param workbook
		 */
		public void removeTemplateSheet(HSSFWorkbook workbook) {
			removeSheet(workbook, TEMPLATE_SHEET_INDEX);
		}

		public HSSFSheet cloneTemplateNoAutoPageBreaks(HSSFWorkbook workbook,
				String sheetName) {
			HSSFSheet sheet = workbook
					.cloneSheet(TEMPLATE_SHEET_NO_AUTO_PAGEBREAK);
			workbook.setSheetName(workbook.getNumberOfSheets() - 1, sheetName);
			return sheet;
		}

		public void removeTemplateNoAutoPageBreaks(HSSFWorkbook workbook) {
			removeSheet(workbook, TEMPLATE_SHEET_NO_AUTO_PAGEBREAK);
		}

		/**
		 * ets the zoom magnification for the sheet. The zoom is expressed as a
		 * fraction. For example to express a zoom of 75% use 3 for the
		 * numerator and 4 for the denominator.
		 * 
		 * @param sheet
		 * @param numerator
		 * @param denominator
		 */
		public void setZoom(HSSFSheet sheet, int numerator, int denominator) {
			sheet.setZoom(numerator, denominator);
		}

		public void setRowFreeze(HSSFSheet sheet, int rowSplit) {
			sheet.createFreezePane(0, rowSplit);
		}
	}

	/**
	 * ExcelのRowの処理
	 * 
	 */
	public static class RowOperation extends ExcelOperation {

		/**
		 * copy a row
		 * 
		 * @param sheet
		 * @param row
		 * @param cloneSheet
		 * @param cloneRow
		 * @return HSSFRow
		 */
		public HSSFRow newCopyRow(HSSFSheet sheet, int row,
				HSSFSheet cloneSheet, int cloneRow) {

			HSSFRow newRow = createRow(sheet, row);

			HSSFRow templateRow = getRow(cloneSheet, cloneRow);

			copyRowHeight(newRow, templateRow);

			int maxColumn = templateRow.getPhysicalNumberOfCells();

			for (int col = 0; col < maxColumn; col++) {

				HSSFCell templateCell = templateRow.getCell(col);

				if (templateCell != null) {

					HSSFCell destCell = createCell(newRow, col);
					destCell.setCellStyle(templateCell.getCellStyle());
				}
			}

			return newRow;
		}

		/**
		 * Create a new row within the sheet and return the high level
		 * representation
		 * 
		 * @param sheet
		 * @param rownum
		 * @return HSSFRow
		 */
		public HSSFRow createRow(HSSFSheet sheet, int rownum) {
			return sheet.createRow(rownum);
		}

		/**
		 * Returns the logical row (not physical) 0-based. If you ask for a row
		 * that is not defined you get a null. This is to say row 4 represents
		 * the fifth row on a sheet.
		 * 
		 * @param sheet
		 * @param rowIndex
		 * @return HSSFRow
		 */
		public HSSFRow getRow(HSSFSheet sheet, int rowIndex) {
			return sheet.getRow(rowIndex);
		}

		/**
		 * Remove a row from this sheet. All cells contained in the row are
		 * removed as well
		 * 
		 * @param sheet
		 * @param row
		 */
		public void removeRow(HSSFSheet sheet, HSSFRow row) {
			sheet.removeRow(row);
		}

		/**
		 * Remove a row from this sheet. All cells contained in the row are
		 * removed as well
		 * 
		 * @param sheet
		 * @param row
		 */
		public void removeRow(HSSFSheet sheet, int rowIndex) {
			HSSFRow unusedRow = getRow(sheet, rowIndex);

			if (unusedRow != null) {
				removeRow(sheet, unusedRow);
			}
		}

		/**
		 * insert a new row between two exist row.Because the POI no support to
		 * insert a row between two rows. we must first move all row at the
		 * index one row below, then create a new row at the index.
		 * 
		 * @param workbook
		 * @param sheet
		 * @param rowIndex
		 * @return HSSFRow
		 */
		public HSSFRow insertRow(HSSFWorkbook workbook, HSSFSheet sheet,
				int rowIndex) {

			HSSFRow curRow = sheet.getRow(rowIndex);
			List<HSSFCell> copyCellList = new ArrayList<HSSFCell>();

			@SuppressWarnings("unchecked")
			Iterator<Cell> copyCellIt = curRow.iterator();
			while (copyCellIt.hasNext()) {
				copyCellList.add((HSSFCell) copyCellIt.next());
			}

			int rowCnt = sheet.getPhysicalNumberOfRows();
			sheet.createRow(rowCnt);

			/*
			 * for (int i = rowCnt-1; i >= rowIndex; i--) { sheet.shiftRows(i,
			 * i, 1); }
			 */
			sheet.shiftRows(rowIndex, rowCnt, 1);

			HSSFRow newRow = sheet.createRow(rowIndex);

			for (int cellIndex = 0; cellIndex < copyCellList.size(); cellIndex++) {
				HSSFCell copyCell = copyCellList.get(cellIndex);
				HSSFCell newCell = newRow.createCell(cellIndex);

				HSSFCellStyle cellStyle = workbook.createCellStyle();
				cellStyle.cloneStyleFrom(copyCell.getCellStyle());
				cellStyle.setBorderBottom(HSSFCellStyle.BORDER_NONE);

				newCell.setCellStyle(cellStyle);
			}

			return newRow;
		}

		/**
		 * reset the width of new row to the width of clone row
		 * 
		 * @param destRow
		 * @param cloneRow
		 */
		public void copyRowHeight(HSSFRow destRow, HSSFRow cloneRow) {
			destRow.setHeight(cloneRow.getHeight());
		}

		/**
		 * Use this to create new cells within the row at column and return it.
		 * 
		 * @param row
		 * @param column
		 * @return HSSFCell
		 */
		public HSSFCell createCell(HSSFRow row, int column) {
			return row.createCell(column);
		}
	}

	/**
	 * ExcelのColumnの処理
	 * 
	 */
	static class ColumnOperation extends ExcelOperation {

		/**
		 * reset the height of new column to the height of clone column
		 * 
		 * @param destSheet
		 * @param cloneSheet
		 * @param maxColumn
		 */
		public void copyColumnWidth(HSSFSheet destSheet, HSSFSheet cloneSheet,
				int maxColumn) {
			for (int col = 0; col < maxColumn; col++) {
				destSheet.setColumnWidth(col, cloneSheet.getColumnWidth(col));
			}
		}
	}

	/**
	 * ExcelのCellの処理
	 * 
	 */
	public static class CellOperation extends ExcelOperation {

		/**
		 * Get the hssfcell representing a given column (logical cell) 0-based.
		 * If you ask for a cell that is not defined then you get a null, unless
		 * you have set a different on the base workbook.
		 * 
		 * @param row
		 * @param cellnum
		 * @return HSSFCell
		 */
		public HSSFCell getCell(HSSFRow row, int cellnum) {
			return row.getCell(cellnum);
		}

		/**
		 * get the cell, if it not exist we will create a new cell.
		 * 
		 * @param row
		 * @param cellnum
		 * @return HSSFCell
		 */
		public HSSFCell getCellIfAbsentCreate(HSSFRow row, int cellnum) {
			HSSFCell cell = row.getCell(cellnum);

			if (cell == null) {
				cell = row.createCell(cellnum);
			}

			return cell;
		}
	}

	/**
	 * ExcelのCellFormatの処理
	 * 
	 */
	static class CellFormatOperation extends ExcelOperation {

		/**
		 * copy the cell merge format from template sheet
		 * 
		 * @param destSheet
		 * @param tempSheet
		 */
		public void copyMergeFormat(HSSFSheet destSheet, HSSFSheet tempSheet) {
			copyMergeFormat(destSheet, tempSheet, TEMPLATE_SHEET_INDEX);
		}

		/**
		 * copy the cell merge format from template sheet, offsetRow set the
		 * start row index
		 * 
		 * @param destSheet
		 * @param tempSheet
		 * @param offsetRow
		 */
		public void copyMergeFormat(HSSFSheet destSheet, HSSFSheet tempSheet,
				int offsetRow) {

			int index = 0;
			CellRangeAddress cellRangeAddress;

			while ((cellRangeAddress = tempSheet.getMergedRegion(index++)) != null) {

				CellRangeAddress newCellRangeAddress = cellRangeAddress.copy();
				newCellRangeAddress.setFirstRow(newCellRangeAddress
						.getFirstRow() + offsetRow);
				newCellRangeAddress.setLastRow(newCellRangeAddress.getLastRow()
						+ offsetRow);

				mergeCell(destSheet, newCellRangeAddress);

			}
		}

		/**
		 * adds a merged region of cells (hence those cells form one)
		 * 
		 * @param sheet
		 * @param firstRow
		 * @param lastRow
		 * @param firstCol
		 * @param lastCol
		 */
		public void mergeCell(HSSFSheet sheet, int firstRow, int lastRow,
				int firstCol, int lastCol) {
			CellRangeAddress region = new CellRangeAddress(firstRow, lastRow,
					firstCol, lastCol);

			mergeCell(sheet, region);
		}

		/**
		 * adds a merged region of cells (hence those cells form one)
		 * 
		 * @param sheet
		 * @param region
		 */
		public void mergeCell(HSSFSheet sheet, CellRangeAddress region) {
			sheet.addMergedRegion(region);
		}

		/**
		 * reset the merge region that contains the cell.
		 * 
		 * @param sheet
		 * @param cell
		 * @param firstRow
		 * @param lastRow
		 * @param firstCol
		 * @param lastCol
		 */
		public void resetMergeCell(HSSFSheet sheet, HSSFCell cell,
				int firstRow, int lastRow, int firstCol, int lastCol) {
			int regionIndex = cellInMergedRegion(sheet, cell);

			if (regionIndex >= 0) {
				removeCellMerge(sheet, regionIndex);
				mergeCell(sheet, firstRow, lastRow, firstCol, lastCol);
			}
		}

		public void revmoeMergedCell(HSSFSheet sheet, HSSFCell cell) {
			int regionIndex = cellInMergedRegion(sheet, cell);

			if (regionIndex >= 0) {
				removeCellMerge(sheet, regionIndex);
			}
		}

		/**
		 * check whether cell is in given region.if true return cell, otherwise
		 * return null.
		 * 
		 * @param sheet
		 * @param cell
		 * @return region index
		 */
		public int cellInMergedRegion(HSSFSheet sheet, HSSFCell cell) {
			int regionIndex = -1;

			if (cell != null) {

				int regionSize = sheet.getNumMergedRegions();
				int cellRowIndex = cell.getRowIndex();
				int cellColIndex = cell.getColumnIndex();

				for (int i = 0; i < regionSize; i++) {
					CellRangeAddress region = getMergedRegion(sheet, i);

					int startRow = region.getFirstRow();
					int endRow = region.getLastRow();
					int startCol = region.getFirstColumn();
					int endCol = region.getLastColumn();

					if (cellRowIndex >= startRow && cellRowIndex <= endRow
							&& cellColIndex >= startCol
							&& cellColIndex <= endCol) {
						regionIndex = i;
						break;
					}
				}
			}

			return regionIndex;
		}

		/**
		 * removes a merged region of cells (hence letting them free)
		 * 
		 * @param sheet
		 * @param index
		 */
		public void removeCellMerge(HSSFSheet sheet, int index) {
			sheet.removeMergedRegion(index);
		}

		/**
		 * the merged region at the specified index
		 * 
		 * @param sheet
		 * @param index
		 * @return
		 */
		public CellRangeAddress getMergedRegion(HSSFSheet sheet, int index) {
			return sheet.getMergedRegion(index);
		}
	}

	/**
	 * ExcelのCellStyleの処理
	 * 
	 */
	public static class CellStyleOperation extends ExcelOperation {

		Map<String, HSSFCellStyle> cellStylePool = new HashMap<String, HSSFCellStyle>();

		/**
		 * Cache cell style by key
		 * 
		 * @param workbook
		 * @param key
		 * @return HSSFCellStyle
		 */
		public HSSFCellStyle getCellStyleFromPool(HSSFWorkbook workbook,
				String key) {

			HSSFCellStyle cellStyle = cellStylePool.get(key);

			if (cellStyle == null) {
				cellStyle = createCellStyle(workbook);
				cellStylePool.put(key, cellStyle);
			}

			return cellStyle;
		}

		/**
		 * set the style for the cell.
		 * 
		 * @param cell
		 * @param style
		 */
		public void setCellStyle(HSSFCell cell, HSSFCellStyle style) {
			cell.setCellStyle(style);
		}

		/**
		 * get the style for the cell. This is a reference to a cell style
		 * contained in the workbook object.
		 * 
		 * @param cell
		 * @return HSSFCellStyle
		 */
		public HSSFCellStyle getCellStyle(HSSFCell cell) {
			return cell.getCellStyle();
		}

		/**
		 * create a new Cell style and add it to the workbook's style table
		 * 
		 * @param workbook
		 * @return HSSFCellStyle
		 */
		public HSSFCellStyle createCellStyle(HSSFWorkbook workbook) {
			return workbook.createCellStyle();
		}

		/**
		 * clone a cell style from a given style
		 * 
		 * @param workbook
		 * @param cloneCellStyle
		 * @return
		 */
		public HSSFCellStyle cloneCellStyle(HSSFWorkbook workbook,
				HSSFCellStyle cloneCellStyle) {

			int cloneStyleIndex = cloneCellStyle.getIndex();
			int maxStyleNum = workbook.getNumCellStyles();

			HSSFCellStyle cellStyle;

			// exist cell style
			if (cloneStyleIndex < maxStyleNum) {
				cellStyle = workbook.getCellStyleAt((short) cloneStyleIndex);
			}
			// not in current workbook's style table, may be from another
			// workbook we should create a new one
			else {
				cellStyle = createCellStyle(workbook);
				cloneCellStyleDirect(cellStyle, cloneCellStyle);
			}

			return cellStyle;
		}

		/**
		 * cell style copy
		 * 
		 * @param cellStyle
		 * @param cloneCellStyle
		 */
		public void cloneCellStyleDirect(HSSFCellStyle cellStyle,
				HSSFCellStyle cloneCellStyle) {
			cellStyle.cloneStyleFrom(cloneCellStyle);
		}

		private Map<String, List<CacheCellStyle>> colorCellStyleCache = new HashMap<String, List<CacheCellStyle>>();

		private HSSFCellStyle CELLSTYLE_NULL;

		static class CacheCellStyle {
			HSSFCellStyle ref;

			HSSFCellStyle target;
		}

		/**
		 * get the cell style with specified background color and font color, if
		 * no such style exist then create new one
		 * 
		 * @param workbook
		 * @param cellStyle
		 * @param bgColor
		 * @param fontColor
		 * @return HSSFCellStyle
		 */
		public HSSFCellStyle getCellStyleWithBgFontColor(HSSFWorkbook workbook,
				HSSFCellStyle cellStyle, String bgColor, String fontColor) {

			// no color
			if (StringUtils.isBlank(bgColor) && StringUtils.isBlank(fontColor)) {
				return cellStyle;
			}

			if (cellStyle == null) {
				if (CELLSTYLE_NULL == null) {
					CELLSTYLE_NULL = createCellStyle(workbook);
				}
				cellStyle = CELLSTYLE_NULL;
			}

			// index key for cached color style pool
			// cell style ->(mapping) color cell style pool
			// String key = String.valueOf(cellStyle.getIndex());
			String key = "";

			if (!StringUtils.isBlank(bgColor)) {
				key += bgColor;
			}

			if (!StringUtils.isBlank(fontColor)) {
				key += "_" + fontColor;
			}

			// check the style whether it is already in the cahce
			List<CacheCellStyle> cachedColorCellStyleList = colorCellStyleCache
					.get(key);
			HSSFCellStyle colorCellStyle = null;
			// HSSFCellStyle colorCellStyle = colorCellStyleCache.get(key);

			if (cachedColorCellStyleList != null) {

				Iterator<CacheCellStyle> cachedColorCellStyleIt = cachedColorCellStyleList
						.iterator();
				while (cachedColorCellStyleIt.hasNext()) {
					CacheCellStyle cachedCellStyle = cachedColorCellStyleIt
							.next();

					if (cachedCellStyle.ref.getIndex() == cellStyle.getIndex()) {
						colorCellStyle = cachedCellStyle.target;
						// if (LOG.isDebugEnabled()) {
						// LOG.debug("style cache hit");
						// }
						break;
					}
				}

			}

			// mismatch cached color
			if (colorCellStyle == null) {

				colorCellStyle = createCellStyle(workbook);
				cloneCellStyleDirect(colorCellStyle, cellStyle);

				CacheCellStyle cacheCellStype = new CacheCellStyle();
				cacheCellStype.ref = cellStyle;
				cacheCellStype.target = colorCellStyle;

				if (cachedColorCellStyleList == null) {
					cachedColorCellStyleList = new ArrayList<CacheCellStyle>();
				}
				cachedColorCellStyleList.add(cacheCellStype);

				colorCellStyleCache.put(key, cachedColorCellStyleList);
			}

			HSSFColor hsBgColor = null;
			if (!StringUtils.isBlank(bgColor)) {
				hsBgColor = getHSSFColor(bgColor);
				// LOG.debug("Background POI Color " + hsBgColor.toString());
			}

			if (hsBgColor != null) {
				colorCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				colorCellStyle.setFillForegroundColor(hsBgColor.getIndex());
			}

			if (!StringUtils.isBlank(fontColor)) {
				HSSFFont colorFont = getFontColorIfAbsent(workbook,
						colorCellStyle, fontColor);
				colorCellStyle.setFont(colorFont);
			}

			return colorCellStyle;
		}

		/*--------------------------------  Font ----------------------------------------*/

		/**
		 * create a new Font and add it to the workbook's font table
		 * 
		 * @param workbook
		 * @return HSSFFont
		 */
		public HSSFFont createFont(HSSFWorkbook workbook) {
			return workbook.createFont();
		}

		/**
		 * gets the font for this style
		 * 
		 * @param workbook
		 * @param cellStyle
		 * @return HSSFFont
		 */
		public HSSFFont getFont(HSSFWorkbook workbook, HSSFCellStyle cellStyle) {
			return cellStyle.getFont(workbook);
		}

		/**
		 * gets the font for this style, if not exist to create new one
		 * 
		 * @param workbook
		 * @param cellStyle
		 * @return HSSFFont
		 */
		public HSSFFont getFontIfAbsentCreat(HSSFWorkbook workbook,
				HSSFCellStyle cellStyle) {

			HSSFFont font = cellStyle.getFont(workbook);

			if (font == null) {
				font = createFont(workbook);
			}

			return font;
		}

		private Map<String, List<CacheFont>> colorFontsCache = new HashMap<String, List<CacheFont>>();

		private HSSFFont FONT_NULL = null;

		static class CacheFont {
			HSSFFont ref;

			HSSFFont target;
		}

		/**
		 * set a color to font, if it is already exist return it.
		 */
		public HSSFFont getFontColorIfAbsent(HSSFWorkbook workbook,
				HSSFCellStyle cellStyle, String color) {

			if (cellStyle == null) {
				throw new IllegalArgumentException(
						"setFontColor to a null cell style.");
			}

			HSSFFont currentFont = getFont(workbook, cellStyle);
			if (currentFont == null) {

				if (FONT_NULL == null) {
					FONT_NULL = createFont(workbook);
				}

				currentFont = FONT_NULL;
			}

			String key = color;

			List<CacheFont> cacheColorFontList = colorFontsCache.get(key);
			HSSFFont colorFont = null;

			if (cacheColorFontList != null) {

				Iterator<CacheFont> cacheFontIt = cacheColorFontList.iterator();

				while (cacheFontIt.hasNext()) {

					CacheFont cacheFont = cacheFontIt.next();

					if (cacheFont.ref.getIndex() == currentFont.getIndex()) {
						colorFont = cacheFont.target;
						if (LOG.isDebugEnabled()) {
							LOG.debug("font cache hit");
						}
						break;
					}
				}

			}

			// no font using this color in cache, create
			if (colorFont == null) {

				colorFont = createFont(workbook);
				try {
					BeanUtils.copyProperties(colorFont, currentFont);
				} catch (Exception e) {
					LOG.error("clone font fail.");
					LOG.error(this, e);
				}

				CacheFont cahceFont = new CacheFont();
				cahceFont.ref = currentFont;
				cahceFont.target = colorFont;

				if (cacheColorFontList == null) {
					cacheColorFontList = new ArrayList<CacheFont>();
				}

				cacheColorFontList.add(cahceFont);

				colorFontsCache.put(key, cacheColorFontList);
			}

			HSSFColor pioColor = getHSSFColor(color);
			if (pioColor != null) {
				colorFont.setColor(pioColor.getIndex());
			}

			return colorFont;
		}

		/*--------------------------------  Color ----------------------------------------*/

		public static String GREY_50_PERCENT_HEX = "808080";

		public static String GREY_25_PERCENT_HEX = "C0C0C0";

		POIColorIndex poiColorIndex = new POIColorIndex();

		/**
		 * get the POI HSSFColor object by the hex string code of color
		 * 
		 * @param hexColor
		 * @return
		 */
		public HSSFColor getHSSFColor(String hexColor) {

			if (StringUtils.isBlank(hexColor))
				return null;

			return poiColorIndex.getPOIColor(hexColor.toLowerCase());
		}

		/*--------------------------------  Border ----------------------------------------*/

		/**
		 * set the type of border to use for the bottom border of the cell
		 */
		public void setBorderBottom(HSSFCell cell, short border) {
			setBorderBottom(getCellStyle(cell), border);
		}

		/**
		 * set the type of border to use for the bottom border of the cell
		 * 
		 * @param cellStyle
		 * @param border
		 */
		public void setBorderBottom(HSSFCellStyle cellStyle, short border) {
			cellStyle.setBorderBottom(border);
		}
	}

	/**
	 * Header Footer
	 */
	public static class HeaderFooterOperation extends ExcelOperation {

		/**
		 * set Footer page number (style: 1/2)
		 * 
		 * @param sheet
		 */
		public void setFooterPageNumberDefault(HSSFSheet sheet) {
			HSSFFooter footer = sheet.getFooter();
			footer.setCenter(HSSFFooter.page() + "/" + HSSFFooter.numPages());
		}

		/**
		 * set print header of sheet
		 * 
		 * @param sheet
		 * @param headerInfo
		 */
		public void setPrintHeader(HSSFSheet sheet, String headerInfo) {
			HSSFHeader header = sheet.getHeader();
			header.setCenter(headerInfo);
		}

	}

	/**
	 * Excel Print Area
	 */
	public static class PrintOperation extends ExcelOperation {

		public HSSFPrintSetup getPrintSetup(HSSFSheet sheet) {
			return sheet.getPrintSetup();
		}

		/**
		 * set the number of pages wide and height to fit the sheet
		 * 
		 * @param sheet
		 * @param fitWidth
		 * @param fitHeight
		 */
		public void initPrintAreaFit(HSSFSheet sheet, short fitWidth,
				short fitHeight) {

			// sheet.setAutobreaks(true);

			// reset RowBreaks
			int[] rowBreaks = sheet.getRowBreaks();
			for (int i = 0; i < rowBreaks.length; i++) {
				sheet.removeRowBreak(rowBreaks[i]);
			}

			// reset ColumnBreaks
			int[] clomumnBreaks = sheet.getColumnBreaks();
			for (int i = 0; i < clomumnBreaks.length; i++) {
				sheet.removeColumnBreak(clomumnBreaks[i]);
			}

			// sheet.setFitToPage(true);

			HSSFPrintSetup printSetup = getPrintSetup(sheet);
			if (fitWidth > 0)
				printSetup.setFitWidth(fitWidth);
			if (fitHeight > 0)
				printSetup.setFitHeight(fitHeight);
			printSetup.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
		}

		public void addPrintRowBreakPage(HSSFSheet sheet, int row) {
			sheet.setRowBreak(row);
		}

		public void addPrintColumnBreakPage(HSSFSheet sheet, int column) {
			sheet.setColumnBreak(column);
		}

		/**
		 * For the Convenience of Java Programmers maintaining pointers.
		 * 
		 * @param workbook
		 * @param sheet
		 * @param startColumn
		 * @param endColumn
		 * @param startRow
		 * @param endRow
		 */
		public void setPrintArea(HSSFWorkbook workbook, HSSFSheet sheet,
				int startColumn, int endColumn, int startRow, int endRow) {
			workbook.setPrintArea(workbook.getSheetIndex(sheet), startColumn,
					endColumn, startRow, endRow);
		}

		public void setPrintArea(HSSFWorkbook workbook, int sheetIndex,
				int startColumn, int endColumn, int startRow, int endRow) {
			workbook.setPrintArea(sheetIndex, startColumn, endColumn, startRow,
					endRow);
		}

		public void setPrintArea(HSSFWorkbook workbook, int sheetIndex,
				List<int[]> printArea) {

			StringBuilder printAreaBuffer = new StringBuilder();

			int i = 0;
			for (int[] area : printArea) {

				if (i > 0) {
					printAreaBuffer.append(",");
				}

				CellReference cell = new CellReference(area[0], area[1], true,
						true);
				String reference = cell.formatAsString();

				cell = new CellReference(area[2], area[3], true, true);
				reference = reference + ":" + cell.formatAsString();

				printAreaBuffer.append(reference);

				i++;
			}

			workbook.setPrintArea(sheetIndex, printAreaBuffer.toString());
		}

		public int[] createPrintArea(int startRow, int startColumn, int endRow,
				int endColumn) {
			int[] printArea = new int[4];

			printArea[0] = startRow;
			printArea[1] = startColumn;

			printArea[2] = endRow;
			printArea[3] = endColumn;

			return printArea;
		}

	}

	POIColorIndex _POIColorIndex = new POIColorIndex();

	public HSSFColor getPoiHSSFColorByName(String name) {
		return _POIColorIndex.getPOIColorByName(name);
	}

	private Map<String, Integer> sheetMaxRows = new HashMap<String, Integer>();

	private Map<String, Integer> sheetMaxCols = new HashMap<String, Integer>();

	public void putMaxRowForSheet(String sheetName, Integer maxRow) {
		sheetMaxRows.put(sheetName, maxRow);
	}

	public void putMaxColForSheet(String sheetName, Integer maxCol) {
		sheetMaxCols.put(sheetName, maxCol);
	}

	public Integer getMaxRowForSheet(String sheetName) {
		Integer maxRow = sheetMaxRows.get(sheetName);

		if (maxRow == null) {
			LOG.warn("no max row info for sheet [" + sheetName + "]");
		}

		return maxRow;
	}

	public Integer getMaxColForSheet(String sheetName) {
		Integer maxCol = sheetMaxCols.get(sheetName);

		if (maxCol == null) {
			LOG.warn("no max column info for sheet [" + sheetName + "]");
		}

		return maxCol;
	}

	private Map<String, List<Integer>> sheetRowBreaks = new HashMap<String, List<Integer>>();

	public void putRowBreakForSheet(String sheetName, Integer breakAtRow) {

		List<Integer> rowBreaks = sheetRowBreaks.get(sheetName);
		if (rowBreaks == null) {
			rowBreaks = new ArrayList<Integer>();
			sheetRowBreaks.put(sheetName, rowBreaks);
		}

		rowBreaks.add(breakAtRow);
	}

	public List<Integer> getRowBreaksAtSheet(String sheetName) {
		return sheetRowBreaks.get(sheetName);
	}

	public void autoCreatePrintAreaAndPageBreaks(HSSFWorkbook workbook) {

		int size = workbook.getNumberOfSheets();
		for (int i = 0; i < size; i++) {
			HSSFSheet printSheet = SheetOp.getSheet(workbook, i);
			String sheetName = SheetOp.getSheetName(printSheet);
			Integer maxRow = getMaxRowForSheet(sheetName);
			Integer maxCol = getMaxColForSheet(sheetName);

			// Print Area
			if (maxRow != null && maxCol != null) {
				PrintOp.setPrintArea(workbook, printSheet, 0, maxCol - 1, 0,
						maxRow - 1);
			}

			// Page Break
			List<Integer> rowBreaks = getRowBreaksAtSheet(sheetName);
			if (rowBreaks != null) {

				for (Integer rowBreak : rowBreaks) {
					PrintOp.addPrintRowBreakPage(printSheet, rowBreak);
				}

				PrintOp.addPrintColumnBreakPage(printSheet, maxCol - 1);
			}

		}
	}

	private static final int TRUNCATE_THRESHOLD = 10;

	public String truncateString(String content) {
		return truncateString(content, TRUNCATE_THRESHOLD);
	}

	public String truncateString(String content, int threshold) {

		if (StringUtils.isBlank(content) || content.length() <= threshold)
			return content;

		return content.substring(0, threshold) + "...";
	}

	/**
	 * find the start and end index,in which data is at same day. when you want
	 * to use this method, you must assure that the data in list is sort by
	 * start_dt ASC.
	 * 
	 * @param scheduleList
	 * @param day
	 * @return int[]
	 */

	// public int[] findSameDayRange(List<ScheduleBean> scheduleList, int day) {
	// int range[] = new int[3];
	// // start
	// range[0] = -1;
	// // end
	// range[1] = -1;
	// // size
	// range[2] = 0;
	//
	// int matchedDayIndex = -1;
	// if (scheduleList != null && scheduleList.size() > 0) {
	// int low = 0;
	// int high = scheduleList.size() - 1;
	// while (low <= high) {
	// int mid = (high + low) >>> 2;
	//
	// ScheduleBean scheudle = scheduleList.get(mid);
	// int eventDay = scheudle.getStartDt().getDayOfMonth();
	// if (eventDay < day) {
	// low = mid + 1;
	// } else if (eventDay > day) {
	// high = mid - 1;
	// } else {
	// matchedDayIndex = low;
	// break;
	// }
	// }
	//
	// if (matchedDayIndex >= 0) {
	// int start = matchedDayIndex;
	// for (; start >= 0; start--) {
	// ScheduleBean scheudle = scheduleList.get(start);
	// int eventDay = scheudle.getStartDt().getDayOfMonth();
	//
	// if (eventDay != day) {
	// break;
	// }
	// }
	// range[0] = start;
	//
	// int end = matchedDayIndex;
	// for (; end < scheduleList.size(); end++) {
	// ScheduleBean scheudle = scheduleList.get(end);
	// int eventDay = scheudle.getStartDt().getDayOfMonth();
	//
	// if (eventDay != day) {
	// break;
	// }
	// }
	// range[1] = end;
	//
	// range[2] = end - start + 1;
	// }
	//
	// }
	//
	// return range;
	// }
}
