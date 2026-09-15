HE THONG MICROSERVICES QUAN LY THE LOAI VA PHIM (GENRE AND MOVIE MANAGEMENT SYSTEM)

1. TONG QUAN KIEN TRUC HE THONG
He thong duoc xay dung theo mo hinh Microservices su dung Spring Boot va Spring Cloud gom 5 dich vu:
- config-server (Port 8888): Cung cap cau hinh tap trung tu thu muc config-repo.
- eureka-server (Port 8761): Dang ky va quan ly phat hien dich vu (Service Discovery).
- genre-service (Port 8081): Quan ly thong tin the loai phim (H2 in-memory Database).
- movie-service (Port 8082): Quan ly thong tin phim (H2 in-memory Database, giao tiep OpenFeign sang genre-service).
- api-gateway (Port 8080): Cong vao duy nhat (Single Entry Point), dinh tuyen load balancing (lb://).

2. THU TU KHOI CHAY CAC DICH VU
Moi dich vu duoc mo trong mot terminal rieng biet va khoi chay theo dung thu tu sau:

Buoc 1: Khoi dong Config Server
cd config-server
.\gradlew bootRun

Buoc 2: Khoi dong Eureka Server (Kiem tra dashboard tai http://localhost:8761)
cd eureka-server
.\gradlew bootRun

Buoc 3: Khoi dong Genre Service (Lay cau hinh tu config-server va dang ky vao eureka)
cd genre-service
.\gradlew bootRun

Buoc 4: Khoi dong Movie Service (Lay cau hinh va dang ky vao eureka)
cd movie-service
.\gradlew bootRun

Buoc 5: Khoi dong API Gateway (Cong giao tiep 8080)
cd api-gateway
.\gradlew bootRun

3. DANH SACH DICH VU VA CONG TRUY CAP
- Config Server: http://localhost:8888
- Eureka Server Dashboard: http://localhost:8761
- API Gateway (Cac client va Postman chi goi vao day): http://localhost:8080
- Genre Service (Noi bo): http://localhost:8081
- Movie Service (Noi bo): http://localhost:8082

4. DU LIEU KHOI TAO SAN TRONG HE THONG
Khi genre-service khoi dong, co san 2 the loai phim duoc nap vao Database:
- Genre ID 1: Action (Phim hanh dong voi nhung pha hanh dong kich tinh)
- Genre ID 2: Sci-Fi (Phim khoa hoc vien tuong va kham pha vu tru)

5. HUONG DAN KIEM THU 3 KICH BAN QUA CONG 8080 (API GATEWAY)
He thong ho tro ca 2 dang duong dan tuong thich:
- Dang chuan so nhieu: /api/genres va /api/movies
- Dang theo ten service: /api/genre-service va /api/movie-service

Kich ban 1: Them moi phim thanh cong voi genreId hop le (genreId = 1)
- Method: POST
- URL: http://localhost:8080/api/movies (hoac http://localhost:8080/api/movie-service)
- Headers: Content-Type: application/json
- Request Body:
```json
{
  "title": "Avengers: Endgame",
  "director": "Anthony Russo, Joe Russo",
  "duration": 181,
  "genreId": 1,
  "ticketPrice": 120000.0
}
```
- Ket qua ky vong:
  - HTTP Status: 201 Created
  - Response Body chua thong tin phim kem id tu dong tang (vi du: id = 1).
- Giai thich: movie-service goi OpenFeign sang genre-service kiem tra the loai co id = 1 ton tai, sau do luu vao CSDL thanh cong.

Kich ban 2: Them moi phim that bai voi genreId khong ton tai (genreId = 999)
- Method: POST
- URL: http://localhost:8080/api/movies (hoac http://localhost:8080/api/movie-service)
- Headers: Content-Type: application/json
- Request Body:
```json
{
  "title": "Interstellar",
  "director": "Christopher Nolan",
  "duration": 169,
  "genreId": 999,
  "ticketPrice": 110000.0
}
```
- Ket qua ky vong:
  - HTTP Status: 404 Not Found
  - Response Body:
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Genre not found with id: 999"
}
```
- Giai thich: Feign Client phat hien the loai 999 khong ton tai trong genre-service, he thong chan khong luu vao CSDL va tra ve loi 404.

Kich ban 3: Lay danh sach tat ca phim thanh cong
- Method: GET
- URL: http://localhost:8080/api/movies (hoac http://localhost:8080/api/movie-service)
- Ket qua ky vong:
  - HTTP Status: 200 OK
  - Response Body: Danh sach mang JSON cac bo phim da duoc luu hop le.

API bo tro: Kiem tra lay thong tin the loai theo ID qua Gateway
- Method: GET
- URL: http://localhost:8080/api/genres/1 (hoac http://localhost:8080/api/genre-service/1)
- Ket qua ky vong: HTTP 200 OK chua thong tin the loai Action.

6. HUONG DAN CHAY LENH CURL KIEM THU TRUC TIEP
Kich ban 1:
curl -X POST http://localhost:8080/api/movies -H "Content-Type: application/json" -d "{\"title\":\"Avengers: Endgame\",\"director\":\"Anthony Russo, Joe Russo\",\"duration\":181,\"genreId\":1,\"ticketPrice\":120000.0}"

Kich ban 2:
curl -X POST http://localhost:8080/api/movies -H "Content-Type: application/json" -d "{\"title\":\"Interstellar\",\"director\":\"Christopher Nolan\",\"duration\":169,\"genreId\":999,\"ticketPrice\":110000.0}"

Kich ban 3:
curl -X GET http://localhost:8080/api/movies
