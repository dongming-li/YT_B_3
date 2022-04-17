package com.mygdx.creepycaverns;
/**
 * Created by arctu_000 on 11/4/2017.
 */

interface Character {
    //required functionality for all characters:

    /**
     * attempt a movement
     * @param xMod
     * @param yMod
     * @param map
     * @param charMap
     * @return true if successful
     */
    public boolean attemptMove(int xMod, int yMod, char[][] map, Character[][] charMap);

    /**
     *
     * @return x
     */
    public int getX();

    /**
     *
     * @return y
     */
    public int getY();

    /**
     * if it is the pc
     * @return true if pc
     */
    public boolean isPc();

    /**
     * Hit a character
     * @param damage
     * @param charMap
     */
    public void hitFor(int damage, Character[][] charMap);

    /**
     * check if a creature has moved
     * @return true if it has moved
     */
    public boolean hasMoved();

    /**
     * Set hasMoved value
     * @param value
     */
    public void setHasMoved(boolean value);

    /**
     * Check if character has been hit for graphic display
     * @return true if hit
     */
    public boolean isHit();

    /**
     * reset hitCounter after a certain number of frames
     */
    public void resetHit();
}
