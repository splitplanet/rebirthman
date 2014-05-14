package baileys.Brad.Work_Recorder;


import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import jxl.*;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.*;
import jxl.write.Label;

//import jxl.JXLException;

	//URL of article with details of this code
	//http://www.vogella.com/articles/JavaExcel/article.html


public class WriteExcel {

	  private WritableCellFormat timesBold;
	  private WritableCellFormat times;
	  private String inputFile;
	  Context c;
	  
	  
	  public WriteExcel(Context cIn) {
		// TODO Auto-generated constructor stub
		  c = cIn;
	}
	  
	  
	public void setOutputFile(String inputFile) {
	  this.inputFile = inputFile;
	  }
	
	  public void write(String Jobname) throws IOException, WriteException {
		  
		//  File root = Environment.getExternalStorageDirectory(  );
	    File file = new File( Environment.getExternalStorageDirectory(  ) + inputFile);

	    
	    WorkbookSettings wbSettings = new WorkbookSettings();
	
	    wbSettings.setLocale(new Locale("en", "EN"));
	
	    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
	    workbook.createSheet("Report", 0);
	    WritableSheet excelSheet = workbook.getSheet(0);
	    createLabel(excelSheet);
	    createContent(excelSheet,Jobname);
	
	    workbook.write();
	    workbook.close();
	  }
	
	  private void createLabel(WritableSheet sheet)
	      throws WriteException {
	    // Lets create a times font
	    WritableFont arial12pt = new WritableFont(WritableFont.TAHOMA, 12);
	    // Define the cell format
	    times = new WritableCellFormat(arial12pt);
	    // Lets automatically wrap the cells
	    times.setWrap(true);
	    
	    //Number format
	    NumberFormat POUND = new NumberFormat( NumberFormat.CURRENCY_POUND);
	    
	    
	    // create create a bold font with underlines
	    WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TAHOMA, 12, WritableFont.BOLD);
	    timesBold = new WritableCellFormat(times10ptBoldUnderline);
	    // Lets automatically wrap the cells
	    timesBold.setWrap(true);
	
	    CellView cv = new CellView();
	    cv.setFormat(times);
	    
	    CellView cv2 = new CellView();
	    cv2.setFormat(timesBold);
	    //cv.setAutosize(true);
	
	    // Write a few headers
//	    addCaption(sheet, 0, 0, "Header 1");
//	    addCaption(sheet, 1, 0, "This is another header");
	    
	//	Set column widths
	    sheet.setColumnView(0,24);
	    sheet.setColumnView(1,20);
	    sheet.setColumnView(2,50);
	    sheet.setColumnView(3,11);
	    sheet.setColumnView(4,11);
	    sheet.setColumnView(5,12);
	    sheet.setColumnView(6,17);
	    
	//	merging cells
	    sheet.mergeCells( 1,3, 2,3 );
	    sheet.mergeCells( 1,4, 2,4 );
	    sheet.mergeCells( 1,5, 2,5 );
	    sheet.mergeCells( 1,6, 2,6 );
	    
