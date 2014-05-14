package baileys.Brad.Work_Recorder;

import java.util.Scanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class add_job extends Activity {

	String Full_item_jobList;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addtojoblayout);

		String Job_name_pos_String = getIntent().getStringExtra("Job_name_position");
		int Job_name_position = 0; 
		
		if ( Job_name_pos_String != null ) { Job_name_position =  Integer.parseInt(Job_name_pos_String) ;}
		
		String Item_name_pos_String = getIntent().getStringExtra("Item_name_position");
		int Item_name_position = 0; 
		
		if ( Item_name_pos_String != null ) { Item_name_position =  Integer.parseInt(Item_name_pos_String) ;}
		

		
		
		 
		
		/* Deal with location buttons first */
		Button home = (Button) findViewById(R.id.Home);
		Button AddItem = (Button) findViewById(R.id.JobAdd_b_AddToJob);

		home.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(),
						Work_RecorderActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});

		AddItem.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Create_job_Item();
				Intent myIntent = new Intent(view.getContext(), add_job.class);
				
				Spinner tmp_spinner = (Spinner) findViewById(R.id.JobAdd_s_jobname);
				int tmp_spinnoir_pos = tmp_spinner.getSelectedItemPosition();
				myIntent.putExtra("Job_name_position", Integer.toString(tmp_spinnoir_pos));
			
				Spinner tmp_spinner2 = (Spinner) findViewById(R.id.JobAdd_s_itemname);
				tmp_spinnoir_pos = tmp_spinner2.getSelectedItemPosition();
				myIntent.putExtra("Item_name_position", Integer.toString(tmp_spinnoir_pos));
				
				
				startActivityForResult(myIntent, 0);
				

				//Spinner tmp_spinner2 = (Spinner) findViewById(R.id.JobAdd_s_jobname);
				//tmp_spinner2.setSelection(tmp_spinnoir_pos  );
				
			}
		});

		Spinner s_jobname = (Spinner) findViewById(R.id.JobAdd_s_jobname);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s_jobname.setAdapter(adapter);
		sql info = new sql(this);

		Spinner s_jobItemname = (Spinner) findViewById(R.id.JobAdd_s_itemname);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s_jobItemname.setAdapter(adapter2);

		try {

			// Deal with SQL
			info.open();
			String data = info.getDataForSpinner();
			info.close();

			Scanner spinnerScan = new Scanner(data).useDelimiter("#####");

			while (spinnerScan.hasNext()) {

				adapter.add(spinnerScan.next());
			}

			// Deal with SQL
			info.open();
			String data2 = info.getDataForSpinner_item();
			info.close();

			spinnerScan = new Scanner(data2).useDelimiter("#####");
			Full_item_jobList = data2;

			while (spinnerScan.hasNext()) {

				adapter2.add(spinnerScan.next());
			}

			spinnerScan.close();

		} finally {
		}
		
		
		//Set  correct location from before
		s_jobname.setSelection(Job_name_position);
		s_jobItemname.setSelection(Item_name_position);
		
		
	}

	public void Create_job_Item() {

		/* Take a copy of the new values and pass them to edit job */
		/*
		 * JobName Location spinner1 // to do later desc
		 */

		// sql info = new sql(this);
		sql update = new sql(this);

		Spinner sJob = (Spinner) findViewById(R.id.JobAdd_s_jobname);
		String Job_Name = sJob.getSelectedItem().toString();

		Spinner sItem = (Spinner) findViewById(R.id.JobAdd_s_itemname);
		String Job_Item = sItem.getSelectedItem().toString();
		
		EditText etQuant = (EditText) findViewById( R.id.JobAdd_b_quant );
		String job_quant = etQuant.getText().toString();

		update.open();
		update.AddToJobSQL(Job_Name, Job_Item,job_quant);
		update.close();

	}
}
