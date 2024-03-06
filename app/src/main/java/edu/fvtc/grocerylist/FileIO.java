package edu.fvtc.grocerylist;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FileIO {
    public static final String TAG = "FileIOMethods";

    public void writeFile(String filename,
                          AppCompatActivity activity,
                          String[] items)
    {
        try {
            OutputStreamWriter writer = new OutputStreamWriter(activity.openFileOutput(filename, Context.MODE_PRIVATE));
            String line = "";

            for(int counter = 0; counter < items.length; counter++)
            {
                line = items[counter];
                if(counter < items.length - 1)
                    line += "\r\n";
                writer.write(line);
                Log.d(TAG, "writeFile: " + line);
            }
            writer.close();

        } catch (FileNotFoundException e) {
            Log.d(TAG, "writeFile: FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "writeFile: IOException: " + e.getMessage());
        } catch(Exception e)
        {
            Log.i(TAG, "writeFile: " + e.getMessage());
        }
    }

    public ArrayList<String> readFile(String filename, AppCompatActivity activity)
    {
        ArrayList<String> items = new ArrayList<String>();

        try{
            InputStream inputStream = activity.openFileInput(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                items.add(line);
            }

            bufferedReader = null;
            inputStreamReader.close();
            inputStream.close();


        }catch(Exception e) {
            Log.d(TAG, "readFile: " + e.getMessage());
        }
        return items;
    }

    public ArrayList<GroceryItem> ReadFromXmlFile(String filename, AppCompatActivity activity)
    {
        ArrayList<GroceryItem> actors = new ArrayList<GroceryItem>();
        Log.d(TAG, "ReadFromXmlFile: Start");

        try{
            InputStream inputStream = activity.openFileInput(filename);
            XmlPullParser xmlPullParser = Xml.newPullParser();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            xmlPullParser.setInput(inputStreamReader);
            Log.d(TAG, "ReadFromXmlFile: ");
            while(xmlPullParser.getEventType() != XmlPullParser.END_DOCUMENT) {
                Log.d(TAG, "ReadFromXmlFile: End_Document");
                if (xmlPullParser.getEventType() == XmlPullParser.START_TAG) {
                    if (xmlPullParser.getName().equals("actor")) {
                        Log.d(TAG, "ReadFromXmlFile: Start Grocery Parsing");
                        String groceryDescription = xmlPullParser.getAttributeValue(null, "groceryDescription");
                        int isOnShopList = Integer.parseInt(xmlPullParser.getAttributeValue(null, "isOnShoppingList"));
                        int isInCart = Integer.parseInt(xmlPullParser.getAttributeValue(null, "isInCart"));
                        GroceryItem groceryItem = new GroceryItem(groceryDescription, isOnShopList, isInCart);
                        actors.add(groceryItem);
                        Log.d(TAG, "ReadFromXmlFile: " + groceryItem.toString());
                    }
                }
                xmlPullParser.next();
            }
            inputStreamReader.close();
            inputStream.close();
        }
        catch(Exception e)
        {
            Log.d(TAG, "ReadFromXmlFile: " + e.getMessage());
        }
        return actors;
    }

    public void WriteXMLFile(String filename,
                             AppCompatActivity activity,
                             ArrayList<GroceryItem> groceryItems)
    {
        try
        {
            Log.d(TAG, "WriteXMLFile: Start");
            XmlSerializer serializer = Xml.newSerializer();
            File file = new File(filename);
            OutputStreamWriter outputStreamWriter = null;

            outputStreamWriter = new OutputStreamWriter(activity.getApplicationContext().openFileOutput(filename,
                    Context.MODE_PRIVATE));

            serializer.setOutput(outputStreamWriter);

            serializer.startDocument("UTF-8", true);
            serializer.startTag("","grocerys");
            serializer.attribute("", "number", String.valueOf(groceryItems.size()));

            for (GroceryItem groceryItem : groceryItems)
            {
                serializer.startTag("", "grocery");
                serializer.attribute("", "id", String.valueOf(groceryItem.getId()));
                serializer.attribute("", "groceryName", String.valueOf(groceryItem.getName()));
                serializer.endTag("", "grocery");
                Log.d(TAG, "WriteXMLFile: ");
            }

            serializer.endTag("","grocerys");
            serializer.endDocument();
            serializer.flush();
            outputStreamWriter.close();
            Log.d(TAG, "WriteXMLFile: Wrote" + groceryItems.size());
            Log.d(TAG, "WriteXMLFile: End");
        }
        catch (Exception e)
        {
            Log.d(TAG, "WriteXMLFile: " + e.getMessage());
        }
    }
}

