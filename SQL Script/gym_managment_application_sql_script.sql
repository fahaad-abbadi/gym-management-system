CREATE DATABASE gym_management_application;
USE gym_management_application;

-- members table
CREATE TABLE Member(
	member_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100) UNIQUE,
    phone_number VARCHAR(15),
    date_of_birth DATE,
    gender ENUM('Male', 'Female'),
    join_date DATE,
    address VARCHAR(25),
    city VARCHAR(50),
    state VARCHAR(50),
    postal_code VARCHAR(10),
    emergency_contact_name VARCHAR(100),
    emergency_contact_phone VARCHAR(15)
);

-- trainers table
CREATE TABLE Trainer(
	trainer_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100) UNIQUE,
    phone_number VARCHAR(15), 
    hire_date DATE, 
    speciality VARCHAR(100)
);

-- membership table
CREATE TABLE Membership(
	membership_id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT, 
    start_date DATE,
    end_date DATE,
    price DECIMAL(8,2),
    payment_status ENUM('Paid', 'Due'),
    FOREIGN KEY (member_id) REFERENCES Member(member_id)
);

CREATE TABLE Workout_Plan(
	plan_id INT AUTO_INCREMENT PRIMARY KEY,
    workout_name VARCHAR(100),
    description TEXT,
    start_date DATE,
    end_date DATE,
    
	member_id INT,
    FOREIGN KEY (member_id) REFERENCES Member(member_id),
    
    trainer_id INT,
    FOREIGN KEY (trainer_id) REFERENCES Trainer(trainer_id)
);

CREATE TABLE workout_session(
	session_id INT AUTO_INCREMENT PRIMARY KEY,
    session_date DATE,
    duration_minutes INT,
    calories_burned INT,
    feedback VARCHAR(255),
    
	member_id INT,
    FOREIGN KEY (member_id) REFERENCES Member(member_id),
    
    workout_id INT,
    FOREIGN KEY (workout_id) REFERENCES Workout_Plan(plan_id)
);

CREATE TABLE Trainer_Schedule (
    schedule_id INT AUTO_INCREMENT PRIMARY KEY,

    session_date DATETIME,
    session_duration INT,
    session_type ENUM('PersonalTraining', 'GroupClass'),
    
	trainer_id INT,
    FOREIGN KEY (trainer_id) REFERENCES Trainer(trainer_id),
    
    member_id INT,
    FOREIGN KEY (member_id) REFERENCES Member(member_id)
);

CREATE TABLE Equipment (
    equipment_id INT AUTO_INCREMENT PRIMARY KEY,
    equipment_name VARCHAR(100)
);

CREATE TABLE Class (
    class_id INT AUTO_INCREMENT PRIMARY KEY,
    class_name VARCHAR(100),
    trainer_id INT,
    class_type ENUM('Cardio', 'StrengthTraining', 'HIIT'),
    schedule_time DATETIME,
    duration_minutes INT,
    max_capacity INT,
    FOREIGN KEY (trainer_id) REFERENCES Trainer(trainer_id)
);

CREATE TABLE Class_Enrollment (
    enrollment_id INT AUTO_INCREMENT PRIMARY KEY,
    class_id INT,
    member_id INT,
    enrollment_date DATE,
    FOREIGN KEY (class_id) REFERENCES Class(class_id),
    FOREIGN KEY (member_id) REFERENCES Member(member_id)
);

CREATE TABLE Attendance (
    attendance_id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT,
    class_id INT,
    attendance_date DATE,
    attended BOOLEAN,
    FOREIGN KEY (member_id) REFERENCES Member(member_id),
    FOREIGN KEY (class_id) REFERENCES Class(class_id)
);

CREATE TABLE Diet_Plan (
    diet_plan_id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT,
    trainer_id INT,
    plan_name VARCHAR(100),
    description TEXT,
    start_date DATE,
    end_date DATE,
    FOREIGN KEY (member_id) REFERENCES Member(member_id),
    FOREIGN KEY (trainer_id) REFERENCES Trainer(trainer_id)
);

