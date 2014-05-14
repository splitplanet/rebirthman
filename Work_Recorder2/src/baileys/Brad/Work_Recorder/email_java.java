 package baileys.Brad.Work_Recorder;

import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;

public class email_java extends Activity {
	/** Called when the activity is first created. */
	
	Vector<String> Cur_Emails = new Vector<String>();
	ArrayAdapter<String> adapter;
	Spinner s;
	ListView listView1;
	int current_position;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.email);
		
		
		
		
		
		
		Button back 	  = (Button) findViewById(R.id.email_bu_back);
		Button sendemail  = (Button) findViewById(R.id.email_bu_Send_email);
		Button addmanual  = (Button) findViewById(R.id.email_bu_add_manual);
		Button addspinner = (Button) findViewById(R.id.email_bu_add_spinner);
		Button delemail   = (Button) findViewById(R.id.email_bu_remove_from_list);
		       s		  = (Spinner) findViewById(R.id.email_sp_emails);
		       listView1  = (ListView) findViewById(R.id.email_li_emails_to_send_to);
		       
		       
		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(),
						invoicepage.class);
				startActivityForResult(myIntent, 0);
			}
		});

		
		sendemail.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String fileattchment = "/invoice.xls";
				/*Write file*/
				//Get position
				String JobName = getIntent().getStringExtra("JobName");
				WriteExcel ExcelExporter = new WriteExcel( view.getContext() );
 				ExcelExporter.MasterXLSfile(fileattchment,JobName);

			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
	        emailIntent.setType("plain/text");
	        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"Bstins@hotmail.co.uk"});
	        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Testing");
	        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Testing123");
	        emailIntent.putExtra(android.content.Intent.ACTION_ATTACH_DATA, fileattchment);

	        startActivity(Intent.createChooser(emailIntent, "Send mail..."));

			}
		});

		
		
		addmanual.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				EditText manual_email = (EditText) findViewById(R.id.email_et_manual_email);
				add_email_to_list(manual_email.getText().toString());
			}
		});
		
		addspinner.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (s.getSelectedItem().toString() != "--- CHOOSE EMAIL ADDRESS ---" ) {
				add_email_to_list(s.getSelectedItem().toString());
				}
			}
		});
		
		delemail.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (s.getSelectedItem().toString() != "--- CHOOSE EMAIL ADDRESS ---" ) {
					remove_email_from_list();
				}
			}
		});
		

		
		//Deal with spinner
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(adapter);

		getcontacts emailGraber = new getcontacts();
		
		Vector<String> SpinnerEmails = null;
		
		try {

			// Deal with SQL
			
			 SpinnerEmails = emailGraber.DumbEmail(super.getApplicationContext());

			adapter.add("--- CHOOSE EMAIL ADDRESS ---");

			for (int i =0; i< SpinnerEmails.size() ; i++) {

				adapter.add(SpinnerEmails.get(i));
			}

		} finally {
		}
		
		//Capture where list was last clicked
		
		listView1.setOnItemClickListener(new OnItemClickListener() { 
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			
				current_position = position;
				
				
			}});
	
	}
	

	

	public void add_email_to_list(String email_address_in ) {

		Cur_Emails.add(email_address_in);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Cur_Emails);
		listView1.setAdapter(adapter);

	}
	

	

	public void remove_email_from_list() {
		
		Cur_Emails.remove(current_position);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Cur_Emails);
		listView1.setAdapter(adapter);

	}
	

}
