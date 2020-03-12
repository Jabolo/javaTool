# javaTool
  Narzędzie wykorzystujące javaassist do manipulacji bytecode'em posiada funckjonalności:
	○ Załadowanie pliku .jar (--i), np. --i game.jar
	○ Eksplorację pliku .jar (pakiety --list-packages, klasy --list-classes, metody --list-methods [pelna-nazwa-klasy], pola --list-fields [pelna-nazwa-klasy], konstruktory --list-ctors [pelna-nazwa-klasy]) – pamiętać o prywatnych
	○ Modyfikację wskazanego celu --script [example-modify-game.rts]:
		§ Dodanie/usunięcie dodanego pakietu: add-package [pelna-nazwa] remove-package [pelna-nazwa]
		§ Dodanie/usunięcie dodanej klasy/interfejsu: add-class [pelna-nazwa] add-interface [pelna-nazwa] remove-class [pelna-nazwa] remove-interface [pelna-nazwa]
		§ Dodanie/usunięcie metody: add-method [pelna-nazwa] remove-method [pelna-nazwa]
		§ Nadpisanie ciała metody set-method-body [pelna-nazwa sciezka-do-pliku-src]
		§ Dopisanie kodu na początek/koniec wskazanej metody: add-before-method [pelna-nazwa sciezka-do-pliku-src] add-after-method [pelna-nazwa sciezka-do-pliku-src]
		§ Dodanie/usunięcie pola add-field [pelna-nazwa-klasy nazwa-pola]
		§ Dodanie/usunięcie konstruktora [pelna-nazwa]
		§ Nadpisanie ciała konstruktora [set-ctor-body] [pelna-nazwa sciezka-do-pliku-src]
	○ Zapisanie zmodyfikowanego pliku .jar --o modified-game.jar

Instrukcja do modyfikacji gry SpaceInvaders (dodanie tzw. cheatu)
Z użyciem Maven.install wygenerować plik javaTool_Michal_Jablonski-jar-with-dependencies.jar (można z poziomu Intellij)
W tym samym miejscu co wygenerowany(domyślnie javaTool/target) .jar, trzymać pliki addScoreBody.txt, source.txt, uPress.txt (domyślnie są załączone do wskazanej ścieżki)
W pliku source.txt po parametrze --i wstawić ścieżkę BEZWZGLĘDNĄ do pliku Invader.jar
Z poziomu CMD przejść do w.w. folderu i uruchomić program poleceniem:
java -jar javaTool_Michal_Jablonski-jar-with-dependencies.jar --script source.txt
Uruchomić grę
Po jedno krotnym wciśnięciu litery 'u', zdobywamy od razu po 100 pkt. po trafieniu "przeciwnika". Kolejne kliknięcia 'u' nic nie zmieniają
