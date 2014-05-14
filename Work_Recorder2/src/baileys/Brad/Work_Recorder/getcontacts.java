package baileys.Brad.Work_Recorder;

import java.util.Vector;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

public class getcontacts {
	
	private static final String[] PROJECTION = new String[] {
	    ContactsContract.CommonDataKinds.Email.CONTACT_ID,
	    ContactsContract.Contacts.DISPLAY_NAME,
	    ContactsContract.CommonDataKinds.Email.DATA
	};

	
	public String[][] Dumbcontact( Context mContext ) {
	
		int i = 0;
		
		ContentResolver cR = mContext.getContentResolver();
		Cursor cursor = cR.query(   ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,null, null);
		
		int ContactsSize = cursor.getCount();
		String[][] resultString = new String[ ContactsSize ][ 6 ];

		
		
		while (cursor.moveToNext()) {
			resultString[i][0] =cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			resultString[i][1] = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			
			resultString[i][2] =cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
			resultString[i][3] =cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY ));
			resultString[i][4] =cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
			resultString[i++][5] =cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
			
			
		
		}
			
			
		
		
		return resultString;
	}
	
	
	//Get Emails
	
	
	
	public Vector<String> DumbEmail( Context mContext ) {
		
		int i = 0;
		
		ContentResolver cR = mContext.getContentResolver();
		Cursor emailCur = cR.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, PROJECTION, null, null, null);
	
		
		Vector<String> resultString = new Vector<String>();
		
		
		if (emailCur != null) {
		    try {
		        final int contactIdIndex = emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID);
		        final int displayNameIndex = emailCur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
		        final int emailIndex = emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
		        
		        long contactId;
		        String displayName, address;
		        
		        while (emailCur.moveToNext()) {
		            contactId = emailCur.getLong(contactIdIndex);
		            displayName = emailCur.getString(displayNameIndex);
		            address = emailCur.getString(emailIndex);
		            
		            resultString.add(address);
		        }
		    } finally {
		        emailCur.close();
		    }
		}
		
		
		return resultString;
	}
	

	

} 