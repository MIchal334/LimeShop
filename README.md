# LimeShop
Celem projektu było stworzenie backendu do aplikacji webowej służącej do handlu wapnem. W aplikacji występują trzy role użytkowników:

Client- Może posiadać swoje powiązania ze sprzedawcami wapna. Podglądać statusy swoich zakupów oraz dokonywać nowych.

Dealer- Posiada on listę klientów oraz swoje zasoby wapna akceptuje transakcję oraz może je podejrzeć.

Admin- Może podejrzeć wszystkie transakcje w systemie. Usunąć użytkownika. Konta z tą rolą nie da się stworzyć. Tworzy się ono automatycznie.


Technologie:
Java, Spring Boot, Flayway, Keycloak, Docker, GIT, MySQL.


Uruchomienie: 
Do uruchomienia aplikacji służy plik 'docker-compose.ymal' który jest dostępny w repozytorium. Aby uruchomić aplikację należy wejść do folderu w którym dany plik się znajduje i następnie z poziomu konsoli uruchomić polecenie "docker-compose up". Po odczekaniu kilku minut wszystko co niezbędne powinno się uruchomić. Po uruchomieniu wszystkiego pod adresem  "localhost:8180" dostępna jest konsola keycloka służąca do zarządzania zarejestrowanymi użytkownikami login: "admin" hasło: "admin". Użyewjąc MySQL Workbench możemy połączyć się i przejrzeć strukturę bazy danych login: "limeU", haslo: "limeP". Samą strukturę endpointów wystawianych przez aplikację można podejrzeć za pomocą adresu "localhost:8090/swagger-ui.html". Na repozytorium dostępna jest również kolekcja endpointów która została wyeksportowana z programu PostMan. Jednym z najważniejszych endpointów który nie jest widoczny w swagger jest endpoint o adresie "http://127.0.0.1:8081/auth" który zwraca Toeken autoryzacyjny. Do uzyskania tokena należy wywołać ten endpoint w typie "x-www-form-urlencoded" oraz z ciałem "username" i "password". Jest to enddpoint tymczasowy dla testów samego backenduC.
