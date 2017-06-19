/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.nure.rumiantsev.SummaryTask4.BusinessLogic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.nure.rumiantsev.SummaryTask4.Constant.Role;
import ua.nure.rumiantsev.SummaryTask4.DAO.CourseDAO;
import ua.nure.rumiantsev.SummaryTask4.DAO.JournalDAO;
import ua.nure.rumiantsev.SummaryTask4.DAO.StudentDAO;
import ua.nure.rumiantsev.SummaryTask4.DAO.TeacherDAO;
import ua.nure.rumiantsev.SummaryTask4.DAO.UserDAO;
import ua.nure.rumiantsev.SummaryTask4.Entity.Course;
import ua.nure.rumiantsev.SummaryTask4.Entity.Journal;
import ua.nure.rumiantsev.SummaryTask4.Entity.Student;
import ua.nure.rumiantsev.SummaryTask4.Entity.Teacher;
import ua.nure.rumiantsev.SummaryTask4.Entity.User;
import ua.nure.rumiantsev.SummaryTask4.Utils.AsyncThreadExecutor;
import ua.nure.rumiantsev.SummaryTask4.Utils.CourseComparatorFactory;

/**
 * <p>
 * Class for main logic of application</p>
 *
 * @author Anton Rumiantsev <Anton.Rumiantsev at tohich-hunter.pleasecome.in>
 */
@Service
public class BLO {

