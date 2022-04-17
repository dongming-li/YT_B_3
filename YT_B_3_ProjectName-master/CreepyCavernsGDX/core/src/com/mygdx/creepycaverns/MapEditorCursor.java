package com.mygdx.creepycaverns;

/**
 * Created by arctu_000 on 11/2/2017.
 */

public class MapEditorCursor {
    public int mode;//0=terrain mode, 1=monster mode
    private int xPos;
    private int yPos;
    private int currChar;
    private char[] terrainTypes;
    public int currMon=0;
    MapEditorCursor(int x, int y){
        mode=0;
        xPos=x;
        yPos=y;
        currChar=1;
        terrainTypes=new char[]{'r','s','i','f','w'};
    }

    /**
     * Places an object depending on the mode Cursor is in.
     * @param map
     * @param charMap
     * @return true if placement was successful
     */
    public boolean placeThing(char[][] map, Character[][] charMap){
        if(mode==0){
            return placeTerrain(map, charMap);
        }else if(mode==1){
            return placeMonster(map,charMap);
        }
        return false;
    }

    /**
     * Method attempt to place monster, can only be placed on rooms.
     * @param map
     * @param charMap
     * @return true if placement was successful
     */
    public boolean placeMonster(char[][] map, Character[][] charMap){
        if(map[yPos][xPos]=='r'){
            if(currMon==0) {
                charMap[yPos][xPos] = new NonPlayerCharacter(xPos, yPos);
                return true;
            }else if(currMon==1){
                charMap[yPos][xPos] = new ZombieNPC(xPos,yPos);
            }
        }
        return false;
    }

    /**
     * Places terrain onto the map. Will only allow
     * one start and one finish. NonPlayerCharacters will be
     * overwritten if there is one on the cursor's position.
     * @param map
     * @return true if successful
     */
    public boolean placeTerrain(char[][] map,Character[][] charMap){
        if(charMap[yPos][xPos]!=null){
            charMap[yPos][xPos]=null;
        }
        //check if map has a starting position
        if(terrainTypes[currChar]=='s'){
            boolean hasStart=false;
            for(int i=0;i<map.length;i++){
                for(int j=0;j<map[0].length;j++){
                    if(map[i][j]=='s'){
                        hasStart=true;
                    }
                }
            }
            if(hasStart){
                return false;
            }else{
                map[yPos][xPos]=terrainTypes[currChar];
                return true;
            }
        }
        //check if map has finish
        if(terrainTypes[currChar]=='f'){
            boolean hasFinish=false;
            for(int i=0;i<map.length;i++){
                for(int j=0;j<map[0].length;j++){
                    if(map[i][j]=='f'){
                        hasFinish=true;
                    }
                }
            }
            if(hasFinish){
                return false;
            }else{
                map[yPos][xPos]=terrainTypes[currChar];
                return true;
            }
        }
        //else just add the new terrain
        map[yPos][xPos]=terrainTypes[currChar];
        return true;
    }
    
    /**
     * Switches the placement mode between monsters and tiles
     */
    public void switchMode(){
        if(mode==0){
            mode=1;
        }else if(mode==1){
            mode=0;
        }
    }

    /**
     * the cycler for shifting the indexes to the right.
      */
    public void cycleRight(){
        currMon=1;
        if(currChar+1==terrainTypes.length){
            currChar=0;
        }else{
            currChar+=1;
        }
    }

    /**
     * the cycler for shifting the indexes to the left.
     */
    public void cycleLeft(){
        currMon=0;
        if(currChar-1<0){
            currChar=terrainTypes.length-1;
        }else{
            currChar-=1;
        }

    }
    /**
     * Getters for the current terrain(center terrain)
     * @return the current selected terrain
     */
    public char getTerrain(){
        return terrainTypes[currChar];
    }
    /**
     * get x position
     * @return x position of cursor
     */
    public int getX(){
        return xPos;
    }

    /**
     * get y position
     * @return y position of cursor
     */
    public int getY(){
        return yPos;
    }

    /**
     * Get the terrain to the right in the array.
     * @return terrain to right of selected
     */
    public char getTerrainRight(){
        int temp=currChar;
        if(temp+1==terrainTypes.length){
            temp=0;
        }else{
            temp+=1;
        }
        return terrainTypes[temp];
    }

    /**
     * Get the terrain to the left in the array.
     * @return terrain to left of selected
     */
    public char getTerrainLeft(){
        int temp=currChar;
        if(temp-1<0){
            temp=terrainTypes.length-1;
        }else{
            temp-=1;
        }
        return terrainTypes[temp];
    }

    /**
     * movement method that only needs to check for out of bounds.
     * @return true if move successful, else false
     */
    public boolean attemptMove(int xMod, int yMod, char[][] map){
        int yLength = map.length;
        int xLength = map[0].length;
        //test for out of bounds move attempt
        if(xPos+xMod<0||xPos+xMod>xLength-1){
            return false;
        }else if(yPos+yMod<0||yPos+yMod>yLength-1){
            return false;
        }
        //modify the cursor's x and y positions.
        xPos+=xMod;
        yPos+=yMod;
        return true;
    }
}
