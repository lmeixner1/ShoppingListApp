package edu.fvtc.grocerylist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    public static final String TAG = "MainActivity";
    public static final String FILENAME = "masterlist.txt";

    GroceryItem groceryItem;
    int groceryItemId = -1;
    ArrayList<GroceryItem> masterList;
    ArrayList<GroceryItem> shoppingListItems = new ArrayList<>();

    private boolean isShoppingListShown = false;


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            // Use the index to get an actor
            int position = viewHolder.getAdapterPosition();
            //int id = masterList.get(position).getId();
            GroceryItem groceryItem = masterList.get(position);
            Log.d(TAG, "onClick: " + groceryItem.toString());

            //Add code to startActivity of another activity

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> groceries = new ArrayList<String>();
        masterList = readMaster(this);
        if (masterList.size() == 0)
            createGroceries();


        RebindGroceries();
        Log.d(TAG, "onCreate: isShoppingList Shown = " + isShoppingListShown);
    }

    private void RebindGroceries() {

        RecyclerView rvGroceries = findViewById(R.id.rvGrocerys);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvGroceries.setLayoutManager(layoutManager);
        GroceryAdapter groceryAdapter = new GroceryAdapter(masterList, this);
        groceryAdapter.setOnItemClickListener(onClickListener);
        rvGroceries.setAdapter(groceryAdapter);
    }

    private void initMaster(int itemId) {
        //Get the groceryItems
        masterList = readMaster(this);
        //Get the item
        groceryItem = masterList.get(itemId);
        ShowMasterList();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

      if(id == R.id.action_showMasterList)
        {
            setTitle("Master List");
            Log.d(TAG, "onOptionsItemSelected: " + item.getTitle());
            isShoppingListShown = false;
            ShowMasterList();
            readMaster(this);
        }
        else if (id == R.id.action_showShopList)
        {
            setTitle("Shopping List");
            Log.d(TAG, "onOptionsItemSelected: " + item.getTitle());
            ShowShoppingList();

        }
        else if (id == R.id.action_add_item)
        {
            Log.d(TAG, "onOptionsItemSelected: " + item.getTitle());
            addItemDialog();
        }
        else if (id == R.id.action_clear_all)
        {
            Log.d(TAG, "onOptionsItemSelected: " + item.getTitle());
            ClearAll();
        } else {
            Log.d(TAG, "onOptionsItemSelected: " + item.getTitle());
            DeleteChecked();
        }
        return super.onOptionsItemSelected(item);
    }

    private void ShowMasterList(){
        setTitle("Master List");
        Log.d(TAG, "ShowMasterList: isShoppingListShown = " + isShoppingListShown);
        //Bind the Recyclerview
       RebindGroceries();
    }

    private void ShowShoppingList() {

        isShoppingListShown = true;

        for (GroceryItem item : masterList)
        {
            if (item.getIsOnShoppingList() == 1)
            {
                shoppingListItems.add(item);
            }
        }

        //Bind the Recyclerview
        RecyclerView rvGroceries = findViewById(R.id.rvGrocerys);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvGroceries.setLayoutManager(layoutManager);

        GroceryAdapter groceryAdapter = new GroceryAdapter(shoppingListItems, this);
        rvGroceries.setAdapter(groceryAdapter);
    }

    private void DeleteChecked() {
        shoppingListItems.clear();
        for (int i = masterList.size() - 1; i >= 0; i--) {
            GroceryItem item = masterList.get(i);
            boolean isChecked = item.getIsOnShoppingList() == 1;

            if (isShoppingListShown && isChecked) {
                // Remove from shopping list only
                shoppingListItems.remove(item);
                item.setIsOnShoppingList(0);

            } else if (!isShoppingListShown && isChecked) {
                // Remove from master list only
                masterList.remove(i);
            }
        }

        // Update the view
        if(isShoppingListShown) {
            ShowShoppingList();
        } else {
            ShowMasterList();
        }
        FileIO.writeFile(FILENAME, this, createDataArray(masterList));
    }

    private void ClearAll() {
        for (GroceryItem item : masterList)
        {
            item.setIsOnShoppingList(0);
        }

        if(isShoppingListShown)
        {
            ShowShoppingList();
        }
        else {
            ShowMasterList();
        }
    }



    private void addItemDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View addItemView = layoutInflater.inflate(R.layout.additem, null);

        if(groceryItemId != -1)
        {
            //Get the groceryItem
            initMaster(groceryItemId-1);
        }else{
            groceryItem = new GroceryItem();
        }

        // Show the dialog to the user modularly
        new AlertDialog.Builder(this)
                .setTitle(R.string.add_item)
                .setView(addItemView)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick: Ok");
                        EditText etAddItem = addItemView.findViewById(R.id.etAddItem);


                        if (groceryItemId == -1)
                        {
                            groceryItem.setId(masterList.get(masterList.size()-1).getId() +1);
                            groceryItem.setDescription(etAddItem.getText().toString());
                            masterList.add(groceryItem);

                            if (isShoppingListShown) {
                                shoppingListItems.add(groceryItem);
                            }
                        }
                        else {
                            masterList.set(groceryItemId-1, groceryItem);
                        }

                        FileIO.writeFile(FILENAME, MainActivity.this, MainActivity.createDataArray(masterList));


                    }
                })
                .setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d(TAG, "onClick: Cancel");
                            }
                        }).show();

    }

    private void createGroceries() {
        masterList = new ArrayList<GroceryItem>();

        masterList.add(new GroceryItem(1,"Pop Tarts", 0, 0));
        masterList.add(new GroceryItem(2,"Ice Cream", 0, 0));
        masterList.add(new GroceryItem(3,"Cheetos", 0, 0));
        masterList.add(new GroceryItem(4,"Red Bull", 0, 0));
        masterList.add(new GroceryItem(5,"AlmondJoy", 0,0));
        masterList.add(new GroceryItem( 6,"MilkyWay", 0,0));

        FileIO.writeFile(FILENAME, this, createDataArray(masterList));
        masterList = readMaster(this);
    }
    public static ArrayList<GroceryItem> readMaster(AppCompatActivity activity) {
        ArrayList<String> strData = FileIO.readFile(FILENAME, activity);
        ArrayList<GroceryItem> masterList1 = new ArrayList<GroceryItem>();

        for(String s : strData)
        {
            Log.d(TAG, "readMaster: " + s);
            String[] data = s.split("\\|");
            masterList1.add(new GroceryItem(
                    Integer.parseInt(data[0]),
                    data[1],
                    Integer.parseInt(data[2]),
                    Integer.parseInt(data[3])
            ));
        }
        Log.d(TAG, "readTeams: " + masterList1.size());
        return masterList1;
    }

    public static String[] createDataArray(ArrayList<GroceryItem> masterList)
    {
        String[] groceryData = new String[masterList.size()];
        for(int count = 0; count < masterList.size(); count++)
        {
            groceryData[count] = masterList.get(count).toString();
        }
        return groceryData;
    }

}