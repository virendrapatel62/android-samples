package com.feelfreetocode.findbestinstituteSQL.constants;

public class API {

    public static final String INSTITUTE_REGISTRATION ="http://192.168.43.73:8080/FindBestInstitute/register_institute";
    public static final String INSTITUTE_LOGIN="http://192.168.43.73:8080/FindBestInstitute/login_institute";
    public static final String SAVE_COURSE="http://192.168.43.73:8080/FindBestInstitute/save_course";
    public static final String GET_COURSES="http://192.168.43.73:8080/FindBestInstitute/get_courses";
    public static final String GET_COURSES_BY_INSTITUTE="http://192.168.43.73:8080/FindBestInstitute/get_courses_by_institute_id";
    public static final String REGISTER_STUDENT="http://192.168.43.73:8080/FindBestInstitute/register_student";
    public static final String STUDENT_LOGIN="http://192.168.43.73:8080/FindBestInstitute/student_login";
    public static final String SAVE_ENQUIRY="http://192.168.43.73:8080/FindBestInstitute/save_enquiry";
    public static final String GET_ENQUIRY="http://192.168.43.73:8080/FindBestInstitute/get_enquiry";
    public static final String REMOVE_ENQUIRY="http://192.168.43.73:8080/FindBestInstitute/remove_enquiry";
    public static final String MARK_ADMITTED="http://192.168.43.73:8080/FindBestInstitute/mark_admitted";
    public static final String GET_ADMITTED_STUDNET="http://192.168.43.73:8080/FindBestInstitute/get_admitted_students";
    public static final String GET_COURSES_OF_STUDNET="http://192.168.43.73:8080/FindBestInstitute/get_courses_of_student";
    public static final String RATE="http://192.168.43.73:8080/FindBestInstitute/rate";
    public static final String GET_COURSE_RATE="http://192.168.43.73:8080/FindBestInstitute/get_course_rating";
    public static final String GET_STUDENT_RATING="http://192.168.43.73:8080/FindBestInstitute/get_student_rating";
    public static final String SEARCH_COURSE="http://192.168.43.73:8080/FindBestInstitute/search_course";



    public static final String ACCESS_KEY_ID = "AKIAJ4JV2H4KO5YZ662A";
    public static final String INSTITUTE_DOMAIN = "FIND_BEST_CLASSES_INTITUTE";
    public static final String COURSE_DOMAIN = "FIND_BEST_CLASSES_COURSES";
    public static final String ADMISSSION_DOMAIN = "FIND_BEST_CLASSES_ADMISSION";
    public static final String RATING_DOMAIN = "FIND_BEST_CLASSES_RATING";
    public static final String ENQUIRY_DOMAIN = "FIND_BEST_CLASSES_ENQUIRY";
    public static final String STUDENT_DOMAIN = "FIND_BEST_CLASSES_STUDENTS";
    public static final String SECRET_KEY = "ywbPS48Dj1xxWCWVjEQPjT7tjEhrTa0Q+uIB0x+4";


    public static class InstituteTableColumns{
        public static final String INSTITUTE_NAME = "institutename";
        public static final String ADDRESS = "address";
        public static final String ADMIN_NAME= "adminName";
        public static final String CITY = "city";
        public static final String STATE= "state";
        public static final String CONTACT= "contact";
        public static final String EMAIL = "email";
        public static final String LAT = "lat";
        public static final String LANG = "lang";
        public static final String PASSWORD = "password";
    }

    public static class CourseColumns{
        public static final String COURSE_NAME = "CourseName";
        public static final String INSTITUTE_ID = "instituteId";
        public static final String COURSE_FEE = "CourseFee";
    }

    public static class EnquiryColumns{
        public static final String STUDENT_ID = "student_id";
        public static final String COURSE_ID = "course_id";
        public static final String INSTITUTE_ID = "institute_id";
    }

    public static class AdmissionColumns{
        public static final String STUDENT_ID = "student_id";
        public static final String COURSE_ID = "course_id";
        public static final String INSTITUTE_ID = "institute_id";
    }

    public static class RatingColumns{
        public static final String STUDENT_ID = "student_id";
        public static final String COURSE_ID = "course_id";
        public static final String RATING = "rating";
    }

    public static class StudentColumns{
        public static final String STUDENT_NAME = "studentName";
        public static final String STUDENT_ID = "studentId";
        public static final String STUDENT_EMAIL = "email";
        public static final String STUDENT_ADDRESS = "address";
        public static final String STUDENT_LAT = "lat";
        public static final String STUDENT_LANG = "lang";
        public static final String STUDENT_CONTACT = "contact";
        public static final String STUDENT_PASSOWRD = "password";
        public static final String STUDENT_STATE = "state";
        public static final String STUDENT_CITY = "city";
    }
}
