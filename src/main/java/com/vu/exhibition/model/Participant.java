package com.vu.exhibition.model;

public class Participant {
    private String registrationID;
    private String studentName;
    private String faculty;
    private String projectTitle;
    private String contactNumber;
    private String emailAddress;
    private String projectImagePath;

    // Constructors
    public Participant() {
    }

    public Participant(String registrationID, String studentName, String faculty, String projectTitle, String contactNumber, String emailAddress, String projectImagePath) {
        this.registrationID = registrationID;
        this.studentName = studentName;
        this.faculty = faculty;
        this.projectTitle = projectTitle;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.projectImagePath = projectImagePath;
    }

    // Getters and Setters
    public String getRegistrationID() { return registrationID; }
    public void setRegistrationID(String registrationID) { this.registrationID = registrationID; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getFaculty() { return faculty; }
    public void setFaculty(String faculty) { this.faculty = faculty; }

    public String getProjectTitle() { return projectTitle; }
    public void setProjectTitle(String projectTitle) { this.projectTitle = projectTitle; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public String getProjectImagePath() { return projectImagePath; }
    public void setProjectImagePath(String projectImagePath) { this.projectImagePath = projectImagePath; }

    @Override
    public String toString() {
        return "Participant{" +
               "registrationID='" + registrationID + '\'' +
               ", studentName='" + studentName + '\'' +
               ", faculty='" + faculty + '\'' +
               ", projectTitle='" + projectTitle + '\'' +
               ", contactNumber='" + contactNumber + '\'' +
               ", emailAddress='" + emailAddress + '\'' +
               ", projectImagePath='" + projectImagePath + '\'' +
               '}';
    }
}
