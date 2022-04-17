package com.mygdx.creepycaverns;

/**
 * Created by arctu_000 on 10/30/2017.
 */

public class PlayerCharacter implements Character{
    private int xPos;
    private int yPos;
    private int health;
    private int damage;
    private int hitCounter=0;

    //default constructor
    PlayerCharacter(){
        xPos=0;
        yPos=0;
        health=100;
        damage=5;
    }
    //constructor
    PlayerCharacter(int x,int y){
        xPos=x;
        yPos=y;
        health=100;
        damage=5;
    }

    /**
     * True if this is the pc
     * @return true
     */
    public boolean isPc()
    {
        return true;
    }
    //getters

    /**
     *
     * @return x pos
     */
    public int getX()
    {
        return xPos;
    }

    /**
     *
     * @return y pos
     */
    public int getY()
    {
        return yPos;
    }

    /**
     * return the pc's health
     * @return health
     */
    public int getHealth(){
        return health;
    }

    /**
     * Hit the pc for some damage
     * @param damage
     * @param charMap
     */
    public void hitFor(int damage,Character[][] charMap){
        health-=damage;
        hitCounter=5;
        System.out.println(health);
    }
    //movement function

    /**
     * taking input, attempt to move the npc in the indicated direction
     * @param xMod
     * @param yMod
     * @param map
     * @param charMap
     * @return true if successful
     */
    public boolean attemptMove(int xMod, int yMod, char[][] map,Character[][] charMap){
        boolean success=false;
        int yLength = map.length;
        int xLength = map[0].length;
        //test for out of bounds move attempt
        if(xPos+xMod<0||xPos+xMod>xLength-1){
            return success;
        }else if(yPos+yMod<0||yPos+yMod>yLength-1){
            return success;
        }
        //check what the map's terrain is, apply change to charMap if necessary.
        if(charMap[yPos+yMod][xPos+xMod]==null) {
            switch (map[yPos + yMod][xPos + xMod]) {
                case 'r'://rooms
                    charMap[yPos + yMod][xPos + xMod] = this;
                    charMap[yPos][xPos] = null;
                    success = true;
                    break;
                case 'h'://hallway
                    charMap[yPos + yMod][xPos + xMod] = this;
                    charMap[yPos][xPos] = null;
                    success = true;
                    break;
                case 't'://traps
                    charMap[yPos + yMod][xPos + xMod] = this;
                    charMap[yPos][xPos] = null;
                    success = true;
                    break;
                case 'w'://water
                    charMap[yPos + yMod][xPos + xMod] = this;
                    charMap[yPos][xPos] = null;
                    success = true;
                    break;
                case 's':
                    charMap[yPos + yMod][xPos + xMod] = this;
                    charMap[yPos][xPos] = null;
                    success = true;
                    break;
                case 'f':
                    charMap[yPos + yMod][xPos + xMod] = this;
                    charMap[yPos][xPos] = null;
                    success = true;
                    break;
                case 'i'://impassable terrain
                    return false;
                default:
                    return false;

            }
        }else{
            charMap[yPos+yMod][xPos+xMod].hitFor(damage,charMap);
        }
        if(success) {
            xPos+=xMod;
            yPos+=yMod;
            return success;
        }
        return false;
    }

    /**
     * Placeholder method for npc
     * @return
     */
    public boolean hasMoved(){
        return false;
    }

    /**
     * placeholder method for npc
     * @param value
     */
    public void setHasMoved(boolean value){
    }

    /**
     * Check if pc has been hit for graphics purposes
     * @return isHit
     */
    public boolean isHit(){
        if(hitCounter>0){
            return true;
        }
        return false;
    }

    /**
     * Decrement the hit counter for graphics purposes
     */
    public void resetHit(){
        if(hitCounter>0){
            hitCounter--;
        }
    }
}
