-- Step 1: Create the database
CREATE DATABASE StudentDB;

-- Step 2: Switch to the new database
USE StudentDB;

-- Enable the 'sa' account if it is disabled
ALTER LOGIN sa ENABLE;

-- Set a new password for the 'sa' account
ALTER LOGIN sa WITH PASSWORD = 'my-pass';


-- Step 3: Create Courses table
CREATE TABLE Courses (
    courseId INT IDENTITY(1,1) PRIMARY KEY,
    courseName VARCHAR(100) NOT NULL,
    department VARCHAR(50) NOT NULL,
    credits INT NOT NULL,
    description TEXT
);

-- Step 4: Create Students table
CREATE TABLE Students (
    studentId INT IDENTITY(1,1) PRIMARY KEY,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    dateOfBirth DATE NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address VARCHAR(200),
    courseId INT NOT NULL,
    enrollmentDate DATE NOT NULL,
    FOREIGN KEY (courseId) REFERENCES Courses(courseId)
);

-- Step 5: Insert sample courses
INSERT INTO Courses (courseName, department, credits, description) VALUES 
('Introduction to Computer Science', 'Computer Science', 3, 'A foundation course covering basic computer science concepts'),
('Database Management Systems', 'Computer Science', 4, 'Study of database design, implementation, and management'),
('Calculus I', 'Mathematics', 4, 'Introduction to differentiation and integration of functions'),
('Physics 101', 'Physics', 4, 'Introduction to classical mechanics and thermodynamics'),
('English Composition', 'English', 3, 'Development of writing skills for academic and professional contexts');

-- Step 6: Insert sample students
INSERT INTO Students (firstName, lastName, gender, dateOfBirth, email, phone, address, courseId, enrollmentDate) VALUES
('John', 'Doe', 'Male', '2000-05-15', 'john.doe@example.com', '555-123-4567', '123 College Ave', 1, '2023-09-01'),
('Jane', 'Smith', 'Female', '2001-08-22', 'jane.smith@example.com', '555-987-6543', '456 University Blvd', 2, '2023-09-01'),
('Michael', 'Johnson', 'Male', '2000-03-10', 'michael.j@example.com', '555-456-7890', '789 Campus Dr', 3, '2023-09-01'),
('Emily', 'Williams', 'Female', '2002-11-05', 'emily.w@example.com', '555-789-0123', '321 Education St', 4, '2023-09-01'),
('David', 'Brown', 'Male', '2001-06-30', 'david.b@example.com', '555-234-5678', '654 Learning Ln', 5, '2023-09-01');

SELECT * FROM Students;
SELECT * FROM Courses;

-- Create Grades Table
CREATE TABLE grades (
    id INT PRIMARY KEY IDENTITY(1,1),
    studentId INT NOT NULL,
    courseId INT NOT NULL,
    grade VARCHAR(5) NOT NULL,
    semester VARCHAR(50) NOT NULL,
    academic_year INT NOT NULL,
    FOREIGN KEY (studentId) REFERENCES Students(studentId) ON DELETE CASCADE,
    FOREIGN KEY (courseId) REFERENCES Courses(courseId) ON DELETE CASCADE
);

INSERT INTO grades (studentId, courseId, grade, semester, academic_year) VALUES
(1, 1, 'A', 'Fall', 2023),
(2, 2, 'B+', 'Fall', 2023),
(3, 3, 'A-', 'Fall', 2023),
(4, 4, 'B', 'Fall', 2023),
(5, 5, 'A', 'Fall', 2023);

SELECT * FROM grades;

SELECT COLUMN_NAME
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'students';

SELECT COLUMN_NAME
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'courses';

