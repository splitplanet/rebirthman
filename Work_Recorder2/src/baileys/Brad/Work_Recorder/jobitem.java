package baileys.Brad.Work_Recorder;

import java.util.Scanner;

import android.app.Activity;
import android.app.Dialog;

/*SQL stuff*/
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;





public class jobitem  extends Activity {
    /** Called when the activity is first created. */

    String  Select_item_jobName;
    String  Full_item_jobList  ;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobitemview);
        
 
        /*Deal with location buttons first*/
        Button home = (Button) findViewById(R.id.Home);
            
        home.setOnClickListener(new View.OnClickListener() { public void onClick(View view) {
    	Intent myIntent = new Intent(view.getContext(),Work_RecorderActivity.class);
    	startActivityForResult(myIntent, 0);
    	}});
        
        /*Button to add job*/
        Button Addjob	= (Button) this.findViewById(R.id.Create);				//Add_job
        

        /*Button to add job*/
        Button DeleteJob	= (Button) this.findViewById(R.id.Delete);				//Add_job
        

        /*Button to add job*/
        Button EditButton	= (Button) this.findViewById(R.id.JobItemReview_B_Edit);				//Add_job
        
        Addjob.setOnClickListener(new View.OnClickListener() 
        { public void onClick(View view) {Create_jobItem(view);}});
        

        EditButton.setOnClickListener(new View.OnClickListener() 
        { public void onClick(View view) {Edit_jobItem(view);}});
        

        DeleteJob.setOnClickListener(new View.OnClickListener() 
        { public void onClick(View view) {Delete_jobItem(view);}});
        
        Spinner s = (Spinner) findViewById(R.id.editjobspinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        
        final String[] rowSAVOR = new String[20];
        
   //     edit_job basic = new edit_job();
        sql info = new sql( this);

        try{
        	
        	//Deal with SQL
        	info.open();
        	String data = info.getDataForSpinner_item();
        	info.close();
        	
        	
        	
        	
        	Scanner spinnerScan = new Scanner(data).useDelimiter("#####");
        	Full_item_jobList = data;
        	adapter.add("--- NEW ITEM ---");
        	while (spinnerScan.hasNext()){
        		
        		adapter.add(spinnerScan.next());
        	}
        	
        	spinnerScan.close();

        	} finally {
        	}
        
        
        		s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            	//ed_onItemSelected(info,ed_location,s);
            

            public void onNothingSelected(AdapterView<?> adapterView) {
                //DONOTHING
            }
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				
				GetDetailsOfItem(rowSAVOR);

			}
		});
	}

	public void GetDetailsOfItem(String[] outPUT1) {

		sql info = new sql(this);
		
		EditText 		ItemDesc  		= 	(EditText) findViewById(R.id.JobItemFullDesc);
		EditText 		ItemName 		= 	(EditText) findViewById(R.id.Item_Name);
		EditText 		ItemPrice 		= 	(EditText) findViewById(R.id.editPrice);
     	
		String[] tmp = new String[] {};
		Spinner s = (Spinner) findViewById(R.id.editjobspinner);
		Select_item_jobName = s.getSelectedItem().toString();

		info.open();
		tmp = info.getJobdetailsItem(s.getSelectedItem().toString());
		info.close();

		ItemDesc.setText(tmp[1].toString());
		ItemName.setText(tmp[0].toString());
		ItemPrice.setText(tmp[2].toString());

		outPUT1 = tmp;

	}

		
    
    
    public void Create_jobItem (View view){
    	
    	EditText 		ItemName 		= 	(EditText) findViewById(R.id.Item_Name);
    	String			full_JobName	=	(String) ItemName.getText().toString();
    
    	EditText 		ItemDesc  		= 	(EditText) findViewById(R.id.JobItemFullDesc);
    	String			full_ItemDesc	=	(String) ItemDesc.getText().toString();
    	
    	EditText 		ItemPrice 		= 	(EditText) findViewById(R.id.editPrice);
    	String			full_ItemPrice	=	(String) ItemPrice.getText().toString();
    	
    	Boolean			scanner_check	=	true;
    	String			Name_test;
    	Scanner			nameScan		=	new	Scanner(Full_item_jobList).useDelimiter("#####");
    	
    		
   		while (nameScan.hasNext()  ){
   			
   			Name_test = nameScan.next().toString();
   			
   			if ( Name_test.equals(full_JobName)   ) { 
   						scanner_check = false;}  
   			}
    	
   		nameScan.close();
   		
   		if (scanner_check) {
   		
 //   	final String[] rowSAVOR = new String[20];
    	
    	// Resets the visible value to zero. The values are now saved in the string variable full_JobName etc.
    	ItemName.setText("");  ItemDesc.setText("");  ItemPrice.setText("");
    	
 
    	sql entry =  new sql(jobitem.this);
    	
    	entry.open();
    	
		if (entry.Check_item_name( full_JobName )) {
			entry.Create_job_item( full_JobName,full_ItemDesc,full_ItemPrice);
	    	Intent myIntent = new Intent(view.getContext(),jobitem.class);
	    	startActivityForResult(myIntent, 0);
			}
		else {
			Dialog d = new Dialog(this);
			d.setTitle("Error Item already exists!");
			TextView tv = new TextView(this);
			tv.setText("Try again");
			d.setContentView(tv);
			d.show();
			}
    	
    	
    	entry.close();
   	
    	}
    	
    }
    
    public void Edit_jobItem (View view){
    	

    	
    	EditText 		ItemName 		= 	(EditText) findViewById(R.id.Item_Name);
    	String			full_JobName	=	(String) ItemName.getText().toString();
    
    	EditText 		ItemDesc  		= 	(EditText) findViewById(R.id.JobItemFullDesc);
    	String			full_ItemDesc	=	(String) ItemDesc.getText().toString();
    	
    	EditText 		ItemPrice 		= 	(EditText) findViewById(R.id.editPrice);
    	String			full_ItemPrice	=	(String) ItemPrice.getText().toString();
 
    	
    	
 //   	final String[] rowSAVOR = new String[20];
    	
 
    	sql entry =  new sql(jobitem.this);
    	
    	entry.open();
    	entry.Edit_job_item( Select_item_jobName,full_JobName,full_ItemDesc,full_ItemPrice);
    	entry.close();
    	

    	// Resets the visible value to zero. The values are now saved in the string variable full_JobName etc.
    	ItemName.setText("");  ItemDesc.setText("");  ItemPrice.setText("");

    	Intent myIntent = new Intent(view.getContext(),jobitem.class);
    	startActivityForResult(myIntent, 0);
    	
    	
    	
    }
    
 public void Delete_jobItem (View view){
    	
    	EditText 		ItemName 		= 	(EditText) findViewById(R.id.Item_Name);
    	String			full_JobName	=	(String) ItemName.getText().toString();
    	
    	
    	sql del =  new sql(jobitem.this);
    	
    	del.open();
    	del.DeleteEntry_item( full_JobName);
    	del.close();
    	

    	Intent myIntent = new Intent(view.getContext(),jobitem.class);
    	startActivityForResult(myIntent, 0);
    	
    }
    
}
