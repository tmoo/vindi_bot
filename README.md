[![Build Status](https://travis-ci.org/tmoo/vindi_bot.svg?branch=master)](https://travis-ci.org/tmoo/vindi_bot)

# A bot for the AI game vindinium (http://vindinium.org)

Built on top of https://github.com/bstempi/vindinium-client and using its implementation of HTTP and JSON parsing

# To run (on linux) do one of the following:
1) Run from this repository the script "start" with arguments "TRAINING TheBot true" or "COMPETITION TheBot true", depending on whether you want to compete against random bots or with other people's bots. You only need to use the arguments once, so that mvn compiles the project. You can only use the first argument if you want to compete, default is training. You will find a URL, where you can watch the game, in your terminal output. Make sure to set execution rights to the script if it doesn't already have them. For example "chmod 700 start" should do the trick.
2) Go check out the "CLI Usage" at (https://github.com/bstempi/vindinium-client) and use those commands. (The start-script also uses them)
3) Run from this repository the script "loop" to continously run the bot in compete mode. Remember permissions.


# Koodikatselmoijalle
käytännössä kaikki itse kirjoittamani koodi on paketissa datastructures sekä luokassa TheBot paketissa bot. TheBot on itse botti eli ohjelmalogiikka. Datastructures-paketissa on itse toteuttamani tietorakenteet. Kannattaa siis suunnata heti sinne. Loput on HTTP ja JSON juttuja, jotka on tuosta yllä linkatusta reposta. Toki esimerkiksi luokan GameState tai muiden katselu saattaa olla hyödyllistä ohjelman toiminan ymmärtämiseksi.
