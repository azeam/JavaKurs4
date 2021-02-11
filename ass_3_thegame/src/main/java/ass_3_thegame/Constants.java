package ass_3_thegame;

import javafx.scene.image.Image;

public class Constants {
    // values that could be placed in a settings menu
    public static int 
                            ROOM_WIDTH = 300,
                            ROOM_HEIGHT = 400,
                            NUM_NPCS = 10, // API max is 10
                            NUM_ROOMS = 5, // 2 min                            
                            INV_SIZE_ROOM = 5,
                            INV_SIZE_ROOM_MIN = 1,
                            INV_SIZE_PLAYER = 5;
    public static String  
                            PLAYER_NAME = "Azeam",
                            DIFFICULTY = "Normal"; // only East and Normal implemented
    
    // final values that should probably not be changed
    public final static int 
                            WALL_SIZE = ROOM_HEIGHT - 80,
                            INV_SIZE_NPC = 1, // should not be set higher, only set up for 1
                            INV_SIZE_NPC_MIN = 0,
                            WALL_WIDTH = 2,
                            MARGIN = 50,
                            NPC_WIDTH = 32,  // set to img values for hitbox
                            NPC_HEIGHT = 32, // set to img values for hitbox
                            OBJ_SIZE = 25, // set to img values for hitbox
                            WINDOW_HEIGHT = ROOM_HEIGHT + 300,
                            WINDOW_WIDTH = NUM_ROOMS * ROOM_WIDTH + 2 * MARGIN,
                            ALL_ROOMS_WIDTH = NUM_ROOMS * ROOM_WIDTH,
	                        PLAYER_WIDTH = 32, // set to img values for hitbox
                            PLAYER_HEIGHT = 32;  // set to img values for hitbox

    public final static String   
                            HERO_IMAGE_LOC1 = "hero1.png",
                            HERO_IMAGE_LOC2 = "hero2.png",
                            HERO_IMAGE_LOC3 = "hero3.png",
                            MONSTER_IMG_LOC1 = "monster1.png",
                            MONSTER_IMG_LOC2 = "monster2.png",
                            MONSTER_IMG_LOC3 = "monster3.png",
                            MONSTER_IMG_ITEM_LOC1 = "monster_item1.png",
                            MONSTER_IMG_ITEM_LOC2 = "monster_item2.png",
                            MONSTER_IMG_ITEM_LOC3 = "monster_item3.png",
                            KEY_IMAGE_LOC = "key.png",
                            KEY_GROUND_IMAGE_LOC = "key_ground.png",
                            KEY_MASTER_IMAGE_LOC = "key_master.png",
                            EXCHANGE_IMAGE_LOC = "exchange.png",
                            CHEST_IMAGE_LOC = "chest.png",
                            CHEST_OPEN_IMAGE_LOC = "chest_open.png",
                            DOOR_IMAGE_LOC = "door.png",
                            SOUND_PICKUP = "/pickup.wav",
                            SOUND_DOORKEY_PICKUP = "/doorKeyPickup.wav",
                            SOUND_EMPTY = "/empty.wav",
                            SOUND_LOOP = "/loop.wav",
                            SOUND_WIN = "/win.wav";

    public final Image heroImage1 = new Image(getClass().getResource("/hero1.png").toExternalForm());
    public final Image heroImage2 = new Image(getClass().getResource("/hero2.png").toExternalForm());
    public final Image heroImage3 = new Image(getClass().getResource("/hero3.png").toExternalForm());
    public final Image monsterImage1 = new Image(getClass().getResource("/monster1.png").toExternalForm());
    public final Image monsterItemImage1 = new Image(getClass().getResource("/monster_item1.png").toExternalForm());
    public final Image monsterImage2 = new Image(getClass().getResource("/monster2.png").toExternalForm());
    public final Image monsterItemImage2 = new Image(getClass().getResource("/monster_item2.png").toExternalForm());
    public final Image monsterImage3 = new Image(getClass().getResource("/monster3.png").toExternalForm());
    public final Image monsterItemImage3 = new Image(getClass().getResource("/monster_item3.png").toExternalForm());
    public final Image keyImage = new Image(getClass().getResource("/key.png").toExternalForm());
    public final Image keyMasterImage = new Image(getClass().getResource("/key_master.png").toExternalForm());
    public final Image keyGroundImage = new Image(getClass().getResource("/key_ground.png").toExternalForm());
    public final Image chestImage = new Image(getClass().getResource("/chest.png").toExternalForm());
    public final Image chestOpenImage = new Image(getClass().getResource("/chest_open.png").toExternalForm());
    public final Image doorImage = new Image(getClass().getResource("/door.png").toExternalForm());

}
