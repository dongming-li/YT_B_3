package com.mygdx.creepycaverns;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Launches android game, handles core interface
 */
public class AndroidLauncher extends AndroidApplication {

    /**
     * Creates new VolleySinglton, new AndroidApplicationConfiguration,
     * and new CoreInterface - where all Volley communication happens.
     * @param savedInstanceState
     */
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        new VolleySingleton(getApplicationContext());
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		CoreInterface inter = new CoreInterface() {

		    String USERNAME = "";
		    String PASSWORD = "";
		    public String MAPLIST = "";
		    public String MAPID = "";
		    public Map MAP = new Map();
		    public String MAPSENDURL = "";
		    public char[][] editorMap = new char[10][10];
		    public Character[][] editorMonsters = new Character[10][10];
		    public boolean edit=false;

            @Override
            public String startLogin(String username, String password) {
                String urlLogin = URLs.URL_LOGIN + "?username=" + username + "&password=" + password;

                //if everything is fine
                JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, urlLogin, new JSONObject(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String message = response.getString("message");
                            String message1 = response.getString("message1");
                            String username = response.getString("username");
                            String password = response.getString("password");

                            USERNAME = username;
                            PASSWORD = password;

                            if (message.equalsIgnoreCase("success")) {

                                //creating a user object and giving them the values from json object
                                User user = new User(username, password);

                                if (message1.equalsIgnoreCase("Admin")) {
                                    System.out.println("CHECK LOGIN ADMIN");
                                    SharedPrefManager.getInstance(getApplicationContext()).adminLogin(user);
                                } else {
                                    System.out.println("CHECK LOGIN USER");
                                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user); }


                            } else {
                                // Toast.makeText(getApplicationContext(), "Not registered", Toast.LENGTH_LONG).show();
                            }


                        } catch (Exception e) {
                            // Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjRequest);
                return USERNAME;
            }

            @Override
            public void startRegister(String username, String password) {
                String urlLogin = URLs.URL_REGISTER + "?username=" + username + "&password=" + password;

                //if everything is fine
                JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, urlLogin, new JSONObject(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String message = response.getString("message");
                            String username = response.getString("username");
                            String password = response.getString("password");

                            if (message.equalsIgnoreCase("success")) {
                                //creating a user object and giving them the values from json object
                                User user = new User(username, password);

                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                            } else {
                               // Toast.makeText(getApplicationContext(), "Already registered", Toast.LENGTH_LONG).show();
                            }


                        } catch (Exception e) {
                            // Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjRequest);
            }

            @Override
            public boolean isLoggedIn(String username, String password) {
                // Check if given user is logged in
                return SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn();

            }

            @Override
            public String isAdmin() {
                return SharedPrefManager.getInstance(getApplicationContext()).isAdmin();
            }

            @Override
            public String getUser() {
                User  user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
                return user.getUsername();
            }

            @Override
            public void dispose() {
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }

            @Override
            public String getJSONMaps() {

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URLs.URL_MAPSELECT, new JSONObject(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            MAPLIST = response.toString();
                            // generate buttons
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        System.out.println("Message: There was an error in map select volley");
                    }
                });

                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
                return MAPLIST;
            }

            @Override
            public String getMAPLIST(){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return MAPLIST;
            }

            @Override
            public ArrayList<String> parseMaps(String maps) {

                ArrayList<String> result = new ArrayList<>();

                try {
                    int count = 0;
                    JSONObject jsonObject = new JSONObject(maps);
                    while (jsonObject.has(Integer.toString(count))) {
                        final JSONObject temp = jsonObject.getJSONObject(Integer.toString(count));
                        result.add(temp.getString("mapName"));
                        result.add(temp.getString("username"));
                        result.add(temp.getString("mapid"));
                        count++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return result;
            }

            @Override
            public void setMAPID(String mapID) {
                MAPID = mapID;
            }

            @Override
            public void setMap(String mapID) {

                String urlMap = URLs.URL_MAPREQUEST + "?mapID=" + mapID;

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlMap, new JSONObject(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try{
                            MAP = new Map(jsonObject.toString());
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                    }
                });
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
            }

            @Override
            public char[][] getMap() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return MAP.getMapCells();
            }

            @Override
            public char[][] getMonsters() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return MAP.getMonsterCells();
            }

            @Override
            public String getMAPID() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return MAPID;
            }

            @Override
            public void submitMap(char[][] map, Character[][] charMap, String mapName, String username) {

                String mapSendURL = URLs.URL_MAPSEND + "?mapID=-1&mapName=" + mapName + "&username=" + username ;

                int index = 0;

                for (int a = 0; a < map.length; a++) {
                    for (int b = 0; b < map[0].length; b++) {

                        try {

                            JSONObject temp = new JSONObject();
                            temp.put("x", a);
                            temp.put("y", b);
                            temp.put("data", map[a][b]);

                            mapSendURL += "&" + index + "=" + temp.toString();
                            index++;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

                for (int c = 0; c < charMap.length; c++) {
                    for (int d = 0; d < charMap.length; d++) {
                        if (charMap[c][d] != null) {
                            try {
                                JSONObject temp = new JSONObject();
                                temp.put("x", c);
                                temp.put("y", d);
                                temp.put("data", "m");

                                mapSendURL += "&" + index + "=" + temp.toString();
                                index++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                MAPSENDURL = mapSendURL;

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, mapSendURL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                    }
                });

                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
            }

            @Override
            public String getMapURL() {
                return MAPSENDURL;
            }

            @Override
            public char[][] getEditorMap() {
                return editorMap;
            }

            @Override
            public void setEditorMap(char[][] map) {
                editorMap = map;
                this.MAP.setMapCells(map);
            }

            @Override
            public Character[][] getEditorMonsters() {
                return editorMonsters;
            }

            @Override
            public void setEditorMonster(Character[][] mons) {
                editorMonsters = mons;
            }

            @Override
            public void setMapMonsters(char[][] charMap){
                this.MAP.setMonsterCells(charMap);
            }

            @Override
            public void setEdit(boolean bool){edit=bool;}

            @Override
            public boolean getEdit(){return edit;}

            @Override
            public void banActor(String username, String banner) {
                String urlLogin = URLs.URL_BAN + "?username=" + username + "?banner=" + banner;

                //if everything is fine
                JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, urlLogin, new JSONObject(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                        } catch (Exception e) {
                            // Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjRequest);
            }


        };

		//inter.dispose();
		initialize(new CreepyCaverns(inter), config);
	}
}
