package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }

    @Override
    public ReportingStructure getReportingStructure(String id) {
        // Get employee for corresponding id
        Employee employee = read(id);
        // Call helper function to calculate number of reports
        int numberOfReports = calculateNumberOfReports(employee);
        // Return ReportingStructure with employee and calculated reports
        return new ReportingStructure(employee, numberOfReports);
    }

    private int calculateNumberOfReports(Employee employee) {
        int totalReports = 0;

        // Verify employee has reports
        if (employee.getDirectReports() != null) {
            // Iterate through employees current directReports
            for (Employee directReport : employee.getDirectReports()) {
                // Get the directReports's complete Employee object
                Employee subordinate = read(directReport.getEmployeeId());

                // Recursively call calculateNumberOfReports on current directReport of employee
                // to calculate total directReports
                totalReports += 1 + calculateNumberOfReports(subordinate);
            }
        }

        return totalReports;
    }
}
