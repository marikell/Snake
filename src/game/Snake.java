/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.TextRenderer;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.media.opengl.GL;
import static javax.media.opengl.GL.GL_DEPTH_TEST;
import static javax.media.opengl.GL.GL_LEQUAL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

/**
 *
 * @author Marianne
 */


public class Snake extends GLJPanel implements GLEventListener, KeyListener {

    private Object[] Params;
    
    private boolean CanRestartAnimator = true;
    
    private FPSAnimator FPSAnimator;
    
    private int Score = 0;
        
    private static Timer Timer = new Timer();
    private static boolean IsTimerUp = false;
    
    //Variáveis relacionadas ao jogo em si
    
    private SnakeHead SnakeHead;
    private GLU Glu = new GLU();
    private GL2 GL;
    private final int BoardSize=20;

    private boolean InGame = true;
  
    //Variáveis relacionadas as posições
    
      private int PosX = -15;
      private int PosY = 0;
    
    //Variáveis relacionadas ao controle no teclado
    
    private int DirX = 0;
    private int DirY = 0;
    private int Game = 0;
    private float RotateX, RotateY, RotateZ;
    
    //Variável relacionada a matriz de controle da posição dos blocos.
    
     private int [] [] IntBlocks = new int [20][20];
     
     //private Color Colors[] = {new Color(255,8,127), new Color(213,100,124), new Color(67,142,280)};
     