	//  Seting row to bold + 10pts + underlined
	//    sheet.setRowView(8, cv);
	//	There is a height conversion of 1/20th !!!!!!!!!!!!!!
	    sheet.setRowView(8, cv2);
	    sheet.setRowView(8, 900);
	    
	    
	//set colour
	    
	    
	    
	    
	
	  }
	
	  @SuppressWarnings("deprecation")
	private void createContent(WritableSheet sheet,String Jobname) throws WriteException,
	      RowsExceededException {

		  	sql info = new sql( this.c )
		  	;
		    // create create a bold font with underlines
		    WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TAHOMA, 12, WritableFont.BOLD);
		    WritableCellFormat timesBold1 = new WritableCellFormat(times10ptBoldUnderline);
		    // Lets automatically wrap the cells
		    timesBold1.setWrap(true);
		    timesBold1.setBackground(Colour.GRAY_25, Pattern.SOLID );
		    timesBold1.setAlignment(Alignment.LEFT);
		    timesBold1.setVerticalAlignment(VerticalAlignment.CENTRE);
		    CellView cv2 = new CellView();
		    cv2.setFormat(timesBold1);

		    WritableCellFormat timesBold2 = new WritableCellFormat(times10ptBoldUnderline);
		    // Lets automatically wrap the cells
		    timesBold2.setWrap(true);
		    timesBold2.setBackground(Colour.GRAY_25, Pattern.SOLID );
		    timesBold2.setAlignment(Alignment.CENTRE);
		    timesBold2.setVerticalAlignment(VerticalAlignment.CENTRE);

		    
		    WritableFont times10ptBoldUnderline2 = new WritableFont(WritableFont.TAHOMA, 12);
		    WritableCellFormat timesBold3 = new WritableCellFormat(times10ptBoldUnderline);
		    // Lets automatically wrap the cells
		    timesBold3.setWrap(true);
		    timesBold3.setBackground(Colour.GRAY_25, Pattern.SOLID );
		    timesBold3.setAlignment(Alignment.LEFT);
		    timesBold3.setVerticalAlignment(VerticalAlignment.CENTRE);
		    timesBold3.setBorder( Border.TOP , BorderLineStyle.THIN);
		    
		    WritableCellFormat timesBold4 = timesBold3;
		    timesBold4.setBorder( Border.TOP , BorderLineStyle.NONE);
		    timesBold4.setBorder( Border.BOTTOM , BorderLineStyle.THIN);
		    
		    WritableCellFormat timesBold5 = timesBold3;
		    timesBold5.setBorder( Border.TOP , BorderLineStyle.THIN);
		    timesBold5.setBorder( Border.BOTTOM , BorderLineStyle.DOUBLE);

		    WritableCellFormat timesBold6 = new WritableCellFormat(times10ptBoldUnderline2);
		    timesBold2.setWrap(true);
		    timesBold2.setBackground(Colour.GRAY_25, Pattern.SOLID );
		    timesBold2.setAlignment(Alignment.LEFT);
		    timesBold2.setVerticalAlignment(VerticalAlignment.CENTRE);

		    //Number format
		    NumberFormat POUND = new NumberFormat( "£###,###,##0.00");
		    WritableCellFormat POUNDwrite = new WritableCellFormat(POUND);
		    
		    POUNDwrite.setBackground(jxl.format.Colour.GRAY_25,Pattern.SOLID);
		    POUNDwrite.setFont(times10ptBoldUnderline);
		    
		    
		    WritableCellFormat POUNDwrite2 = new WritableCellFormat(POUND);
	//	    POUNDwrite2.setBackground(jxl.format.Colour.GRAY_25,Pattern.SOLID);
		    POUNDwrite2.setFont(times10ptBoldUnderline2);
		    
		    WritableCellFormat POUNDwrite3 = POUNDwrite;
		    POUNDwrite3.setBorder( Border.TOP , BorderLineStyle.THIN);
		    POUNDwrite3.setBorder( Border.BOTTOM , BorderLineStyle.NONE);
		    
		    WritableCellFormat POUNDwrite4 = POUNDwrite3;
		    POUNDwrite4.setBorder( Border.TOP , BorderLineStyle.NONE);
		    POUNDwrite4.setBorder( Border.BOTTOM , BorderLineStyle.THIN);
		    
		    WritableCellFormat POUNDwrite5 = POUNDwrite4;
		    POUNDwrite5.setBorder( Border.TOP , BorderLineStyle.THIN);
		    POUNDwrite5.setBorder( Border.BOTTOM , BorderLineStyle.DOUBLE);


		    //WritableCellFormat timesBold4 = new WritableCellFormat(times10ptBoldUnderline2);
		    // Lets automatically wrap the cells
		    


		  
		  //Get All Data
			info.open();
			String[]       JobDetailsStringVec;
			String[][] InvoiceDetailsStringVec;
			JobDetailsStringVec 	= info.getJobdetails(Jobname);
			InvoiceDetailsStringVec = info.getInvoiceDetails(Jobname);
			info.close();			

			String ed_location 	= JobDetailsStringVec[0];
			String ed_descrip 	= JobDetailsStringVec[1];
			String ed_jobname 	= JobDetailsStringVec[2];
			String ed_Customer 	= JobDetailsStringVec[4];
			String ed_Labour 	= JobDetailsStringVec[5];
			
		
			//Deal with job details
			
			addLabelFormat(sheet, 0, 1, "Invoice Details", timesBold1 );
			addLabelFormat(sheet, 0, 3, "Job name", timesBold1 );
			addLabelFormat(sheet, 0, 4, "Job Description", timesBold1 );
			addLabelFormat(sheet, 0, 5, "Job Location", timesBold1 );
			addLabelFormat(sheet, 0, 5, "Invoive Date", timesBold1 );
			addLabelFormat(sheet, 0, 6, "Customer name", timesBold1 );
			
			 Date today = new Date();
			 String dd =  String.valueOf( today.getDate() );
			 String mm = String.valueOf(today.getMonth()+1); //January is 0!
			 String yyyy = String.valueOf(today.getYear() + 1900);

			 addLabelFormat(sheet, 1, 3,ed_jobname, timesBold6 );
			 addLabelFormat(sheet, 1, 4,ed_descrip, timesBold6 );
			 addLabelFormat(sheet, 1, 5,ed_location, timesBold6  );
			 addLabelFormat(sheet, 1, 5,yyyy+"-"+mm+"-"+dd, timesBold6);
			 addLabelFormat(sheet, 1, 6,ed_Customer, timesBold6 );
			
			
			String[] row8 = { "Date time", "Item Name", "Item Description", "Item Price", "Quantity", "Total item Price"  };
			
			for (int i = 0; i< row8.length; i++ ){
				
				addLabelFormat(sheet, i, 8, row8[i] , timesBold2 );
				
			}
			
