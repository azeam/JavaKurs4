# 1 The Game  
Efter en lång arbetsvecka så går du som vanligt och lägger dig. Efter en orolig sömn med otäcka drömmar slår du upp drömmarna och inser att du inte alls är hemma. Du befinner dig i en källare med fyra rum och du är inte ensam. Irrande mellan rummen finns det tre stycken mycket märkliga märkliga och förvirrade figurer: Jason, Freddy och Ture Sventon. Källaren saknar fönster men är möblerad med låsta möbler och slängt på golvet ligger diverse saker som du kan använda för att komma därirån. The game is on!  

## 1.1 Allmäna instruktioner  
- LÄS instruktionerna noga och följ dem! Instruktionerna här är det obligatoriska. Spelet går att bygga ut för den som så önskar men se till att uppfylla minimikraven innan ni börjar leka!
- I git hittar ni grundstruktur - om man vill kan använda och bygga
vidare utifrån den!
- Funktion är viktigare än skönhet. Ni kommer att få göra ett GUI. Det ska vara någorlunda lättbegripligt men snöa inte in på att göra det optimalt i första ledet. Det är inte en kurs i GUI eller userXP.

## 1.2 Obligatoriska klasser
- : Main Main ska endast starta spelet genom att skapa en instans av Game!
- Npc Ska vara antingen ett Interface eller en Abstrakt klass och ge ramarna för våra Npc:er.
- Room Ett Room ska ha ett unikt namn, ett Inventory och sen showMetod() som beskriver det för spelaren.
- Player Håller ordning på var spelaren befinner sig (alltså vilket rum som ska beskrivas).
- Update Ska implementera Runnable och starta en tråd som Regelbundet updaterar Guit utifrån vad som händer i spelet.
- GameObjects Ska också vara abstract eller interface hantera alla "icke-levandeöbjekt i spelet (möbler, nycklar etc). GameObject ska innehålla en boolean som avgör om objektet går att ta med sig eller är fastmonterat"i rummet.
- Container En subklass till GameObjects som har ett Inventory. Kan vara låst eller öppet.
- Key En subklass till GameObjects vars objekt används för att låsa upp Containers. Välj om keyobjektet ska hålla koll på vilken instans av container den passar till eller tvärtom!
- Inventory Ska innehålla en array av GameObjects. Arrayen vara anpassad efter det maxantal objekt som det kan bära och säga ifrån om man försöker placera fler saker än det finns plats för. Här ska mekaniken för att plocka upp, ge bort och lägga ned objekt hanteras. ALL HANTERING AV ARRAYERNA SKA SKE MED STREAMS - det är alltså INTE tillåtet att använda ArrayLists/LinkedLists eller andra (smartare) lösningar!
- Person En person är Npc - dessa ska lagras i lista av något slag och ha ett eget liv så till vida att de ska hanteras concurrent. Npc:ernas beteende bestäms av slumptal. Det rör sig mellan rummen, plockar upp, lägger ned saker. Det ska finnas en showPerson, som visar personens namn och vad denne bär på. Vill man ha något som personen bär på så kan man antingen följa efter och vänta på att objekten läggs ned eller be om att byta mot ett objekt i det egna inventory.
## Game  
Game kommer att vara motorn"i spelet. I konstruktorn kickar vi gång GUI:s och trådar. Därefter startas spelloopen som tar in och hanterar kommandon ur ett Textfield. Updaterar spelet utifrån spelarens instruktioner. Notera att Npc kommer att röra sig oavsett vad spelaren göra. I Npc-TextArean ska man kunna se vilka Npc:er som kommer och går i rummet.
