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

    private ArrayList<GroceryItem> checkedGroceries = new ArrayList<>();
    private View.OnClickListener onItemClickListener;


    public static final String TAG = "GroceryAdapter";


    private Context parentContext;

    public void clearAllCheckboxes() {
        for (GroceryItem item : groceryItemData) {
            item.setIsOnShoppingList(0); // Set checkbox state to unchecked
        }
        notifyDataSetChanged(); // Notify adapter about the data change
    }

    public class GroceryViewHolder extends RecyclerView.ViewHolder{

        public CheckBox cbIsOnShopList;
        public TextView tvDescription;
        private View.OnClickListener onClickListener;
        public GroceryViewHolder(@NonNull View itemView) {
            super(itemView);
            cbIsOnShopList = itemView.findViewById(R.id.cbIsOnShopList);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            // Code involved with clicking an item in the list
            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);

        }

        public TextView getTvGroceryName()
        {
            return tvDescription;
        }

        public CheckBox getCbIsOnShopList()
        {
            return cbIsOnShopList;
        }


    }
    public GroceryAdapter(ArrayList<GroceryItem> data, Context context)
    {
        groceryItemData = data;
        Log.d(TAG, "GroceryAdapter: " + data.size());
        parentContext = context;
    }

    public ArrayList<GroceryItem> getCheckedGroceries()
    {
        return checkedGroceries;
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
        groceryViewHolder.getTvGroceryName().setText(groceryItemData.get(position).getName());

        // Set the state of the CheckBox based on the item's properties
        groceryViewHolder.getCbIsOnShopList().setChecked(groceryItemData.get(position).getIsOnShoppingList() == 1);

        // Handle the CheckBox state change
        groceryViewHolder.getCbIsOnShopList().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                groceryItemData.get(position).setIsOnShoppingList(isChecked ? 1 : 0);
                notifyItemChanged(position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return groceryItemData.size();
    }
}

