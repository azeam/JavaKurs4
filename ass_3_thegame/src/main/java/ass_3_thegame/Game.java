package ass_3_thegame;

import javafx.stage.Stage;

public class Game {
    Gui gui;
    public Game(Stage stage){
        /* 
        Game kommer att vara motorn"i spelet. I konstruktorn kickar
        vi gång GUI:s och trådar. Därefter startas spelloopen som tar in och
        hanterar kommandon ur ett Textfield. Updaterar spelet utifrån spe-
        larens instruktioner. Notera att Npc kommer att röra sig oavsett vad
        spelaren göra. I Npc-TextArean ska man kunna se vilka Npc:er som
        kommer och går i rummet.
        */
        
        this.gui = new Gui();

        (new Thread(new Update(this.gui))).start();

        
        
    }

}
