Olen siistinyt projektia ja poistanut starterista kaiken valmiin ohjelmalogiikan. Nyt jäljellä on vain HTTP ja JSON parseamiseen tarvittavat jutut ja satunnaisesti toiminnan valitseva botti. Lisäksi tein luokan, johon rakennan oman bottini. Kirjoitin lyhyen scriptin, joka nopeuttaa pelin ajamista.

Olen testaillut, miltä satunnaisbotin pelit näyttävät. Yleensä näyttänyt juuri siltä kuin olettaisi, mutta yhdessä vaiheessa netti ilmeisesti hidastui niin paljon, että yhden sekunnin miettimisaika ehti mennä aina umpeen botilla jossain vaiheessa, josta seuraa pelin päättyminen (testi-tilassa, kilpa-asetuksilla peli jatku mutta oma botti ei enää liiku). Toivottavasti ongelma on vain tilapäinen, koska bottia olisi todella vaikea testata, jos tämä jatkuu. Mahdollisia ratkaisuja on pyörittäminen paikallisella palvelimella ja Kumpulaan nopeamman netin äärelle hakeutuminen.

Itse algoritmin kimppuun en päässyt vielä, sillä viikko on ollut kiireinen. Tutkin kuitenkin ideoita. Esimerkiksi best reply search -niminen algoritmi vaikuttaa lupaavalta (esitelty täällä: https://dke.maastrichtuniversity.nl/m.winands/documents/BestReplySearch.pdf). Ensi viikolla kirjoitan ainakin jotenkin toimivan botin ja lähden kokeilemaan myös RBS:n toteutusta.

Tunteja käytetty noin 6.
