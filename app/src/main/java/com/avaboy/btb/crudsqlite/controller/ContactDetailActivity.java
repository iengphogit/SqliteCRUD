package com.avaboy.btb.crudsqlite.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avaboy.btb.crudsqlite.R;
import com.avaboy.btb.crudsqlite.model.ContactModel;
import com.avaboy.btb.crudsqlite.model.DBHandler;

public class ContactDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ContactDetailActivity";
    private TextView firstNameTv, lastNameTv, genderTv, phoneTv, emailTv;
    private Button btnEdit, btnDelete;
    private ImageView photoId;
    private ContactModel contactModel;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set to fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_contact_detail);
        //Hide Top Action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        dbHandler = new DBHandler(this);
        Intent data = getIntent();
        contactModel = (ContactModel) data.getSerializableExtra("contact");
        firstNameTv = findViewById(R.id.detail_first_name);
        lastNameTv = findViewById(R.id.detail_last_name);
        genderTv = findViewById(R.id.detail_gender);
        phoneTv = findViewById(R.id.detail_phone);
        emailTv = findViewById(R.id.detail_email);

        photoId = findViewById(R.id.detail_photo_id);

        btnEdit = findViewById(R.id.detail_btn_edit);
        btnDelete = findViewById(R.id.detail_btn_delete);

        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        if (contactModel != null) {
            Log.d(TAG, "onCreate: ");
            firstNameTv.setText(contactModel.getFirstName());
            lastNameTv.setText(contactModel.getLastName());
            genderTv.setText(contactModel.getGender());
            phoneTv.setText(contactModel.getPhone());
            emailTv.setText(contactModel.getEmail());

            if(contactModel.getGender().equals("Female")){
                photoId.setImageDrawable(getResources().getDrawable(R.drawable.female));
            }else if(contactModel.getGender().equals("Male")){
                photoId.setImageDrawable(getResources().getDrawable(R.drawable.male));
            }

        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.detail_btn_edit:
                doEdit(contactModel);
                break;

            case R.id.detail_btn_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(ContactDetailActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure to delete?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doDelete(contactModel);
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                break;
        }
    }

    private void doEdit(ContactModel contact) {

        Intent intent = new Intent(this, AddContactActivity.class);
        intent.putExtra("contact",contact);
        startActivity(intent);
        finish();

    }

    private void doDelete(ContactModel contact) {
        if (dbHandler.deleteContact(contact)) {
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
        }
    }
}
