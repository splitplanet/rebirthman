package baileys.Brad.Work_Recorder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import baileys.Brad.Work_Recorder.*;

public class Work_RecorderActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
    	

        
        /*Deal with location buttons first*/
            Button toedit 		= (Button) findViewById(R.id.EditJobDetails);
            Button toadd 		= (Button) findViewById(R.id.AddToJob	);
            Button JobItem 		= (Button) findViewById(R.id.JobItem	);
            Button JobDetails 	= (Button) findViewById(R.id.JobDetailView	);
                

            
            toedit.setOnClickListener(new View.OnClickListener() { public void onClick(View view) {
        	Intent myIntent = new Intent(view.getContext(),edit_job.class);
        	startActivityForResult(myIntent, 0);
        	}}); 
            
            toadd.setOnClickListener(new View.OnClickListener() { public void onClick(View view) {
        	Intent myIntent = new Intent(view.getContext(),add_job.class);
        	myIntent.putExtra("Job_name_position", "0");
        	myIntent.putExtra("Item_name_position", "0");
        	startActivityForResult(myIntent, 0);
        	}});
            
            JobItem.setOnClickListener(new View.OnClickListener() { public void onClick(View view) {
        	Intent myIntent = new Intent(view.getContext(),jobitem.class);
        	startActivityForResult(myIntent, 0);
        	}});
            
            
            JobDetails.setOnClickListener(new View.OnClickListener() { public void onClick(View view) {
        	Intent myIntent = new Intent(view.getContext(),ListViewDemo.class);
        	startActivityForResult(myIntent, 0);
        	}});
            
//            loc.setOnClickListener(
//            		new View.OnClickListener() { public void onClick(View view) {
//            			interGPS();
//        	}});

    }

    
    public void interGPS(){
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    	TextView		tv = (TextView) findViewById(R.id.exampletextview	);
    	gps gpsbase = new gps(lm,tv);
    	gpsbase.GPSCaller(lm,tv,this);
    	
    	
    }
}
        
    