CREATE TABLE Feedback (
    feedback_id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT,
    trainer_id INT,
    feedback_text TEXT,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    feedback_date DATE,
    FOREIGN KEY (member_id) REFERENCES Member(member_id),
    FOREIGN KEY (trainer_id) REFERENCES Trainer(trainer_id)
);

CREATE TABLE Staff (
    staff_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    role VARCHAR(100),
    hire_date DATE,
    salary DECIMAL(10, 2)
);

CREATE TABLE Inventory (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    item_name VARCHAR(100),
    quantity INT,
    purchase_price DECIMAL(8, 2),
    selling_price DECIMAL
);

-- Insert data into Members table
INSERT INTO Member (first_name, last_name, email, phone_number, date_of_birth, gender, join_date, address, city, state, postal_code, emergency_contact_name, emergency_contact_phone)
VALUES 
('John', 'Doe', 'johndoe@example.com', '1234567890', '1990-05-15', 'Male', '2023-01-10', '123 Elm St', 'New York', 'NY', '10001', 'Jane Doe', '0987654321'),
('Alice', 'Smith', 'alicesmith@example.com', '9876543210', '1985-03-22', 'Female', '2023-02-11', '456 Oak St', 'Los Angeles', 'CA', '90001', 'Bob Smith', '8765432109'),
('Bob', 'Brown', 'bobbrown@example.com', '5678901234', '1992-07-11', 'Male', '2023-03-12', '789 Pine St', 'Chicago', 'IL', '60601', 'Charlie Brown', '7654321098'),
('Carol', 'Jones', 'caroljones@example.com', '5432109876', '1995-08-08', 'Female', '2023-04-13', '101 Maple St', 'Houston', 'TX', '77001', 'David Jones', '6543210987'),
('Eve', 'Davis', 'evedavis@example.com', '4321098765', '1993-01-20', 'Female', '2023-05-14', '202 Cedar St', 'Phoenix', 'AZ', '85001', 'Frank Davis', '5432109876'),
('Frank', 'Taylor', 'franktaylor@example.com', '3210987654', '1988-10-09', 'Male', '2023-06-15', '303 Birch St', 'Philadelphia', 'PA', '19101', 'Grace Taylor', '4321098765'),
('Grace', 'Wilson', 'gracewilson@example.com', '2109876543', '1991-12-05', 'Female', '2023-07-16', '404 Spruce St', 'San Antonio', 'TX', '78201', 'Henry Wilson', '3210987654'),
('Henry', 'Moore', 'henrymoore@example.com', '1098765432', '1994-09-30', 'Male', '2023-08-17', '505 Redwood St', 'San Diego', 'CA', '92101', 'Ivy Moore', '2109876543'),
('Ivy', 'Taylor', 'ivytaylor@example.com', '0987654321', '1990-11-12', 'Female', '2023-09-18', '606 Willow St', 'Dallas', 'TX', '75201', 'Jack Taylor', '1098765432'),
('Jack', 'Anderson', 'jackanderson@example.com', '8765432109', '1987-06-21', 'Male', '2023-10-19', '707 Cypress St', 'Austin', 'TX', '73301', 'Kim Anderson', '0987654321');

-- Insert data into Trainers table
INSERT INTO Trainer (first_name, last_name, email, phone_number, hire_date, speciality)
VALUES 
('Mike', 'Miller', 'mikemiller@example.com', '1112223333', '2022-05-01', 'Strength Training'),
('Nina', 'Lee', 'ninalee@example.com', '2223334444', '2021-08-15', 'Cardio'),
('Oscar', 'King', 'oscarking@example.com', '3334445555', '2020-12-10', 'HIIT'),
('Paul', 'Green', 'paulgreen@example.com', '4445556666', '2019-09-05', 'Personal Training'),
('Quincy', 'Harris', 'quincyharris@example.com', '5556667777', '2023-03-20', 'Functional Training');

