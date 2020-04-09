package com.example.sqllitedemo;

import androidx.appcompat.app.AppCompatActivity;

// for sql database
import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    EditText editEmployeeCode, editName, editDepartment, editDesignation;
    Button btnSave;

    // Database instance variable
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEmployeeCode = findViewById(R.id.editText);
        editName = findViewById(R.id.editText2);
        editDepartment = findViewById(R.id.editText3);
        editDesignation = findViewById(R.id.editText4);

        // Focus the control to this field whenever the app starts
        editEmployeeCode.requestFocus();

        // A temporary database will be created
        db = openOrCreateDatabase("testCompany", Context.MODE_PRIVATE, null);

        // Create a new table
        db.execSQL("CREATE TABLE IF NOT EXISTS testEmployees(empCode VARCHAR, empName VARCHAR, empDept VARCHAR, empDesign VARCHAR)");

        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db.execSQL("INSERT INTO testEmployees(empCode, empName, empDept, empDesign)" +
                        "VALUES('" + editEmployeeCode.getText() + "', '" + editName.getText() + "', '" + editDepartment.getText() + "', '" + editDesignation.getText() + "');");


                Cursor crs = db.rawQuery("SELECT * FROM testEmployees",null);
                if (crs.getCount() == 0) {
                    showStatus("Error", "No Data");
                    return;
                }

                StringBuffer bfr;

                bfr = showRecords(crs);
                showStatus("After Insertion", bfr.toString());
                crs.close();
            }
        });
    }

    public void showStatus(String title, String resultContent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(resultContent);
        builder.show();
    }

    public StringBuffer showRecords(Cursor cr) {
        StringBuffer bfr = new StringBuffer();
        cr.moveToFirst();
        bfr.append("Data" + "\n");
        bfr.append("Employee Code:" + cr.getString(0) + "\n");
        bfr.append("Name:" + cr.getString(1) + "\n");
        bfr.append("Department:" + cr.getString(2) + "\n");
        bfr.append("Designation:" + cr.getString(3) + "\n");

        return  bfr;
    }
}
