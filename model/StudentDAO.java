package model;

import util.DBUtil;
import vo.StudentVO;

import java.sql.*;
import java.util.ArrayList;

public class StudentDAO implements Student {
    private static StudentDAO dao;

    private ArrayList<StudentVO> studentlist;

    private StudentDAO() {}

    public static StudentDAO getInstance() {
        if (dao == null) dao = new StudentDAO();

        return dao;
    }

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private CallableStatement cs = null;


    private void disConnect() {
        if (rs != null) try {
                rs.close();
        } catch (SQLException e) {
        }

        if (pstmt != null) try {
            pstmt.close();
        } catch (SQLException e) {
        }

        if (conn != null) try {
            conn.close();
        } catch (SQLException e) {
        }
    }




    @Override
    public void update(StudentVO newStudent)  {
        try {

            conn = DBUtil.getConnection();
            cs = conn.prepareCall("{call STUDENT_UPDATE(?,?,?,?,?,?,?)}");

            cs.setString(1, newStudent.getSno());
            cs.setString(2, newStudent.getName());
            cs.setInt(3, newStudent.getKorean());
            cs.setInt(4, newStudent.getEnglish());
            cs.setInt(5, newStudent.getMath());
            cs.setInt(6, newStudent.getScience());
            cs.execute();
            int rtn = cs.getInt(2);
            String resultString = null;
            if (rtn == 100) {
                resultString = "수정 실패";
                System.out.println(resultString);
            } else {
                resultString = "수정 성공";
                System.out.println(resultString);
            }

            if (cs != null) cs.close();
            if (conn != null) cs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            disConnect();
        }
    }

    @Override
    public void delete(String deleteNum) {
        try {

            conn = DBUtil.getConnection();
            cs = conn.prepareCall("{call STUDENT_DELETE(?,?)}");

            cs.setString(1, deleteNum);
            cs.registerOutParameter(2, Types.INTEGER);
            cs.execute();

            int rtn = cs.getInt(2);
            String resultString = null;
            if (rtn == 100) {
                resultString = "회원 정보 없음";
                System.out.println(resultString);
            } else {
                resultString = "삭제 성공";
                System.out.println(resultString);
            }

            if (cs != null) cs.close();
            if (conn != null) conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            disConnect();
        }

    }

