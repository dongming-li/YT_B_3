package com.mygdx.creepycaverns;

import java.util.Random;

/**
 * Created by safala on 11/4/2017.
 * Edited by Rtharp on 11/5/2017
 */

public class ZombieNPC implements Character {

    //char symbol;
    // private int survive;
    private int posx;
    private int posy;
    private int health;
    private int damage;
    public boolean hasMoved=false;
    private int hitCounter=0;
    private char standingOn = 'r';
    private int pause=0;

    /**
     * normal constructor
     * @param posx
     * @param posy
     */
    public ZombieNPC(int posx, int posy) {
        this.posx = posx;
        this.posy = posy;
        this.health=20;
        this.damage=5;
    }

    /**
     * Looks for pc on map
     * @param charMap
     * @param map
     * @return true if monster can see pc
     */
    public boolean canSeePc(Character[][] charMap, char[][] map) {
        //search around the monster for pc.
        for (int i = posy-3; i < posy+3; i++) {
            for (int j = posx-3; j < posx+3; j++) {
                if(i>=0&&j>=0&&i<map.length&&j<map[0].length){//check that courdinates being tested are in bounds
                    if(charMap[i][j]!=null){
                        if(charMap[i][j].isPc()) {//this is the pc.
                            //find relative position of the pc, y=1 means pc is below npc, -1 means pc is higher than npc
                            //x=1 means pc is to the right, x=-1 means pc is to the left
                            int xDiff;
                            int yDiff;
                            if (posy < charMap[i][j].getY()) {
                                yDiff = 1;
                            } else if (posy > charMap[i][j].getY()) {
                                yDiff = -1;
                            } else {
                                yDiff = 0;
                            }
                            if (posx < charMap[i][j].getX()) {
                                xDiff = 1;
                            } else if (posx > charMap[i][j].getX()) {
                                xDiff = -1;
                            } else {
                                xDiff = 0;
                            }
                            //use the yDiff, xDiff to see if monster has a path to the pc.
                            //just check if the tile in the direction of the pc is a room or not.
                                if(charMap[posy+yDiff][posx+xDiff]==null){
                                    return true;
                                }else if(charMap[posy+yDiff][posx+xDiff].isPc()){//if it is the pc, we want to attack it, so return true
                                    return true;
                                }
                        }//end if isPc=true conditional
                    }//end checking to see if charMap isn't null
                }//end bounds check conditional
            }//end inner loop
        }//end outer loop
        return false;//could not find the pc.
    }