//			addLabel(sheet, 0, 8, "Date time" );
//			addLabel(sheet, 1, 8, "Item Name" );
//			addLabel(sheet, 2, 8, "Item Description" );
//			addLabel(sheet, 3, 8, "Item Price" );
//			addLabel(sheet, 4, 8, "Quantity" );
//			addLabel(sheet, 5, 8, "Total item Price" );
			
			
			for (int i = 0; i < InvoiceDetailsStringVec.length - 1; i++) {
				
				addLabel(sheet, 0, i+9, InvoiceDetailsStringVec[i][2] );
				addLabel(sheet, 1, i+9, InvoiceDetailsStringVec[i][0] );
				addLabel(sheet, 2, i+9, InvoiceDetailsStringVec[i][3] );
				
				
				addNumberFormat(sheet, 3, i+9, Integer.parseInt(InvoiceDetailsStringVec[i][1]), POUNDwrite2 ) ;
				
				
				
				addNumberFormat(sheet, 4, i+9, Integer.parseInt(InvoiceDetailsStringVec[i][4]), times ) ;
				addNumberFormat(sheet, 5, i+9, 	Integer.parseInt(InvoiceDetailsStringVec[i][4])  * Integer.parseInt(InvoiceDetailsStringVec[i][1]),
						POUNDwrite2 ) ;
				
			}
			
//			InvoiceDetailsStringVec[ InvoiceDetailsStringVec.length][0]
			
			
			
			double netTotal	=  Double.parseDouble(InvoiceDetailsStringVec[ InvoiceDetailsStringVec.length - 1][0]);
		
			double labourprice	= Double.parseDouble(ed_Labour);
			double grandtotal	= (double) netTotal + labourprice;
			
			addLabelFormat(sheet, 3,InvoiceDetailsStringVec.length+10, "Net Total",timesBold3 );
			addNumberFormat(sheet, 5, InvoiceDetailsStringVec.length+10, (double) netTotal , POUNDwrite3 ) ;
			addLabelFormat(sheet, 3,InvoiceDetailsStringVec.length+11, "Labour cost",timesBold4 );
			addNumberFormat(sheet, 5, InvoiceDetailsStringVec.length+11, labourprice , POUNDwrite3 ) ;	
			
			addLabelFormat(sheet, 3,InvoiceDetailsStringVec.length+13, "Grand Total",timesBold5 );
			addNumberFormat(sheet, 5, InvoiceDetailsStringVec.length+13, grandtotal , POUNDwrite5 ) ;	
			
			//Merge columns
			sheet.mergeCells( 3,InvoiceDetailsStringVec.length+10 , 4,InvoiceDetailsStringVec.length+10);
			sheet.mergeCells( 3,InvoiceDetailsStringVec.length+11 , 4,InvoiceDetailsStringVec.length+11);
			sheet.mergeCells( 3,InvoiceDetailsStringVec.length+13 , 4,InvoiceDetailsStringVec.length+13);
			
			
			
			
