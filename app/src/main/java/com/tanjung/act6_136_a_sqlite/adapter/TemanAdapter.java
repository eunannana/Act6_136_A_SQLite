package com.tanjung.act6_136_a_sqlite.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tanjung.act6_136_a_sqlite.MainActivity;
import com.tanjung.act6_136_a_sqlite.R;
import com.tanjung.act6_136_a_sqlite.TemanBaru;
import com.tanjung.act6_136_a_sqlite.database.DBController;
import com.tanjung.act6_136_a_sqlite.database.Teman;
import com.tanjung.act6_136_a_sqlite.edit_teman;

import java.util.ArrayList;
import java.util.HashMap;

public class TemanAdapter extends RecyclerView.Adapter<TemanAdapter.TemanViewHolder> {

    private ArrayList<Teman> listData;
    private Context control;

    public TemanAdapter(ArrayList<Teman> listData) {
        this.listData = listData;
    }

    @Override
    public TemanAdapter.TemanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInf = LayoutInflater.from(parent.getContext());
        View view = layoutInf.inflate(R.layout.row_data_teman, parent, false);
        control = parent.getContext();
        return new TemanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TemanAdapter.TemanViewHolder holder, int position) {
        String nma, tlp, id;

        id = listData.get(position).getId();
        nma = listData.get(position).getNama();
        tlp = listData.get(position).getTelpon();
        DBController db = new DBController(control);

        holder.namaTxt.setTextColor(Color.BLUE);
        holder.namaTxt.setTextSize(20);
        holder.namaTxt.setText(nma);
        holder.telponTxt.setText(tlp);

        holder.cardku.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v){
                PopupMenu popupMenu = new PopupMenu(control, holder.cardku);
                popupMenu.inflate(R.menu.popupmenu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch(menuItem.getItemId()){
                            case R.id.edit:
                                Intent i = new Intent(control, edit_teman.class);
                                i.putExtra("id", id);
                                i.putExtra("nama", nma);
                                i.putExtra("telpon", tlp);
                                control.startActivity(i);
                                break;
                            case R.id.delete:
                                HashMap<String, String> values = new HashMap<>();
                                values.put("id", id);
                                db.DeleteData(values);
                                Intent j = new Intent(control, MainActivity.class);
                                control.startActivity(j);
                                break;
                        }
                        return true;

                    }
                });
                popupMenu.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return (listData != null)?listData.size() : 0;
    }

    public class TemanViewHolder extends RecyclerView.ViewHolder {
        private CardView cardku;
        private TextView namaTxt, telponTxt;
        public TemanViewHolder(View view) {
            super (view);
            cardku = (CardView) view.findViewById(R.id.kartuku);
            namaTxt = (TextView) view.findViewById(R.id.textNama);
            telponTxt = (TextView) view.findViewById(R.id.textTelpon);
        }
    }
}
