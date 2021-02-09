package ass_3_thegame;

public class Constants {
    protected static boolean GL_PAUSED = false; // global paused flag
	protected static Npc GL_NPC_HIT; // global npc hit flag, TODO: could be made in a better way
    
    // values that could be placed in a settings menu (most of them)
    public final static int 
                            WINDOW_HEIGHT = 800,
                            ROOM_WIDTH = 300,
                            ROOM_HEIGHT = 200,
                            NPC_WIDTH = 24,  // set to img values for hitbox
                            NPC_HEIGHT = 30, // set to img values for hitbox
                            OBJ_SIZE = 25,
                            WALL_SIZE = 100,
                            WALL_WIDTH = 1,
                            INV_SIZE_NPC = 1,
                            INV_SIZE_NPC_MIN = 0,
                            INV_SIZE_ROOM = 5,
                            INV_SIZE_ROOM_MIN = 0,
                            INV_SIZE_PLAYER = 5,
                            MARGIN = 50,
                            NUM_NPCS = 10, // API max is 10
                            NUM_ROOMS = 4, // 2 min
                            WINDOW_WIDTH = NUM_ROOMS * ROOM_WIDTH + 2 * MARGIN,
                            ALL_ROOMS_WIDTH = NUM_ROOMS * ROOM_WIDTH,
	                        PLAYER_WIDTH = 40, // set to img values for hitbox
                            PLAYER_HEIGHT = 38,  // set to img values for hitbox
                            DOOR_HEIGHT = 50; 

    public static final String  
                            PLAYER_NAME = "Azeam",
                            HERO_IMAGE_LOC = "https://www.bufonaturvard.se/images/hero.png",
                            MONSTER_IMG_LOC = "https://www.bufonaturvard.se/images/monster2.png",
                            MONSTER_IMG_ITEM_LOC = "https://www.bufonaturvard.se/images/monster_item2.png",
                            KEY_IMAGE_LOC = "https://www.bufonaturvard.se/images/key.png",
                            KEY_GROUND_IMAGE_LOC = "https://www.bufonaturvard.se/images/key_ground.png",
                            KEY_MASTER_IMAGE_LOC = "https://www.bufonaturvard.se/images/key_master.png",
                            EXCHANGE_IMAGE_LOC = "https://www.bufonaturvard.se/images/exchange.png",
                            CHEST_IMAGE_LOC = "https://www.bufonaturvard.se/images/chest.png",
                            CHEST_OPEN_IMAGE_LOC = "https://www.bufonaturvard.se/images/chest_open.png",
                            DOOR_IMAGE_LOC = "https://www.bufonaturvard.se/images/door.png";

}
