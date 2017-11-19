/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Marianne
 */
public class Game {
    
    private int Score;
    private SnakeHead SnakeHead;
    private int [] [] IntBlocks;
    private int BoardSize;
    public Game(){
        Score = 0;
        SnakeHead = new SnakeHead();
        BoardSize = 20;
        IntBlocks = new int[BoardSize][BoardSize];
        
    }
    
    public void InitializeGame(Timer Timer){
        Timer = new Timer();
        
        //RestartAnimator(10);
        IntBlocks = new int[BoardSize][BoardSize];
        DrawWalls();   
        DrawFood(3);
        DrawRandomWall(2);
        SnakeHead = new SnakeHead();
        SnakeHead.SnakeBlocks = new ArrayList();
        
        SnakeHead.setPosX(5);
        SnakeHead.setPosY(1);
        
        SnakeHead.AddBlock(4, 1, new Color(228,0,25));
        SnakeHead.AddBlock(5, 1, Color.BLUE);
    }
    
      public void DrawFood(int FoodCount){
        
        for(int i = 0; i<FoodCount;i++){
            
            int _i = GenerateRandomPosition(IntBlocks)[0];
            int _j = GenerateRandomPosition(IntBlocks)[1];
            IntBlocks[_i][_j] = 4;
            
        }
        
    }
    
        public void DrawLooseScore(int LooseScore){
        
        for(int i = 0; i<LooseScore;i++){
            
            int _i = GenerateRandomPosition(IntBlocks)[0];
            int _j = GenerateRandomPosition(IntBlocks)[1];
            IntBlocks[_i][_j] = 3;
            
        }
        
    }
        
      
        
         private int[] GenerateRandomPosition(int [][] Blocks){
        
        Random Random = new Random();
        int x_Position = -1;
        int y_Position = -1;
        boolean IsPossible = false;
        int [] _Positions = new int[2];
        
        while(!IsPossible){
            x_Position = Random.nextInt(BoardSize-1);
            y_Position = Random.nextInt(BoardSize-1);
            
            if(Blocks[x_Position][y_Position] == 5 || Blocks[x_Position][y_Position] == 1 || Blocks[x_Position][y_Position] == 3 || Blocks[x_Position][y_Position] == 4)
                IsPossible = false;
            else{
                _Positions[0] = x_Position;
                _Positions[1] = y_Position;
                IsPossible = true;
            }           
           
        }
        
        return _Positions;
        
    }
        
        public void DrawWalls(){
         for(int i = 0; i<BoardSize;i++){
            for(int j = 0; j<BoardSize;j++){
                if(i==0 || j == 0 || i== BoardSize-1 || j==BoardSize-1){
                    IntBlocks[i][j] = 1;
                }
            }
    }
         
    }
        
        public void DrawRandomWall(int size){
        
       for(int i = 0; i<size;i++){
            
            int _i = GenerateRandomPosition(IntBlocks)[0];
            int _j = GenerateRandomPosition(IntBlocks)[1];
            IntBlocks[_i][_j] = 1;
            
        }     
        
    }
    
    public int getBoardSize(){
        return BoardSize;
    }
    
    public void setBoardSize(int BoardSize){
        this.BoardSize = BoardSize;
    }
    
    public void setIntBlocks(int [][]IntBlocks){
        this.IntBlocks = IntBlocks;
    }
    
    public int [][] getIntBlocks(){
        return IntBlocks;
    }
    
    public SnakeHead getSnakeHead(){
        return SnakeHead;
    }
    
    public void setSnakeHead(SnakeHead SnakeHead){
        this.SnakeHead = SnakeHead;
    }
    
    public int getScore(){
        return Score;
    }
    
    public void setScore(int Score){
        this.Score = Score;
    }
    
    
    
    
}
