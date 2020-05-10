## Arkkitehtuuri

### Rakenne

Ohjelman rakenne noudattelee kolmitasoista kerrosarkkitehtuuria, ja koodin pakkausrakenne on seuraava:

![Pakkausrakenne](https://github.com/LindaJT/ot-harjoitustyo/blob/master/dokumentaatio/pakkausrakenne.png)

Pakkaus *goalplanner.ui* sisältää JavaFX:llä toteutetun käyttöliittymän, *goalplanner.domain* sovelluslogiikan ja *goalplanner.dao* tietojen pysyväistallennuksesta vastaavan koodin.

### Käyttöliittymä

Käyttöliittymä sisältää neljä erillistä näkymää

* Kirjautuminen
* Uuden käyttäjän luominen
* Pääsivu, jolla navigointi sekä tavoitelistaukset
* Uuden tavoitteen luomis näkymä

jokainen näistä on toteutettu omana Scene-oliona. Näkymistä yksi kerrallaan on näkyvänä eli sijoitettuna sovelluksen stageen. Käyttöliittymä on rakennettu ohjelmallisesti luokassa *goalplanner.ui.GoalPlannerUi*.

Käyttöliittymä on pyritty eristämään täysin sovelluslogiikasta, se ainoastaan kutsuu sopivin parametrein sovelluslogiikan toteuttavan olion *GoalPlannerServicen* metodeja.

Kun sovelluksen tavoitelistan tilanne muuttuu, eli uusi käyttäjä kirjautuu, tavoite merkitään saavutetuksi tai niitä luodaan, kutsutaan sovelluksen metodia redrawGoals joka renderöi pääsivun tavoitelistauksen uudelleen sovelluslogiikalta saamansa näytettävien tavoitteiden listan perusteella. Pääsivulla on napit TODAY, THIS WEEK, THIS MONTH ja THIS YEAR. Näitä painamalla myös pääsivun tavoitelistaus renderöidään uudelleen, sen mukaan minkä listauksen tavoitteet halutaan nähdä. 

### Sovelluslogiikka 

Sovelluksen loogisen datamallin muodostavat luokat User ja Goal, jotka kuvaavat käyttäjiä ja käyttäjien tavoitteita:

![Luokkakaavio](https://github.com/LindaJT/ot-harjoitustyo/blob/master/dokumentaatio/luokkakaavio.png)

Toiminnallisista kokonaisuuksista vastaa luokkan GoalPlannerService ainoa olio. Luokka tarjoaa kaikille käyttäliittymän toiminnoille oman metodin. Näitä ovat esim.

* boolean login(String username)
* List getUnachieved()
* void createGoal(String name, LocalDate date, String category)

GoalPlannerService pääsee käsiksi käyttäjiin ja tavoitteiden tietojen tallennuksesta vastaavan pakkauksessa goalplanner.dao sijaitsevien rajapinnat GoalDao ja UserDao toteuttavien luokkien kautta. Luokkien toteutuksen injektoidaan sovelluslogiikalle konstruktorikutsun yhteydessä.

GoalPlannerServicen ja ohjelman muiden osien suhdetta kuvaava luokka/pakkauskaavio:

![luokka/pakkauskaavio](https://github.com/LindaJT/ot-harjoitustyo/blob/master/dokumentaatio/luokka_pakkauskaavio.png)

### Tietojen pysyväistallennus

Pakkauksen goalplanner.dao luokat FileGoalDao ja FileUserDao huolehtivat tietojen tallettamisesta tiedostoihin.

Luokat noudattavat Data Access Object -suunnittelumallia ja ne on tarvittaessa mahdollista korvata uusilla toteutuksilla, jos sovelluksen datan talletustapaa päätetään vaihtaa. Luokat onkin eristetty rajapintojen GoalDao ja UserDao taakse ja sovelluslogiikka ei käytä luokkia suoraan.

Sovelluslogiikan testauksessa hyödynnetäänkin tätä siten, että testeissä käytetään tiedostoon tallentavien DAO-olioiden sijaan keskusmuistiin tallentavia toteutuksia.

#### Tiedostot

Sovellus tallettaa käyttäjien ja todojen tiedot erillisiin tiedostoihin.

Sovellus tallettaa käyttäjät seuraavassa formaatissa

`Teppo;testaaja`
`Matti;mmeikalainen`

eli ensin käyttäjän nimi ja puolipisteellä erotettuna käyttäjän käyttäjätunnus.

Käyttäjien työt tallettavan tiedoston formaatti on seuraava

`1;80 op tehtynä;2020-05-23;false;testaaja;Study`
`2;juo 2 litraa vettä;2020-05-24;false;testaaja;Health`

Kentät on eroteltu puolipistein. Ensimmäisenä tavoitteen tunniste eli id, toisena kuvaus, kolmantena tavoitteen tavoitepäivämäärä, neljäntenä tieto siitä onko tavoite jo saavutettu, viidentenä tavoitteen luoneen käyttäjän käyttäjätunnus ja viimeisenä tavoitteen kategoria. 

#### Päätoiminnallisuudet

Kuvataan seuraavaksi sovelluksen toimintalogiikka muutaman päätoiminnallisuuden osalta sekvenssikaaviona.

**Käyttäjän sisäänkirjautuminen**

![sekvenssikaavio1](https://github.com/LindaJT/ot-harjoitustyo/blob/master/dokumentaatio/sekvenssikaavio.png)

**Uuden käyttäjän luominen**

![sekvenssikaavio2](https://github.com/LindaJT/ot-harjoitustyo/blob/master/dokumentaatio/sekvenssikaavio2.png)


**uuden tavoitteen luominen**

![sekvenssikaavio4](https://github.com/LindaJT/ot-harjoitustyo/blob/master/dokumentaatio/sekvenssikaavio4.png)

**Tämän päivän tavoitteiden hakeminen**

![sekvenssikaavio5](https://github.com/LindaJT/ot-harjoitustyo/blob/master/dokumentaatio/sekvenssikaavio5.png)


