/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;

/**
 *
 * @author Marianne
 */
public class Block {
    private int PosX;
    private int PosY;
    private Color Color;

    public Block(){
        
    }   
        public Block(int APosX, int APosY, Color AColor){
        setPosX(APosX);
        setPosY(APosY);
        setColor(AColor);
        }
    
    public Color getColor(){
        return Color;
    }
    
    public void setColor(Color Color){
        this.Color = Color;
    }
    public int getPosX() {
        return PosX;
    }

    public void setPosX(int PosX) {
        this.PosX = PosX;
    }

    public int getPosY() {
        return PosY;
    }

    public void setPosY(int PosY) {
        this.PosY = PosY;
    }
      
}
