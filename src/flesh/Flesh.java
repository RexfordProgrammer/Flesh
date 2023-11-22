package flesh;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;

import flesh.entities.FleshEntity;
import flesh.entities.FleshNPCEntity;
import flesh.entities.FleshPlayerEntity;
import flesh.entities.FleshProjectileEntity;
import flesh.entities.FleshResourceEntity;
import javafx.embed.swing.JFXPanel;

public class Flesh extends JFXPanel {

    //Original frame to fill
    public static JFrame j1 = new JFrame("FLESH");
    public static CustomMouseListener mouseListener = new CustomMouseListener();
    //Flesh Things
    public static Flesh fleshInstance = new Flesh();
    public static FleshPlayerEntity player = new FleshPlayerEntity(30, 3, 100, new FleshPoint(0, 0), Color.MAGENTA);
    public static boolean isLevelDisplayed = false;
    public static boolean isGamePaused = true;
    public static boolean fadeIn = true;
    public static double score = 0;
    public static int numWave = 1;
    public static int timeRelease = 3000;
    public static int outOfAmmoFade = 0;
    public static boolean slowMo = false;
    public static boolean skipTurn = false;
    public static Image background = null;
            
    //Threads
    public static FleshMainThread mainThread = new FleshMainThread();
    public static FleshSpawnThread spawnThread = new FleshSpawnThread();
    //public static FleshSoundFXThread audioThread = new FleshSoundFXThread();

    //Font metrics etc
    public static FontMetrics fm;
    public static Rectangle2D r;

    //Projectile and NPC Storage Units
    public static CopyOnWriteArrayList<FleshProjectileEntity> projectileEntities = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<FleshNPCEntity> NPCEntities = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<FleshResourceEntity> resources = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        //Create the window
        j1.setSize(800, 500);
        j1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j1.getContentPane().add(fleshInstance);

        //Add KeyListener and Mouse Listener
        j1.addKeyListener(new FleshKeyListener());
        j1.setFocusable(true);
        j1.requestFocusInWindow();
        fleshInstance.addMouseListener(mouseListener);

        //Initiate threads
        spawnThread.start();
        mainThread.start();
        //audioThread.start();
        //Add basic entities
        player = new FleshPlayerEntity(30, 3, 100,
                new FleshPoint(j1.getWidth() / 2, j1.getHeight() / 2), Color.MAGENTA);
        j1.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        //Clears canvas
        super.paint(g);
        //calls helper methods
        if (!isGamePaused && !skipTurn) {
            updatePlayer();
            updateProjectileHitboxes();
            updatePlayerHitbox();
            if (slowMo)
                skipTurn = true;
        } else if (skipTurn && slowMo) {
            skipTurn = false;
        }
        //typecast to Graphics2D object
        Graphics2D g2d = (Graphics2D) g;
        //Set Background Color
        drawBackground(Color.CYAN, g2d);