-- Insert data into Memberships table
INSERT INTO Membership (member_id, start_date, end_date, price, payment_status)
VALUES 
(1, '2023-01-10', '2024-01-10', 600.00, 'Paid'),
(2, '2023-02-11', '2024-02-11', 600.00, 'Paid'),
(3, '2023-03-12', '2024-03-12', 600.00, 'Paid'),
(4, '2023-04-13', '2024-04-13', 600.00, 'Due'),
(5, '2023-05-14', '2024-05-14', 600.00, 'Paid'),
(6, '2023-06-15', '2024-06-15', 600.00, 'Paid'),
(7, '2023-07-16', '2024-07-16', 600.00, 'Due'),
(8, '2023-08-17', '2024-08-17', 600.00, 'Paid'),
(9, '2023-09-18', '2024-09-18', 600.00, 'Paid'),
(10, '2023-10-19', '2024-10-19', 600.00, 'Paid');

-- Insert data into Workout_Plans table
INSERT INTO Workout_Plan (member_id, trainer_id, workout_name, description, start_date, end_date)
VALUES 
(1, 1, 'Strength Training Plan', 'Full body strength and muscle building.', '2023-01-15', '2023-12-15'),
(2, 2, 'Endurance Cardio Plan', 'Focus on cardiovascular endurance.', '2023-02-20', '2023-11-20'),
(3, 3, 'HIIT Plan', 'High-Intensity Interval Training for fat loss.', '2023-03-25', '2023-12-25'),
(4, 4, 'Functional Fitness', 'Improving daily movement efficiency.', '2023-04-30', '2024-04-30'),
(5, 5, 'Core Strength Program', 'Building core strength for functional movement.', '2023-05-10', '2024-05-10'),
(6, 1, 'Advanced Strength Training', 'Advanced strength workouts.', '2023-06-12', '2024-06-12'),
(7, 2, 'High-Intensity Cardio', 'Cardio sessions for endurance and fat burn.', '2023-07-15', '2024-07-15'),
(8, 3, 'HIIT Extreme', 'Extreme HIIT for maximum fat loss.', '2023-08-18', '2024-08-18'),
(9, 4, 'Functional Mobility', 'Functional mobility exercises.', '2023-09-20', '2024-09-20'),
(10, 5, 'Core and Strength Combo', 'Combination of core and strength training.', '2023-10-22', '2024-10-22');

-- Insert data into Trainer_Schedules table
INSERT INTO Trainer_Schedule (trainer_id, member_id, session_date, session_duration, session_type)
VALUES
(1, 1, '2023-02-01 10:00:00', 60, 'PersonalTraining'),
(2, 2, '2023-03-05 11:00:00', 45, 'PersonalTraining'),
(3, 3, '2023-04-10 14:00:00', 30, 'PersonalTraining'),
(4, 4, '2023-05-15 09:00:00', 50, 'GroupClass'),
(5, 5, '2023-06-20 08:00:00', 60, 'GroupClass');

-- Insert data into Equipment table
INSERT INTO Equipment (equipment_name)
VALUES
('Treadmill'),
('Dumbbells'),
('Barbell'),
('Kettlebell'),
('Resistance Bands'),
('Stationary Bike'),
('Pull-Up Bar'),
('Rowing Machine'),
('Jump Rope'),
('Medicine Ball');

-- Insert data into Classes table
INSERT INTO Class (class_name, trainer_id, class_type, schedule_time, duration_minutes, max_capacity)
VALUES
('Strength Training 101', 1, 'StrengthTraining', '2023-03-01 17:00:00', 60, 20),
('Cardio Burnout', 2, 'Cardio', '2023-04-01 18:00:00', 45, 15),
('HIIT Blast', 3, 'HIIT', '2023-05-01 16:00:00', 30, 25),
('Functional Fitness Basics', 4, 'StrengthTraining', '2023-06-01 10:00:00', 50, 10),
('Core Strength Circuit', 5, 'StrengthTraining', '2023-07-01 09:00:00', 45, 20);

-- Insert data into Class_Enrollments table
INSERT INTO Class_Enrollment (class_id, member_id, enrollment_date)
VALUES
(1, 1, '2023-02-10'),
(2, 2, '2023-03-15'),
(3, 3, '2023-04-20'),
(4, 4, '2023-05-25'),
(5, 5, '2023-06-30');

