/**
 * AppletJuego
 *
 * Personaje para juego previo Examen
 *
 * @author Omar Antonio Carreón Medrano A01036074
 * @author Gabriel Salazar De Urquidi A01139126
 * @version 1.00 2008/6/13
 */
 
 //para ver si funciona
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.LinkedList;
import javax.swing.JFrame;


public class AppletExamen extends JFrame implements Runnable, MouseListener {

    /* objetos para manejar el buffer del Applet y este no parpadee */
    private Image    imaImagenApplet;   // Imagen a proyectar en Applet	
    private Graphics graGraficaApplet;  // Objeto grafico de la Imagen
    private int iRandomVidas;           // Vidas al azar
    private Personaje perNena;          // Objeto de la clase Personaje
    private int iPosX;                  // Posicion X
    private int iPosY;                  // Posicion Y
    private boolean bClick;             // checa si se ha dado click
    private int iDireccionNena;         // indica la dirección de nena
    private LinkedList lnkCaminadores;  // Coleccion de caminadores
    private LinkedList lnkCorredores;   // Coleccion de corredores
    private Personaje perCaminador;   // objeto de la clase personaje
    private Personaje perCorredor;    // objeto de la case personaje
    private int iRandomCamina;          // Caminadores al azar
    private int iRandomCorredores;      // corredores al azar
    private int iScore;                 // score del juego
    private int iContCorredor;          // contador para corredores
    private Image imaImagenGameover;    // Imagen de gameover
    private SoundClip souCaminador; // Objeto SoundClip sonido caminador
    private SoundClip souCorredor; // Objeto SoundClip sonido corredor
    /** 
     * init
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos
     * a usarse en el <code>Applet</code> y se definen funcionalidades.
     */
    public AppletExamen() {
        init();
        start();
    }
    
    public void init() {
        // hago el applet de un tamaño 500,500
        setSize(800, 600);
        
        // introducir instrucciones para iniciar juego
       
        // incializa booleano del click
        bClick = false;
        
        // inicializa score del juego
        iScore = 0;
        
        // inicializa contador para corredores
        iContCorredor = 0;
        
        // inicializa random de caminadores
        iRandomCamina = (int) (Math.random() * 3 + 10);
        
        // inicializa random de corredores
        iRandomCorredores = (int) (Math.random() * 4 + 12);
        
        /*
        * inicializa direccion de nena hacia la dercha. 
        * 1 = arriba
        * 2 = abajo
        * 3 = derecha
        * 4 = izquierda
        */  
        iDireccionNena = 3;
        
        // se crea el objeto de la colección de corredores
        lnkCorredores = new LinkedList();
        
        // se crea el objeto de la colección de caminadores
        lnkCaminadores = new LinkedList();

        // inicializa random de vidas
        iRandomVidas = (int) (Math.random() * 3 + 4 );
        
        // se crea imagen de Nena
        URL urlImagenNena = this.getClass().getResource("nena.gif");
        
        
        // se crea a Nena 
	perNena = new Personaje(0, 0,
                Toolkit.getDefaultToolkit().getImage(urlImagenNena));
        // inicializa posicion de Nena
        perNena.setX((getWidth() / 2) - (perNena.getAncho() / 2));
        perNena.setY((getHeight() - perNena.getAlto()));
        

        // ciclo para crear de 5 a 10 caminadores
        for (int iI = 1; iI <= iRandomCamina; iI++) {      
            URL urlImagenCaminador = 
                    this.getClass().getResource("alien1Camina.gif");
            // se crea Caminador
            perCaminador = new Personaje(0,0,
                    Toolkit.getDefaultToolkit().getImage(urlImagenCaminador));
            // se posiciona a caminador afuera del applet del lado superior
            perCaminador.setX((int) (Math.random() * (getWidth())));
            perCaminador.setY( (int) (Math.random() * (getHeight() * -1 )
                    - perCaminador.getAlto()));
            lnkCaminadores.add(perCaminador);  // agrega caminador a coleccion

        }
                // ciclo para crear de 8 a 10 corredores
        for (int iI = 1; iI <= iRandomCorredores; iI++) {
            // se crea el corredor
            URL urlImagenCorredor = 
                    this.getClass().getResource("alien2Corre.gif");
            perCorredor = new Personaje(0,0,
                    Toolkit.getDefaultToolkit().getImage(urlImagenCorredor));
            // se posiciona al corredor afuera del applet del lado izquierdo
            perCorredor.setX(-1 * (int) (Math.random() * getWidth() / 4) 
                    - perCaminador.getAncho());
            perCorredor.setY((int) (Math.random() * getHeight()));
            lnkCorredores.add(perCorredor); // agrega corredor a la coleccion
        }
        
        // se crea imagen de gameover
        // crea de fondo la imagen del espacio
        //URL urlImagenGameover= this.getClass().getResource("gameover.jpg");
        //imaImagenGameover = Toolkit.getDefaultToolkit().getImage(urlImagenGameover);
        
        // creo el sondio del caminador
        souCaminador = new SoundClip("caminador.wav");
        
        // croe el sonido del corredor
        souCorredor = new SoundClip("corredor.wav");
        
        
        // se añade para que el mouse sea escuchado en el applet
        addMouseListener(this);
    }
	
