import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.nio.file.*;

public class Sgame extends JFrame implements KeyListener {
    private Player player = new Player();
    private List<Monster> monsterlist = new ArrayList<Monster>();
    private List<Item> itemlist = new ArrayList<Item>();
    private Rune rune1 = new Rune(),
                 rune2 = new Rune();
    private Canvas canvas = new Canvas();
    private ImageIcon walls = new ImageIcon("images/wall.png"),
                      ground = new ImageIcon("images/grass.png");
    private HashMap<Integer, BlockType> blocks = new HashMap<>();
    private List<Character> mapChar = readFile("maps/map.txt");

    public Sgame() throws Exception { // Define contructor for the solo game with a new window using JFrame 
        this.setTitle("Solo RPG");
        int size = mapSize();
        this.setSize(size*50, size*52); // Define window size according to the size of the map in map.txt
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        canvas.setLayout(new GridLayout(size, size)); // Create a grid layout using map size

        int index = 0, 
            heroalreadyset = 0;
        for (int i = 0; i < mapChar.size(); i++) { // Generate the map according to different values of characters in map
            char c = mapChar.get(i).charValue();
            if (c == '\r' || c == '\n')
                continue; // Ignore line breaks
            BlockType blockType = BlockType.getByChar(c);
            JLabel label;
            switch (blockType) {
            case BRICK:
                label = new JLabel(walls);
                break;
            case GRASS:
                label = new JLabel(ground);
                break;
            case SPAWN:
                label = new JLabel(ground);
                if (heroalreadyset == 0) { // Set the player (only for the first spawn point encountred)
                    player.setX((index % size) * 50);
                    player.setY((index / size) * 50);
                    player.setHp(100);
                    heroalreadyset++;
                }
                break;
            case ENEMY:
                label = new JLabel(ground);
                Monster monster = new Monster();
                monster.setX((index % size) * 50);
                monster.setY((index / size) * 50);
                monster.setHp(100);
                monsterlist.add(monster); // Add the monster freshly created in a list so we can use a loop for each monsters
                break;
            case BONUS:
                label = new JLabel(ground);
                Bonus potion = new Bonus();
                potion.setX((index % size) * 50);
                potion.setY((index / size) * 50);
                itemlist.add(potion); // Add the bonus freshly created in a list so we can use a loop for each bonus
                break;
            case TRAP:
                label = new JLabel(ground);
                Trap skull = new Trap();
                skull.setX((index % size) * 50);
                skull.setY((index / size) * 50);
                itemlist.add(skull); // Add the trap freshly created in a list so we can use a loop for each trap
                break;
            default:
                throw new NullPointerException("BlockType " + blockType + " is not linked !");
            }
            blocks.put(new BlockLocation(index % size, index / size).hashCode(), blockType);
            index++;
            canvas.add(label);
        }

        new Thread(() -> { // New Thread to get inputs fo user keyboard
            addKeyListener(this);
        }).start();

        monsters(); // Call the method that manage every actions to monsters
        itemEffects();

        new Thread(() -> { // New Thread that check every 150ms if the player won, lose or neither
            while (!player.isDead() && stillAlive()) {
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            player.setHp(0); // If the player win or lose, set player's hp to 0 si all the other Threads stops
            new FinalScreen(!stillAlive()); // Launch FinalScreen constructor
            this.dispose();
        }).start();        

        this.setContentPane(canvas);
        this.setVisible(true);
    }

    public int mapSize() { // Get map size according to map.txt
        for (int i = 0; i < mapChar.size(); i++) {
            if((int)mapChar.get(i) == 10)
                return i;
        }
        return 0;
    }

    public boolean stillAlive() { // Check if there is a monster that is still alive
        for (int i = 0; i < monsterlist.size(); i++) {
            if (!monsterlist.get(i).isDead())
                return true;
        }
        return false;
    }

    public void itemEffects() { // New Thread to check every 100ms if the player is on a item and if yes, use it properly
        new Thread(() -> {
            while (!player.isDead()) {
                for (int i = 0; i < itemlist.size(); i++) {
                    Rectangle ri = itemlist.get(i).getRectItem();
                    Rectangle rp = player.getRectEntities();
                    if (rp.intersects(ri) && !itemlist.get(i).isUsed()) {
                        player.useItem(player, itemlist.get(i));
                        itemlist.get(i).setUsed(true);
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void monsters() {
        new Thread(() -> {
            while (!player.isDead()) { 
                // Execute this Thread while the player is alive (this is why we have to set player's hp to 0 if he win)
                for (int i = 0; i < monsterlist.size(); i++) {  // Call the method to move the monster for each monster
                    monsterlist.get(i).moveMonster(monsterlist.get(i), player);
                }

                update(); // Repaint map once we've move all monsters

                try { // Pause Thread for 35ms
                    Thread.sleep(35);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < monsterlist.size(); i++) { // Call the method to damage monster for each monster
                    monsterlist.get(i).damageMonster(monsterlist.get(i), rune1, rune2);
                }

                for (int i = 0; i < monsterlist.size(); i++) { // Call the method to delete entity for each monster if it's necessary
                    monsterlist.get(i).entityDelete();
                }

                new Thread(() -> { // New Thread to check every 10ms if we have to apply damages to the player
                    player.damagePlayer(player, monsterlist);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();

                player.entityDelete(); // Call th method to check if we have to delete the player
            }
        }).start();
    }

    public void keyTyped(KeyEvent evt) {
    }

    public void keyReleased(KeyEvent evt) {
    }

    static int moveFwd = -10,  // Set the move speed for player in each directions 
               moveBck = 10,
               moveLeft = -10,
               moveRight = 10;

    public void keyPressed(KeyEvent evt) {
        int x = player.getX(),
            y = player.getY();
        if (!player.isDead()) { // Only of the player is allive
            switch (evt.getKeyChar()) { // Get input keys and do different events according to the key that is actually pushed
            case 'z':
            case 'w':
                y += moveFwd;
                break;
            case 'q':
            case 'a':
                x += moveLeft;
                break;
            case 's':
                y += moveBck;
                break;
            case 'd':
                x += moveRight;
                break;
            case ' ': // Set rune number one if it's not defined already and the second one in the same way
                if (rune1.getX() == 0 && rune1.getY() == 0) {
                    rune1.setX(player.getX());
                    rune1.setY(player.getY());
                } else if (rune2.getX() == 0 && rune2.getY() == 0) {
                    rune2.setX(player.getX());
                    rune2.setY(player.getY());
                }
                break;
            default:
                return;
            }
        }
        
        // Stop action if the player is going in a wall
        if(new BlockLocation((x)/50, (y+25)/50).getType().isSolid())
            return;
        if(new BlockLocation((x+25)/50, (y)/50).getType().isSolid())
            return;
        
        player.setX(x);
        player.setY(y);
        update(); // Repaint map if we've changed the player's coordinates
    }

    public void update() {
        canvas.updateGame();
    }

    public static List<Character> readFile(String fileName) throws Exception { // Read the map file and put all char in a List
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        ArrayList<Character> values = new ArrayList<Character>();
        for (int i = 0; i < data.length(); i++) {
            values.add(data.charAt(i));
        }
        return values; 
    }

    public Player getPlayer() {
        return player;
    }

    public List<Monster> getMonsterList() {
        return monsterlist;
    }

    public List<Item> getItemlist() {
        return this.itemlist;
    }

    public Rune getRune1() {
        return this.rune1;
    }

    public Rune getRune2() {
        return this.rune2;
    }

    public HashMap<Integer, BlockType> getBlocks() {
        return blocks;
    }
}