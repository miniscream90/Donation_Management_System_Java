# Donation Management System

A modern, user-friendly Java desktop application built with Swing to manage charitable donations, track donor contributions, and monitor fund usage for organizations. This application provides a professional GUI, secure authentication, and file-based data persistence, making it suitable for small to medium-sized charitable organizations.

## 📋 Table of Contents
1. [Application Overview](#application-overview)
2. [System Requirements](#system-requirements)
3. [Installation & Setup](#installation--setup)
4. [User Guide](#user-guide)
5. [Code Architecture](#code-architecture)
6. [Technical Details](#technical-details)
7. [Features & Functionality](#features--functionality)
8. [File Structure](#file-structure)
9. [Troubleshooting](#troubleshooting)
10. [Future Enhancements](#future-enhancements)
11. [Support & Contact](#support--contact)
12. [Version History](#version-history)

## Application Overview
**Purpose**: The Donation Management System streamlines donation tracking and fund management for charitable organizations. It allows administrators to register donors, record donations, log fund usage, and view transaction histories through an intuitive graphical interface.

**Key Features**:
- Secure admin login system
- Donor registration and management
- Donation recording with timestamps
- Fund usage logging and tracking
- Persistent storage using text files
- Modern, professional Swing-based GUI

**Design Philosophy**:
- Clean and intuitive user interface
- Consistent styling with clear typography
- User-friendly navigation and feedback
- Robust error handling and data validation

## System Requirements
### Technical Requirements
- **Java Development Kit (JDK)**: Version 17 or higher
- **Operating System**: Windows 10/11, macOS, or Linux
- **Memory**: Minimum 4GB RAM
- **Disk Space**: 100MB free
- **Screen Resolution**: 1024x768 or higher

### Development Environment
- **GUI Framework**: Java Swing
- **Data Storage**: File-based (no external database required)
- **Encoding**: UTF-8
- **Look and Feel**: Default Swing look and feel (customizable to Nimbus)

## Installation & Setup
### Prerequisites
1. **Install JDK**:
   - Download from [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html) or [Adoptium OpenJDK](https://adoptium.net/).
   - Verify installation:
     ```bash
     java -version
     javac -version
     ```
2. **Directory Permissions**:
   - Ensure write access to the project directory for creating `donors.txt`, `donations.txt`, and `fund_usage.txt`.

### Installation Steps
1. **Clone or Download the Repository**:
   - Clone using Git:
     ```bash
     git clone https://github.com/yourusername/donation-management-system.git
     ```
   - Or download the source files from GitHub.

2. **Navigate to Project Directory**:
   ```bash
   cd donation-management-system
   ```

3. **Compile the Application**:
   ```bash
   javac DonationManagementSystem.java
   ```

4. **Run the Application**:
   ```bash
   java DonationManagementSystem
   ```

### Quick Start (Windows)
- Use a batch file (`run-gui.bat`) for convenience:
  ```bat
  @echo off
  javac DonationManagementSystem.java
  java DonationManagementSystem
  pause
  ```
- Save as `run-gui.bat`, place in the project directory, and double-click to run.

## User Guide
### Login Credentials
- **Username**: `admin`
- **Password**: `admin123`

### Main Features
1. **Register Donor**:
   - Enter a unique Donor ID in the dashboard.
   - Click "Register Donor" and provide the donor’s name and email in pop-up dialogs.
   - Receive confirmation of successful registration or an error if the ID exists.

2. **Add Donation**:
   - Enter an existing Donor ID.
   - Click "Add Donation" and input the donation amount in the dialog.
   - The system validates the amount and updates donor records.

3. **View Donations**:
   - Enter a Donor ID and click "View Donations".
   - View the donor’s donation history in a scrollable text area.

4. **Log Fund Usage**:
   - Click "Log Fund Usage" and enter the purpose and amount in dialogs.
   - The system logs the usage with a timestamp.

5. **View Fund Usage**:
   - Click "View Fund Usage" to see all fund usage records in a scrollable text area.

6. **Logout**:
   - Click "Logout" to return to the login screen.

### Navigation
- The login window provides fields for username/password and buttons for Login/Register.
- The dashboard displays a Donor ID field and buttons for all functions, with pop-up dialogs for input and feedback.

## Code Architecture
### Class Structure
- **DonationManagementSystem** (Main Class):
  - Entry point (`main` method).
  - Initializes the `DonationSystem` backend and launches the `LoginFrame`.
- **LoginFrame** (GUI Class):
  - Displays the login interface with username/password fields and Login/Register buttons.
  - Handles authentication and transitions to `DashboardFrame`.
- **DashboardFrame** (GUI Class):
  - Provides the main interface with buttons for all operations (Register Donor, Add Donation, etc.).
  - Uses pop-up dialogs for input and displays data in scrollable text areas.
- **DonationSystem** (Backend Logic):
  - Manages donor data, donations, and fund usage.
  - Methods: `registerDonor`, `findDonor`, `updateDonor`, `logDonation`, `logFundUsage`, `getDonations`, `getFundUsage`, `loadDonors`, `saveDonors`.
- **Donor** (Data Model):
  - Stores donor details: `donorId`, `name`, `email`, `totalDonated`.
  - Method: `addDonation` to update donation totals.

## Technical Details
### UI Framework
- **Java Swing**: For desktop GUI components.
- **Layout**: `GridLayout` for login and dashboard windows, with `JOptionPane` for dialogs.
- **Styling**: Default Swing look and feel, with consistent spacing and typography.

### Design Patterns
- **MVC Separation**: `Donor` (Model), `DonationSystem` (Controller), Swing components (View).
- **Event-Driven**: Button actions trigger backend operations via listeners.
- **Modular Design**: Separate classes for UI and logic.

### Technical Features
- **Input Validation**:
  - Checks for non-empty fields and valid numeric inputs.
  - Email validation (basic format check).
  - User-friendly error messages via `JOptionPane`.
- **Data Storage**:
  - Text files: `donors.txt` (CSV format), `donations.txt`, `fund_usage.txt`.
  - Automatic file creation and timestamped logs.
- **Performance**:
  - Efficient `HashMap` for donor lookups.
  - Minimal memory usage for small-scale applications.

## Features & Functionality
### Security
- Hardcoded admin login (`admin`/`admin123`) for simplicity.
- Secure file-based data storage with error handling.

### Data Management
- Donor registration with unique IDs.
- Donation tracking with timestamps.
- Fund usage logging with purpose and amount.
- Persistent storage across sessions.

### User Interface
- Clean, intuitive Swing-based GUI.
- Pop-up dialogs for input and feedback.
- Scrollable text areas for viewing histories.

### Performance
- Fast startup and response times.
- Optimized file I/O with buffered readers/writers.
- Lightweight design suitable for small organizations.

## File Structure
```
donation-management-system/
├── DonationManagementSystem.java  # Main GUI application
├── run-gui.bat                   # Optional Windows batch launcher
├── donors.txt                    # Auto-generated: Donor data (CSV)
├── donations.txt                 # Auto-generated: Donation logs
├── fund_usage.txt                # Auto-generated: Fund usage logs
├── DonationManagementSystem.class # Compiled class file
├── Donor.class                   # Compiled class file
├── DonationSystem.class          # Compiled class file
├── LoginFrame.class              # Compiled class file
├── DashboardFrame.class          # Compiled class file
```

## Troubleshooting
### Compilation Errors
- **Issue**: `'javac' is not recognized`.
  - **Solution**: Ensure JDK is installed and added to PATH:
    ```bash
    java -version
    javac -version
    ```
    Add `C:\Program Files\Java\jdk-17\bin` to System PATH.
- **Issue**: Syntax errors.
  - **Solution**: Verify `DonationManagementSystem.java` matches the provided code.

### Runtime Errors
- **Issue**: `Could not find or load main class`.
  - **Solution**: Ensure `DonationManagementSystem.class` exists in the directory:
    ```bash
    dir
    javac DonationManagementSystem.java
    java DonationManagementSystem
    ```
- **Issue**: Files not created.
  - **Solution**: Check write permissions in the directory or run terminal as Administrator:
    ```bash
    Start-Process powershell -Verb RunAs
    ```

### UI Issues
- **Issue**: GUI not displaying correctly.
  - **Solution**: Ensure JDK supports Swing (JDK 17+ is fine). Try a different screen resolution or restart the application.

## Future Enhancements
- **Reporting**: Add PDF/Excel export for donation and fund usage reports.
- **Security**: Implement multiple user accounts with encrypted passwords.
- **Database**: Replace file storage with MySQL/PostgreSQL for scalability.
- **Web Interface**: Develop a web-based version using Spring or JavaFX.
- **Analytics**: Include donation trends and fund usage metrics.
- **Mobile Support**: Create an Android app for remote access.

## Support & Contact
- **Issues**: Report bugs or request features via GitHub Issues.
- **Documentation**: Refer to this README or Java Swing documentation for guidance.
- **Customization**: Modify the code to change styling, add features, or adjust validation rules.

## Version History
- **Version 1.0** (October 2025):
  - Initial release with Swing GUI.
  - Full donation management functionality.
  - File-based persistence and input validation.

---
**Generated on**: October 8, 2025  
**Application Version**: 1.0  
**Java Version**: JDK 17+  
**Platform**: Windows 10/11, macOS, Linux  

This README provides a comprehensive guide to the Donation Management System. Keep it in your project root for reference.
