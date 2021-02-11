package ass_3_thegame;

public class Constants {
    protected static boolean GL_PAUSED = false; // global paused flag
	protected static Npc GL_NPC_HIT; // global npc hit flag, TODO: could be made in a better way
    
    // values that could be placed in a settings menu (most of them)
    public final static int 
                            WINDOW_HEIGHT = 800,
                            ROOM_WIDTH = 300,
                            ROOM_HEIGHT = 200,
                            NPC_WIDTH = 32,  // set to img values for hitbox
                            NPC_HEIGHT = 32, // set to img values for hitbox
                            OBJ_SIZE = 25,
                            WALL_SIZE = 120,
                            WALL_WIDTH = 1,
                            INV_SIZE_NPC = 1, // should not be set higher, only set up for 1
                            INV_SIZE_NPC_MIN = 0,
                            INV_SIZE_ROOM = 5,
                            INV_SIZE_ROOM_MIN = 1,
                            INV_SIZE_PLAYER = 5,
                            MARGIN = 50,
                            NUM_NPCS = 10, // API max is 10
                            NUM_ROOMS = 4, // 2 min
                            WINDOW_WIDTH = NUM_ROOMS * ROOM_WIDTH + 2 * MARGIN,
                            ALL_ROOMS_WIDTH = NUM_ROOMS * ROOM_WIDTH,
	                        PLAYER_WIDTH = 32, // set to img values for hitbox
                            PLAYER_HEIGHT = 32,  // set to img values for hitbox
                            DOOR_HEIGHT = 50; // more looks better but placing objects needs to be fixed or there is higher chance of getting stuck in rooms

    public static final String  
                            PLAYER_NAME = "Azeam",
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
                            DOOR_IMAGE_LOC = "door.png";

}
