package com.example.sringer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
public class MainActivity extends Activity {
    Button btnNext;
    
    static SharedPreferences sharedpreferences;
    static Editor editor;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "ModeKey";
    
   
    static SQLiteDatabase contactdb;
    
    
   
    
    
    @Override
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
        setContentView(R.layout.activity_main);
        
       
              
  contactdb=openOrCreateDatabase("ContactDB", Context.MODE_PRIVATE, null);
        
        contactdb.execSQL("CREATE TABLE IF NOT EXISTS whitelist(name VARCHAR,number VARCHAR);");
     
     sharedpreferences = getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);

        
       
       
      if (sharedpreferences.contains(Name))
        {
        	 System.out.println("retrive key - value");
        	 String mode = sharedpreferences.getString(Name,"");
        	 System.out.println(mode); 
        	 TextView tv2=(TextView) findViewById(R.id.textView2); 
           tv2.setText(mode);

        }
   
        
        btnNext = (Button) findViewById(R.id.button1);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent ob = new Intent(MainActivity.this, CallManager.class);
                startActivity(ob);
            }
        });
    }
    
    
    
  
    
    
    
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
     
    overridePendingTransition(R.anim.anim_in, R.anim.anim_out);     
  }
}