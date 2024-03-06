package edu.fvtc.grocerylist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class  GroceryAdapter extends RecyclerView.Adapter{
    private ArrayList<GroceryItem> groceryItemData;
    private View.OnClickListener onItemClickListener;


    public static final String TAG = "GroceryAdapter";


    private Context parentContext;

    public class GroceryViewHolder extends RecyclerView.ViewHolder{

        public CheckBox cbIsInShopList;
        public TextView tvDescription;
        private View.OnClickListener onClickListener;
        public GroceryViewHolder(@NonNull View itemView) {
            super(itemView);
            cbIsInShopList = itemView.findViewById(R.id.cbIsInShopList);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            // Code involved with clicking an item in the list
            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);

        }

        public TextView getTvGroceryName()
        {
            return tvDescription;
        }

        public CheckBox getCbIsInShopList()
        {
            return cbIsInShopList;
        }

    }
    public GroceryAdapter(ArrayList<GroceryItem> data, Context context)
    {
        groceryItemData = data;
        Log.d(TAG, "GroceryAdapter: " + data.size());
        parentContext = context;
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener)
    {
        Log.d(TAG, "setOnItemClickListener: ");
        onItemClickListener = itemClickListener;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.complex_item_view, parent, false);
        return new GroceryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + groceryItemData.get(position));
        GroceryViewHolder groceryViewHolder = (GroceryViewHolder) holder;
        groceryViewHolder.getTvGroceryName().setText(groceryItemData.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return groceryItemData.size();
    }
}

