package baileys.Brad.Work_Recorder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class sql {

	/* All can access *//* Stays the same */

	/* List columns */
	public static final String KEY_ROWID = "_id";
	public static final String KEY_JOBNAME = "JOB_NAME";
	public static final String KEY_LOCATION = "LOCATION";
	public static final String KEY_DESCRIPTION = "DESCRIPTION";
	public static final String KEY_LABOUR_PRICE = "LABOUR_PRICE";
	public static final String KEY_CUSTOMER = "CUSTOMER_FULL_NAME";

	public static final String KEY_ITEM_ID = "ITEM_ID";
	public static final String KEY_ITEM_NAME = "ITEM_NAME";
	public static final String KEY_ITEM_DESC = "ITEM_DESC";
	public static final String KEY_PRICE = "ITEM_PRICE";

	public static final String JOB_TABLE_JOBNAME = "JOB_NAME";
	public static final String JOB_TABLE_ITEMNAME = "ITEM_NAME";
	public static final String JOB_TABLE_QUANTITY = "QUANTITY";


	private static final String DATABASE_NAME = "SQLDB";

	/* List tables */
	private static final String DATABASE_JOBS = "JOBS_TABLE";
	private static final String DATABASE_ITEM = "JOBS_ITEM";
	private static final String DATABASE_JOB_TABLE = "JOB_TABLE";

	/* Database version not really sure why you need this! */
	private static final int DATABASE_VERSION = 80;

	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			
			// drop tables
			
			//db.execSQL("drop table " + DATABASE_JOBS);
			//db.execSQL("drop table " + DATABASE_ITEM);
            //db.execSQL("drop table " + DATABASE_JOB_TABLE);

			/**
			 * -------------------------------------------------
			 * 
			 * ------- Table to detail job information
			 * 
			 * -------------------------------------------------
			 */
			db.execSQL("CREATE TABLE " + DATABASE_JOBS + " ( " + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT" + "," + KEY_JOBNAME
					+ " CHAR(100) NOT NULL" + "," + KEY_LOCATION
					+ " VARCHAR(100) " + "," + KEY_DESCRIPTION
					+ " VARCHAR(1000), " + KEY_LABOUR_PRICE + " NUMBER(8,2),"+ KEY_CUSTOMER + " varchar(200) );");

			/**
			 * ----------------------------------------------------------------
			 * 
			 * ------- Table for job items
			 * 
			 * ------------------------------------------------- *
			 */

			db.execSQL("CREATE TABLE " + DATABASE_ITEM + " ( " + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT" + ","
					+ KEY_ITEM_NAME + " CHAR(100) NOT NULL"
					+
					// "," + KEY_ITEM_ID + " INTEGER  AUTOINCREMENT" +
					"," + KEY_ITEM_DESC + " VARCHAR(1000) " + "," + KEY_PRICE
					+ " NUMBER(8,2) " + ");");

			/**
			 * -------------------------------------------------
			 * 
			 * ------- Transaction table that links the previous tables
			 * 
			 * -------------------------------------------------
			 */

			db.execSQL("CREATE TABLE "
					+ DATABASE_JOB_TABLE
					+ " ( "
					+ KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT"
					+ ","
					+ JOB_TABLE_JOBNAME
					+ " CHAR(100) NOT NULL"
					+ ","
					+ JOB_TABLE_ITEMNAME
					+ " CHAR(100) NOT NULL"
					+ ",    date                        datetime default current_timestamp,"
					+ JOB_TABLE_QUANTITY 
					+ " int"
					+ ");");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_JOBS + ";");
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_ITEM + ";");
			onCreate(db);
		}

	}

	public sql(Context c) {
		ourContext = c;
	}
	


	public sql open() {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		ourHelper.close();
	}

	public long creatEntry(String full_JobName, String full_location,
			String full_desc,String labour_cost,String full_Customer_name) {
		// TODO Auto-generated method stub
		/* set up cv for insert method */

		ContentValues cv = new ContentValues();
		cv.put(KEY_JOBNAME, full_JobName);
		cv.put(KEY_LOCATION, full_location);
		cv.put(KEY_DESCRIPTION, full_desc);
		cv.put(KEY_LABOUR_PRICE, labour_cost);
		cv.put(KEY_CUSTOMER, full_Customer_name);

		/* Insert all of puts into table */
		return ourDatabase.insert(DATABASE_JOBS, null, cv);

	}

	public long AddToJobSQL(String full_JobName, String full_item_name,String quantity) {
		// TODO Auto-generated method stub
		/* set up cv for insert method */

		ContentValues cv = new ContentValues();
		cv.put(JOB_TABLE_JOBNAME, full_JobName);
		cv.put(JOB_TABLE_ITEMNAME, full_item_name);
		cv.put(JOB_TABLE_QUANTITY, quantity);

		/* Insert all of puts into table */
		return ourDatabase.insert(DATABASE_JOB_TABLE, null, cv);

	}

	public long Create_job_item(String item_name, String item_desc,
			String item_price) {
		// TODO Auto-generated method stub
		/* set up cv for insert method */
		ContentValues cv = new ContentValues();
		cv.put(KEY_ITEM_NAME, item_name);
		cv.put(KEY_ITEM_DESC, item_desc);
		cv.put(KEY_PRICE, item_price);
		/* Insert all of puts into table */
		return ourDatabase.insert(DATABASE_ITEM, null, cv);

	}

	public void DeleteEntry_item(String full_JobName) {
		// TODO Auto-generated method stub
		/* set up cv for insert method */

		/* Insert all of puts into table */
		String where_clause = "ITEM_NAME = '" + full_JobName + "'";
		ourDatabase.delete(DATABASE_ITEM, where_clause, null);

	}

	public long DeleteEntry(String full_JobName) {
		// TODO Auto-generated method stub
		/* set up cv for insert method */

		/* Insert all of puts into table */
		String where_clause = "trim(upper(JOB_NAME)) = trim(upper('"
				+ full_JobName + "'))";
		return ourDatabase.delete(DATABASE_JOBS, where_clause, null);

	}

	public String getData() {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_JOBNAME, KEY_LOCATION,
				KEY_DESCRIPTION };
		Cursor c = ourDatabase.query(DATABASE_JOBS, columns, null, null, null,
				null, null);
		String result = "";

		int iRow = c.getColumnIndex(KEY_ROWID);
		int iJOBNAME = c.getColumnIndex(KEY_JOBNAME);
		int iLOCATION = c.getColumnIndex(KEY_LOCATION);
		int iDESCRIPTION = c.getColumnIndex(KEY_DESCRIPTION);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iRow) + "¬" + c.getString(iJOBNAME)
					+ "¬" + c.getString(iLOCATION) + "¬"
					+ c.getString(iDESCRIPTION) + "\n";
		}

		return result;
	}

	/* Spinner */
	public String getDataForSpinner() {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_JOBNAME, KEY_LOCATION,
				KEY_DESCRIPTION,KEY_CUSTOMER };
		Cursor c = ourDatabase.query(DATABASE_JOBS, columns, null, null, null,
				null, null);
		String result = "";

		int iRow = c.getColumnIndex(KEY_ROWID);
		int iJOBNAME = c.getColumnIndex(KEY_JOBNAME);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iJOBNAME) + "#####";
		}
		if (result.length() > 5) {
			result = result.substring(0, result.length() - 5);
		}
		return result;
	}

	public String[][] getDataForSpinner_array() {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_JOBNAME, KEY_LOCATION,
				KEY_DESCRIPTION };
		Cursor c = ourDatabase.query(DATABASE_JOBS, columns, null, null, null,
				null, null);
		String[][] result = new String[c.getCount()][3];

		int iRow = c.getColumnIndex(KEY_ROWID);
		int iJOBNAME = c.getColumnIndex(KEY_JOBNAME);
		int iLOCATION = c.getColumnIndex(KEY_LOCATION);
		int iDESCRIPTION = c.getColumnIndex(KEY_DESCRIPTION);

		int i = 0;

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result[i][0] = c.getString(iJOBNAME);
			result[i][1] = c.getString(iLOCATION);
			result[i][2] = c.getString(iDESCRIPTION);
			i = i + 1;
		}
		return result;
	}

	/* Spinner */
	public String getDataForSpinner_item() {

		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_ITEM_NAME };
		Cursor c = ourDatabase.query(DATABASE_ITEM, columns, null, null, null,
				null, null);

		String result = "";
		//
		//
		//
		int iRow = c.getColumnIndex(KEY_ROWID);
		int iJOBNAME = c.getColumnIndex(KEY_ITEM_NAME);

		//
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iJOBNAME) + "#####";
		}
		//
		if (result.length() > 5) {
			result = result.substring(0, result.length() - 5);
		}

		return result;
	}

	public String[] getCursor_jobDetails() {

		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ITEM_NAME, KEY_PRICE };
		Cursor c = ourDatabase.query(DATABASE_ITEM, columns, null, null, null,
				null, null);

		String[] result = new String[c.getCount() * 2];
		int iJOTITEMNAME = c.getColumnIndex(KEY_ITEM_NAME);
		int iJOBITEMPRICE = c.getColumnIndex(KEY_PRICE);
		int i = 0;

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result[i] = c.getString(iJOTITEMNAME).toString();
			result[i + 1] = c.getString(iJOBITEMPRICE).toString();
			i = i + 2;

		}

		return result;
	}

	public String[] getJobdetails(String jobname) {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_JOBNAME, KEY_LOCATION,
				KEY_DESCRIPTION,KEY_CUSTOMER,KEY_LABOUR_PRICE };
		Cursor c = ourDatabase.query(DATABASE_JOBS, columns, KEY_JOBNAME
				+ " = '" + jobname + "'", null, null, null, null);

		String[] result = new String[6];
		Arrays.fill(result, "");

		int iROW = c.getColumnIndex(KEY_ROWID);
		int iJOBNAME = c.getColumnIndex(KEY_JOBNAME);
		int iLOCATION = c.getColumnIndex(KEY_LOCATION);
		int iDESCRIPTION = c.getColumnIndex(KEY_DESCRIPTION);
		int iCUSTOMER = c.getColumnIndex(KEY_CUSTOMER);
		int iLABOUR = c.getColumnIndex(KEY_LABOUR_PRICE);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result[0] = c.getString(iLOCATION).toString();
			result[1] = c.getString(iDESCRIPTION).toString();
			result[2] = c.getString(iJOBNAME).toString();
			result[3] = c.getString(iROW).toString();
			result[4] = c.getString(iCUSTOMER).toString();
			result[5] = c.getString(iLABOUR).toString();


		}

		return result;
	}

	public String[] getJobdetailsItem(String jobname) {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ITEM_NAME, KEY_ITEM_DESC,
				KEY_PRICE };

		Cursor c = ourDatabase.query(DATABASE_ITEM, columns, "trim("
				+ KEY_ITEM_NAME + ") = trim('" + jobname + "')", null, null,
				null, null);

		// Cursor c = ourDatabase.query(DATABASE_ITEM, columns, null, null,
		// null, null, null);

		String[] result = new String[20];
		Arrays.fill(result, "");

		int iITEMNAME = c.getColumnIndex(KEY_ITEM_NAME);
		int iDESC = c.getColumnIndex(KEY_ITEM_DESC);
		int iPRICE = c.getColumnIndex(KEY_PRICE);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result[0] = c.getString(iITEMNAME).toString();
			result[1] = c.getString(iDESC).toString();
			result[2] = c.getString(iPRICE).toString();

		}

		return result;
	}

	public void editSave(String[] jobname) {
		// TODO Auto-generated method stub
		ContentValues cvUpdate = new ContentValues();
		cvUpdate.put(KEY_LOCATION, jobname[0]);
		cvUpdate.put(KEY_DESCRIPTION, jobname[1]);
		cvUpdate.put(KEY_JOBNAME, jobname[2]);

		ourDatabase.update(DATABASE_JOBS, cvUpdate, KEY_ROWID + " = "
				+ jobname[3], null);
	}

	public void Edit_job_item(String Select_item_jobName, String name,
			String Desc, String Price) {
		// TODO Auto-generated method stub

		ContentValues cvUpdate = new ContentValues();
		cvUpdate.put(KEY_ITEM_NAME, name);
		cvUpdate.put(KEY_ITEM_DESC, Desc);
		cvUpdate.put(KEY_PRICE, Price);

		ourDatabase.update(DATABASE_ITEM, cvUpdate, "trim(" + KEY_ITEM_NAME
				+ ") = trim('" + Select_item_jobName + "')", null);
	}

	public void update_jobs_table(String Original_full_JobName,
			String full_JobName, String full_location, String full_desc,String full_customer_name) {

		ContentValues cv = new ContentValues();
		cv.put("JOB_NAME", full_JobName); // These Fields should be your String
											// values of actual column names
		cv.put("LOCATION", full_location);
		cv.put("DESCRIPTION", full_desc);
		cv.put("CUSTOMER_FULL_NAME",full_customer_name);
		String where_clause = "JOB_NAME = '" + Original_full_JobName + "'";

		ourDatabase.update(DATABASE_JOBS, cv, where_clause, null);

	}

	public String[][] getInvoiceDetails(String jobname) {
		// TODO Auto-generated method stub
		String sqlString = "SELECT " + DATABASE_ITEM + "." + KEY_ITEM_NAME
				+ "," + DATABASE_ITEM + "." + KEY_PRICE + ","
				+  DATABASE_ITEM + "." + KEY_ITEM_DESC + ","
				+ DATABASE_JOB_TABLE + ".date ," + DATABASE_JOB_TABLE + "." + JOB_TABLE_QUANTITY
				+ " FROM " + DATABASE_JOB_TABLE
				+ " inner join " + DATABASE_ITEM + " on " + DATABASE_JOB_TABLE
				+ "." + KEY_ITEM_NAME + " = " + DATABASE_ITEM + "."
				+ JOB_TABLE_ITEMNAME + " where " + DATABASE_JOB_TABLE + "."
				+ JOB_TABLE_JOBNAME + " = '" + jobname + "'";
		Cursor c = ourDatabase.rawQuery(sqlString, null);

		String[][] result = new String[c.getCount() + 1][5];

		int iITEM = c.getColumnIndex(KEY_ITEM_NAME);
		int iPRICE = c.getColumnIndex(KEY_PRICE);
		int iDATE = c.getColumnIndex("date");
		int iDES = c.getColumnIndex(KEY_ITEM_DESC);
		int iQuant = c.getColumnIndex(JOB_TABLE_QUANTITY);
		

		int i = 0;
		double totPrice = 0.0;

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result[i][0] = c.getString(iITEM).toString();
			result[i][1] = c.getString(iPRICE).toString();
			result[i][2] = c.getString(iDATE).toString();
			result[i][3] = c.getString(iDES).toString();
			result[i][4] = c.getString(iQuant).toString();


			totPrice += Double.parseDouble(result[i][1]) * Double.parseDouble(result[i][4]);
			i++;
		}

		result[i][0] = Double.toString(totPrice);

		return result;
	}

	public Cursor getInvoiceDetailsCursor(String jobname) {
		// TODO Auto-generated method stub
		String sqlString = "SELECT " + DATABASE_ITEM + "." +  KEY_ROWID + "," + DATABASE_ITEM + "." + KEY_ITEM_NAME
				+ ", cast(" + DATABASE_ITEM + "." + KEY_PRICE + " as number(18,2))"
				+ ", cast(" + DATABASE_JOB_TABLE + "." + JOB_TABLE_QUANTITY + " as number(18,2)),"
				+ DATABASE_JOB_TABLE + ".date FROM " + DATABASE_JOB_TABLE
				+ " inner join " + DATABASE_ITEM + " on " + DATABASE_JOB_TABLE
				+ "." + KEY_ITEM_NAME + " = " + DATABASE_ITEM + "."
				+ JOB_TABLE_ITEMNAME + " where " + DATABASE_JOB_TABLE + "."
				+ JOB_TABLE_JOBNAME + " = '" + jobname + "'";

		return ourDatabase.rawQuery(sqlString, null);
		
	}

	public ArrayList<HashMap<String,String>> getInvoiceDetailsHashMap(String jobname) {
		// TODO Auto-generated method stub
		String sqlString = "SELECT " + DATABASE_ITEM + "." +  KEY_ROWID + "," + DATABASE_ITEM + "." + KEY_ITEM_NAME
				+ "," + DATABASE_ITEM + "." + KEY_PRICE   
				+ "," + DATABASE_JOB_TABLE + "." + JOB_TABLE_QUANTITY + "," 
				+ DATABASE_JOB_TABLE + ".date FROM " + DATABASE_JOB_TABLE
				+ " inner join " + DATABASE_ITEM + " on " + DATABASE_JOB_TABLE
				+ "." + KEY_ITEM_NAME + " = " + DATABASE_ITEM + "."
				+ JOB_TABLE_ITEMNAME + " where " + DATABASE_JOB_TABLE + "."
				+ JOB_TABLE_JOBNAME + " = '" + jobname + "'";
		Cursor c = ourDatabase.rawQuery(sqlString, null);

		String[][] result = new String[c.getCount() + 1][3];
		
		int iROW  = c.getColumnIndex(KEY_ROWID);
		int iITEM  = c.getColumnIndex(KEY_ITEM_NAME);
		int iPRICE = c.getColumnIndex(KEY_PRICE);
		int iQUANT = c.getColumnIndex(JOB_TABLE_QUANTITY);
		int iDATE  = c.getColumnIndex("date");
		
		ArrayList<HashMap<String,String>> listLocal = new ArrayList<HashMap<String,String>>();

		double grandtotPrice = 0.0;
		int i = 0;
		HashMap<String,String> item;
		
		DecimalFormat myFormatter = new DecimalFormat("£###,###,###,##0.00");


		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			  item = new HashMap<String,String>();
			  i++;
			  
			  String tmpPrice = c.getString(iPRICE).toString();
			  String tmpQuant = c.getString(iQUANT).toString();
			  
			  double totPrice = Double.parseDouble(tmpPrice) * Double.parseDouble(tmpQuant);
			  grandtotPrice += totPrice;
			  
			  /*Print invoice*/
			  item.put( "line1", c.getString(iDATE).toString() + " || Item Number " + i + " : " +  c.getString(iITEM).toString() + " X " +  c.getString(iQUANT).toString() ); 
			  item.put( "line2", myFormatter.format( totPrice )  );
			  /*Keep row number*/
			  listLocal.add( item );
		}

		item = new HashMap<String,String>();
		item.put( "line1", "Total price"  );
		item.put( "line2", myFormatter.format( grandtotPrice )  );
		listLocal.add( item );

		return listLocal;
		
	}
	
	public Boolean Check_job_name(String jobname) {
		// TODO Auto-generated method stub
		String sqlString = "SELECT " + DATABASE_JOBS  + "." + KEY_JOBNAME
				+ " FROM "  + DATABASE_JOBS 
				+ " where " + DATABASE_JOBS + "." + KEY_JOBNAME + " = '" + jobname + "'";
		Cursor c = ourDatabase.rawQuery(sqlString, null);
		
		boolean tmptest = true;
		if ( c.getCount() > 0 ) { tmptest = false; }
		
		return tmptest;		}
	
	public Boolean Check_item_name(String jobname) {
		// TODO Auto-generated method stub
		String sqlString = "SELECT " + DATABASE_ITEM  + "." + KEY_ITEM_NAME 
				+ " FROM "  + DATABASE_ITEM 
				+ " where " + DATABASE_ITEM + "." + KEY_ITEM_NAME  + " = '" + jobname + "'";
		Cursor c = ourDatabase.rawQuery(sqlString, null);
		
		boolean tmptest = true;
		if ( c.getCount() > 0 ) { tmptest = false; }
		
		return tmptest;		}
	
	public void Delete_Invoice_Item(String full_JobName,String DT) {
		// TODO Auto-generated method stub
		/* set up cv for insert method */

		/* Insert all of puts into table */
		String where_clause = JOB_TABLE_JOBNAME + " = '" + full_JobName + "' and date = '" + DT + "'";
		ourDatabase.delete(DATABASE_JOB_TABLE, where_clause, null);

	}
	
}
