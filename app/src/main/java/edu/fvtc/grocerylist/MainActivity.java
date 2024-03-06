package edu.fvtc.grocerylist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public static final String FILENAME = "data.txt";

    public static final String XMLFILENAME = "data.xml";

    ArrayList<GroceryItem> masterList;
    ArrayList<GroceryItem> shoppingCartList;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            // Use the index to get an actor
            int position = viewHolder.getAdapterPosition();
            int id = masterList.get(position).getId();
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

        //Bind the Recyclerview
        RecyclerView rvGroceries = findViewById(R.id.rvGrocerys);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvGroceries.setLayoutManager(layoutManager);
        GroceryAdapter groceryAdapter = new GroceryAdapter(masterList, this);
        groceryAdapter.setOnItemClickListener(onClickListener);

        rvGroceries.setAdapter(groceryAdapter);

        Log.d(TAG, "onCreate: ");

    }


    private void createGroceries() {

        masterList = new ArrayList<GroceryItem>();
        masterList.add(new GroceryItem("Pop Tarts", 0, 0));
        masterList.add(new GroceryItem("Ice Cream", 0, 0));
        masterList.add(new GroceryItem("Cheetos", 0, 0));
        masterList.add(new GroceryItem("Red Bull", 0,0));
        masterList.add(new GroceryItem("AlmondJoy", 0,0));
        masterList.add(new GroceryItem( "MilkyWay", 0,0));

        shoppingCartList = new ArrayList<GroceryItem>();
    }

    private void ShowMasterList()
    {
        RecyclerView rvGroceries = findViewById(R.id.rvGrocerys);
        GroceryAdapter adapter = new GroceryAdapter(masterList, this);
        adapter.setOnItemClickListener(onClickListener); // Set item click listener
        rvGroceries.setAdapter(adapter);
    }

    private void ShowShoppingList()
    {

        RecyclerView rvGroceries = findViewById(R.id.rvGrocerys);
        GroceryAdapter adapter = new GroceryAdapter(shoppingCartList, this);
        adapter.setOnItemClickListener(onClickListener); // Set item click listener
        rvGroceries.setAdapter(adapter);
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
            SaveData();
        }
        else if (id == R.id.action_clear_all)
        {
            Log.d(TAG, "onOptionsItemSelected: " + item.getTitle());
            //ClearAll();
        } else {
            Log.d(TAG, "onOptionsItemSelected: " + item.getTitle());
            //DeleteChecked();
            SaveData();
        }
        return super.onOptionsItemSelected(item);
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
                Log.d(TAG, "ReadTextFile: " + masterList.get(masterList.size() - 1).getDescription());
            }
            Log.d(TAG, "ReadTextFile: " + masterList.size());

        } catch (Exception e) {
            Log.d(TAG, "ReadTextFile: " + e.getMessage());
        }
    }

}