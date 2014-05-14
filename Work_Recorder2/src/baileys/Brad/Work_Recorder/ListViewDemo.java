package baileys.Brad.Work_Recorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ListViewDemo extends Activity {
	/** Called when the activity is first created. */

	// TextView textView = (TextView)
	// findViewById(R.id.listitemselected_textview_temp);

    /*Deal with location buttons first*/
    
	
	String[] arrayDataFinal;
	ArrayAdapter<String> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jobdetails);

		Button home = (Button) findViewById(R.id.home);
        
	    
		home.setOnClickListener(new View.OnClickListener() { public void onClick(View view) {
		Intent myIntent = new Intent(view.getContext(),Work_RecorderActivity.class);
		startActivityForResult(myIntent, 0);
		}});	
		
		
		ListViewDemo temp = new ListViewDemo();

		sql info = new sql(this);

		String[] arrayData = temp.myListView(info);
		arrayDataFinal = arrayData;

		ListView listView1 = (ListView) findViewById(R.id.listView1);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, arrayData);

		listView1.setAdapter(adapter);

		String[] arrayData2 = { "item 1", "item 2", "item 3" };

		listView1.setOnItemClickListener(new OnItemClickListener() { 
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// textView.setText("position = " + position);

				Intent myIntent = new Intent(view.getContext(),
						invoicepage.class);

				myIntent.putExtra("JobName", arrayDataFinal[position]);
				startActivityForResult(myIntent, 0);
			}
		});
	}

	public String[] myListView(sql info) {

		String[][] data = null;
		try {
			info.open();
			data = info.getDataForSpinner_array();
			info.close();
		} catch (Exception e) {

			e.printStackTrace();
		}

		int i = 0;

		String[] result = new String[data.length];

		while (i < data.length) {

			result[i] = data[i][0];
			i = i + 1;

		}

		return result;
	}

}
