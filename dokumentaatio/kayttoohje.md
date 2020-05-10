## Käyttöohje - GoalPlanner

### Ohjelman käynnistäminen

Ohjelma käynnistyy komennolla 

`java -jar goalplanner.jar`

### Kirjautuminen

Sovellus käynnistyy kirjautumisnäkymään. Kirjautuminen onnistuu kirjoittamalla olemassaoleva käyttäjätunnus syötekenttään ja painamalla login.

### Uuden käyttäjän luominen

Kirjautumisnäkymästä on mahdollista siirtyä uuden käyttäjän luomisnäkymään panikkeella Create new user. Uusi käyttäjä luodaan syöttämällä tiedot syötekenttiin ja painamalla Create. Jos käyttäjän luominen onnistuu, palataan kirjautumisnäkymään.

### Tavoitteiden tarkastelu

Onnistuneen kirjautumisen myötä siirrytään käyttäjän etusivulle, josta käyttäjä voi uloskirjautua Logout-painikkeella tai siirtyä uuden tavoitteen luomiseen Set a goal-painikkeella. 

Etusivulla on myös napit TODAY, THIS WEEK, THIS MONTH ja THIS YEAR. Käyttäjä voi valita näistä napeista, minkä aikavälin tavoitteita halua tarkastella. Esimerkiksi THIS MONTH -napista saa näkymään kuluvan kuukauden vielä saavuttamattomat tavoitteet aikajärjestyksessä. Tavoitteen voi merkitä saavutetuksi painamalla Achieved the goal!.

### Uuden tavoitteen luominen

Käyttäjä pääsee luomaan uuden tavoitteen Set a goal-painikkeella. Tavoitteen luomisnäkymässä käyttäjän tulee syöttää tavoitteen nimi, kategoria ja sen tavoitepäivämäärä. Päivä tulee Day kenttään, kuukausi Month kenttään ja vuosi Year kenttään. Päivämäärä ei saa olla menneisyydessä. Halutessaan käyttäjä voi asettaa tavoitteen toistumaan päivittäin, viikoittain, kuukausittain tai vuosittain. Tämä tehdään kirjoittamalla *Repeat?* kenttään Daily, Weekly, Monthly tai Yearly ja *How many times?* kenttään kuinka monta kertaa haluaa tavoitteen toistaa. Tavoite asetetaan painamalla Set. 
