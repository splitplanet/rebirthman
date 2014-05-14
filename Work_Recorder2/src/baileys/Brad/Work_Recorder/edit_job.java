package baileys.Brad.Work_Recorder;

import java.util.Scanner;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class edit_job extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editjob);
		// TextView tv = (TextView) findViewById(R.id.ed_SQL_info);
		
		// Memory item is used to create empty 
		
		
		
		
		//Blank out all fields
		EditText ed_location = (EditText) findViewById(R.id.ed_Location);
		EditText ed_descrip = (EditText) findViewById(R.id.editJob_desc);
		EditText ed_jobname = (EditText) findViewById(R.id.editJob_ET_JobName);
		ed_location.setText("") ;
		ed_descrip.setText("") ;
		ed_jobname.setText("") ;
		

		/* Deal with location buttons first */
		Button home = (Button) findViewById(R.id.Home);
		Button editB = (Button) findViewById(R.id.Edit);
		Button DelB = (Button) findViewById(R.id.Delete);
		Button CreateB = (Button) findViewById(R.id.editJob_B_Create);

		final String[] rowSAVOR = new String[20];

		home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(),
						Work_RecorderActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});
		
		getcontacts contactMaster = new getcontacts( );
		
		
		CreateB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Create_job(view);
			}
		});

		editB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Edit_job1();
				Intent myIntent = new Intent(view.getContext(), edit_job.class);
				startActivityForResult(myIntent, 0);
			}
		});

		DelB.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Del_job1();
				Intent myIntent = new Intent(view.getContext(), edit_job.class);
				startActivityForResult(myIntent, 0);
			}
		});

		
		// String ed_full_location = (String) ed_location.getText().toString();

		Spinner s = (Spinner) findViewById(R.id.editjobspinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(adapter);
		sql info = new sql(this);

		try {

			// Deal with SQL
			info.open();
			String data = info.getDataForSpinner();
			info.close();

			Scanner spinnerScan = new Scanner(data).useDelimiter("#####");

			adapter.add("--- NEW JOB ---");

			while (spinnerScan.hasNext()) {

				adapter.add(spinnerScan.next());
			}

		} finally {
		}
		
		//Customer Spinner
		Spinner scustomer = (Spinner) findViewById(R.id.ed_spinner1);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		scustomer.setAdapter(adapter2);
		
		try {

			String[][] tempStringArrayContacts = contactMaster.Dumbcontact( super.getApplicationContext() );
			
			adapter2.add("--- NO CUSTOMER SELECTED ---");
			
			for(int i=0; i<tempStringArrayContacts.length;i++){
				
				adapter2.add(tempStringArrayContacts[i][0]);	
				}

		} finally {
		}
		
		
		

		s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			// ed_onItemSelected(info,ed_location,s);

			public void onNothingSelected(AdapterView<?> adapterView) {
				// DONOTHING
			}

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				spinGetData(rowSAVOR);

			}
		});
	}

	public void spinGetData(String[] outPUT1 ) {

		sql info = new sql(this);
		EditText ed_location = (EditText) findViewById(R.id.ed_Location);
		EditText ed_descrip = (EditText) findViewById(R.id.editJob_desc);
		EditText ed_jobname = (EditText) findViewById(R.id.editJob_ET_JobName);
		EditText ed_Labour = (EditText) findViewById(R.id.editLabourPrice);
		String[] tmp = new String[] {};
		Spinner s = (Spinner) findViewById(R.id.editjobspinner);
		Spinner Customer = (Spinner) findViewById(R.id.ed_spinner1);

		info.open();
		tmp = info.getJobdetails(s.getSelectedItem().toString());
		info.close();

		ed_location.setText(tmp[0].toString());
		ed_descrip.setText(tmp[1].toString());
		ed_jobname.setText(tmp[2].toString());
		ed_Labour.setText(tmp[5].toString());
		
		int maxcust = Customer.getAdapter().getCount();
		
		
		
		for (int i=0 ;i < maxcust; i++) {
			if (Customer.getItemAtPosition(i).toString().equals(tmp[4])) {
				Customer.setSelection(i);
				break;
			}

		}

		outPUT1 = tmp;

	}

	public void Edit_job1() {

		/* Take a copy of the new values and pass them to edit job */
		/*
		 * JobName Location spinner1 // to do later desc
		 */

		// sql info = new sql(this);
		sql update = new sql(edit_job.this);

		Spinner s = (Spinner) findViewById(R.id.editjobspinner);

		EditText location = (EditText) findViewById(R.id.ed_Location);
		String full_location = (String) location.getText().toString();

		EditText desc = (EditText) findViewById(R.id.editJob_desc);
		String full_desc = (String) desc.getText().toString();

		String Job_Name = s.getSelectedItem().toString();
		
		//Deal with customer name
		Spinner s2 = (Spinner) findViewById(R.id.ed_spinner1);
		String full_customer_namer = s2.getSelectedItem().toString();

		update.open();
		update.update_jobs_table(Job_Name, Job_Name, full_location, full_desc,full_customer_namer);
		update.close();

	}

	public void Del_job1() {

		/* Take a copy of the new values and pass them to edit job */
		/*
		 * JobName Location spinner1 // to do later desc
		 */

		// sql info = new sql(this);
		sql update = new sql(edit_job.this);

		Spinner s = (Spinner) findViewById(R.id.editjobspinner);
		String Job_Name = s.getSelectedItem().toString();

		update.open();
		update.DeleteEntry(Job_Name);
		update.close();

	}

	public void Create_job( View view ) {
		EditText location = (EditText) findViewById(R.id.ed_Location);
		String full_location = (String) location.getText().toString();

		EditText jobname = (EditText) findViewById(R.id.editJob_ET_JobName);
		String full_JobName = (String) jobname.getText().toString();

		EditText desc = (EditText) findViewById(R.id.editJob_desc);
		String full_desc = (String) desc.getText().toString();
		
		EditText lab_price = (EditText) findViewById(R.id.editLabourPrice);
		String full_labour_price = (String) lab_price.getText().toString();

		//Deal with customer name
		Spinner s = (Spinner) findViewById(R.id.ed_spinner1);
		String full_customer_namer = s.getSelectedItem().toString();
		
		sql info = new sql(edit_job.this);
		info.open();
		if (info.Check_job_name( full_JobName )) {
			info.creatEntry(full_JobName, full_location, full_desc,full_labour_price,full_customer_namer);
			Intent myIntent = new Intent(view.getContext(), edit_job.class);
			startActivityForResult(myIntent, 0);
		}
		else {
			Dialog d = new Dialog(this);
			d.setTitle("Error Job already exists!");
			TextView tv = new TextView(this);
			tv.setText("Try again");
			d.setContentView(tv);
			d.show();
			}
		info.close();
	}

}