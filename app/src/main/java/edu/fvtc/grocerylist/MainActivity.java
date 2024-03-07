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
    public static final String FILENAME = "data.txt";

    public static final String XMLFILENAME = "data.xml";

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

        createGroceries();

        ArrayList<String> groceries = new ArrayList<String>();
        for(GroceryItem groceryItem : masterList)
        {
            groceries.add(groceryItem.toString());
        }


        ShowMasterList();
        Log.d(TAG, "onCreate: isShoppingList Shown = " + isShoppingListShown);
    }
    private void ShowMasterList(){
        Log.d(TAG, "ShowMasterList: isShoppingListShown = " + isShoppingListShown);
        //Bind the Recyclerview
        RecyclerView rvGroceries = findViewById(R.id.rvGrocerys);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvGroceries.setLayoutManager(layoutManager);
        GroceryAdapter groceryAdapter = new GroceryAdapter(masterList, this);
        groceryAdapter.setOnItemClickListener(onClickListener);
        rvGroceries.setAdapter(groceryAdapter);
    }

    private void ShowShoppingList() {
        isShoppingListShown = true; // used in dialog to determine what is being shown

        Log.d(TAG, "ShowShoppingList: isShoppingListShown = " + isShoppingListShown);


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

        // Log the contents of shoppingListItems
        for (GroceryItem item : shoppingListItems) {
            Log.d(TAG, "Shopping List Item: " + item.getName());
        }
    }
    private void createGroceries() {

        masterList = new ArrayList<GroceryItem>();
        masterList.add(new GroceryItem("Pop Tarts", 0, 0));
        masterList.add(new GroceryItem("Ice Cream", 0, 0));
        masterList.add(new GroceryItem("Cheetos", 0, 0));
        masterList.add(new GroceryItem("Red Bull", 0,0));
        masterList.add(new GroceryItem("AlmondJoy", 0,0));
        masterList.add(new GroceryItem( "MilkyWay", 0,0));


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
            Log.d(TAG, "onOptionsItemSelected: " + item.getTitle());
            isShoppingListShown = false;
            ShowMasterList();
        }
        else if (id == R.id.action_showShopList)
        {
            Log.d(TAG, "onOptionsItemSelected: " + item.getTitle());
            ShowShoppingList();

        }
        else if (id == R.id.action_add_item)
        {
            Log.d(TAG, "onOptionsItemSelected: " + item.getTitle());
            addItemDialog();
            SaveData();
        }
        else if (id == R.id.action_clear_all)
        {
            Log.d(TAG, "onOptionsItemSelected: " + item.getTitle());
            ClearAll();
        } else {
            Log.d(TAG, "onOptionsItemSelected: " + item.getTitle());
            //DeleteChecked();
            SaveData();
        }
        return super.onOptionsItemSelected(item);
    }

    private void ClearAll() {
        for (GroceryItem item : masterList)
        {
            item.setIsOnShoppingList(0);
        }
        ShowMasterList();
    }


    private void addItemDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View addItemView = layoutInflater.inflate(R.layout.additem, null);

        // Show the dialog to the user modularly
        new AlertDialog.Builder(this)
                .setTitle(R.string.add_item)
                .setView(addItemView)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick: Ok");
                        EditText etAddItem = addItemView.findViewById(R.id.etAddItem);
                        String item = etAddItem.getText().toString();

                        if (isShoppingListShown)
                        {
                            masterList.add(new GroceryItem(item, 1, 0));
                            ShowShoppingList();

                        } else
                        {
                            masterList.add(new GroceryItem(item, 0, 0));
                            ShowMasterList();
                        }

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

   private void SaveData() {
        WriteTextFile();
        WriteXMLFile();
   }

    private void LoadData() {
        ReadTextFile();
        ReadXMLFile();
    }
    private void WriteXMLFile() {
        try {
            FileIO fileIO = new FileIO();
            fileIO.WriteXMLFile(XMLFILENAME, this, masterList);
            Log.d(TAG, "writeXMLFile: End");
        }
        catch(Exception e)
        {
            Log.d(TAG, "writeXMLFile: " + e.getMessage());
        }
    }

    private void ReadXMLFile() {
        FileIO fileIO = new FileIO();
        masterList = fileIO.ReadFromXmlFile(XMLFILENAME, this);
        Log.d(TAG, "readXMLFile: Actors:" + masterList.size());
    }

    private void WriteTextFile() {
        try{
            FileIO fileIO = new FileIO();
            int counter = 0;
            String[] data = new String[masterList.size()];

            for(GroceryItem groceryItem : masterList)
            {
                data[counter++] = groceryItem.toString();
            }
            fileIO.writeFile(FILENAME, this, data);

        }
        catch(Exception e)
        {
            Log.d(TAG, "WriteTextFile: " + e.getMessage());
        }
    }


    private void ReadTextFile() {
        try {
            FileIO fileIO = new FileIO();
            ArrayList<String> strData = fileIO.readFile(FILENAME, this);

            masterList = new ArrayList<GroceryItem>();

            for (String s : strData) {
                String[] data = s.split("\\|");
                masterList.add(new GroceryItem(
                        data[0],
                        Integer.parseInt(data[1]),
                        Integer.parseInt(data[2])));
                Log.d(TAG, "ReadTextFile: " + masterList.get(masterList.size() - 1).getName());
            }
            Log.d(TAG, "ReadTextFile: " + masterList.size());

        } catch (Exception e) {
            Log.d(TAG, "ReadTextFile: " + e.getMessage());
        }
    }

}