    /** 
     * start
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se crea e inicializa el hilo
     * para la animacion este metodo es llamado despues del init o 
     * cuando el usuario visita otra pagina y luego regresa a la pagina
     * en donde esta este <code>Applet</code>
     * 
     */
    public void start () {
        // Declaras un hilo
        Thread th = new Thread (this);
        // Empieza el hilo
        th.start ();
    }
	
    /** 
     * run
     * 
     * Metodo sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, que contendrá las instrucciones
     * de nuestro juego.
     * 
     */
    public void run () {
        // se realiza el ciclo del juego mientras las vidas no se acaben
        while (iRandomVidas != 0) {
            /* mientras dure el juego, se actualizan posiciones de jugadores
               se checa si hubo colisiones para desaparecer jugadores o corregir
               movimientos y se vuelve a pintar todo
            */ 
            actualiza();
            checaColision();
            repaint();
            try	{
                // El thread se duerme.
                Thread.sleep (20);
            }
            catch (InterruptedException iexError)	{
                System.out.println("Hubo un error en el juego " + 
                        iexError.toString());
            }
	}
    }
	
    /** 
     * actualiza
     * 
     * Metodo que actualiza la posicion del objeto elefante 
     * 
     */
    public void actualiza(){
        // instrucciones para actualizar personajes
        if (iDireccionNena == 1) {
            perNena.arriba();
        } else if (iDireccionNena == 2) {
            perNena.abajo();
        } else if (iDireccionNena == 3) {
            perNena.derecha();
        } else if (iDireccionNena == 4) {
            perNena.izquierda();
        }

        
        // actualiza coleccion de Caminadores
        for (Object lnkCaminador : lnkCaminadores) {
           Personaje perCaminador = (Personaje)lnkCaminador;
           perCaminador.abajo();
        }
        // actualiza coleccion de corredores
        for (Object lnkCorredor : lnkCorredores) {
           Personaje perCorredor = (Personaje)lnkCorredor;
           perCorredor.derecha();
        }
    }
	
    /**
     * checaColision
     * 
     * Metodo usado para checar la colision del objeto elefante
     * con las orillas del <code>Applet</code>.
     * 
     */
    public void checaColision(){
        // instrucciones para checar colision y reacomodar personajes si 
        // es necesario
        
        //si llega al limite superior
        if (perNena.getY() - (perNena.getAlto() / 2) < 0 ) {
            perNena.setY(perNena.getAlto() / 2);
            
            // si llega al limite inferior
        } else if (perNena.getY() + perNena.getAlto() > getHeight()) {
            perNena.setY(getHeight() - perNena.getAlto());
            
            // si llega al limite derecho
        } else if(perNena.getX() + perNena.getAncho() > getWidth()) {
            perNena.setX(getWidth() - perNena.getAncho());
            
            // si llega al limite izquierdo
        } else if(perNena.getX() < 0) {
            perNena.setX(0);
        }
        
         // ciclo para checar colision de cada caminador
        for (Object lnkCaminador : lnkCaminadores) {
            Personaje perCaminador = (Personaje)lnkCaminador;

             // si colisiona caminador con nena  
            if (perCaminador.colisiona(perNena)) {
                iScore++;   // incrementa el score + 1
                // reposiciona al caminador
                perCaminador.setX((int) (Math.random() * (getWidth())));
                perCaminador.setY((int) (Math.random() * 
                        (getHeight()) - perCaminador.getAlto()) * -1);
                souCaminador.play();
                
            }
            // si colisiona con la pared
            if (perCaminador.getY() + perCaminador.getAlto() > getHeight()) {
                // reposiciona al caminador
                perCaminador.setX((int) (Math.random() * (getWidth())));
                perCaminador.setY((int) (Math.random() * 
                        (getHeight()) - perCaminador.getAlto()) * -1);
            }
        }
        // ciclo para checar colision de cada corredor
        for (Object lnkCorredor : lnkCorredores) {
            Personaje perCorredor = (Personaje)lnkCorredor;

             // si colisiona corredor con nena  
            if (perCorredor.colisiona(perNena)) {
                iContCorredor++;  // incrementa contador de corredor
                if (iContCorredor % 5 == 0) {
                    iRandomVidas--;                  
                }
                souCorredor.play();
                // se posiciona al corredor afuera del applet del lado izquierdo
                perCorredor.setX(-1 * (int) (Math.random() * getWidth() / 4));
                perCorredor.setY((int) (Math.random() * getHeight()));
                
            }
            // si colisiona con la pared
            if (perCorredor.getX() + perCorredor.getAncho() > getWidth()) {
                // se posiciona al corredor afuera del applet del lado izquierdo
                perCorredor.setX(-1 * (int) (Math.random() * getWidth() / 4));
                perCorredor.setY((int) (Math.random() * getHeight()));
            }
        }

    }
	