     public Snake(){
         GLJPanel canvas = new GLJPanel();
        canvas.addGLEventListener(this);

        JFrame frame = new JFrame("Snake");
        frame.setSize(700, 700);
        frame.getContentPane().add(canvas);
        frame.setVisible(true);

        //Instantiating and Initiating Animator 
        final FPSAnimator animator = new FPSAnimator(canvas, 10, true);
        FPSAnimator = animator;
        animator.start();

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new Thread(() -> {
                    System.exit(0);
                }).start();
            }
        });
        frame.addKeyListener(this);   
     }
     public static void main(String[] args) {
         Timer.scheduleAtFixedRate(new TimerTask() {
             @Override
            public void run() {
                IsTimerUp = true;
                          }         
           
        }, 10000, 25000);

        new Snake();
    }
     
     public void setIntBlocks(int[][] IntBlocks){
         this.IntBlocks = IntBlocks;
     }
     
     public int [][] getIntBlocks(){
         return IntBlocks;
     }
     
     public boolean getInGame(){
         return InGame;
     }
     
     public void setInGame(boolean InGame){
         this.InGame = InGame;
     }
     
     public void setGlu(GLU Glu){
         this.Glu = Glu;
     }
     
     public GLU getGlu(){
         return Glu;
     }
    public void setSnakeHead(SnakeHead SnakeHead){
        this.SnakeHead = SnakeHead;
    }
    
    public SnakeHead getSnakeHead(){
        return SnakeHead;
    }
      public void setGL(GL2 GL){
        this.GL = GL;
    }
    
    public GL2 getGl(){
        return GL;
    }
    
    public int getPosX(){
        return PosX;
    }
    
    public int getPosY(){
        return PosY;
    }
    
    public void setPosX(int PosX){
        this.PosX = PosX;
    }
    
    public void setPosY(int PosY){
        this.PosY = PosY;
    }
    
    public void setDirX(int DirX){
        this.DirX = DirX;
    }
     public void setDirY(int DirY){
        this.DirY = DirY;
    }
     
    public int getDirX(){
        return DirX;
    }
    
    public int getDirY(){
        return DirY;
    }
    
    public void setRotateX(float RotateX){
        this.RotateX = RotateX;
    }
    public void setRotateY(float RotateY){
        this.RotateY = RotateY;
    }
    public void setRotateZ(float RotateZ){
        this.RotateZ = RotateZ;
    }
    
    public float getRotateX(){
        return RotateX;
    }
    
     public float getRotateY(){
        return RotateY;
    }
     
     public float getRotateZ(){
         return RotateZ;
     }

      private void RotateScreen() {
        GL.glRotatef(RotateZ, 0, 0, 1);
        GL.glRotatef(RotateY, 0, 1, 0);
        GL.glRotatef(RotateX, 1, 0, 0);
    }
      
      private void DrawText(String Text, int FontSize, Color Color, int PosX, int PosY){
          
        TextRenderer TextRenderer = new TextRenderer(new Font("Arial", Font.BOLD, FontSize));
        TextRenderer.beginRendering(700, 700);
        TextRenderer.setColor(Color);
        TextRenderer.setSmoothing(true);

        TextRenderer.draw(Text, PosX, PosY);
        TextRenderer.endRendering();
      }
      
     private void DrawCube(Color AColor) {
        
        GL.glPushMatrix();
        DrawSquare(AColor);
        GL.glPopMatrix();

        GL.glPushMatrix();
        GL.glRotatef(180, 0, 1, 0);
        DrawSquare(AColor);
        GL.glPopMatrix();

        GL.glPushMatrix();
        GL.glRotatef(-90, 0, 1, 0);
        DrawSquare(AColor);
        GL.glPopMatrix();

        GL.glPushMatrix();
        GL.glRotatef(90, 0, 1, 0);
        DrawSquare(AColor);
        GL.glPopMatrix();

        GL.glPushMatrix();
        GL.glRotatef(-90, 1, 0, 0);
        DrawSquare(AColor);
        GL.glPopMatrix();

        GL.glPushMatrix();
        GL.glRotatef(90, 1, 0, 0);
        DrawSquare(AColor);
        GL.glPopMatrix();
    }
     
     private void RestartParams(int size){
         Params = new Object[size];
     }
     
     private void DrawSquare(Color AColor) {
        // define a cor da face
        GL.glColor3f(AColor.getRed()/255f, AColor.getGreen()/255f, AColor.getBlue()/255f);

        // desenha o quadrado
        GL.glBegin(GL2.GL_QUADS);
        GL.glVertex3d(0.5, 0.5, 0.5);
        GL.glVertex3d(0.5, -0.5, 0.5);
        GL.glVertex3d(-0.5, -0.5, 0.5);
        GL.glVertex3d(-0.5, 0.5, 0.5);
        GL.glEnd();
    }
     
      private void DrawBlock(Block ABlock, Color AColor){             
        GL.glPushMatrix();
            GL.glTranslated(ABlock.getPosX(), 1, ABlock.getPosY());
            DrawCube(AColor);
        GL.glPopMatrix();
    }
      
       private Block GetSnakeBlock(int APosX, int APosY){
       
      
       for(Block item : SnakeHead.getSnakeBlocks()){
           if(item.getPosX() == APosX && item.getPosY() == APosY)
               return item;
       }
       return null;
   }
       
    
    private void InitializeIntBlocks(Object[] Params){
        
        if(IntBlocks[SnakeHead.getPosX()][SnakeHead.getPosY()]== 1 || SnakeHead.getSnakeBlocks().size() == 1)
        {   
            InGame = false;
        }
        else if(IntBlocks[SnakeHead.getPosX()][SnakeHead.getPosY()]== 3){
            if(Score>0){
                 Score--;
            }
            
                 Block LastBlock = SnakeHead.RemoveBlock();
                 if(LastBlock!=null){
                     IntBlocks[LastBlock.getPosX()][LastBlock.getPosY()] = 0;
                 }
               
            
        }
        else if(IntBlocks[SnakeHead.getPosX()][SnakeHead.getPosY()]==4){

            //Direita
            int _x = Integer.parseInt(Params[0].toString());
            int _y = Integer.parseInt(Params[1].toString());
            
            
            SnakeHead.AddBlock(_x, _y, Color.BLUE);
            
            IntBlocks[SnakeHead.getPosX()][SnakeHead.getPosY()] = 0;
            Score++;
            if(Score%4 == 0){
                RestartAnimator(FPSAnimator.getFPS()+2);
            }
        }
        else
            IntBlocks[SnakeHead.getPosX()][SnakeHead.getPosY()] = 5;
        
        for(int i = 0; i<20;i++){
            for(int j = 0; j < 20;j++){
                
                if(IntBlocks[i][j] == 5){
                    IntBlocks[i][j] = 0;
                }
                
            }
        }
        for(int i = 0; i<SnakeHead.getSnakeBlocks().size(); i++){
            
        IntBlocks[SnakeHead.getSnakeBlocks().get(i).getPosX()][SnakeHead.getSnakeBlocks().get(i).getPosY()] = 5;
        }       
    }
    
    
    public void RenderGameScreen(String Text, Color Color, String Type){
        
          GL.glClearDepth(1.0f);
        GL.glEnable(GL_DEPTH_TEST);
        GL.glDepthFunc(GL_LEQUAL);
        GL.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        //GL.glClearColor(1,1,1,1);
        GL.glLoadIdentity();
        GL.glTranslated(-10, 5, -40);
        
        DrawText(Text,40,Color,220,450);
        if(Type.equals("Lose")){
         DrawText("Score: " + Score,40,Color.BLUE,300,400);

        }
        DrawText("Pressione ENTER para reiniciar.",40,Color.YELLOW,40,350);

    }
    
       
    private void RestartAnimator(int fps){
        FPSAnimator.stop(); 
        FPSAnimator.setFPS(fps); 
        FPSAnimator.start();
       
    }
    private void RenderScreen(int[][] IntBlocks) {

        // Inicializa o Depth Buffer 
        GL.glClearDepth(1.0f);
        GL.glEnable(GL_DEPTH_TEST);
        GL.glDepthFunc(GL_LEQUAL);
        GL.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
          GL.glEnable(GL2.GL_LIGHTING);  
	GL.glEnable(GL2.GL_LIGHT0);
	GL.glEnable(GL2.GL_DEPTH_TEST);
        GL.glEnable(GL2.GL_COLOR_MATERIAL);
        
        GL.glLoadIdentity();
        GL.glTranslated(-10, 5, -40);
        RotateX = 45;
        RotateY = 0;
        RotateZ = 0;
        RotateScreen();
        RestartParams(2);
        Params[0] = SnakeHead.getLastBlock().getPosX();
        Params[1] = SnakeHead.getLastBlock().getPosY();
        InitializeIntBlocks(Params);
        if(IsTimerUp){
            DrawFood(2);
            DrawLooseScore(2);
            DrawRandomWall(5);
            IsTimerUp = false;
        }
    
        // desenha chão
        GL.glPushMatrix();
            for (int i = 0; i < BoardSize; i++) {
                for (int j = 0; j < BoardSize; j++) {
                     if(IntBlocks[i][j] == 1){                    
                         GL.glPushMatrix();
                             GL.glTranslated(0, 1, 0);
                             DrawCube(new Color(255,0,72));
                         GL.glPopMatrix();
                     }
                     else if(IntBlocks[i][j] == 5){
                          GL.glPushMatrix();
                             GL.glTranslated(0, 1, 0);
                             DrawCube(GetSnakeBlock(i, j).getColor());
                         GL.glPopMatrix();
                     }
                     else if(IntBlocks[i][j]==4){
                         GL.glPushMatrix();
                             GL.glTranslated(0, 1, 0);
                             DrawCube(Color.GREEN);
                         GL.glPopMatrix();
                     }
                     else if(IntBlocks[i][j] == 3){
                          GL.glPushMatrix();
                             GL.glTranslated(0, 1, 0);
                             DrawCube(new Color(254,90,18));
                         GL.glPopMatrix();
                     }
                     else {
                        DrawCube(Color.YELLOW);
                     }

                     GL.glTranslated(0, 0, 1);
                 }
                 GL.glTranslated(1, 0, -20);
            }
        GL.glPopMatrix();
        RotateScreen();
        
        if(!InGame)
        {
              GL.glClearDepth(1.0f);
        GL.glEnable(GL_DEPTH_TEST);
        GL.glDepthFunc(GL_LEQUAL);
        GL.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        //GL.glClearColor(1,1,1,1);
        GL.glLoadIdentity();
        GL.glTranslated(-10, 5, -40);
        RenderGameScreen("VOCÊ PERDEU!!!", Color.RED, "Lose");
            
        if(Game == 1){
            Game = 0;
             InitializeGame();
            InGame = true;
            DirX=0;
            DirY=0;
            Score = 0;
        }          
            
        }
        else{
             if(DirX != 0) SnakeHead.setPosX(SnakeHead.getPosX() + DirX);
            if(DirY != 0) SnakeHead.setPosY(SnakeHead.getPosY() + DirY);
            DrawText("SCORE: ",40, Color.ORANGE,20,650);
            DrawText(Integer.toString(Score),40, Color.ORANGE,180,650);         
            DrawMenu();
            
        }
        
    }
    
    private void DrawMenu(){
        
        DrawText("•", 80, Color.GREEN, 100, 35);
        DrawText("Comida",30, Color.WHITE,140,55);
        DrawText("•", 80, new Color(255,0,72), 270, 35);
        DrawText("Parede",30, Color.WHITE,300,55);
        DrawText("•", 80,new Color(254,90,18) , 420, 35);
        DrawText("Dano",30, Color.WHITE,450,55);

         
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
    
    public void DrawRandomWall(int size){
        
       for(int i = 0; i<size;i++){
            
            int _i = GenerateRandomPosition(IntBlocks)[0];
            int _j = GenerateRandomPosition(IntBlocks)[1];
            IntBlocks[_i][_j] = 1;
            
        }     
        
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
    
    
    
    public void InitializeGame(){
        
        Timer = new Timer();
        
        RestartAnimator(10);
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
    
    public void PutLight(){
         
    }
    
    @Override
    public void init(GLAutoDrawable glad) {
        
        InitializeGame();
        
       
    }

    @Override
    public void dispose(GLAutoDrawable glad) {

    }

    @Override
    public void display(GLAutoDrawable glad) {
            
        GL = glad.getGL().getGL2();

        RenderScreen(IntBlocks);
        
        PosX += DirX;
        PosY += DirY;
    }

    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {
        // called when user resizes the window
        GL = glad.getGL().getGL2();
        GL.glMatrixMode(GL2.GL_PROJECTION);
        GL.glLoadIdentity();
        Glu.gluPerspective(60, 1, 1, 60);
        GL.glMatrixMode(GL2.GL_MODELVIEW);
        GL.glLoadIdentity();
        GL.glTranslated(0, 0, -5);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

  
    @Override
    public void keyReleased(KeyEvent e) {
    }
    
     @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if(key == KeyEvent.VK_RIGHT)
        { 
            DirX = 1;
            DirY = 0;
        }
        
        if(key == KeyEvent.VK_LEFT)
        {
            DirY = 0;
            DirX = -1;
        }
        
        if(key == KeyEvent.VK_UP)
        {
            DirY = -1;
            DirX = 0;
        }
        
        if(key == KeyEvent.VK_DOWN)
        {
            DirY = 1;
            DirX = 0;
        }
        
        if(key == KeyEvent.VK_ENTER){
            DirY = 0;
            DirX = 0;
            Game = 1;
        }
            
    }
    
}
