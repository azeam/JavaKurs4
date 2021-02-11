package ass_3_thegame;

public class Main { // workaround with an extra Main for Maven JavaFX packages
    public static void main(String[] args) {
        
        // this is for some reason needed to limit the FPS to 60 (as it _should_ be by default) on my Windows test bench
        System.setProperty("quantum.multithreaded", "false");
        
        Game.main(args);
    }
}

