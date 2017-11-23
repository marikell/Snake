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

    //ParÃ¢metros da Matriz
    private Object[] Params;
      
    private FPSAnimator FPSAnimator;
    
    //Classe Jogo
    private Game Game;
        
    private static Timer Timer = new Timer();
    private static boolean IsTimerUp = false;
    
    //VariÃ¡veis relacionadas ao jogo em si
    
    private GLU Glu = new GLU();
    private GL2 GL;

    //VariÃ¡vel que determina se estÃ¡ em jogo ou nÃ£o
    private boolean InGame = true;
  
    private int VerifyGame;
    //VariÃ¡veis relacionadas as posiÃ§Ãµes
    
      private int PosX = -15;
      private int PosY = 0;
    
    //VariÃ¡veis relacionadas ao controle no teclado
    
    private int DirX = 0;
    private int DirY = 0;
    private float RotateX, RotateY, RotateZ;
    
     public Snake(){
         Game = new Game();
         GLJPanel canvas = new GLJPanel();
        canvas.addGLEventListener(this);

        JFrame frame = new JFrame("Snake");
        frame.setSize(700, 700);
        frame.getContentPane().add(canvas);
        frame.setVisible(true);

        final FPSAnimator animator = new FPSAnimator(canvas, 8, true);
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

     //MÃ©todo responsÃ¡vel por rotacional a tela de acordo com as variÃ¡veis setadas lÃ¡ em cima.
      private void RotateScreen() {
        GL.glRotatef(RotateZ, 0, 0, 1);
        GL.glRotatef(RotateY, 0, 1, 0);
        GL.glRotatef(RotateX, 1, 0, 0);
    }      
      //MÃ©todo que renderiza um texto na tela.
      private void DrawText(String Text, int FontSize, Color Color, int PosX, int PosY){          
        TextRenderer TextRenderer = new TextRenderer(new Font("Arial", Font.BOLD, FontSize));
        TextRenderer.beginRendering(700, 700);
        TextRenderer.setColor(Color);
        TextRenderer.setSmoothing(true);
        TextRenderer.draw(Text, PosX, PosY);
        TextRenderer.endRendering();
      }
      //MÃ©todo que desenha um cubo de determinada cor.
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
       
      
       for(Block item : Game.getSnakeHead().getSnakeBlocks()){
           if(item.getPosX() == APosX && item.getPosY() == APosY)
               return item;
       }
       return null;
   }
       
         private void InitializeIntBlocks(Object[] Params){
        
             int[][] IntBlocks = Game.getIntBlocks();
             SnakeHead SnakeHead = Game.getSnakeHead();
             
        if(IntBlocks[SnakeHead.getPosX()][SnakeHead.getPosY()]== 1 || SnakeHead.getSnakeBlocks().size() == 0)
        {   
            InGame = false;
        }
        else if(IntBlocks[SnakeHead.getPosX()][SnakeHead.getPosY()] == 5){
            InGame = false;
        }
        else if(IntBlocks[SnakeHead.getPosX()][SnakeHead.getPosY()]== 3){
            if(Game.getScore()>0){
                 Game.setScore(Game.getScore()-1);
            }            
            Block LastBlock = SnakeHead.RemoveBlock();
            if(LastBlock!=null){
                     IntBlocks[LastBlock.getPosX()][LastBlock.getPosY()] = 0;
                 }
               
            
        }
        else if(IntBlocks[SnakeHead.getPosX()][SnakeHead.getPosY()]==4){
            int _x = Integer.parseInt(Params[0].toString());
            int _y = Integer.parseInt(Params[1].toString());            
            
            SnakeHead.AddBlock(_x, _y, Color.BLUE);
            
            IntBlocks[SnakeHead.getPosX()][SnakeHead.getPosY()] = 0;
            Game.setScore(Game.getScore()+1);
            if(Game.getScore()%4 == 0){
                RestartAnimator(FPSAnimator.getFPS()+1);
            }
        }
        else if(IntBlocks[SnakeHead.getPosX()][SnakeHead.getPosY()] != 8)
            IntBlocks[SnakeHead.getPosX()][SnakeHead.getPosY()] = 5;
        
        for(int i = 0; i<20;i++){
            for(int j = 0; j < 20;j++){
                
                if(IntBlocks[i][j] == 5 || IntBlocks[i][j] == 8){
                    IntBlocks[i][j] = 0;
                }
                
            }
        }
        IntBlocks[SnakeHead.getPosX()][SnakeHead.getPosY()] = 8;
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
         DrawText("Score: " + Game.getScore(),40,Color.BLUE,300,400);

        }
        DrawText("Pressione ENTER para reiniciar.",40,Color.YELLOW,40,350);

    }
    
       
    private void RestartAnimator(int fps){
        FPSAnimator.stop(); 
        FPSAnimator.setFPS(fps); 
        FPSAnimator.start();
       
    }
    private void RenderScreen(int[][] IntBlocks) {

        SnakeHead SnakeHead = Game.getSnakeHead();
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
            Game.DrawFood(2);
            Game.DrawLooseScore(2);
            Game.DrawRandomWall(5);
            IsTimerUp = false;
        }
    
        // desenha chÃ£o
        GL.glPushMatrix();
            for (int i = 0; i < Game.getBoardSize(); i++) {
                for (int j = 0; j < Game.getBoardSize(); j++) {
                     if(IntBlocks[i][j] == 1){                    
                         GL.glPushMatrix();
                             GL.glTranslated(0, 1, 0);
                             DrawCube(new Color(255,0,72));
                         GL.glPopMatrix();
                     }
                     else if(IntBlocks[i][j] == 5){
                          GL.glPushMatrix();
                             GL.glTranslated(0, 1, 0);
                             Block Block = GetSnakeBlock(i,j);
                             DrawCube(Block.getColor());
                         GL.glPopMatrix();
                     }
                     else if(IntBlocks[i][j] == 8){
                         GL.glPushMatrix();
                             GL.glTranslated(0, 1, 0);
                             DrawCube(SnakeHead.getColor());
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
        GL.glLoadIdentity();
        GL.glTranslated(-10, 5, -40);
        RenderGameScreen("VOCÊ PERDEU!!!", Color.RED, "Lose");
            
        if(VerifyGame == 1){
            VerifyGame = 0;
            Game = new Game();
             Game.InitializeGame(Timer);             
            InGame = true;
            DirX=0;
            DirY=0;
            RestartAnimator(8);
        }          
            
        }
        else{
             if(DirX != 0) SnakeHead.setPosX(SnakeHead.getPosX() + DirX);
            if(DirY != 0) SnakeHead.setPosY(SnakeHead.getPosY() + DirY);
            DrawText("SCORE: ",40, Color.ORANGE,20,650);
            DrawText(Integer.toString(Game.getScore()),40, Color.ORANGE,180,650);         
            DrawMenu();            
        }
        
    }
    private void DrawMenu(){
        
        DrawText("●", 50, Color.GREEN, 100, 35);
        DrawText("Comida",30, Color.WHITE,140,35);
        DrawText("●", 50, new Color(255,0,72), 270, 35);
        DrawText("Parede",30, Color.WHITE,300,35);
        DrawText("●", 50,new Color(254,90,18) , 420, 35);
        DrawText("Dano",30, Color.WHITE,450,35);

         
    }
      
    @Override
    public void init(GLAutoDrawable glad) {        
        Game.InitializeGame(Timer);
    }

    @Override
    public void dispose(GLAutoDrawable glad) {

    }

    @Override
    public void display(GLAutoDrawable glad) {
            
        GL = glad.getGL().getGL2();

        RenderScreen(Game.getIntBlocks());
        
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
            VerifyGame = 1;
        }
            
    }
    
}
