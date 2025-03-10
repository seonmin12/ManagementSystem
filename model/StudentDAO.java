package model;

import util.DBUtil;
import vo.FulltimeVO;
import vo.ParttimeVO;
import vo.PersonVO;
import vo.StudentVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * {@code StudentDAO} 클래스는 학생 데이터를 관리하기 위한 데이터 접근 객체(DAO)입니다.
 * 데이터베이스에 연결되어 학생 데이터를 추가, 삭제, 수정, 검색, 정렬하는 기능을 제공합니다.
 *
 * 이 클래스는 다음과 같은 주요 기능을 포함합니다:
 * - 데이터베이스 연결 및 쿼리 실행
 * - 학생 데이터 추가, 변경, 삭제 및 정렬
 * - 합계, 평균, 등급 계산
 */
public class StudentDAO implements Student {
    /**
     * 싱글톤(Singleton)으로 구현된 DAO 인스턴스
     */
    private static StudentDAO dao;

    /**
     * 생성자를 private으로 설정하여, 외부에서의 객체 생성을 제한합니다.
     */
    private StudentDAO() {}

    /**
     * DAO 인스턴스를 반환하는 싱글톤(Singleton) 메서드
     *
     * @return {@code StudentDAO} 인스턴스
     */
    public static StudentDAO getInstance() {
        if (dao == null) dao = new StudentDAO();

        return dao;
    }

    /** 학생 데이터 관리 리스트 */
    private ArrayList<StudentVO> studentlist = new ArrayList<>();
    /** 데이터베이스 연결 객체 */
    private Connection conn;
    /** SQL 문 실행을 위한 PreparedStatement 객체 */
    private PreparedStatement pstmt;
    /** SQL 문 실행을 위한 Statement 객체 */
    private Statement stmt;
    /** SQL 쿼리 결과를 저장하는 ResultSet 객체 */
    private ResultSet rs;
    /** 저장 프로시저 호출을 위한 CallableStatement 객체 */
    private CallableStatement cs;

    /**
     * 데이터베이스 연결 종료 메서드
     * 사용한 ResultSet, Statement, PreparedStatement, Connection 객체를 닫습니다.
     */
    private void disConnect() {
        if (rs != null) try {rs.close();} catch (SQLException e) {}
        if (stmt != null) try {stmt.close();} catch (SQLException e) {}
        if (pstmt != null) try {pstmt.close();} catch (SQLException e) {}
        if (conn != null) try {conn.close();} catch (SQLException e) {}
    }

