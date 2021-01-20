Del 1  
Använd java streams Skriv lämplig kod som besvarar följande frågor:  
1. Hur många ord med fler än 8 bokstäver innhåller texten?  
2. Hur många unika ord innehåller texten?  
3. hur många ord med färre än 4 tecken?  
4. Hur många unika ord med fler än 8 bokstäver?  
5. Vilken är den genomsnittliga ordlängden (Hint! Kolla upp Optional-
Double: https://docs.oracle.com/javase/8/docs/api/java/util/
OptionalDouble.html)  
6. Det totala antalet tecken i listan (dvs summan av längden av alla in-
gående strängar)?  
7. Deklarera två predicate och använd dessa (med streams) för att svara
på om a) Är alla ord kortare än 12 tecken? b) Är all ord längre än två
bokstäver?  
Del 2  
Array nummer2 är en Double array med hjälp av streams:  
1. Hur många av talen är större än 1000?  
2. använd map, filter och distinct för att skapa en doublearray som in-
nehåller alla tal som är mindre än 1000 och har en heltalsdel som är
jämnt delbara med tre.  
3. Vilken är summan av alla tal i arrayen under 500?  
4. Medelvärdet för alla tal mellan 2000 och 3000?  
5. Vilket är det Största värdet respektiva minsta värdet i arrayen?  
Del 3  
Skapa en klass, MyObject som innehåller tre instansvariabler, en boolean -
bool, en int -value och en String - name. Gör en passande konstruktor och
lägg till getters och setters. Skapa sedan en metod som genererar 50 instanser
av dessa med slumpmässiga Heltal(1-50) och booleans samt unika names och
och placerar in dessa i en array. Gör lämpligen också en toString-metod för
att kunna skriva ut ditt objekt på lämpligt sätt.
Använd sedan javaStreams för att:  
1. Skapa en Array som bara innehåller objekt vars value överstiger 20.  
2. Beräkna medelvärdet av alla values  
3. Byta namnet till this is trueför alla objekt vars boolean är true.  
4. Lek vidare...