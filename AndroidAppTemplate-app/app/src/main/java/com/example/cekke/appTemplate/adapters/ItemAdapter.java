package com.example.cekke.appTemplate.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cekke.appTemplate.ShowFragment;
import com.example.cekke.appTemplate.R;
import com.example.cekke.appTemplate.ShowActivity;
import com.example.cekke.appTemplate.model.Item;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.CryptoHolder>{
    private ArrayList<Item> mData;
    private Activity mACtivity;
    private int valuteCount=0;

    public ItemAdapter(ArrayList<Item> data, Activity activity) {
        this.mData = data;
        this.mACtivity = activity;
        valuteCount=0;
    }

    @Override
    public ItemAdapter.CryptoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if(viewType == R.layout.row){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_button, parent, false);
            return new ItemAdapter.CryptoHolder(view,false);
        }

        return new ItemAdapter.CryptoHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.CryptoHolder holder, int position) {
        Item item = mData.get(position);

        if(position!=mData.size()-1)
        {
            holder.setId(item.getId());
            holder.setName(item.getName());
            holder.setValue(item.getValue());

            String temp= item.getValue();
            Log.e("item","valuta= "+item.getId()+""+item.getId()+" - "+item.getName()+" - "+item.getValue());

            int pos=0, incOrDec=0;
            for (pos = 0 ; pos<temp.length() ; pos++)
            {
                if ((temp.charAt(pos) == '+')){
                    incOrDec=1;
                    break;
                }
                if ((temp.charAt(pos) == '-')){
                    incOrDec=0;
                    break;
                }
            }
            if (pos == temp.length())
                incOrDec=2;

            Glide.with(mACtivity)
                    .load(item.getUrlImage())
                    .into(holder.itemImageView);

        }else{
            //doNothing... here we have the button layout so we have nothing to set on the holder
            //if we set something it crash
        }
    }

    @Override
    public int getItemCount() {
        if (mData == null)
            return 0;
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mData.size()-1) ? R.layout.load_button : R.layout.row;
    }

    public class CryptoHolder extends RecyclerView.ViewHolder {

        ImageView itemImageView,itemCartImageView;
        TextView itemNameTextView, itemDetailTextView,itemValueTextView;
        String hiddenText;
        int item_id=-1;
        Button button;

        public CryptoHolder(final View itemView) {
            super(itemView);

            itemImageView = (ImageView) itemView.findViewById(R.id.iv_item_img);
            itemCartImageView = (ImageView) itemView.findViewById(R.id.iv_item_cart);
            itemCartImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(),"EnterProcedure to add to cart",Toast.LENGTH_LONG).show();
                }
            });

            itemNameTextView = (TextView) itemView.findViewById(R.id.tb_item_name);
            itemValueTextView = (TextView) itemView.findViewById(R.id.tv_item_rating);

            itemDetailTextView = (TextView) itemView.findViewById(R.id.tb_item_detail);
            itemDetailTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(),"Open Details",Toast.LENGTH_LONG).show();
                    Intent contact = new Intent(mACtivity.getApplicationContext(), ShowActivity.class);
                    contact.putExtra("cod",String.valueOf(item_id));
                    mACtivity.startActivity(contact);
                }
            });
        }

        public CryptoHolder(final View itemView, boolean end) {
            super(itemView);

            button= (Button) itemView.findViewById(R.id.loadButton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String temp= mACtivity.getLocalClassName();
                    Log.i("event","print other  with caller -> "+ temp);
                    if (temp.contains("LocaliListActivity"))
                    {
                        Log.i("check","localilist is calling");
                        //set refresh variable
                        ShowFragment.setReload(true);
                    }
                    //dont wanna waste time like in the list mode, because here the restaurant are surelly less
                    //so its not so much usefull to do it
                    if (temp.contains("MainActivity"))
                    {
                        Log.i("check","mainfrag is calling");
                        //set refresh variable
                        Toast.makeText(itemView.getContext(), "Impossibile Aggiornare, non ci sono altre valute nell'elenco",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

        public void setId(int id) {
            item_id=id;
        }

        public void setName(String name) {
            itemNameTextView.setText(name);
        }

        public void setValue(String val) {
            itemValueTextView.setText(val);
        }


    }
}
