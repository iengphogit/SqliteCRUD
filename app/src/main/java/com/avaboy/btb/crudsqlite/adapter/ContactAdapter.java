package com.avaboy.btb.crudsqlite.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avaboy.btb.crudsqlite.R;
import com.avaboy.btb.crudsqlite.controller.ContactDetailActivity;
import com.avaboy.btb.crudsqlite.model.ContactModel;

import java.util.List;

/**
 * Created by iengpho on 8/29/18.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<ContactModel> contactModelList;
    private Context context;

    public ContactAdapter(List<ContactModel> contactModels) {
        this.contactModelList = contactModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ContactModel contactModel = contactModelList.get(position);
//        holder.photoId.setImageResource();
        if(contactModel.getGender().equals("Female")){
            holder.photoId.setImageDrawable(context.getResources().getDrawable(R.drawable.female));
        }else if(contactModel.getGender().equals("Male")){
            holder.photoId.setImageDrawable(context.getResources().getDrawable(R.drawable.male));
        }else{
            holder.photoId.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_launcher));
        }

        holder.nameTv.setText(String.format("%s %s", contactModel.getLastName(), contactModel.getFirstName()));
        holder.phoneTv.setText(contactModel.getPhone());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("my_log_RecycleAdapter", "onClick: ");
                Intent intent = new Intent(context, ContactDetailActivity.class);
                intent.putExtra("contact", contactModel);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return contactModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTv, phoneTv;
        ImageView photoId;
        View layout;

        ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            nameTv = itemView.findViewById(R.id.contact_row_full_name);
            phoneTv = itemView.findViewById(R.id.contact_row_phone_number);
            photoId = itemView.findViewById(R.id.contact_row_photo_id);
        }
    }


}
