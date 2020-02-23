package com.feelfreetocode.findbestinstitute.constants;

public class API {
    public static final String ACCESS_KEY_ID = "AKIAJHJNZCOERWIT7P2A";
    public static final String INSTITUTE_DOMAIN = "FIND_BEST_CLASSES_INTITUTE";
    public static final String COURSE_DOMAIN = "FIND_BEST_CLASSES_COURSES";
    public static final String ADMISSSION_DOMAIN = "FIND_BEST_CLASSES_ADMISSION";
    public static final String RATING_DOMAIN = "FIND_BEST_CLASSES_RATING";
    public static final String ENQUIRY_DOMAIN = "FIND_BEST_CLASSES_ENQUIRY";
    public static final String STUDENT_DOMAIN = "FIND_BEST_CLASSES_STUDENTS";
    public static final String SECRET_KEY = "FohEoxeNMj9t3KzYFKc+Es0CiYhqga6rH2WycpW5";


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