    /**
     * paint
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor y 
     * define cuando usar ahora el paint
     * @param graGrafico es el <code>objeto grafico</code> usado para dibujar.
     * 
     */
    public void paint (Graphics graGrafico){
        // Inicializan el DoubleBuffer
        if (imaImagenApplet == null){
                imaImagenApplet = createImage (this.getSize().width, 
                        this.getSize().height);
                graGraficaApplet = imaImagenApplet.getGraphics ();
        }

        // crea de fondo la imagen del espacio
        URL urlImagenBackground = this.getClass().getResource("espacio.jpg");
        Image imaImagenBackground = 
                Toolkit.getDefaultToolkit().getImage(urlImagenBackground);

        // Despliego la imagen de fondo
        graGraficaApplet.drawImage(imaImagenBackground, 0, 0, 
                getWidth(), getHeight(), this);

        // Actualiza el Foreground.
        graGraficaApplet.setColor (getForeground());
        paint1(graGraficaApplet);

        // Dibuja la imagen actualizada
        graGrafico.drawImage (imaImagenApplet, 0, 0, this);
    }
    
    /**
     * paint1
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada,
     * ademas que cuando la imagen es cargada te despliega una advertencia.
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     * 
     */
    public void paint1 (Graphics g) {
        if (perNena != null & lnkCorredores != null & lnkCaminadores != null) { 
            g.drawImage(perNena.getImagen(), perNena.getX(),
                        perNena.getY(), this);
            // dibuja coleccion de caminadores
            for (Object lnkCaminador : lnkCaminadores) {
                Personaje perCaminador = (Personaje)lnkCaminador;
                //Dibuja la imagen de Susy en la posicion actualizada
                g.drawImage(perCaminador.getImagen(), perCaminador.getX(),
                        perCaminador.getY(), this);
            }
            // ciclo que dibuja la coleccion de corredores
            for (Object lnkCorredor : lnkCorredores) {
                Personaje perCorredor = (Personaje)lnkCorredor;
                //Dibuja la imagen de chango en la posicion actualizada
                g.drawImage(perCorredor.getImagen(), perCorredor.getX(),
                        perCorredor.getY(), this);
            }
            
            g.setColor(Color.white); //pone el string en color blanco
            g.drawString("Score "+ iScore, 100, 50); // Dibuja el score 
            g.drawString("Vidas " + iRandomVidas, 100, 70); // dibuja las vidas
            
            if (iRandomVidas == 0) {   // si se acabaron las vidas
            //    g.drawImage(imaImagenGameover, 0, 0, 
              //  getWidth(), getHeight(), this);
            }
        } else {
            
            //Da un mensaje mientras se carga el dibujo	
            g.drawString("No se cargo la imagen..", 20, 20);
            }
    }

    @Override
    public void mouseClicked(MouseEvent mouEvent) {
         // si se hace click
        bClick = true;
        
        //Click a la izquierda del elefante
        if (mouEvent.getX() < perNena.getX()){
            iDireccionNena = 4;
            
            //Click a la derecha del elefante
        } else if (mouEvent.getX() > perNena.getX() + perNena.getAncho()) {
            iDireccionNena = 3;
            
            //Click arriba del elefante
        } else if (mouEvent.getY() < perNena.getY()) {
            iDireccionNena = 1;
            
            //Click debajo del elefante
        } else if (mouEvent.getY() > perNena.getY() + perNena.getAlto()) {
            iDireccionNena = 2;
        }
        
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
