package com.sciaps.android.libs.mainsettings.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by MonkeyFish on 1/17/14.
 */
public class LibzSettings {

    private static final String TAG ="LibzSettings";



    private boolean factoryMode;

    public LibzSettings(Context ctx){

        try {
            this.readSettingsXml(ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isFactoryMode() {
        return factoryMode;
    }

    public static boolean writeSettingsXml(){
        //create a new file called "new.xml" in the SD card
        //File newxmlfile = new File(Environment.getExternalStorageDirectory()+"/settings.xml");

        File newDir = new File(Environment.getExternalStorageDirectory()+"/LibZ");
        newDir.mkdirs();

        File newxmlfile = new File(newDir,"/settings.xml");


        try{
            newxmlfile.createNewFile();
        }catch(IOException e){
            Log.e("IOException", "exception in createNewFile() method");
        }
        //we have to bind the new file with a FileOutputStream
        FileOutputStream fileos = null;
        try{
            fileos = new FileOutputStream(newxmlfile);
        }catch(FileNotFoundException e){
            Log.e("FileNotFoundException", "can't create FileOutputStream");
        }
        //we create a XmlSerializer in order to write xml data
        XmlSerializer serializer = Xml.newSerializer();
        try {
            //we set the FileOutputStream as output for the serializer, using UTF-8 encoding
            serializer.setOutput(fileos, "UTF-8");
            //Write <?xml declaration with encoding (if encoding not null) and standalone flag (if standalone not null)
            serializer.startDocument(null, Boolean.valueOf(true));
            //set indentation option
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            //start a tag called "root"
            serializer.startTag(null, "device_settings");
            //i indent code just to have a view similar to xml-tree
            serializer.startTag(null, "factory_mode");
            serializer.text(String.valueOf(false));
            serializer.endTag(null, "factory_mode");

            serializer.startTag(null, "child2");
            //set an attribute called "attribute" with a "value" for <child2>
            serializer.attribute(null, "attribute", "value");
            serializer.endTag(null, "child2");

            serializer.startTag(null, "child3");
            //write some text inside <child3>
            serializer.endTag(null, "child3");

            serializer.endTag(null, "device_settings");
            serializer.endDocument();
            //write xml data into the FileOutputStream
            serializer.flush();
            //finally we close the file stream
            fileos.close();

            return true;
        } catch (Exception e) {
            Log.e("Exception","error occurred while creating xml file");
            return false;
        }
    };

    public void readSettingsXml(Context ctx){


        InputStream in_s = null;



        File dir = new File(Environment.getExternalStorageDirectory()+"/LibZ");
        File imageFile = null;
        final int readLimit = 16 * 1024;
        if(dir != null){
            imageFile = new File(dir, "settings.xml");
        } else {
            Log.w(TAG, "DIRECTORY is not available!");
        }
        if(imageFile != null){
            try {
                in_s = new BufferedInputStream(new FileInputStream(imageFile));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }

        } else {
            Log.w(TAG, "GIF image is not available!");
        }





        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            parseXML(parser);

        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    };


    private void parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
    {
        //     ArrayList<product> products = null;
        int eventType = parser.getEventType();
        LibzSettings currentSettings = null;

        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = null;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    //products = new ArrayList();


                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();

                    if (name.equals("factory_mode")){

                        try {
                            this.factoryMode = Boolean.parseBoolean(parser.nextText());
                            Log.w(TAG, "got factory mode "+parser.nextText());
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
            }
            eventType = parser.next();

        }
    }

}
