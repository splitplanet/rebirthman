 package baileys.Brad.Work_Recorder;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class invoicepage extends Activity {
	/** Called when the activity is first created. */
	
	ArrayAdapter<String> adapter;
	ListView listView2;
	SimpleAdapter  adapter2;
	int currentPos1;
	sql info = new sql(this);
	String JobName ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listitemselected);
		
		@SuppressWarnings("unused")

		TextView mytv = (TextView) findViewById(R.id.listitemselected_textview_temp);
		JobName = getIntent().getStringExtra("JobName");
		mytv.setText(JobName);
		

		try {
			info.open();
		} catch (Exception e) {
			e.printStackTrace();
		}

		invoicepage temp = new invoicepage();

		listView2 = (ListView) findViewById(R.id.mainListView);
		
		Cursor mCursor = JobInvoiceDetailscursor(info, JobName);
		ArrayList<HashMap<String,String>> listInvoiceOage = JobInvoiceDetailsArray(info,JobName);
		
		adapter2 = new SimpleAdapter(
				this,
				listInvoiceOage,
				android.R.layout.two_line_list_item ,  	  											// Pass in the cursor to bind to.
                new String[] {"line1","line2"}, 		// Array of cursor columns to bind to.
                new int[] {android.R.id.text1, android.R.id.text2});  	// Parallel array of which template objects to bind to those columns.

        // Bind to our new adapter.
		 
		listView2.setAdapter(adapter2);
		
		
		//Long click delete
		
		registerForContextMenu(listView2);
	//	Activity mContext = this;
		
		listView2.setOnLongClickListener(new OnLongClickListener() {
			
			public boolean onLongClick(View v ) {
				// TODO Auto-generated method stub
				currentPos1 = listView2.getSelectedItemPosition();
				
//				((Activity)mContext).openContextMenu(v);
				return false;
			}
		}); 

		
	
		
		
//		listView2.setOnItemLongClickListener(new OnItemLongClickListener() {
//			
//			public void onItemLongClick(AdapterView<?> parent, View view,
//					int JobName, long id) {
//
//				registerForContextMenu(listView2);
//				
//				
//
//				
//			}
//			
//			
//		});
        
        
		
		
		/* Deal with location buttons first */
		Button tocreate = (Button) findViewById(R.id.listitemselected_button_back);
		Button toemail = (Button) findViewById(R.id.listitemselected_button_email);

		tocreate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(),
						ListViewDemo.class);
				startActivityForResult(myIntent, 0);
			}
		});
		
		toemail.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(),
						email_java.class);
				String JobName = getIntent().getStringExtra("JobName");
				myIntent.putExtra("JobName", JobName);
				startActivityForResult(myIntent, 0);
			}
		});
		
		

		DatabaseUtils test = null;
		
		DatabaseUtils.dumpCursor( mCursor  );
		
		
		info.close();
		mCursor.close();

	}
	
	//On item selected listener
	

	
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		

	    // Get the list
	    ListView list = (ListView) v  ;

	    // Get the list item JobName    
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo)menuInfo;
	    currentPos1 = info.position;
	    
	    int lSize = listView2.getCount();
	    		
	    if ( listView2.getCount() != currentPos1 - 1 ) {
	    
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		
		menu.add("Delete");
		menu.add("Cancel");
	    }
	    
	    
		
	}
	

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		
		super.onContextItemSelected(item);
		
		
		if(item.getTitle() == "Delete") {
			String current_itemString = listView2.getItemAtPosition(currentPos1).toString();
			String dt = current_itemString.substring(current_itemString.indexOf("=") + 1 , current_itemString.indexOf("=") + 20);
			info.open();
			info.Delete_Invoice_Item(JobName,dt);
			info.close();
			


			Intent myIntent = new Intent(this.getBaseContext(),invoicepage.class);

			myIntent.putExtra("JobName", JobName);
			startActivityForResult(myIntent, 0);
			
			
			Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show();
			
			
		}
		if(item.getTitle() == "Cancel") {
			Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
		}
				
		return true;
	}

	public Cursor JobInvoiceDetailscursor(sql info, String JobName) {

		return  info.getInvoiceDetailsCursor(JobName);
	}
	
	public ArrayList<HashMap<String,String>>  JobInvoiceDetailsArray(sql info, String JobName) {

		return  info.getInvoiceDetailsHashMap(JobName);
	}

}