    /**
     * 데이터베이스 연결 및 학생 데이터 읽어오기
     * DB에서 `Student` 테이블의 데이터를 읽어와 `studentlist` 리스트에 저장합니다.
     */
    private void connect() {
        try {
            conn = DBUtil.getConnection();
            String sql = " select * from student ";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                StudentVO studentVO = new StudentVO();

                studentVO.setSno(rs.getString("sno"));
                studentVO.setName(rs.getString("name"));
                studentVO.setKorean(rs.getInt("korean"));
                studentVO.setEnglish(rs.getInt("english"));
                studentVO.setMath(rs.getInt("math"));
                studentVO.setScience(rs.getInt("science"));

                if (studentVO.getKorean() < 0) studentVO.setKorean(0);
                else if (studentVO.getKorean() > 100) studentVO.setKorean(100);
                if (studentVO.getEnglish() < 0) studentVO.setEnglish(0);
                else if (studentVO.getEnglish() > 100) studentVO.setEnglish(100);
                if (studentVO.getMath() < 0) studentVO.setMath(0);
                else if (studentVO.getMath() > 100) studentVO.setMath(100);
                if (studentVO.getScience() < 0) studentVO.setScience(0);
                else if (studentVO.getScience() > 100) studentVO.setScience(100);

                // 합계, 평균, 등급 계산
                this.total(studentVO);
                this.average(studentVO);
                this.grade(studentVO);

                // 리스트에 추가
                studentlist.add(studentVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
    }

    /**
     * 학생 데이터를 추가합니다.
     *
     * 데이터베이스와 리스트에 해당 학생 정보를 추가합니다.
     * 추가된 데이터에 따라 합계, 평균, 등급 을 계산합니다.
     *
     * @param personVO 추가할 학생 데이터
     */
    @Override
    public void input(PersonVO personVO) {

        StudentVO newStudent = (StudentVO) personVO;
        if(studentlist.size() == 0) this.connect();

        //  studentlist가 비어있으면 DB에서 데이터 읽어오기
        if (studentlist.size() == 0) {
            this.connect();
        }

        try {
            conn = DBUtil.getConnection();

            cs = conn.prepareCall("{call STUDENT_INSERT(?,?,?,?,?,?,?)}");

            cs.setString(1, newStudent.getSno());
            cs.setString(2, newStudent.getName());
            cs.setInt(3, newStudent.getKorean());
            cs.setInt(4, newStudent.getEnglish());
            cs.setInt(5, newStudent.getMath());
            cs.setInt(6, newStudent.getScience());

            cs.registerOutParameter(7, Types.INTEGER);
            cs.execute();

            int resultMsg = cs.getInt(7);
            if (resultMsg == 100) {
                System.out.println("디비 입력 실패");
            } else {
                if (newStudent.getKorean() < 0) newStudent.setKorean(0);
                else if (newStudent.getKorean() > 100) newStudent.setKorean(100);
                if (newStudent.getEnglish() < 0) newStudent.setEnglish(0);
                else if (newStudent.getEnglish() > 100) newStudent.setEnglish(100);
                if (newStudent.getMath() < 0) newStudent.setMath(0);
                else if (newStudent.getMath() > 100) newStudent.setMath(100);
                if (newStudent.getScience() < 0) newStudent.setScience(0);
                else if (newStudent.getScience() > 100) newStudent.setScience(100);

                // 합계, 평균, 등급 계산
                this.total(newStudent);
                this.average(newStudent);
                this.grade(newStudent);

                // 리스트에 추가
                studentlist.add(newStudent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
    }

    /**
     * 학생 데이터를 수정합니다.
     *
     * 데이터베이스와 리스트에서 해당 학생 정보를 갱신합니다.
     * 수정된 데이터에 따라 합계, 평균, 등급 등을 갱신하며 `studentlist` 내에서도 업데이트가 이루어집니다.
     *
     * @param personVO 수정할 학생 데이터 객체
     */
    @Override
    public void update(PersonVO personVO) {

        StudentVO newStudent = (StudentVO) personVO;

        //  studentlist가 비어있으면 DB에서 데이터 읽어오기
        if (studentlist.size() == 0) {
            this.connect();
        }

        try {

            conn = DBUtil.getConnection();

            cs = conn.prepareCall("{call STUDENT_UPDATE(?,?,?,?,?,?,?)}");

            cs.setString(1, newStudent.getSno());
            cs.setString(2, newStudent.getName());
            cs.setInt(3, newStudent.getKorean());
            cs.setInt(4, newStudent.getEnglish());
            cs.setInt(5, newStudent.getMath());
            cs.setInt(6, newStudent.getScience());

            cs.registerOutParameter(7, Types.INTEGER);
            cs.execute();

            int resultMsg = cs.getInt(7);
            if (resultMsg == 100) {
                System.out.println("디비 수정 실패");
            } else {
                if (newStudent.getKorean() < 0) newStudent.setKorean(0);
                else if (newStudent.getKorean() > 100) newStudent.setKorean(100);
                if (newStudent.getEnglish() < 0) newStudent.setEnglish(0);
                else if (newStudent.getEnglish() > 100) newStudent.setEnglish(100);
                if (newStudent.getMath() < 0) newStudent.setMath(0);
                else if (newStudent.getMath() > 100) newStudent.setMath(100);
                if (newStudent.getScience() < 0) newStudent.setScience(0);
                else if (newStudent.getScience() > 100) newStudent.setScience(100);

                // 합계, 평균, 등급 계산
                this.total(newStudent);
                this.average(newStudent);
                this.grade(newStudent);

                // 리스트에서 수정
                studentlist.set(studentlist.indexOf(newStudent), newStudent);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
    }

    /**
     * 학생 데이터를 삭제합니다.
     *
     * 데이터베이스와 `studentlist`에서 특정 ID에 해당하는 학생 데이터를 삭제합니다.
     * 삭제 실패 시 오류 메시지를 출력합니다.
     *
     * @param deleteNum 삭제할 학생의 ID
     */
    @Override
    public void delete(String deleteNum) {
        //  studentlist가 비어있으면 DB에서 데이터 읽어오기
        if (studentlist.size() == 0) {
            this.connect();
        }
        try {
            conn = DBUtil.getConnection();

            cs = conn.prepareCall("{call STUDENT_DELETE(?,?)}");

            cs.setString(1, deleteNum);

            cs.registerOutParameter(2, Types.INTEGER);
            cs.execute();

            int resultMsg = cs.getInt(2);
            if (resultMsg == 100) {
                System.out.println("디비 삭제 실패");
            } else {
                studentlist.remove(studentlist.indexOf(
                        new StudentVO(deleteNum, null, 0, 0, 0, 0)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
    }

    /**
     * 전체 학생 데이터를 특정 조건에 따라 정렬하여 출력합니다.
     *
     * `sortNum`에 따라 이름순(1), 학번순(2), 성적순(3) 등의 정렬을 수행하며,
     * 정렬된 데이터는 단순 출력 목적으로 사용됩니다.
     *
     * @param sortNum 정렬 조건을 나타내는 번호
     */
    public void totalSearch(int sortNum) {
        //  studentlist가 비어있으면 DB에서 데이터 읽어오기
        if (studentlist.size() == 0) {
            this.connect();
        }

        // 리스트의 내용을 sortNum 으로 정렬
        this.sort(sortNum);
      
        // 리스트의 내용을 출력
        studentlist.forEach(System.out::println);
    }

    /**
     * 특정 학생 데이터를 검색하여 출력합니다.
     *
     * `studentlist`에 저장된 특정 ID를 가진 학생 데이터를 찾아 출력합니다.
     * 학생 데이터가 없으면 특별한 동작 없이 진행됩니다.
     *
     * @param searchNum 검색할 학생의 ID
     */
    @Override
    public void search(String searchNum) {
        //  studentlist가 비어있으면 DB에서 데이터 읽어오기
        if (studentlist.size() == 0) {
            this.connect();
        }

        StudentVO temp = studentlist.get(studentlist.indexOf(
                new StudentVO(searchNum, null, 0, 0, 0, 0)
        ));
        System.out.println(temp);
    }

    /**
     * 학생 데이터를 정렬합니다.
     *
     * `sortNum` 변수에 따라 학생 데이터를 다양한 기준으로 정렬합니다:
     * - 1: 이름순
     * - 2: 학번순
     * - 3: 성적순 (내림차순) 등
     *
     * @param sortNum 정렬 조건을 나타내는 번호
     */
    @Override
    public void sort(int sortNum) {
        switch (sortNum) {
            case 1: // 이름순
                studentlist.sort(Comparator.comparing(StudentVO::getName));
                break;
            case 2: // 학번순
                studentlist.sort((o1, o2) -> o1.compareTo(o2));
                break;
            case 3: // 성적순
                studentlist.sort(Comparator.comparing(StudentVO::getTotal).reversed());
                break;
            case 4:
                break;
        }
    }

    /**
     * 학생의 총점을 계산합니다.
     *
     * 국어, 영어, 수학, 과학 점수를 합산하여 총점을 계산합니다.
     *
     * @param studentVO 총점을 계산할 학생 객체
     */
    public void total(StudentVO studentVO){
        int total = studentVO.getKorean()
                    + studentVO.getEnglish()
                    + studentVO.getMath()
                    + studentVO.getScience();
        studentVO.setTotal(total);
    }

    /**
     * 학생의 평균 점수를 계산합니다.
     *
     * 총점을 과목 수로 나누어 평균값을 계산합니다.
     *
     * @param studentVO 평균 점수를 계산할 학생 객체
     */
    @Override
    public void average(StudentVO studentVO){
        float average = studentVO.getTotal() / 4.0f;
        studentVO.setAverage(average);
    }

    /**
     * 학생의 등급을 결정합니다.
     *
     * 평균 점수에 따라 등급을 다음과 같이 지정합니다:
     * - 90점 이상: A
     * - 80점 이상: B
     * - 70점 이상: C
     * - 60점 이상: D
     * - 60점 미만: F
     *
     * @param studentVO 등급을 계산할 학생 객체
     */
    @Override
    public void grade(StudentVO studentVO){
        String grade;
        if (studentVO.getAverage() >= 90) {
            grade = "A";
        } else if (studentVO.getAverage() >= 80) {
            grade = "B";
        } else if (studentVO.getAverage() >= 70) {
            grade = "C";
        } else if (studentVO.getAverage() >= 60) {
            grade = "D";
        } else {
            grade = "F";
        }
        studentVO.setGrade(grade);
    }
}