    private CourseDAO courseDAO;
    private TeacherDAO teacherDAO;
    private StudentDAO studentDAO;
    private JournalDAO journalDAO;
    private UserDAO userDAO;
    private BCryptPasswordEncoder passwordEncoder;
    private static final Logger LOGGER = Logger.getLogger(BLO.class);

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setCourseDAO(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    @Autowired
    public void setTeacherDAO(TeacherDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }

    @Autowired
    public void setStudentDAO(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Autowired
    public void setJournalDAO(JournalDAO journalDAO) {
        this.journalDAO = journalDAO;
    }

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Returns string formatted list of courses
     *
     * @param sort type of sorting
     * @param role role of user
     * @param id identifier of user
     * @return JSON course
     */
    public String getCourses(String sort, String role, long id) {
        if (role == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        String s = "";
        List<Course> sc = null;
        switch (role) {
            case "student":
                sc = (List) getStudentCourses(id, sort);
                break;
            case "teacher":
                sc = (List) getTeacherCourses(id);
                Collections.sort(sc, CourseComparatorFactory.getComparator(sort));
                break;
            default:
                sc = (List) getCourses();
        }
        
        try {
            s = mapper.writeValueAsString(sc);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return s;
    }

    /**
     * Returns list of all courses
     *
     * @param sort type of sorting
     * @return JSON with courses list
     */
    public String getCourses(String sort) {
        ObjectMapper mapper = new ObjectMapper();
        String s = "";
        List<Course> sc = (List) getCourses();

        Collections.sort(sc, CourseComparatorFactory.getComparator(sort));
        try {
            s = mapper.writeValueAsString(sc);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return s;
    }

    /**
     * Returns all of courses
     *
     * @return collection of
     * {@link ua.nure.rumiantsev.SummaryTask4.Entity.Course Courses}
     */
    public Collection getCourses() {
        Collection<Course> sc = null;
        AsyncThreadExecutor<Collection<Course>> ate = new AsyncThreadExecutor<Collection<Course>>() {
            @Override
            public Collection<Course> task() {
                return courseDAO.readAll();
            }
        };
        try {
            sc = ate.execute();
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return sc;
    }

    /**
     * Returns all courses, leaded by certain teacher
     *
     * @param id identifier of teacher
     * @return collection of
     * {@link ua.nure.rumiantsev.SummaryTask4.Entity.Course Courses}
     */
    public Collection getTeacherCourses(final long id) {
        Collection<Course> sc = null;
        AsyncThreadExecutor<Collection<Course>> ate = new AsyncThreadExecutor<Collection<Course>>() {
            @Override
            public Collection<Course> task() {
                return courseDAO.readByTeacherId(id);
            }
        };
        try {
            sc = ate.execute();
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return sc;
    }

    /**
     * Returns collection of all courses, where some student is subscribed
     *
     * @param id identidier of certain student
     * @return collection of
     * {@link ua.nure.rumiantsev.SummaryTask4.Entity.Course Courses}
     */
    public Collection getStudentCourses(final long id, final String sort) {
        Collection<Course> sc = null;
        AsyncThreadExecutor<Collection<Course>> ate = new AsyncThreadExecutor<Collection<Course>>() {
            @Override
            public Collection<Course> task() {
                switch(sort){
                    case "on":return courseDAO.readByStudentIdOncoming(id);
                    
                    case "fin":return courseDAO.readByStudentIdFinished(id);
                    
                    case "in" :
                            
                    default : return courseDAO.readByStudentIdInProgress(id);
                }
            }
        };
        try {
            sc = ate.execute();
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return sc;
    }

    /**
     * Sets updated by admin courses to DB
     *
     * @param json JSON list of courses
     */
    public void setCourses(String json) {
        if (json != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                final List<Course> courses = objectMapper.readValue(json, new TypeReference<List<Course>>() {
                });
                AsyncThreadExecutor<Void> ate = new AsyncThreadExecutor<Void>() {
                    @Override
                    public Void task() {
                        for (Course c : courses) {
                            courseDAO.update(c);
                        }
                        return null;
                    }
                };
                ate.execute();
            } catch (IOException | InterruptedException | ExecutionException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }
    }

    /**
     * Adds new course, created by admin to DB
     *
     * @param json JSON with new course instance
     * @return instance of course with generated keys
     */
    public Course addCourse(final String json) {
        final ObjectMapper objectMapper = new ObjectMapper();

        AsyncThreadExecutor<Course> ate = new AsyncThreadExecutor<Course>() {
            @Override
            public Course task() {
                Course course = null;
                try {
                    course = objectMapper.readValue(json, Course.class);
                } catch (IOException ex) {
                    LOGGER.error(ex.getMessage(), ex);
                }

                return courseDAO.create(course);
            }
        };
        try {
            return ate.execute();
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return null;

    }

    /**
     * Returns all teachers in JSON
     *
     * @return JSON with all teachers
     */
    public String getTeachers() {

        ObjectMapper mapper = new ObjectMapper();
        String s = "";
        List<Teacher> sc = (List) getTeachers("");
        try {
            s = mapper.writeValueAsString(sc);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return s;
    }

    /**
     * Returns collection of all teachers
     *
     * @param arg
     * @return {@link ua.nure.rumiantsev.SummaryTask4.Entity.Teacher Teacher}
     */
    public Collection getTeachers(String... arg) {
        Collection<Teacher> sc = null;
        AsyncThreadExecutor<Collection<Teacher>> ate = new AsyncThreadExecutor<Collection<Teacher>>() {
            @Override
            public Collection<Teacher> task() {
                return teacherDAO.readAll();
            }
        };
        try {
            sc = ate.execute();
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return sc;
    }

    /**
     * Sets teachers info, updated by admin to DB
     *
     * @param json JSON with teachers info
     */
    public void setTeachers(String json) {
        if (json != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                final List<Teacher> teachers = objectMapper.readValue(json, new TypeReference<List<Teacher>>() {
                });
                AsyncThreadExecutor<Void> ate = new AsyncThreadExecutor<Void>() {
                    @Override
                    public Void task() {
                        for (Teacher c : teachers) {
                            teacherDAO.update(c);
                        }
                        return null;
                    }
                };
                ate.execute();
            } catch (IOException | InterruptedException | ExecutionException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }
    }

    /**
     * Adds new instance of
     * {@link ua.nure.rumiantsev.SummaryTask4.Entity.Teacher Teacher} to DB
     *
     * @param json JSON with teacher info
     * @return instance of teacher with generated keys
     */
    public Teacher addTeacher(final String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        Teacher teacher = null;
        try {
            teacher = objectMapper.readValue(json, Teacher.class);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        final Teacher teacher1 = teacher;
        teacher1.setPassword(
                passwordEncoder.encode(teacher1.getPassword()));
        teacher1.setRole(Role.TEACHER.getValue());
        AsyncThreadExecutor<Teacher> ate = new AsyncThreadExecutor<Teacher>() {
            @Override
            public Teacher task() {
                return teacherDAO.create(teacher1);
            }
        };
        try {
            return ate.execute();
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return null;

    }

    /**
     * Adds new registered student's info to DB
     *
     * @param request HTTP request with student's parameters
     * @return instance of student with generated keys
     */
    public Student addStudent(HttpServletRequest request) {
        final Student student = new Student();
        student.setFirstName(request.getParameter("firstName"));
        student.setLastName(request.getParameter("lastName"));
        student.setLogin(request.getParameter("login"));
        student.setPassword(
                passwordEncoder.encode(request.getParameter("password")));
        student.setRole(Role.STUDENT.getValue());
        AsyncThreadExecutor<Student> ate = new AsyncThreadExecutor<Student>() {
            @Override
            public Student task() {
                return studentDAO.create(student);
            }
        };
        try {
            return ate.execute();
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return null;

    }

    /**
     * Puts student to DB list of listeners of some course
     *
     * @param courseId DB identifier of course
     * @param studentId DB identifier of student
     */
    public void subscribeStudent(long courseId, long studentId) {
        final Journal journal = new Journal();
        Student s = new Student();
        s.setId(studentId);
        Set<Student> set = new HashSet<>();
        set.add(s);
        journal.setId(courseId);
        journal.setStudents(set);
        AsyncThreadExecutor<Void> ate = new AsyncThreadExecutor<Void>() {
            @Override
            public Void task() {
                journalDAO.create(journal);
                return null;
            }
        };
        try {
            ate.execute();
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    /**
     * Returns journal of some course to the teacher
     *
     * @param courseId DB identifier of course
     * @return instance of
     * {@link ua.nure.rumiantsev.SummaryTask4.Entity.Journal Journal}
     */
    public Journal getJournal(final long courseId) {
        Journal journal = null;
        AsyncThreadExecutor<Journal> ate = new AsyncThreadExecutor<Journal>() {
            @Override
            public Journal task() {
                return journalDAO.read(courseId);
            }
        };
        try {
            journal = ate.execute();
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        LOGGER.debug(journal);
        return journal;
    }

    /**
     * Sets updated by teacher journal with marks to DB
     *
     * @param request HTTP request with journal parameters
     * @return instance of journal
     */
    public Journal setJournal(final HttpServletRequest request) {

        AsyncThreadExecutor<Journal> ate = new AsyncThreadExecutor<Journal>() {
            @Override
            public Journal task() {
                Journal journal = (Journal) request.getSession().getAttribute("journal");
                journal.setId(Long.valueOf(request.getParameter("id")));
                LOGGER.debug(journal);
                Map<Long, Integer> points = journal.getPoints();
                for (Student l : journal.getStudents()) {
                    points.put(l.getId(),
                            Integer.valueOf(request.getParameter("" + l.getId())));
                }
                journal.setPoints(points);
                journalDAO.update(journal);
                return journal;
            }
        };
        try {
            return ate.execute();
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.error(ex.getMessage(), ex);
            return null;
        }

    }

    /**
     * Updates user info in DB
     *
     * @param request HTTP request with new student info
     * @return instance of
     * {@link ua.nure.rumiantsev.SummaryTask4.Entity.User User}
     */
    public User updateUser(final HttpServletRequest request) {
        AsyncThreadExecutor<User> ate = new AsyncThreadExecutor<User>() {
            @Override
            public User task() {
                User user = new User();
                user.setPassword(passwordEncoder.encode(request.getParameter("password")));
                user.setId((long) request.getSession().getAttribute("userId"));
                user.setFirstName(request.getParameter("firstName"));
                user.setLastName(request.getParameter("lastName"));
                user.setLogin(request.getParameter("login"));

                userDAO.update(user);
                return user;
            }
        };
        try {
            return ate.execute();
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.error(ex.getMessage(), ex);
            return null;
        }

    }

    /**
     * Sets language, selected by user as default to his session
     *
     * @param request HTP request with language parameter
     */
    public void setLanguage(HttpServletRequest request) {
        String locale = request.getParameter("lang");
        if (locale.equals("")) {
            locale = "en";
        }
        Locale.setDefault(new Locale(locale.toLowerCase()));
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resources", Locale.getDefault());
        request.getSession().setAttribute("resourceBundle", resourceBundle);
    }
    /**
     * Changes student's role to "blocked"
     * @param id student's id
     */
    public void blockStudent(final long id){
        AsyncThreadExecutor<Void> ate = new AsyncThreadExecutor<Void>() {
            @Override
            public Void task() {
                userDAO.block(id);
                return null;
            }
        };
        try {
            ate.execute();
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
    /**
     * Removes students role "blocked" and replaces it with "student"
     * @param id students identifier
     */
     public void unblockStudent(final long id){
        AsyncThreadExecutor<Void> ate = new AsyncThreadExecutor<Void>() {
            @Override
            public Void task() {
                userDAO.unblock(id);
                return null;
            }
        };
        try {
            ate.execute();
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
     /**
      * Returns collection of all students with their roles
      * @return collection of Users
      */
     public Collection<User> getStudents(){
          AsyncThreadExecutor<Collection<User>> ate = new AsyncThreadExecutor<Collection<User>>() {
            @Override
            public Collection<User> task() {
                return userDAO.readAll();
            }
        };
        try {
            return ate.execute();
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.error(ex.getMessage(), ex);
            return null;
        }
     }
     
     /**
      * Returns courses of certain teachers
      * @param id teacher's id
      * @return collection of students
      */
     public Collection<Student> getTeachersStudents(long id){
         
                return studentDAO.readByTeacherId(id);
          
     }
     
     /*
     Removes course certain form DB
     */
    public void removeCourse(long id){
         
                courseDAO.delete(id);
          
     }

}
