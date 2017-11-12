/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Marianne
 */
public class SnakeHead extends Block{
    
    public ArrayList<Block> SnakeBlocks;
    
    public Block getLastBlock(){
        return SnakeBlocks.get(SnakeBlocks.size()-1);
    }
    
    @Override
    public void setPosX(int APosX) {
        UpdateBlocks();
        super.setPosX(APosX);
    }
    
    @Override
    public void setPosY(int APosY) {
        UpdateBlocks();
        super.setPosY(APosY);        
    }
    
    private void UpdateBlocks()
    {
        int x_atual, y_atual;
        int x_anterior = 0, y_anterior = 0;
        
        for (int i = 0; i < SnakeBlocks.size(); i++) {
            
            x_atual = SnakeBlocks.get(i).getPosX();
            y_atual = SnakeBlocks.get(i).getPosY();
            
            if(i == 0)
            {
                SnakeBlocks.get(i).setPosX(getPosX());
                SnakeBlocks.get(i).setPosY(getPosY());                
            }
            else
            {
                SnakeBlocks.get(i).setPosX(x_anterior);
                SnakeBlocks.get(i).setPosY(y_anterior);
            }
            
            x_anterior = x_atual;
            y_anterior = y_atual;
        }                
    }
    
    public void AddBlock(int APosX, int APosY){
        if(SnakeBlocks!=null) SnakeBlocks.add(new Block(APosX, APosY,GetRandomColor()));
    }
    
        
    public void AddBlock(int APosX, int APosY, Color Color){
        if(SnakeBlocks!=null) SnakeBlocks.add(new Block(APosX, APosY,Color));
    }
    
     private Color GetRandomColor(){
        Random LRandom = new Random();
        return new Color(LRandom.nextInt(255), LRandom.nextInt(255), LRandom.nextInt(255));
    }
    
    public void AddBlock(Block ABlock){
        if(SnakeBlocks!=null) SnakeBlocks.add(ABlock);
    }
    
    public void setSnakeBlocks(ArrayList<Block> SnakeBlocks){
        this.SnakeBlocks = SnakeBlocks;
    }
    
    public ArrayList<Block> getSnakeBlocks(){
        return SnakeBlocks;
    }
    
}
