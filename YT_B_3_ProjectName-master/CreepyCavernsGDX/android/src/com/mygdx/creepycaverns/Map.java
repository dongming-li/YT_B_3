package com.mygdx.creepycaverns;

/**
 * Created by daman_000 on 10/6/2017.
 */

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Map class, used to parse json data from server and turn it into a 2d char array containing data for map tiles and a 2d array for the monster positions
 */
public class Map {
    private char[][] mapCells = new char[10][10];
    private char[][] monsterCells = new char[10][10];
    private int mapID;
    private String mapName;

    /**
     * default constructor that is not really used, creates an empty map
     */
    public Map()
    {
        try {
            JSONObject reader = new JSONObject("0");
            mapName = reader.getString("mapName");
            mapID = reader.getInt("mapID");
            //JSONArray mapData = reader.getJSONArray("mapData");
            int i = 0;
            while (reader.has(Integer.toString(i)))
            {
                if(reader.getJSONObject(Integer.toString(i)).getString("data").charAt(0) != 'm' || reader.getJSONObject(Integer.toString(i)).getString("data").charAt(0) != 'z')
                {
                    mapCells[reader.getJSONObject(Integer.toString(i)).getInt("x")][reader.getJSONObject(Integer.toString(i)).getInt("y")] = reader.getJSONObject(Integer.toString(i)).getString("data").charAt(0);
                    System.out.println("Message: getting map cell");
                }
                else
                {
                    monsterCells[reader.getJSONObject(Integer.toString(i)).getInt("x")][reader.getJSONObject(Integer.toString(i)).getInt("y")] = reader.getJSONObject(Integer.toString(i)).getString("data").charAt(0);
                    System.out.println("Message: Monster at " + reader.getJSONObject(Integer.toString(i)).getInt("x") + " " + reader.getJSONObject(Integer.toString(i)).getInt("y"));
                }
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Map constructor that parses the given json into arrays
     * @param json the json retrieved from the server that will be parsed
     */
    public Map(String json)
    {
        try {
            JSONObject reader = new JSONObject(json);
            mapName = reader.getString("mapName");
            mapID = reader.getInt("mapID");
            //JSONArray mapData = reader.getJSONArray("mapData");
            int i = 0;
            while (reader.has(Integer.toString(i)))
            {
                if(reader.getJSONObject(Integer.toString(i)).getString("data").charAt(0) != 'm' && reader.getJSONObject(Integer.toString(i)).getString("data").charAt(0) != 'z')
                {
                    mapCells[reader.getJSONObject(Integer.toString(i)).getInt("x")][reader.getJSONObject(Integer.toString(i)).getInt("y")] = reader.getJSONObject(Integer.toString(i)).getString("data").charAt(0);
                    System.out.println("Message: getting map cell " + reader.getJSONObject(Integer.toString(i)).getString("data").charAt(0));
                }
                else
                {
                    monsterCells[reader.getJSONObject(Integer.toString(i)).getInt("x")][reader.getJSONObject(Integer.toString(i)).getInt("y")] = reader.getJSONObject(Integer.toString(i)).getString("data").charAt(0);
                    System.out.println("Message: Monster at " + reader.getJSONObject(Integer.toString(i)).getInt("x") + " " + reader.getJSONObject(Integer.toString(i)).getInt("y"));
                }
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * getter for returning the char array containing the map tile data
     * @return 2d char array of map data
     */
    public char[][] getMapCells()
    {
        return mapCells;
    }

    /**
     * getter for returning the char array containing the monsters and their positions
     * @return 2d array containing monsters
     */
    public char[][] getMonsterCells()
    {
        return monsterCells;
    }
    public void setMapCells(char[][] map){
        this.mapCells=map;
    }
    public void setMonsterCells(char[][] charMap){
        this.monsterCells=charMap;
    }
}
