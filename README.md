# Student Manager

Ứng dụng quản lý sinh viên bằng Spring Boot + JPA + SQL Server.

## Yêu cầu

- Java 21
- Maven
- Docker Desktop (nếu dùng SQL Server qua Docker)

## Chạy bằng Docker (khuyến nghị)

1. Mở terminal trong thư mục project
2. Chạy:

```bash
docker compose up -d
```

3. Chạy ứng dụng:

```bash
./mvnw spring-boot:run
```

4. Mở trình duyệt:

- http://localhost:8111/students
- http://localhost:8111/swagger-ui/index.html

## Dữ liệu mẫu

Dữ liệu ban đầu được lưu trong file:

```text
src/main/resources/data.sql
```

Khi Spring Boot khởi động, nó sẽ tự đọc file này và insert dữ liệu mẫu vào database nếu chưa có.

## Cấu hình database

File cấu hình:

```text
src/main/resources/application.properties
```

Cấu hình mặc định đang dùng SQL Server trên localhost:

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=QuanLySinhVien;encrypt=true;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=Abc123456789!
```

## Lưu ý

- Không đẩy cơ sở dữ liệu thật lên GitHub
- Chỉ đẩy code và file dữ liệu mẫu (`data.sql`)
- Người chấm cần database đang chạy trước khi app khởi động
