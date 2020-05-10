# Goal Planner App

Sovelluksen avulla käyttäjän on mahdollista asettaa itselleen eri kategorioihin liittyviä tavoitteita sekä merkitä tavoitteet saavutetuksi.

## Dokumentaatio

[Vaatimusmäärittely](https://github.com/LindaJT/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/LindaJT/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)

[Arkkitehtuuri](https://github.com/LindaJT/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Käyttöohje](https://github.com/LindaJT/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

## Releaset

[Loppupalautus](https://github.com/LindaJT/ot-harjoitustyo/releases/tag/Final)

[Viikko 6](https://github.com/LindaJT/ot-harjoitustyo/releases/tag/viikko6)

[Viikko 5](https://github.com/LindaJT/ot-harjoitustyo/releases/tag/viikko5)

## Komentorivitoiminnot

Komennot tulee suorittaa hakemistossa GoalPlanner

### Testaus

Testit suoritetaan komennolla:
`mvn test`

Testikattavuusraportti luodaan komennolla:
`mvn jacoco:report`

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedoston */GoalPlanner/target/site/jacoco/index.html* .

### Suoritettavan jarin generointi

Komento:
`mvn package`

### JavaDoc

JavaDoc generoidaan komennolla 

`mvn javadoc:javadoc`

JavaDocia voi tarkastella avaamalla selaimella tiedosto *GoalPlanner/target/site/apidocs/index.html*

### Checkstyle

Tiedostoon checkstyle.xml määrittelemät tarkistukset suoritetaan komennolla:

`mvn jxr:jxr checkstyle:checkstyle`

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto */GoalPlanner/target/site/checkstyle.html* .