    @Override
    public void totalSearch() {

        System.out.println("학번 이름 ");
        try {
            StudentVO studentVO = new StudentVO();
            studentlist = new ArrayList<>();
            conn = DBUtil.getConnection();
            String sql = " select * from student ";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                studentVO.setSno(rs.getString("sno"));
                studentVO.setName(rs.getString("name"));
                studentVO.setKorean(rs.getInt("korean"));
                studentVO.setEnglish(rs.getInt("english"));
                studentVO.setMath(rs.getInt("math"));
                studentVO.setScience(rs.getInt("science"));

                // 총점과 평균 계산
                int total = studentVO.getKorean() + studentVO.getEnglish() + studentVO.getMath() + studentVO.getScience();
                float average = total / 4.0f;
                studentVO.setTotal(total);
                studentVO.setAverage(average);

                // 등급 설정
                String grade;
                if (average >= 90) {
                    grade = "A";
                } else if (average >= 80) {
                    grade = "B";
                } else if (average >= 70) {
                    grade = "C";
                } else if (average >= 60) {
                    grade = "D";
                } else {
                    grade = "F";
                }
                studentVO.setGrade(grade);
                studentlist.add(studentVO);
                System.out.println(studentVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            disConnect();
        }
    }

    @Override
    public void search(String searchNum) {
        try {
            studentlist = new ArrayList<>();
            conn = DBUtil.getConnection();
            String sql = " select * from student where sno = ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, searchNum);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                StudentVO studentVO = new StudentVO();
                studentVO.setSno(rs.getString("sno"));
                studentVO.setName(rs.getString("name"));
                studentVO.setKorean(rs.getInt("korean"));
                studentVO.setEnglish(rs.getInt("english"));
                studentVO.setMath(rs.getInt("math"));
                studentVO.setScience(rs.getInt("science"));

                studentlist.add(studentVO);
                System.out.println(studentVO);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }



    @Override
    public void sort(int sortNum) {
        try {
            conn = DBUtil.getConnection();
            String sql = "";

            switch (sortNum) {
                case 1:
                    sql = " select * from student order by sno ";
                    break;
                case 2:
                    sql = " select * from student order by name "; // 이름순
                    break;
                case 3:
                    sql = " select * from student order by (korean+english+math+science)"; // 총점순
                    break;
                default:
                    System.out.println("잘못눌렀습니다.");
                    return;
            }
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int sno = rs.getInt("sno");
                String name = rs.getString("name");
                int korean = rs.getInt("korean");
                int english = rs.getInt("english");
                int math = rs.getInt("math");
                int science = rs.getInt("science");
                int total = korean + english + math + science;

                System.out.println("학번: " + sno + ", 이름: " + name + ", 총점: " + total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            disConnect();
        }
    }

    @Override
    public void total() {
        try{
            studentlist = new ArrayList<>();
            conn = DBUtil.getConnection();
            String sql = " select * from student ";

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                // StudentVO 객체에 데이터 저장
                StudentVO studentVO = new StudentVO();
                studentVO.setSno(rs.getString("sno"));  // 학번
                studentVO.setName(rs.getString("name"));  // 이름
                studentVO.setKorean(rs.getInt("korean"));  // 국어 점수
                studentVO.setEnglish(rs.getInt("english"));  // 영어 점수
                studentVO.setMath(rs.getInt("math"));  // 수학 점수
                studentVO.setScience(rs.getInt("science"));  // 과학 점수

                int total = studentVO.getKorean() + studentVO.getEnglish() + studentVO.getMath() + studentVO.getScience();
                studentVO.setTotal(total);
                System.out.println(studentVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            disConnect();
        }
    }



    @Override
    public void avg() {


        try {
            conn = DBUtil.getConnection();
            String sql = " select * from student ";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();


            while (rs.next()) {
                StudentVO studentVOavg = new StudentVO();
                String sno = rs.getString("sno");
                String name = rs.getString("name");
                int korean = rs.getInt("korean");
                int english = rs.getInt("english");
                int math = rs.getInt("math");
                int science = rs.getInt("science");
                float avg = (korean + english + math + science) / 4.0f;
                studentVOavg.setAverage(avg);
                System.out.println("학번: " + sno + ", 이름: " + name + ", 국어: " + korean + ", 영어: " + english + ", 수학: " + math + ", " +
                        "과학: " + science + ", 평균: " + avg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
    }


    @Override
    public void grade() {

        try {
            conn = DBUtil.getConnection();
            String sql = " select * from student ";

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String sno = rs.getString("sno");
                String name = rs.getString("name");
                int korean = rs.getInt("korean");
                int english = rs.getInt("english");
                int math = rs.getInt("math");
                int science = rs.getInt("science");

                float avarage = (korean + english + math + science) / 4;
                String grade;

                if (avarage >= 90) {
                    grade = "A";
                } else if (avarage >= 80) {
                    grade = "B";
                } else if (avarage >= 70) {
                    grade = "C";
                } else if (avarage >= 60) {
                    grade = "D";
                } else {
                    grade = "F";
                }
                System.out.println("학번: " + sno + ", 이름: " + name + ", 등급: " + grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disConnect();
        }
    }



    @Override
    public void input(StudentVO newStudent)  {
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
            boolean flag = cs.execute();


            int rtn = cs.getInt(7);
            String resultString = null;
            if (rtn == 100) {
                resultString = "입력실패";
                System.out.println(resultString);
            } else {
                resultString = "입력성공";
                System.out.println(resultString);
            }

            if (cs != null) cs.close();
            if (conn != null) conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            disConnect();
        }

    }

    public static void main(String[] args) {
        getInstance().totalSearch();
    }


}

