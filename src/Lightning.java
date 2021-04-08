import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;

import java.io.FileReader;
import java.util.Iterator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ethlo.quadkey.*;

public class Lightning {

    public static void main(String[] args){

        //initialize variable used in processing
        JSONParser parser = new JSONParser();
        JSONObject lightningJson;
        JSONObject assetJson;
        HashMap <String, Boolean> printChecker = new HashMap<String, Boolean>();
        Boolean checker;
        Coordinate strikePoint;
        QuadKey strikeQuadKey;
        int heartBeat=9;
        Long flashType;


        //Prepare asset as Hashmap
        String assetFile = "../assets/assets.json";
        HashMap<String, JSONObject> assetMap = new HashMap<String, JSONObject>();
        assetMap = constructAssets(assetFile);

        String lightningFile = "../input/lightning.json";
        try {
            //Read lightning file
            File lightningData = new File(lightningFile);
            Scanner lightningReader = new Scanner(lightningData);
            //iterate lightning line by line at the same time check if it has asset
            while (lightningReader.hasNextLine()) {
                String lightning = (String) lightningReader.nextLine();
                lightningJson = (JSONObject) parser.parse(lightning);
                
                //exclude heartbeat(flashtype = 9) in printing
                flashType = (Long)lightningJson.get("flashType");
                if(flashType.intValue() != heartBeat){
                    strikePoint = new Coordinate((Double)lightningJson.get("latitude"), (Double)lightningJson.get("longitude"));
                    strikeQuadKey = new QuadKey(strikePoint, 12);
                    
                    //check if asset is already printed
                    checker = printChecker.get(strikeQuadKey.getAsString());
                    if(checker == null){
                        assetJson = assetMap.get(strikeQuadKey.getAsString());
                        if(assetJson != null){
                            //lightning alert for <assetOwner>:<assetName>
                            System.out.println("lightning alert for "+assetJson.get("assetOwner")+":" +assetJson.get("assetName"));
                            //update printChecker that asset has been printed.
                            printChecker.put(strikeQuadKey.getAsString(), true);
                        }
                    }
                }
                // System.out.println(strikeQuadKey.getAsString());
            }
            lightningReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException error occurred.");
            e.printStackTrace();
        }catch (Exception e) {
            System.out.println("Exception error occurred.");
            e.printStackTrace();
        }

    }

    /**
     * Read assetfile as JSON and construct it into hashmap
     * using quadKey as key and asset as value
     * @param assetFile file path of asset file
     * @return HashMap(quadKey:asset)
     */
    private static HashMap<String, JSONObject> constructAssets(String assetFile){
        HashMap<String, JSONObject> assetMap = new HashMap<String, JSONObject>();
        JSONParser jsonParser = new JSONParser();
        
        try (FileReader reader = new FileReader(assetFile)){
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray assetList = (JSONArray) obj;
            assetList.forEach( asset -> assignToAssetMap(assetMap, (JSONObject) asset));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return assetMap;
    }

    /**
     * Assign asset in assetMap using quadKey as key
     * @param assetMap Hashmap quadkey as key and asset as value
     * @param asset single asset object
     */
    private static void assignToAssetMap(
        HashMap<String, JSONObject> assetMap,
        JSONObject asset
    ) {
        assetMap.put((String)asset.get("quadKey"), asset);
    }
}

