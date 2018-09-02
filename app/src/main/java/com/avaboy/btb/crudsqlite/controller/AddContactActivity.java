package com.avaboy.btb.crudsqlite.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.avaboy.btb.crudsqlite.R;
import com.avaboy.btb.crudsqlite.model.ContactModel;
import com.avaboy.btb.crudsqlite.model.DBHandler;

public class AddContactActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText firstNameEdt, lastNameEdt, phoneNumberEdt, emailEdt;
    private Spinner genderSpn;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set to fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_add_contact);

        //Hide Top Action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        firstNameEdt = findViewById(R.id.add_contact_first_name);
        lastNameEdt = findViewById(R.id.add_contact_last_name);
        phoneNumberEdt = findViewById(R.id.add_contact_phone_number);
        emailEdt = findViewById(R.id.add_contact_email);

        genderSpn = findViewById(R.id.add_contact_gender);

        btnSave = findViewById(R.id.add_contact_btn_save);
        btnSave.setOnClickListener(this);


        //On Edit mode
        Intent data = getIntent();
        ContactModel contactModel = (ContactModel) data.getSerializableExtra("contact");
        if(contactModel != null){
            doMapFields(contactModel);
        }
    }

    private boolean isEditMode;
    private int mId;
    private void doMapFields(ContactModel contactModel){
        isEditMode = true;
        mId = contactModel.getId();
        firstNameEdt.setText(contactModel.getFirstName());
        lastNameEdt.setText(contactModel.getLastName());

        String[] genderArrs = getResources().getStringArray(R.array.gender);
        int length = genderArrs.length;
        for(int i=0; i < length; i++){
            if(contactModel.getGender().equals(genderArrs[i])){
                genderSpn.setSelection(i);
                break;
            }
        }

        phoneNumberEdt.setText(contactModel.getPhone());
        emailEdt.setText(contactModel.getEmail());

        btnSave.setText("Update");

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.add_contact_btn_save) {

            ContactModel contact = new ContactModel();
            contact.setFirstName(firstNameEdt.getText().toString());
            contact.setLastName(lastNameEdt.getText().toString());
            contact.setGender(genderSpn.getSelectedItem().toString());
            contact.setPhone(phoneNumberEdt.getText().toString());
            contact.setEmail(emailEdt.getText().toString());
            if(isEditMode){
                //Update
                contact.setId(mId);
                doSubmitUpdate(contact);
            }else{
                //Add new
                doSubmitSave(contact);
            }

        }
    }

    private void doSubmitUpdate(ContactModel contact) {
        DBHandler dbHandler = new DBHandler(this);
        if(isValid(contact)){
            if(dbHandler.updateContact(contact) > 0){
                Toast.makeText(this,"Update Success!",Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this,"Update Failed!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void doSubmitSave(ContactModel contact) {
        DBHandler dbHandler = new DBHandler(this);
        if (isValid(contact)) {
            if (dbHandler.addContact(contact)) {
                //Add success
                Toast.makeText(this,"Success!",Toast.LENGTH_SHORT).show();
                finish();
            } else {
                //Failed
                Toast.makeText(this,"Failed!",Toast.LENGTH_SHORT).show();
            }
        }

    }

    private boolean isValid(ContactModel contact) {
        if (contact.getGender().equals("Gender")) {
            //return false;

            //ignore this step without check validation
            contact.setGender("");
            return true;
        }
        return true;
    }
}