-- Insert data into Workout_Sessions table
INSERT INTO workout_session (member_id, workout_id, session_date, duration_minutes, calories_burned, feedback)
VALUES
(1, 1, '2023-01-16', 60, 500, 'Great session, feeling stronger!'),
(2, 2, '2023-02-21', 45, 350, 'Challenging but rewarding.'),
(3, 3, '2023-03-26', 30, 450, 'Intense HIIT workout, loved it.'),
(4, 4, '2023-04-30', 50, 400, 'Functional movements felt great.'),
(5, 5, '2023-05-11', 40, 320, 'Core workout was tough but effective.');

-- Insert data into Attendance table
INSERT INTO Attendance (member_id, class_id, attendance_date, attended)
VALUES
(1, 1, '2023-03-01', TRUE),
(2, 2, '2023-04-01', TRUE),
(3, 3, '2023-05-01', TRUE),
(4, 4, '2023-06-01', TRUE),
(5, 5, '2023-07-01', TRUE);

-- Insert data into Diet_Plans table
INSERT INTO Diet_Plan (member_id, trainer_id, plan_name, description, start_date, end_date)
VALUES
(1, 1, 'Muscle Gain Diet', 'High-protein diet focused on muscle gain.', '2023-01-10', '2023-12-10'),
(2, 2, 'Cardio Diet', 'Low-carb diet focused on cardiovascular endurance.', '2023-02-15', '2023-11-15'),
(3, 3, 'HIIT Nutrition', 'Balanced diet supporting intense HIIT workouts.', '2023-03-20', '2023-12-20'),
(4, 4, 'Functional Movement Diet', 'Diet focused on improving mobility and energy.', '2023-04-25', '2024-04-25'),
(5, 5, 'Core Strength Diet', 'Diet supporting core strength workouts.', '2023-05-30', '2024-05-30');

-- Insert data into Feedback table
INSERT INTO Feedback (member_id, trainer_id, feedback_text, rating, feedback_date)
VALUES
(1, 1, 'Amazing trainer, helped me build a lot of strength!', 5, '2023-01-20'),
(2, 2, 'Great cardio sessions, I feel more fit!', 4, '2023-02-25'),
(3, 3, 'Loved the HIIT workouts, very effective.', 5, '2023-03-30'),
(4, 4, 'Functional movements made a big difference in my daily life.', 4, '2023-04-05'),
(5, 5, 'Core strength improved a lot thanks to this trainer.', 5, '2023-05-10');

-- Insert data into Staff table
INSERT INTO Staff (first_name, last_name, role, hire_date, salary)
VALUES
('Lisa', 'Brown', 'Front Desk Manager', '2021-10-15', 45000.00),
('Tom', 'Clark', 'Cleaning Staff', '2022-03-20', 30000.00),
('Sam', 'Young', 'Gym Manager', '2020-01-05', 60000.00),
('Emma', 'White', 'Receptionist', '2023-07-01', 35000.00),
('Jake', 'Hill', 'Maintenance Staff', '2023-02-10', 32000.00);

-- Insert data into Inventory table
INSERT INTO Inventory (item_name, quantity, purchase_price, selling_price)
VALUES
('Treadmill', 10, 1500.00, 2000.00),
('Dumbbells (Set)', 20, 500.00, 700.00),
('Barbell', 15, 250.00, 350.00),
('Kettlebell', 25, 100.00, 150.00),
('Resistance Bands (Set)', 30, 50.00, 80.00),
('Stationary Bike', 8, 1000.00, 1300.00),
('Pull-Up Bar', 12, 200.00, 250.00),
('Rowing Machine', 6, 1200.00, 1500.00),
('Jump Rope', 40, 15.00, 25.00),
('Medicine Ball', 20, 30.00, 50.00);

DROP TABLE inventory;
DROP TABLE feedback;
DROP TABLE equipment;
DROP TABLE diet_plan;
DROP TABLE class_enrollment;
DROP TABLE attendance;
DROP TABLE membership;
DROP TABLE staff;
DROP TABLE trainer_schedule;
DROP TABLE workout_session;
DROP TABLE class;
DROP TABLE workout_plan;
DROP TABLE member;
DROP TABLE trainer;