//			int InvoiceDetailsStringVecSize = InvoiceDetailsStringVec.length;
			
			/*
			result[i][0] = c.getString(iITEM).toString();
			result[i][1] = c.getString(iPRICE).toString();
			result[i][2] = c.getString(iDATE).toString();
			result[i][3] = c.getString(iDES).toString();
			result[i][4] = c.getString(iQuant).toString();*/

		  
		  // Write Job Names
		  
		  
		  
		  
		//Examples of how to write stuff  
		//Write numbers
//	    for (int i = 1; i < 10; i++) {
//	      // First column
//	      addNumber(sheet, 0, i, i + 10);
//	      // Second column
//	      addNumber(sheet, 1, i, i * i);
//	    }
//	    // Lets calculate the sum of it
//	    StringBuffer buf = new StringBuffer();
//	    buf.append("SUM(A2:A10)");
//	    Formula f = new Formula(0, 10, buf.toString());
//	    sheet.addCell(f);
//	    buf = new StringBuffer();
//	    buf.append("SUM(B2:B10)");
//	    f = new Formula(1, 10, buf.toString());
//	    sheet.addCell(f);
//	
//	    // now a bit of text
//	    for (int i = 12; i < 20; i++) {
//	      // First column
//	      addLabel(sheet, 0, i, "Boring text " + i);
//	      // Second column
//	      addLabel(sheet, 1, i, "Another text");
//	    }
	  }
	
	  private void addCaption(WritableSheet sheet, int column, int row, String s)
	      throws RowsExceededException, WriteException {
	    Label label;
	    label = new Label(column, row, s, timesBold);
	    sheet.addCell(label);
	  }
	
	  private void addNumber(WritableSheet sheet, int column, int row,
	      int integer) throws WriteException, RowsExceededException {
	    Number number;
	    number = new Number(column, row, integer, times);
	    sheet.addCell(number);
	  }
	  

		
	  private void addNumber(WritableSheet sheet, int column, int row,
	      double dNumber) throws WriteException, RowsExceededException {
	    Number number;
	    number = new Number(column, row, dNumber, times);
	    sheet.addCell(number);
	  }
	
	  private void addLabel(WritableSheet sheet, int column, int row, String s)
	      throws WriteException, RowsExceededException {
	    Label label;
	    label = new Label(column, row, s, times);
	    sheet.addCell(label);
	  }
	  

		
	private void addLabelFormat(WritableSheet sheet, int column, int row, String s,   WritableCellFormat cF )
	      throws WriteException, RowsExceededException {
	    Label label;
	    label = new Label(column, row, s, cF);
	    sheet.addCell(label);
	  }

	
	  private void addNumberFormat(WritableSheet sheet, int column, int row,
	      int integer,   WritableCellFormat cF ) throws WriteException, RowsExceededException {
	    Number number;
	    number = new Number(column, row, integer, cF);
	    sheet.addCell(number);
	  }
	  

	  private void addNumberFormat(WritableSheet sheet, int column, int row,
	      double dNumber,   WritableCellFormat cF ) throws WriteException, RowsExceededException {
	    Number number;
	    number = new Number(column, row, dNumber, cF);
	    sheet.addCell(number);
	  }
	
		public void MasterXLSfile( String fileName, String Jobname )  {
			  
			  
			    WriteExcel test = new WriteExcel( c );
			    test.setOutputFile(fileName);
			    
				try {
					test.write(Jobname);
				} catch (WriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
			    
			    System.out.println("Please check the result file under c:/temp/lars.xls ");
			  
			  
		  }
	
	  public void main(String[] args)  {
	  }
}
	

