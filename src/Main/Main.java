package Main;

import Controller.EmployeController;
import Controller.HolidayController;
import Controller.LoginController;
import DAO.EmployeDAOimpl;
import DAO.HolidayDAOimpl;
import DAO.LoginDAOimpl;
import Model.EmployeModel;
import Model.HolidayModel;
import Model.LoginModel;
import View.Employe_HolidayView;
import View.LoginView;

public class Main {
    public static void main(String[] args) {
        // Create DAO instances
        LoginDAOimpl loginDAO = new LoginDAOimpl();
        EmployeDAOimpl employeDAO = new EmployeDAOimpl();
        HolidayDAOimpl holidayDAO = new HolidayDAOimpl();

        // Create model instances
        LoginModel loginModel = new LoginModel(loginDAO);
        EmployeModel employeModel = new EmployeModel(employeDAO);
        HolidayModel holidayModel = new HolidayModel(holidayDAO);

        // Create views
        LoginView loginView = new LoginView();
        Employe_HolidayView employeHolidayView = new Employe_HolidayView();

        // Create controllers
        new LoginController(loginView, loginModel);

        // Show the login view
        loginView.setVisible(true);

        // Listen for login success to show the main application
        loginView.addLoginListener(e -> {
            if (loginModel.authenticate(loginView.getUsername(), loginView.getPassword())) {
                // Login successful
                loginView.setVisible(false); // Hide login view

                // Initialize controllers for employee and holiday management
                new EmployeController(employeHolidayView, employeModel);
                new HolidayController(employeHolidayView, holidayModel);

                // Show the main application view
                employeHolidayView.setVisible(true);
            } else {
                // Login failed
                loginView.showError("Invalid username or password. Please try again.");
            }
        });
    }
}
