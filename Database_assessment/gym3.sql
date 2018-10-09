CREATE SCHEMA Gym;
CREATE TABLE Gym.Member (MembershipID INT NOT NULL PRIMARY KEY, Status VARCHAR(8), fname VARCHAR(30), lname VARCHAR(30), Address VARCHAR(150), PhoneNumber INT);
CREATE TABLE Gym.Facility (FacilityType VARCHAR(100) NOT NULL PRIMARY KEY, HourlyCost INT);
CREATE TABLE Gym.Instructor (InstructorID INT NOT NULL PRIMARY KEY, fname VARCHAR(30), lname VARCHAR(30), Email VARCHAR(60));
CREATE TABLE Gym.Course (CourseID INT, CourseName VARCHAR(30) PRIMARY KEY NOT NULL, Cost NUMERIC(5,2), MaxPlaces INT, TimeSlot NUMERIC(4), InstructorID INT CONSTRAINT Course_InstructorID REFERENCES Gym.Instructor(InstructorID), Weekday VARCHAR(10));
CREATE TABLE Gym.InstructorQualifications (InstructorID INT CONSTRAINT InstructorQualifications_InstructorID REFERENCES Gym.Instructor(InstructorID), Qualification VARCHAR(50));
CREATE TABLE Gym.MemberCourse(MembershipID INT CONSTRAINT MemberCourse_MembershipID REFERENCES Gym.Member(MembershipID), BookingNumber INT CHECK (BookingNumber <=40), Course VARCHAR(30) CONSTRAINT MemberCourse_Course REFERENCES Gym.Course(courseName));
CREATE TABLE Gym.MemberFacility (MembershipID INT CONSTRAINT MemberFacility_MembershipID REFERENCES Gym.Member(MembershipID), FacilityType VARCHAR(100) CONSTRAINT MemberFacility_FacilityType  REFERENCES Gym.Facility(FacilityType ), DayLog DATE, Activity VARCHAR(30));
