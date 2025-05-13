
# Gym Management System

## Overview

The Gym Management System is an advanced Java-based solution designed to streamline operations within a gym environment. Leveraging JPA (Jakarta Persistence API), the system ensures robust data management and interaction. The application covers a wide array of functionalities, from membership management to trainer scheduling and inventory tracking.

## Features

| Feature                   | Description                                               |
|---------------------------|-----------------------------------------------------------|
| Member Management         | Register and manage gym members, including emergency contacts |
| Trainer Management        | Manage trainers, schedules, classes, and specializations  |
| Workout and Diet Plans    | Assign and manage workout and diet plans for members      |
| Class Scheduling          | Create, enroll, and track attendance in gym classes       |
| Inventory Management      | Manage gym equipment and product inventory               |
| Feedback System           | Collect feedback and ratings for trainers                |
| Payment Management        | Manage memberships, billing, and payment status          |
| Staff Management          | Manage staff profiles, roles, and salaries               |

## Technologies Used

- **Java 17**
- **Spring Boot**
- **Jakarta Persistence API (JPA)**
- **Hibernate ORM**
- **MySQL/PostgreSQL (Optional for persistence layer)**
- **Lombok (for cleaner code, optional)**

## Admin Dashboard
![image](https://github.com/user-attachments/assets/8b3e7a63-08a0-475d-9376-04fad65842ac)

## Enums Used

| Enum            | Values                               |
|-----------------|--------------------------------------|
| PaymentStatus   | Paid, Due                            |
| ClassType       | Cardio, StrengthTraining, HIIT       |
| Gender          | Male, Female                         |
| SessionType     | PersonalTraining, GroupClass         |

## Best Practices Applied

- **Entity Modularity:** Each entity is clearly defined following industry standards.
- **Cascade Handling:** Applied appropriate cascading to maintain data integrity.
- **Fetch Strategies:** Used LAZY fetching by default to optimize performance.
- **Validation:** Applied basic validation for critical fields (e.g., rating limits).
- **Naming Conventions:** Used clear and consistent naming aligned with Java standards.

## How to Run

1. Clone the repository.
2. Configure the `application.properties` for your database.
3. Use your preferred IDE to build and run the application.
4. Access APIs via REST clients like Postman or integrate with UI.

## Contributing

Contributions are welcome. Please open issues for suggestions or pull requests for improvements.

## License

This project is licensed under the MIT License.
