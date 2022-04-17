package com.mygdx.creepycaverns;

import java.util.ArrayList;

/**
 * Interface between core and android modules.
 *
 * Created by Brennyn
 */

public interface CoreInterface {

    /**
     * Attempts to login using the given name and password,
     * fails if no such user exists in the DB.
     *
     * @param username given username
     * @param password given password
     * @return String username
     */
    String startLogin(String username, String password);

    /**
     * Attempts registration using the given name and password,
     * fails if a user with given username already exists.
     *
     * @param username given username
     * @param password given password
     */
    void startRegister(String username, String password);

    /**
     * Checks if given user is logged in
     *
     * @param username given username
     * @param password given password
     * @return boolean if given user is logged in
     */
    boolean isLoggedIn(String username, String password);

    /**
     * Checks if current user is an Admin user
     *
     * @return String "true" if user is an admin, else null
     */
    String isAdmin();

    /**
     * @return String username of current user
     */
    String getUser();

    /**
     * Deletes current session
     */
    void dispose();

    /**
     * Retrieves list of playable maps from DB
     *
     * @return List of maps
     */
    String getJSONMaps();

    /**
     * Retrieves list of maps
     *
     * @return List of maps
     */
    String getMAPLIST();

    /**
     * Parses list of maps -
     * gets mapName, username, and mapID
     *
     * @param maps - List of maps
     * @return ArrayList<String> of JSON objects representing each map's information
     */
    ArrayList<String> parseMaps(String maps);

    /**
     *
     * @param mapID
     */
    void setMAPID(String mapID);

    /**
     * Sets mapID
     *
     * @param mapID mapID to set
     */
    void setMap(String mapID);

    /**
     * Retrieves map
     *
     * @return char[][] of map tiles
     */
    char[][] getMap();

    char[][] getMonsters();

    /**
     * Returns MAPID
     *
     * @return MAPID
     */
    String getMAPID();

    /**
     * Submits map and charMap from Map Editor
     *
     * @param map char[][] of tiles in map
     * @param charMap Character[][] of character map
     */
    void submitMap(char[][] map, Character[][] charMap, String mapName, String username);

    /**
     * Testing - gets MapURL from last submit
     * @return last MapURL
     */
    String getMapURL();

    char[][] getEditorMap();
    void setEditorMap(char[][] map);
    Character[][] getEditorMonsters();
    void setEditorMonster(Character[][] mons);
    void setEdit(boolean bool);
    void setMapMonsters(char[][] charMap);
    boolean getEdit();

    void banActor(String username, String banner);

}