        //draws projectiles
        drawProjectileEntities(g2d);
        //draw packages
        drawResourceEntities(g2d);
        //paints all the npcs
        drawNPCEntities(g2d);
        //draws the player
        player.tickerUp(g2d);
        //draws strings
        drawStrings(g2d);
        j1.requestFocusInWindow();
    }

    public void drawNPCEntities(Graphics2D g2d) {
        for (FleshNPCEntity NPCEntity : NPCEntities) {
            g2d.setColor(NPCEntity.color);
            NPCEntity.tickerUp(g2d);
        }
    }

    public void drawProjectileEntities(Graphics2D g2d) {
        for (FleshProjectileEntity projectileEntity : projectileEntities) {
            g2d.setColor(projectileEntity.color);
            projectileEntity.tickerUp(g2d);
        }
    }

    public void drawResourceEntities(Graphics2D g2d) {
        for (FleshResourceEntity resource : resources) {
            g2d.setColor(resource.color);
            resource.tickerUp(g2d);
        }
    }

    public static boolean outOfBounds(FleshEntity entity) {
        return entity.location.x >= Flesh.j1.getWidth() || entity.location.x < 0
                || entity.location.y >= Flesh.j1.getHeight() || entity.location.y < 0;
    }

    private void drawStrings(Graphics2D g2d) {
        //Set Color and Font
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Impact", Font.PLAIN, 20));

        // Draw Weapon Title
        g2d.drawString("Weapon: " + player.weapons.get(player.currentWeapIndex), 10, fleshInstance.getHeight() - 10);

        // Draw Health
        g2d.drawString("Health: " + (int) player.health, fleshInstance.getWidth() - 100, fleshInstance.getHeight() - 10);

        //Draw Paused Screen
        drawGamePausedString(g2d);

        //Draw Ammo String
        drawAmmoString(g2d);

        //Draw Score
        drawScoreString(g2d);

        // Out of ammo String
        drawOutOfAmmoString(g2d);
    }

    private void updatePlayer() {
        if (Flesh.player.health > 0) {
            if (Flesh.player.dirUp && Flesh.player.location.y() >= 0+30) {
                Flesh.player.location.y -= Flesh.player.speed;
            }
            if (Flesh.player.dirDown && Flesh.player.location.y() <= fleshInstance.getHeight()-30) {
                Flesh.player.location.y += Flesh.player.speed;
            }
            if (Flesh.player.dirLeft && Flesh.player.location.x() >= 30) {
                Flesh.player.location.x -= Flesh.player.speed;
            }
            if (Flesh.player.dirRight && Flesh.player.location.x() <= fleshInstance.getWidth()-30) {
                Flesh.player.location.x += Flesh.player.speed;
            }
            if (Flesh.player.isFiring) {
                fireWithDelay();
            }
        }
    }

    private void updatePlayerHitbox() {
        //Projectile Hitboxes
        for (FleshNPCEntity characterEntity : Flesh.NPCEntities) {
            if (player.compareTo(characterEntity) > 0 && player.health > 0) {
                player.health -= 1;
            }
        }
        for (FleshResourceEntity resource : Flesh.resources) {
            if (player.compareTo(resource) > 0) {
                resource.hit();
            }
        }
    }

    private void fireWithDelay() {
        if ((new Date().getTime() - player.firePrevious) > player.weapons.get(player.currentWeapIndex).fireDelay) {
            player.weapons.get(player.currentWeapIndex).fire();
            player.firePrevious = new Date().getTime();
        }
    }

    private void updateProjectileHitboxes() {
        //Projectile Hitboxes
        for (FleshNPCEntity characterEntity : Flesh.NPCEntities) {
            for (FleshProjectileEntity projectileEntity : Flesh.projectileEntities) {
                if (characterEntity.compareTo(projectileEntity) > 0 && projectileEntity.isVolitile == true) {
                    characterEntity.dieNPC(projectileEntity);
                    projectileEntity.hit();
                }
            }
        }
    }

    private void drawBackground(Color c, Graphics2D g2d) {
        g2d.setColor(c);
        //Drawing a real background is too computationally intensive
        //try {
        //    background = ImageIO.read(FleshPlayerEntity.class.getResource("/flesh/images/backgroundTile.jpg"));
        //} catch (IOException e) {
        //}
        //AffineTransform at = new AffineTransform();
        //g2d.drawImage(background, at, null);
        g2d.fillRect(0, 0, j1.getWidth(), j1.getHeight());
    }

    
    
    // Draw Strings
    
    private void drawAmmoString(Graphics2D g2d) {
        String ammoString;

        if (player.weapons.get(player.currentWeapIndex).ammo < 0) {
            ammoString = "Ammo: INFINITE";
        } else {
            ammoString = "Ammo: " + player.weapons.get(player.currentWeapIndex).ammo;
        }

        fm = g2d.getFontMetrics();
        r = fm.getStringBounds(ammoString, g2d);
        int x = (this.getWidth() - (int) r.getWidth()) / 2 - fm.getDescent();
        int y = (this.getHeight() - (int) r.getHeight()) + 10;
        g2d.drawString(ammoString, x, y);
    }

    private void drawScoreString(Graphics2D g2d) {
        String scoreString = "Score: " + (int) score;

        g2d.setFont(new Font("Impact", Font.PLAIN, 30));
        fm = g2d.getFontMetrics();
        r = fm.getStringBounds(scoreString, g2d);
        int x = (this.getWidth() - (int) r.getWidth()) / 2;
        int y = fm.getDescent() + 20;
        g2d.drawString(scoreString, x, y);

        //Game paused string
        g2d.setFont(new Font("Impact", Font.PLAIN, 30));
        if (isLevelDisplayed || isGamePaused) {
            String levelString = "Level: " + numWave;
            fm = g2d.getFontMetrics();
            r = fm.getStringBounds(levelString, g2d);
            x = (this.getWidth() - (int) r.getWidth()) / 2 + fm.getDescent();
            y = (this.getHeight() - (int) r.getHeight()) / 2 + fm.getAscent();
            g2d.drawString(levelString, x, y);
        }
    }

    private void drawOutOfAmmoString(Graphics2D g2d) {
        g2d.setFont(new Font("Impact", Font.PLAIN, 30));

        if (player.weapons.get(player.currentWeapIndex).ammo == 0) {
            String outOfAmmoString = "OUT OF AMMO";

            if (fadeIn) {
                if (outOfAmmoFade >= 100) {
                    fadeIn = false;
                } else {
                    outOfAmmoFade++;
                }
            } else {
                if (outOfAmmoFade == 0) {
                    fadeIn = true;
                } else {
                    outOfAmmoFade--;
                }
            }

            Color c1 = new Color(250, 0, 0, outOfAmmoFade);
            g2d.setColor(c1);
            
            fm = g2d.getFontMetrics();
            r = fm.getStringBounds(outOfAmmoString, g2d);
            int x = (this.getWidth() - (int) r.getWidth()) / 2 + fm.getDescent();
            int y = fm.getAscent() * 2;
            g2d.drawString(outOfAmmoString, x, y);
        }
    }

    private void drawGamePausedString(Graphics2D g2d) {
        if (isGamePaused) {
            String pauseString = "Pause";
            fm = g2d.getFontMetrics();
            r = fm.getStringBounds(pauseString, g2d);
            int x = fm.getDescent();
            int y = fm.getAscent();
            g2d.drawString(pauseString, x, y);
        }
    }
    
}