    /**
     * Attempts to move a monster through an algorithm, also will attempt combat if necessary
     * @param xMod
     * @param yMod
     * @param map
     * @param charMap
     * @return true if movement successful
     */
    @Override
    public boolean attemptMove(int xMod, int yMod, char[][] map, Character[][] charMap) {
        System.out.println(posx+" "+posy);
        int yDiff;
        int xDiff;
        boolean success = false;
        //test for out of bounds move attempt
        for (int i = 0; i < charMap.length; i++) {
            for (int j = 0; j < charMap[0].length; j++) {
                //monster can see pc
                if (charMap[i][j] != null) {
                    //if the character is the pc
                    if (charMap[i][j].isPc()) {
                        if (posy < charMap[i][j].getY()) {
                            yDiff = 1;
                        } else if (posy > charMap[i][j].getY()) {
                            yDiff = -1;
                        } else {
                            yDiff = 0;
                        }
                        if (posx < charMap[i][j].getX()){
                            xDiff = 1;
                        } else if (posx > charMap[i][j].getX()) {
                            xDiff = -1;
                        } else {
                            xDiff = 0;
                        }
                        if (true) {//we can see the pc, so either move towards it, or attack it
                                    if (charMap[posy + yDiff][posx+xDiff] == null) {
                                        charMap[posy +yDiff][posx+xDiff] = this;
                                        charMap[posy][posx] = null;
                                        posy += yDiff;
                                        posx += xDiff;
                                    }else if(charMap[posy +yDiff][posx+xDiff].isPc()){
                                        charMap[posy-1][posx].hitFor(damage,charMap);//hit the pc.
                                    }
                            standingOn = map[posy][posx];
                            if(standingOn=='i'){
                                map[posy][posx]='r';
                            }
                            pause=5;
                            return true;//if the monster can't attack the pc or move towards it, movement is complete, return true.
                        }//end canSeePc==true conditional
                    }//end isPC conditional
                }//end if charMap,i,j is not null conditional
            }//end inner loop searching for player
        }//end outer loop searching for player
        //monster didn't find pc, decided to move randomly
        Random rand = new Random();
        int random = rand.nextInt(9);
        switch (random) {
            case 0://up and to the left
                if(posy-1>=0&&posx-1>=0) {//bounds check
                    if (map[posy - 1][posx - 1] == 'r') {//check if terrain is room
                        if (charMap[posy - 1][posx - 1] == null) {//check if charMap has space
                            charMap[posy - 1][posx - 1] = this;
                            charMap[posy][posx] = null;
                            posy -= 1;
                            posx -= 1;
                            success = true;
                        }
                    }
                }
                break;
            case 1://straight up
                if(posy-1>=0) {
                    if (map[posy - 1][posx] == 'r') {
                        if (charMap[posy - 1][posx] == null) {
                            charMap[posy - 1][posx] = this;
                            charMap[posy][posx] = null;
                            posy -= 1;
                            success = true;

                        }
                    }
                }
                break;

            case 2://up and to the right
                if(posy-1>=0&&posx+1<map[0].length) {
                    if (map[posy - 1][posx + 1] == 'r') {
                        if (charMap[posy - 1][posx + 1] == null) {
                            charMap[posy - 1][posx + 1] = this;
                            charMap[posy][posx] = null;
                            posy -= 1;
                            posx += 1;
                            success = true;

                        }
                    }
                }
                break;
            case 3:// straight down
                if(posy+1<map.length) {
                    if (map[posy + 1][posx] == 'r') {
                        if (charMap[posy + 1][posx] == null) {
                            charMap[posy + 1][posx] = this;
                            charMap[posy][posx] = null;
                            posy += 1;
                            success = true;

                        }
                    }
                }
                break;
            case 4://lower and to the right
                if(posy+1<map.length&&posx+1<map[0].length) {
                    if (map[posy + 1][posx + 1] == 'r') {
                        if (charMap[posy + 1][posx + 1] == null) {
                            charMap[posy + 1][posx + 1] = this;
                            charMap[posy][posx] = null;
                            posy += 1;
                            posx += 1;
                            success = true;
                        }
                    }
                }
                break;

            case 5://lower and to the left
                if(posy+1<map.length&&posx-1>=0) {
                    if (map[posy + 1][posx - 1] == 'r') {
                        if (charMap[posy + 1][posx - 1] == null) {
                            charMap[posy + 1][posx - 1] = this;
                            charMap[posy][posx] = null;
                            posy += 1;
                            posx -= 1;
                            success = true;
                        }
                    }
                }
                break;
            case 6://to the left
                if(posx-1>=0) {
                    if (map[posy][posx - 1] == 'r') {
                        if (charMap[posy][posx - 1] == null) {
                            charMap[posy][posx - 1] = this;
                            charMap[posy][posx] = null;
                            posx -= 1;
                            success = true;
                        }
                    }
                }
                break;
            case 7://to the right
                if(posx+1<map[0].length) {
                    if (map[posy][posx + 1] == 'r') {
                        if (charMap[posy][posx + 1] == null) {
                            charMap[posy][posx + 1] = this;
                            charMap[posy][posx] = null;
                            posx += 1;
                            success = true;
                        }
                    }
                }
                break;
            case 8:
                success = true;
                break;
        }
        standingOn = map[posy][posx];
        if(standingOn=='i'){
            map[posy][posx]='r';
        }
        return success;
    }//end attempt move
    //Getters

    /**
     * Get the Xpos
     * @return x
     */
    public int getX () {
        return posx;
    }

    /**
     * return the ypos
     * @return y
     */
    public int getY () {
        return posy;
    }

    /**
     * returns false
     * @return false
     */
    public boolean isPc() {
        return false;
    }
    //hit the npc.
    /**
     * Damage this character
     * @param damage
     * @param charMap
     */
    public void hitFor(int damage,Character[][] charMap){
        health-=damage;
        hitCounter=5;
        System.out.println(health);
        if(health<=0){
            charMap[posy][posx]=null;//monster removes itself from references.
        }
    }

    /**
     * Check if the monster has moved recently
     * @return true if has moved
     */
    public boolean hasMoved(){
        if(pause>0){
            pause--;
        }
        return hasMoved;
    }

    /**
     * Set the hasMoved variable to true or false
     * @param value
     */
    public void setHasMoved(boolean value){
        if(pause==0){
            hasMoved=value;
        }
    }

    /**
     * Check if monster has been hit for graphics reasons
     * @return true if is hit.
     */
    public boolean isHit(){
        if(hitCounter>0){
            return true;
        }
        return false;
    }

    /**
     * Reset the isHit variable by an increment.
     */
    public void resetHit(){
        if(hitCounter>0){
            hitCounter--;
        }
    }
}


