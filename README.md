# 🚀 UI Automation With Selenium - Cucumber - TestNG - AllureReports
![Build Status](https://img.shields.io/badge/build-passing-brightgreen)
![License](https://img.shields.io/badge/license-MIT-blue)
![Java](https://img.shields.io/badge/Java-8+-orange)
![Maven](https://img.shields.io/badge/Maven-3.6+-red)

A powerful, enterprise-grade UI automation framework combining the strengths of Selenium, Cucumber BDD, and TestNG to deliver efficient, maintainable, and scalable test automation solutions.

## ✨ Key Features

### 🎯 Core Capabilities
- **BDD Framework Integration**: Cucumber implementation for human-readable specifications
- **Flexible and Dynamic UI Testing**: Selenium for comprehensive UI validation
- **Parallel Execution**: TestNG-powered concurrent test execution
- **Dynamic Data Handling**: External test data management
- **Custom Reporting**: Detailed Allure and TestNG reports

### 🛠 Technical Stack
- Selenium for UI testing
- Cucumber for BDD implementation
- TestNG for test execution and assertions
- Maven for dependency management
- Allure for advanced reporting

## 🚀 Getting Started

### Prerequisites
- Java 8 or higher
- Maven 3.6+
- Your favorite IDE (IntelliJ IDEA recommended)
- Git

### Quick Setup
```bash
# Clone the repository
git clone https://github.com/shanakasp/ITQA-API-Testing-Grp33-RestAssured-Cucumber-TestNG.git

# Navigate to project directory
cd api-automation-framework

# Install dependencies
mvn clean install
```

### 🏃‍♂️ Running Tests
```bash
# Run all tests
mvn test

# Run specific test tags
mvn test -Dcucumber.filter.tags="@AnyTagsYouLikeInFeatureFiles"
```

## 📁 Project Structure
```
src
├── main/java
├── test/java
│   ├── stepdefs/       # Step definitions
│   └── runners/        # Test runners
│   ├── utils/          # Utilities that needed
└── test/resources
    ├── driver/         # driver configuration
    ├── features/       # Cucumber feature files
```

## 📊 Reporting

### Sample Report Screenshots
![Test Report Dashboard](https://github.com/user-attachments/assets/8cd786e4-4d6e-46b0-b589-0927d49536cf)

### Report Generation
- Allure reports are automatically generated after test execution
- Find detailed reports in `target/allure-results`
- Generate HTML report using: `allure serve target/allure-results`

## 🤝 Contributing
1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request


## 💡 Best Practices
- Follow the page object pattern for better maintenance
- Keep feature files simple and readable
- Maintain test data separately from test logic
- Use appropriate tags for test organization

## 🌟 Show Your Support
If you find this framework useful, please consider giving it a star! ⭐

---
Made with ❤️ for the QA Automation Community by #FIT20 UOM
