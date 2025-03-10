# ManagementSystem

## 프로젝트 소개
ManagementSystem은 학생과 직원의 정보를 쉽고 편리하게 관리할 수 있는 시스템입니다. Java와 MySQL로 개발되었으며, 명령줄 인터페이스(CLI)를 통해 간단하고 직관적인 사용 환경을 제공합니다.

---

## 주요 기능

### 학생 관리
- 학생 정보의 추가, 수정, 삭제, 조회 가능

### 직원 관리
- 정규직 직원의 성과 평가와 급여 관리
- 파트타임 직원의 시급 및 근무시간을 통한 급여 자동 계산

### 데이터베이스 관리
- MySQL 데이터베이스 연동
- 저장 프로시저(Stored Procedure)를 이용한 효율적인 데이터 처리

---

## 설치 방법

### 요구 사항
- Java 8 이상
- MySQL 5.7 이상

### 프로젝트 다운로드
```bash
git clone https://github.com/your-repo/ManagementSystem.git
cd ManagementSystem
```

### 데이터베이스 설정

#### dbinfo 파일 설정
다음과 같이 `util/dbinfo.properties` 파일을 생성하고, 본인의 데이터베이스 설정에 맞게 YOURDATA 부분을 변경하세요.

```properties
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/YOURDATA?serverTimezone=YOURDATA
user=YOURDATA
password=YOURDATA
```

#### 테이블 생성

**학생 테이블**
```sql
CREATE TABLE Student (
    sno VARCHAR(20) PRIMARY KEY,
    name VARCHAR(20),
    korean INT,
    english INT,
    math INT,
    science INT
);
```

**정규직 직원 테이블**
```sql
CREATE TABLE Fulltime (
    empno VARCHAR(20) PRIMARY KEY,
    name VARCHAR(20),
    result INT,
    basicsalary INT
);
```

**파트타임 직원 테이블**
```sql
CREATE TABLE Parttime (
    empno VARCHAR(20) PRIMARY KEY,
    name VARCHAR(20),
    hourwage INT,
    workhour INT
);
```

