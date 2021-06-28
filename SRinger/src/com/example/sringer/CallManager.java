package com.example.sringer;

import java.util.ArrayList;

import android.animation.AnimatorSet.Builder;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
public class CallManager extends Activity {
	static final int PICK_CONTACT_REQUEST = 1;  // The request code
	ArrayList<Contact> contacts = new ArrayList<Contact>();
	ListView listView;
	int attendeesSelectionFlag=0;
	ContactsAdapter adapter;
	
	String d_Mobile;
	String d_Name;
	
	Cursor eCursor;
	

	public void addContact(View view)
	{
		Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
	    pickContactIntent.setType(Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
	    startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
	}
	
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
        setContentView(R.layout.call_manager);
        setTitle("Call Manager");
        listView = (ListView) findViewById(R.id.contactsView);
    
        MainActivity.sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES,Context.MODE_PRIVATE);

        
        
        
        if (MainActivity.sharedpreferences.contains("radio_id"))
          {
          	
          	 int prev_radio = MainActivity.sharedpreferences.getInt("radio_id",0);
          	 System.out.println(prev_radio); 
          	 RadioButton rb=(RadioButton) findViewById(prev_radio); 
             rb.setChecked(true);

          }
        
      
        eCursor=MainActivity.contactdb.rawQuery("SELECT * FROM whitelist", null);
        if (eCursor .moveToFirst()) {

            while (eCursor.isAfterLast() == false) {
                String name = eCursor.getString(0);
                String number = eCursor.getString(1);
                Contact con = new Contact(name,number);
				contacts.add(con);
				listView.setAdapter(new ContactsAdapter(this, contacts));
               
                eCursor.moveToNext();
            }
        }
        
       
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
            // setting onItemLongClickListener and passing the position to the function
                      @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                    int position, long arg3) {
                removeItemFromList(position);   
                
                return true;
            }
        });
    }

    
    
    
    // method to remove list item
    protected void removeItemFromList(int position) {
        final int deletePosition = position;
        
        AlertDialog.Builder alert = new AlertDialog.Builder(
                CallManager.this);
    
        alert.setTitle("Delete");
        alert.setMessage("delete this Contact?");
        alert.setPositiveButton("YES", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TOD O Auto-generated method stub
                    
                    // main code on after clicking yes

            	
            	d_Name=contacts.get(deletePosition).getName().toString();		
				d_Mobile=contacts.get(deletePosition).getMobile().toString();
				System.out.println(d_Name+d_Mobile);
				MainActivity.contactdb.execSQL("DELETE FROM whitelist WHERE name='"+d_Name+"' and number='"+d_Mobile+"';");
				finish();
				startActivity(getIntent());   
            }
        });
        alert.setNegativeButton("CANCEL", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
      
        alert.show();
      
    }
        
        
    
   public void getMode(View v)
   {
	  
	   
	   RadioGroup optionsGroup = null; 
		 RadioButton optionSelected=null;

		
		optionsGroup = (RadioGroup) findViewById(R.id.rg1); 
		
		int selected = optionsGroup.getCheckedRadioButtonId();
		optionSelected = (RadioButton) findViewById(selected);

		String selectedMode = optionSelected.getText().toString();
		
		System.out.println(selectedMode);
	
	MainActivity.sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);

	MainActivity.editor = MainActivity.sharedpreferences.edit();
	     MainActivity.editor.putString(MainActivity.Name, selectedMode);
	     MainActivity.editor.putInt("radio_id",selected);
	     
	     MainActivity.editor.commit();
	    
	     System.out.println("saved key - value");
	
		
		
		
	Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	
		
   }
    
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // Check which request it is that we're responding to
	    if (requestCode == PICK_CONTACT_REQUEST) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
	        	attendeesSelectionFlag=1;
	            // Get the URI that points to the selected contact
	            Uri contactUri = data.getData();
	            // We only need the NUMBER column, because there will be only one row in the result
	            String[] projection = {Phone.NUMBER,Phone.DISPLAY_NAME};

	            // Perform the query on the contact to get the NUMBER column
	            // We don't need a selection or sort order (there's only one result for the given URI)
	            // CAUTION: The query() method should be called from a separate thread to avoid blocking
	            // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
	            // Consider using CursorLoader to perform the query.
	            Cursor cursor = getContentResolver()
	                    .query(contactUri, projection, null, null, null);
	            cursor.moveToFirst();

	            // Retrieve the phone number from the NUMBER column
	            int numColumn = cursor.getColumnIndex(Phone.NUMBER);
	            String number = cursor.getString(numColumn);
	            int nameColumn= cursor.getColumnIndex(Phone.DISPLAY_NAME);
	            String name = cursor.getString(nameColumn);
	         // Do something with the phone number...
//	            TextView t=(TextView)findViewById(R.id.contactList);
//	            t.setText(number+name);
	            
//	            Contact con = new Contact(cursor.getString(cursor
//						.getColumnIndex(Contacts.DISPLAY_NAME)), cursor.getString(cursor
//						.getColumnIndex(Phone.NUMBER)));
	            Contact con = new Contact(name,number);
				contacts.add(con);
				MainActivity.contactdb.execSQL("INSERT INTO whitelist VALUES('"+name+"','"+number+"');");
				listView.setAdapter(new ContactsAdapter(this, contacts));

	        }
    
		}
	   
    }
}
     
     
     
     
     
     
     