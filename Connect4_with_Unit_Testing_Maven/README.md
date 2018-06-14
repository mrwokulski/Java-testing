# Testowanie aplikacji JAVA 2017-2018
## Projekt 1 (JUnit, narzędzie Hamcrest oraz MAVEN)

[Travis]
 https://travis-ci.com/TestowanieJAVA2017-2018Gr3/projekt1-mrwokulski
-----------------------

Gra w czwórki. Czwórki to gra dla dwóch osób. Pierwszy gracz wybiera kolor, a następnie obie osoby na zmianę wrzucają kolorowe krążki w kolumny pionowej planszy o wymiarach 7 kolumn x 6 wierszy. Krążki spadają w dół i zajmują najniższe wolne miejsce w danej kolumnie. Celem gry jest umieszczenie czterech krążków w swoim kolorze w pionie, w poziomie lub
po przekątnej. Wygrywa osoba, która zrobi to pierwsza. [Tutaj jest link do gry](http://www.xlgry.pl/gry-board-card/czworki/index.htm).

Wymagania do tej gry są następujące:
- Plansza składa się z siedmiu kolumn i sześciu wierszy. Początkowo wszystkie pola są puste.
- Gracze wrzucają od góry krążki w kolumny. Jeśli kolumna jest pusta, wrzucony krążek spada
w dół planszy. Krążki dodane do tej kolumny będą się znajdować nad tymi wcześniej wrzu-
conymi.
- Gra jest przeznaczona dla dwóch osób. Każdemu graczowi jest przypisany jeden kolor. Jedna
osoba używa krążków czerwonych, a druga zielonych. Gracze wykonują ruchy na zmianę i za
każdym razem wrzucają jeden krążek.
- Program ma informować o zdarzeniach i błędach w grze. Dane wyjściowe powinny przedsta-
wiać stan na planszy po każdym ruchu.
- Gdy nie można dodać kolejnych krążków. Gra kończy się remisem.
- Jeśli gracz dodał krążek i w ten sposób połączył więcej niż trzy krążki w swoim kolorze
przylegające do siebie w pionowej linii, wygrywa.
- To samo dzieje się, jeśli połączono krążki w linii poziomej.
- To samo dzieje się, jeśli połączono krążki po stosie.
- Tworzenie list rankingowych graczy: imię, punktacja, ilość wygranych itd


