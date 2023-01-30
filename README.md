# Payroll Management API 

This project implements a payroll management system, where payrolls are sent directly to employees' accounts on the corporate website, rather than through corporate mail.

## Service Structure
The system is built using Java and Spring Framework.

## Authentication
The authentication process is based on the employee's login credentials, stored on an in memory database for the purpose of this exercise.

## Authorization 
Access to payroll information is restricted based on employee role and permissions. 
The role model defines the different types of employees and the actions they are authorized to perform on the payroll system.

## Log security events, detect attacks, and monitor user activities 
The system logs all security events and user activities to detect and prevent any security breaches.

## Securing connection using HTTPS
The system uses HTTPS to secure the connection between the employee and the payroll management system.

## Installation
To run the project, you need to have Java and Spring Framework installed on your machine. 
1. Clone the repository using `git clone https://github.com/hyperskill-account-service.git`
2. Navigate to the project directory using `cd hyperskill-account-service`
3. Run the project using `./mvnw spring-boot:run`

## Contributing
To contribute to the project, please follow the [contributing guidelines](CONTRIBUTING.md)

## License
The project is licensed under the [MIT License](LICENSE)

## Contact
For any questions or inquiries, please open an issue or reach out to [Don Isiko](thecodingdon@googlemail.com)
