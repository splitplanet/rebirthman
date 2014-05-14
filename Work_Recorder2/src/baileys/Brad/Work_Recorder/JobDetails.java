package baileys.Brad.Work_Recorder;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class JobDetails extends Activity  {
    /** Called when the activity is first created. */
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobdetails);
        
/*        	Button Home 	= (Button) findViewById(R.id.Jobdetails_button_Home	);
      		ListView list = (ListView) findViewById(R.layout.);
        
        Home.setOnClickListener(new View.OnClickListener() { public void onClick(View view) {
        Intent myIntent = new Intent(view.getContext(),Work_RecorderActivity.class);
    	startActivityForResult(myIntent, 0);
    	}});	*/


    	sql info = new sql( this);
        
    	
    	String[] stringData = null;
    	
    	try {
			info.open();
			int i =0;
			
			stringData = info.getCursor_jobDetails();
			info.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	

    	
    	
    	
    	
    }

    
 
}
        
    
