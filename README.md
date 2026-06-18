# HotelApp - Hotel Booking Platform

This is a microservice-based application designed for booking hotels and processing payments. To run the complete system locally with a MySQL database, simply download this repository and run `docker compose up`.

```yaml
version: '3.8'

services:
  hotel-db:
    image: mysql:8.0
    container_name: hotel-db
    ports:
      - "3306:3306"
    environment:
      MYSQL_DATABASE: hotel_db
      MYSQL_ROOT_PASSWORD: root
    volumes:
      - mysql-data:/var/lib/mysql

  payment-service:
    build: ./payment-service
    container_name: payment-service
    ports:
      - "8081:8081"
    environment:
      - DB_HOST=hotel-db
      - DB_PORT=3306
      - DB_NAME=hotel_db
      - DB_USER=root
      - DB_PASSWORD=root
    depends_on:
      - hotel-db
      
  hotel-booking-app:
    build: ./hotel-booking-app
    container_name: hotel-booking-app
    ports:
      - "8080:8080"
    environment:
      - DB_HOST=hotel-db
      - DB_PORT=3306
      - DB_NAME=hotel_db
      - DB_USER=root
      - DB_PASSWORD=root
      - PAYMENT_SERVICE_URL=http://payment-service:8081
    depends_on:
      - hotel-db
      - payment-service

volumes:
  mysql-data:
```

Features

User Authentication: Secure registration and login using Spring Security.
Hotel Management (Admin only): Create, view, edit, and delete hotel listings (CRUD operations).
Image Upload: Local file storage for multiple hotel gallery images.
Interactive Google Maps: Automatically embeds a Google Map on the details page based on the hotel's address and location.
Reviews & Ratings: Authenticated users can leave reviews and star ratings for each hotel.
Dynamic Hotel Search: Filter and search hotels instantly by location.
Bookings & Payments: Calculate total prices based on nights and process simulated payments through a dedicated payment microservice.
Responsive UI: Built with Bootstrap 5 and custom CSS for a modern, mobile-friendly look.

Tech Stack

Backend: Java 17, Spring Boot 3.3.5, Spring MVC, Spring Data JPA, Spring Security, Spring Cloud OpenFeign.
Database: MySQL, Hibernate ORM.
Frontend: Thymeleaf, HTML5, CSS3, Bootstrap 5.
Tools: Maven, Git, IntelliJ IDEA, Lombok, Docker, Docker Compose.

How to Run Locally

1. Clone the repository:
git clone https://github.com/MitkoVasilev01/hotel-booking-app.git

2. Create the Database:
Open your MySQL terminal or MySQL Workbench and execute the following query to create an empty schema:
CREATE DATABASE hotel_db;

3. Configure Database Credentials:
Open src/main/resources/application.properties and update the database connection details with your local MySQL credentials:
spring.datasource.url=jdbc:mysql://localhost:3306/hotel_db
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password

4. Run the application:
You can run the application directly from your IDE (IntelliJ IDEA) by executing the main method in HotelBookingAppApplication.java, or via terminal:
mvn spring-boot:run

5. Access the Web Interface:
Open your browser and navigate to: http://localhost:8080

Demo Credentials for Testing
On the first run, the database is automatically seeded with demo data (2 hotels, user, and admin). You can use the following default credentials to test all features (creating, managing, and deleting hotels):

Admin Account
Username: admin
Password: admin123.

Client Account
Username: user
Password: user123

Screenshots:

Home page:
<img width="3819" height="1662" alt="Screenshot 2026-06-15 173410" src="https://github.com/user-attachments/assets/f508ee04-dfe9-4e9f-8ddb-3684b9698e09" />
Hotel details:
<img width="3823" height="1688" alt="Screenshot 2026-06-15 173447" src="https://github.com/user-attachments/assets/506a3adc-e07b-4030-90ee-e9437298e4d3" />
Profile details and reservations:
<img width="3805" height="1702" alt="Screenshot 2026-06-15 173513" src="https://github.com/user-attachments/assets/b5656038-ed12-42a6-a140-827cb118c1b8" />
