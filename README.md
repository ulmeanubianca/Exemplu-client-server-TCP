# Sistem de Rezervări Aventuri — Client-Server TCP

Aplicație Java care simulează un sistem de rezervări pentru aventuri turistice (drumeții, cățărări etc.), cu două componente:

1. **Procesare de date** — citire din JSON/CSV, agregare cu Stream API, generare raport de venituri.
2. **Comunicare în rețea** — arhitectură client-server peste socket-uri TCP, cu server multi-threaded capabil să deservească mai mulți clienți simultan.

## De ce acest proiect

L-am construit pentru a înțelege în profunzime cum funcționează comunicarea de rețea la nivel de socket (fără framework-uri precum Spring), înainte de a folosi abstractizări de nivel înalt (REST, gRPC) în proiecte viitoare.

## Arhitectură

```
┌──────────┐         TCP Socket          ┌──────────────────┐
│  Client  │ ───────────────────────────▶ │      Server       │
│          │ ◀─────────────────────────── │  (ServerSocket)   │
└──────────┘         (port 2222)          └────────┬──────────┘
                                                     │
                                          accept() în loop infinit
                                                     │
                                         ┌───────────▼────────────┐
                                         │   Thread Pool (10)     │
                                         │   ClientHandler x N    │
                                         └─────────────────────────┘
```

- **Server**: ascultă pe portul `2222`, acceptă conexiuni într-un loop infinit (`accept()`), și deleagă fiecare conexiune unui `ClientHandler` rulat pe un thread din pool (`ExecutorService`, thread-per-connection cu pool fix).
- **ClientHandler**: procesează cererile unui singur client, ținând conexiunea deschisă pentru mai multe schimburi de mesaje (nu se închide după un singur răspuns).
- **Client**: trimite o serie de cereri text, citește răspunsurile, trimite `STOP` pentru a încheia sesiunea.

### Protocolul de aplicație (peste TCP)

| Cerere client            | Răspuns server                          |
|---------------------------|------------------------------------------|
| `<denumire_aventura>`     | `<locuri_disponibile>` sau `nu exista`   |
| `STOP`                     | `byee` (server închide conexiunea)      |

Fiecare mesaj e o linie de text (`println`/`readLine`), similar cu protocoale text-based precum SMTP.

## Structura proiectului

```
src/
  Main.java              → procesare date, raport local (fără rețea)
  model/
    Aventura.java
    Rezervare.java
  data/
    DataLoader.java       → citire aventuri.json / rezervari.txt
  server/
    Server.java           → pornire ServerSocket + thread pool
    ClientHandler.java     → logica de procesare per conexiune
  client/
    Client.java            → trimite cereri către server
data/
  aventuri.json
  rezervari.txt
```

## Cum se rulează

### 1. Procesarea de date (fără rețea)

```bash
java src/Main.java
```

Afișează în consolă aventurile cu peste 20 de locuri disponibile, aventurile cu cel puțin 5 locuri rămase după rezervări, și salvează un raport de venituri în `venituri.txt`.

### 2. Sistemul client-server

Pornește mai întâi serverul:

```bash
java src/server/Server.java
```

Apoi, într-un terminal separat, rulează unul sau mai mulți clienți:

```bash
java src/client/Client.java
```

Poți rula mai mulți clienți simultan (în terminale diferite) pentru a observa cum serverul îi procesează concurent, fiecare pe thread-ul lui din pool.

## Concepte demonstrate

- **TCP sockets** (`Socket`, `ServerSocket`) — comunicare orientată pe conexiune, garantată și ordonată.
- **Thread pool** (`ExecutorService`) — evitarea creării unui thread nou nelimitat per conexiune.
- **Blocking I/O** — `readLine()` blochează thread-ul până sosesc date noi sau conexiunea se închide.
- **Separare pe straturi** — model / acces la date / logică de rețea, fără stare globală partajată.
- **Java Stream API** — grupare, agregare și sortare pe colecții (`groupingBy`, `summingInt`).

## Posibile îmbunătățiri viitoare

- [ ] Sincronizare pentru operații de scriere concurentă (ex: comandă `REZERVA` care modifică `locuriDisponibile`).
- [ ] Protocol structurat (JSON în loc de text simplu) pentru cereri/răspunsuri.
- [ ] Teste unitare pentru `DataLoader` și logica de agregare.

## Tehnologii

- Java 17+
- [org.json](https://github.com/stleary/JSON-java) pentru parsare JSON