#### 저장 프로시저 생성 예시
**학생 정보 추가 프로시저**
```sql
DELIMITER $$
CREATE PROCEDURE STUDENT_INSERT (
    IN sno VARCHAR(20), IN name VARCHAR(20),
    IN korean INT, IN english INT, IN math INT, IN science INT,
    OUT RTN_CODE INT
)
BEGIN
    INSERT INTO Student VALUES(sno, name, korean, english, math, science);
    SET RTN_CODE = 200;
END$$
DELIMITER ;
```
그 외 프로시저의 생성 예시는 [저장 프로시저](https://github.com/psns0122/ms_DBProcedure)를 참고하세요.

---

## 실행 방법
```bash
javac -d bin $(find . -name "*.java")
java -cp bin view.MainMenu
```

---

## 프로젝트 구조

```
ManagementSystem/
├── controller/        # 사용자 요청을 처리하여 모델과 뷰를 연결
├── model/             # 데이터베이스 접근 및 관리
├── util/              # 공통 유틸리티
├── view/              # 사용자 인터페이스 (CLI)
├── vo/                # 데이터 전송 객체(Value Objects)
├── .gitignore         # Git 버전 관리 제외 목록
├── LICENSE            # MIT 라이선스 정보
└── README.md          # 프로젝트 설명서
```

---

## 기여 방법
- GitHub에서 Issue를 등록하여 개선 사항을 제안할 수 있습니다.
- Repository를 Fork한 뒤, 수정 사항을 반영하여 Pull Request를 제출해 주세요.

---

## 제작자

- [정유진](https://github.com/yujini02): 사용자 인터페이스 담당 (yujinjeong76@gmail.com)
- [김성준](https://github.com/kimsj18): 학생 모델 담당 (kimsj0118@gmail.com)
- [김선민](https://github.com/seonmin12): 직원 모델 담당 (seonmin.kim1030@gmail.com)
- [박건희](https://github.com/psns0122): DB 모델링 담당 (psns0122@nave.com)

---

## 라이선스
본 프로젝트는 MIT 라이선스를 따릅니다. 자세한 내용은 [LICENSE](LICENSE)를 참고하세요.

---

## 참고 자료
- [MySQL 공식 문서](https://dev.mysql.com/doc/)
- [Java JDBC 튜토리얼](https://docs.oracle.com/javase/tutorial/jdbc/)
- [MySQL 저장 프로시저 가이드](https://www.mysqltutorial.org/getting-started-with-mysql-stored-procedures.aspx)

---
## 개인 정리
- 담당 역할 정리
  Employee Model 구현(ParttimeVO, FulltimeVO, ParttimeDAO, FulltimeDAO, 직원 관련 인터페이스 담당)

- 느낀점 및 개선할 점\t
  [느낀점]\t
  프로그래밍을 배운지 2달 정도 된 시점에서 진행한 첫 팀 프로젝트여서 더 미숙하고 실수도 많이 하고 오류도 많이 겪었지만 많이 성장했다고 느낌.
  설계의 중요성을 많이 느꼈고 처음에 설걔를 진행할 때, 이 서비스의 사용자는 누구인지 명확하게 정하고 들어가는 것이 매우 중요함을 느낌.
  사용자와 클라이언트 입장에서 많이 생각해보고 도메인 모델을 확실히 정하는 것이 큰 차이를 만듬.
  이번 프로젝트의 경우, 초기에 설계할때 사용자를 소규모 학원을 운영하는 운영자로 명확히 정하고 들어갔고 이는 실제 설계할 때 많은 도움이 됨.
  또한 MVC 패턴을 적용하여 공통 인터페이스와 변수명들을 조원들과 상의 한 뒤, Model, View, Controller, DB등 역할을 나누어 각자 구현을 하고 git을 사용해 Merge 함
  처음엔 마냥 외계어 같이 느껴졌던 git을 이번에 협업툴로 사용하며 pull request도 날려보고, branch도 나누어보고, merge하는 과정도 보고 pull도 해보며 git 협업 경험을 쌓을 수 있어 의미 있었음
  DAO 클래스를 담당하다 보니 DB와 java와 연동하는 코드를 많이 짜게 되었고 Preparestatement나 Statement(interface, java.sql)를 이용해 쿼리도 날려보고 Callablestatement로 프로시저도 호출해보며
  java에서 제공하는 sql 관련 인터페이스를 직접 사용해보며 숙지함. 수업시간에 공부했던 걸 실제로 적용해보니 수많은 오류도 있었지만 더 완벽하게 이해도를 높임.

  [개선해야할 점]\t
  기간이 짧았고 웜업 프로젝트였어서 규모가 크지 않아 DB에 다양한 데이터를 넣지 못함. 만약 향후 개선한다면 학생과 직원에 관련된 더 다양한 정보들을 DB에 담고싶음
  처음에 월금 인상 부분을 java로 구현해서 코드가 매우 지저분해졌는데 기회가 된다면 이번에 DB 담당은 아니었지만 직접 프로시저를 한번 구현해보고 싶음
  다른 팀들처럼 삭제한 직원이나 학생의 테이블을 저장할 수 있는 백업 테이블을 하나 만들어도 좋을 것 같다.
  수업시간에 배웠던 트리거도 활용해보고 싶다. 예를 들면 실적이 얼마 이상 오르면 월급이 일정 비율 자동 인상되는?
  Enum클래스를 적용할 수 있는 부분들은 적용해봐도 좋을 것 같다
  git을 팀장님 주도로 사용해봤는데 다음엔 좀 더 주도적으로 마냥 따라하는 게 아닌 좀 더 의미를 알고 써야겠다. 
  발표 스킬을 길러야겠다. 기간이 짧아 PPT 작성과 코드 수정 하느라고 발표 준비를 많이 못했는데 다음 프로젝트땐 좀 더 일목요연하게 차분하게 말하고 싶다. 이번엔 좀 다급하게 정신 없이 말했던 것 같다.
  
  
  

  
