# Charity YouTube Likes Counter

Program umożliwiający liczenie w czasie rzeczywistym wszystkich likeów zgromadzonych pod publicznymi filmami dowolnego użytkownika YouTube, od podanej przez użytkownika daty i przeliczanie zgromadzonych łapek na środki finansowe zgodnie z przelicznikiem. 

Software powstał specjalnie dla jednego z większych polskich twórców internetowych, który organizował akcję charytatywną w ramach której za każdą "łapkę" pod grudniowym filmem przekazywał określoną ilość pieniędzy na cele charytatywne.

Kod jest zoptymalizowany pod kątem wysyłanych requestów, tak aby na pewno nie przekroczyć limitu 10.000 żądań. Wszystkie filmy są zapisywane jako obiekty filmów, a następnie jedynie odświeża się ich konkretny stan. Wysyłane są największe możliwe requesty (50 filmów jednocześnie) tak aby zoptymalizować korzystanie z API. Jeżeli ktoś z czytelników ma jakieś uwagi do kodu, ma ochotę na pull requesta to jak najbardziej zapraszam do uczestnictwa :))

![app_demo](https://user-images.githubusercontent.com/77535280/170532418-1fb6509d-6226-4f8a-8804-acb458206c75.gif)


# TODO

- Dodanie możliwości konfiguracji odstępu między odświeżeniami licznika
- Zmiana koloru tekstu, czcionki
- Zapisywanie i wczytywanie różnych konfiguracji
- Pasek postępu łączenia z api, informacja zwrotna
- Funkcjonalność drukowania do plików wszystkich filmów, które są liczone z indywidualnym podziałem na ilość zgromadzonych like
- Liczenie łapek w dół, wyświetleń

# Zrzuty ekranu

Nie przywiązywałem dużej uwagi do warstwy prezentacji, ponieważ głównym zadaniem tego programu jest dostarczenie danych. Licznik ma być wykluczowywany w programie do transmisji na żywo i wkładany w odpowiednie miejsce w overlay. Poniżej, w tutorialu, znajduje się klika przykładowych zrzutów ekranu z aplikacji.

# Instalacja

Z uwagi na specyfikę projektu, trzeba pobrać kilka danych od googla, aby móc korzystać z API. Poniżej znajdziesz dokładną instrukcję

*Konifugracja dla osób nie mających nigdy styczności z usługami API od Google*

Przejdź pod adres https://console.cloud.google.com/ i zaloguj się swoim kontem Google. Nie musi być to konto, którego łapki w górę chcesz śledzić.

![select_project](https://user-images.githubusercontent.com/77535280/151875005-98a0381e-29b0-4a56-b84b-b75a880708d9.png)

Stwórz nowy projekt

![choose_project](https://user-images.githubusercontent.com/77535280/151875008-7fa1d94f-2d3d-4644-a6bc-6f3b35ee04c6.png)

Kliknij "brak organizacji"


![create_project](https://user-images.githubusercontent.com/77535280/151875014-4fee783d-4be7-46db-ae7e-0f7b136941a1.png)

Nazwij dowolnie swój projekt. *Zapisz gdzieś Identyfikator projektu!*

![library_findYTDAPI](https://user-images.githubusercontent.com/77535280/151875044-352a66c0-12d6-426b-8cd9-d449b37880c7.png)

Wejdź w *Interfejsy API i usługi* i kliknij *Biblioteka*, wyszukaj *youtube data api v3*

![turnOnApi](https://user-images.githubusercontent.com/77535280/151875046-cc82331c-c5ff-4e0f-a9c0-491b28bc29b3.png)

Włącz api YouTube

![create_credintials](https://user-images.githubusercontent.com/77535280/151875016-19da6eb1-7afa-48e7-af63-aa2216198a02.png)

Przejdź do zakładki *Dane logowania*

![create_credintials2](https://user-images.githubusercontent.com/77535280/151875018-caff52ab-8d01-46d2-962c-8697824e2898.png)

Kliknij *Utwórz dane logowania*


![select_oauth](https://user-images.githubusercontent.com/77535280/151875021-d368d5ce-a4d7-40b9-b8bd-62b10a64ade9.png)

Wybierz *Indentyfikator klienta OAuth*

![create_oauth_screen](https://user-images.githubusercontent.com/77535280/151875022-997f2ed6-5cb3-4b16-bcd3-63192192215d.png)

Przejdź do kreatora *Ekranu zgody OAuth*

![oauth_screen1](https://user-images.githubusercontent.com/77535280/151875023-b0f0938b-0095-4da7-b120-0bbc989df265.png)

Wybierz *zewnętrzny*

![oauth_screen2](https://user-images.githubusercontent.com/77535280/151875024-7c4b3bf6-8b07-4d6e-b5ef-d0e316a3714b.png)

Uzupełnij dane aplikacji zgodnie z twoim pomysłem

![zakresy](https://user-images.githubusercontent.com/77535280/151875026-2034b02d-c5de-45ba-80f4-35ba5b2255ef.png)

Kliknij *Dodaj lub usuń zakresy*

![select_zakresy](https://user-images.githubusercontent.com/77535280/151875027-3bcaf29c-b1bc-4a69-834c-ab83fed986dd.png)

W wysuwanym panelu wyszukaj *youtube* i zaznacz wszystko. To umożliwi ci korzystanie z przyszłych funkcji projektu bez konieczności zmian w ustawieniach API

![test_users](https://user-images.githubusercontent.com/77535280/151875028-de332d53-98bd-492f-9cc4-a78cb5a8f068.png)

Dodaj użytkowników testowych. Nawet, jeżeli do Cloud Console jesteś zalogowany z tego samego maila, którym chcesz się logować do aplikacji.

![add_credintials](https://user-images.githubusercontent.com/77535280/151875029-88e34af0-f963-47a2-86e4-8183643755d1.png)

Ponownie wejdź w zakładkę *Dane logowania*

![select_apptype](https://user-images.githubusercontent.com/77535280/151875030-c29a7546-ad44-4d5f-95f0-13829b318788.png)

Wybierz *Aplikacja komputerowa*

![oauth_confirmation](https://user-images.githubusercontent.com/77535280/151875032-ac19b24e-5628-44ce-b44b-a531bc623a01.png)

Skopiuj identyfikator klienta, a następnie klucz klienta

![app_configureAPI](https://user-images.githubusercontent.com/77535280/151875033-5ec92f23-d715-4d62-84f2-dda2164e505e.png)

Wprowadź skopiowane wartości do programu (client_id -> identyfikator klienta, client_secret -> tajny klucz klienta, project_id -> id projektu, które zanotowałeś na początku tutorialu

![channel_id](https://user-images.githubusercontent.com/77535280/151875035-e3f4a3f2-ad08-447b-82ca-1a80cf6d2c34.png)

Zdobądź ID kanału, który chcesz śledzić. Przepisz ten, jeżeli chcesz śledzić twórczość PewDiePie'a. Jeżeli natomiast chcesz śledzić swoją twórczość wejdź w youtube studio i skopiuj tę część linka po /channel/

![app_mainscreen_configured](https://user-images.githubusercontent.com/77535280/151875038-f9fa9b0b-2260-44df-bb2f-82f498ae171c.png)

Wklej ID Kanału w pole *id kanału*, ustaw odpowiedni przelicznik (pamiętaj o korzystaniu z kropki, a nie przecinka 

![app_editTextScreen](https://user-images.githubusercontent.com/77535280/151875040-4e0911df-abe9-461f-b00a-4ee7d1bf61e6.png)

Na ekranie możesz też edytować tekst górny i dolny wyświetlany na liczniku

![oauth_loginScreen](https://user-images.githubusercontent.com/77535280/151875042-10475850-643c-4396-8ab5-ae28e52d09d6.png)

Kliknij *Wyświetl licznik* zaloguj się i baw się dobrze.

**UWAGA Czasem Google potrzebuje trochę czasu, aby zaktualizować dane dot. API Jeżeli postępowałeś wg. tego poradnika, zaczekaj maksymalnie kilka godzin (u nas z reguły uprawnienia API aktualizowały się do 30 minut)**

## Update 24.02.22
Dodano równolegle działające okienko z ładowaniem i logami
![image](https://user-images.githubusercontent.com/77535280/155524078-37bad384-f65b-4e86-b23e-a175c3a73c89.png)


[![CodeFactor](https://www.codefactor.io/repository/github/komp15/youtube-likes-counter/badge)](https://www.codefactor.io/repository/github/komp15/youtube-likes-counter)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/d82a2c4266934deaa24fd524ac152dfe)](https://www.codacy.com/gh/komp15/youtube-likes-counter/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=komp15/youtube-likes-counter&amp;utm_campaign=Badge_Grade)
