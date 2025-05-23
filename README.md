# Spring Certificate App

## 실행 방법 (팀원용)

1. H2 TCP 모드 서버 실행:
   ```bash
   java -cp h2-2.3.232.jar org.h2.tools.Server -tcp
   ```

2. Spring Boot 프로젝트 실행:
   - IntelliJ로 `SpringCertificateApplication` 실행
   - 또는 CLI에서:
     ```bash
     ./mvnw spring-boot:run
     ```

3. H2 Console 접속:
   - URL: http://localhost:8080/h2-console
   - JDBC URL: `jdbc:h2:tcp://localhost/~/testdb`
   - 사용자명: `sa`
   - 비밀번호: 없음

## Git 사용
```bash
git clone https://github.com/yourname/Spring_Certificate.git
cd Spring_Certificate
```