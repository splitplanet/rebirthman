package baileys.Brad.Work_Recorder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import baileys.Brad.Work_Recorder.*;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


public class gps  extends Activity implements LocationListener {
	
			private LocationManager lm;
			private TextView tv;
			private Geocoder gc;
	
			public gps(LocationManager lmtmp, TextView tvtmp){
				lm=lmtmp;
				tv=tvtmp;
			}

		    /** Called when the activity is first created. */
		    public void onLocationChanged(Location arg0) {
		        String lat = String.valueOf(arg0.getLatitude());
		        String lon = String.valueOf(arg0.getLongitude());
		        Log.e("GPS", "location changed: lat="+lat+", lon="+lon);
		        this.tv.setText("lat="+lat+", lon="+lon);
		    }
		    

		    public void onLocationChanged2(Location arg0,TextView tv1, Context this2) throws IOException {
		        String lat = String.valueOf(arg0.getLatitude());
		        String lon = String.valueOf(arg0.getLongitude());
		        Log.e("GPS", "location changed: lat="+lat+", lon="+lon);
		        double lat1  = Double.parseDouble(lat);
		        double lon1  = Double.parseDouble(lon);
		        
		        //lat1 = 42.40; lon1 = 73.45;
		        
		        //List<Address> addresses = new Geocoder(Shout.this,Locale.getDefault()).getFromLocation(lat1, lon1, 1);
		    
	            List<Address> addresses =	null;
		        
		        try{
		            Geocoder gcd = new Geocoder(this2, Locale.getDefault());
		           addresses = gcd.getFromLocation(lat1, lon1,100);
		           
		           
		            if (addresses.size() > 0) {
		                StringBuilder result = new StringBuilder();
		                for(int i = 0; i < addresses.size(); i++){
		                    Address address =  addresses.get(i);
		                    int maxIndex = address.getMaxAddressLineIndex();
		                    for (int x = 0; x <= maxIndex; x++ ){
		                        result.append(address.getAddressLine(x));
		                        result.append(",");
		                    }               
		                    result.append(address.getLocality());
		                    result.append(",");
		                    result.append(address.getPostalCode());
		                    result.append("\n\n");
		                }
		                tv1.setText(result.toString());
		            }
		        }
		        catch(IOException ex){
		        	tv1.setText(ex.getMessage().toString());
		        }
		        
		        if (addresses.size() > 0) 
		            System.out.print(addresses.get(0).getLocality());
		        
		        
		        tv1.setText("lat="+lat+", lon="+lon);
		        
	        
		    }
		    
		    
		    public void onProviderDisabled(String arg0) {
		        Log.e("GPS", "provider disabled " + arg0);
		    }
		    public void onProviderEnabled(String arg0) {
		        Log.e("GPS", "provider enabled " + arg0);
		    }
		    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		        Log.e("GPS", "status changed to " + arg0 + " [" + arg1 + "]");
		    }
		    
		    
		

	public void GPSCaller(LocationManager lm1,TextView tv1, Context this2 ) {
		
		this.tv = tv1;
		this.lm = lm1;
		gps home = new gps(lm,tv);

        
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);
		
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		String provider = lm.getBestProvider(criteria, true);

		Location location = new Location(provider);
		
//		String[] MockLoc = str.split(",");
//		Location location = new Location(mocLocationProvider);            
//		Double lat = Double.valueOf(MockLoc[0]);
//		location.setLatitude(lat);
//		Double longi = Double.valueOf(MockLoc[1]);
//		location.setLongitude(longi);
//		Double alti = Double.valueOf(MockLoc[2]);
//		location.setAltitude(alti);
		
		
		try {
			this.onLocationChanged2(location,tv,this2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
    }
}
