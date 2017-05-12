# Yksinkertaisin tapa saada jotain tapahtumaan: aja scripti start_jar ilman parametreja.

Jar tiedosto löytyy projektin juuresta nimellä vindi_bot.jar. Se vaatii parametreja, joten kannattaa käyttää bash-scriptiä "start_jar". Ilman parametreja se aloittaa harjoituspelin. Jos parametriksi antaa "COMPETITION", aloitetaan ottelu netissä muita botteja vastaan (jos muita pelihalukkaita on tarpeeksi). Scriptille voi antaa myös parametrin KEY, joka kertoo serverillä millä botilla pelataan eli mille botille kirjataan ELO-pisteet. Se on oletusarvoiseksi "git gud"-nimisen bottini avain. Sen pelejä voi seurata sivulta http://vindinium.org/ai/cyilo7v7.

Joka tapauksessa pelin aluksi consoliin tulostuu heti pelin alettua seuraavaa muistuttava rivi: 
"21:53:15.247 [main] INFO  bot.BotRunner - Game URL: http://vindinium.org/b08xzvb7"
Kopioimalla game-url selaimeen voi pelin kulkua seurata. Tämä voi olla hyödyllistä etenkin harjoitusmodella pelatessa, sillä niitä pelejä ei voi seurata botin omalta sivulta.

Consoliin tulostuu myös kentän tila kullakin hetkellä (merkkien merkityksen voi tarkistaa sivulta http://vindinium.org/doc). Lisäksi tulostuu arvot d_time ja c_time. d_time tarkoittaa päätöksen tekoon käytettyä aikaa millisekunneissa ja c_time tiedon välittämiseen palvelimelle käytettyä aikaa millisekunneissa.
