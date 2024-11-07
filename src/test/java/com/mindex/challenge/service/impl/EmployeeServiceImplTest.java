package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeServiceImplTest {

    private String employeeUrl;
    private String employeeIdUrl;
    private String reportingStructureUrl;

    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        employeeIdUrl = "http://localhost:" + port + "/employee/{id}";
        reportingStructureUrl = "http://localhost:" + port + "/employeeReportingStructure/{id}";
    }

    @Test
    public void testCreateReadUpdate() {
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");

        // Create checks
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();

        assertNotNull(createdEmployee.getEmployeeId());
        assertEmployeeEquivalence(testEmployee, createdEmployee);

        // Read checks
        Employee readEmployee = restTemplate
                .getForEntity(employeeIdUrl, Employee.class, createdEmployee.getEmployeeId()).getBody();
        assertEquals(createdEmployee.getEmployeeId(), readEmployee.getEmployeeId());
        assertEmployeeEquivalence(createdEmployee, readEmployee);

        // Update checks
        readEmployee.setPosition("Development Manager");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Employee updatedEmployee = restTemplate.exchange(employeeIdUrl,
                HttpMethod.PUT,
                new HttpEntity<Employee>(readEmployee, headers),
                Employee.class,
                readEmployee.getEmployeeId()).getBody();

        assertEmployeeEquivalence(readEmployee, updatedEmployee);
    }

    @Test
    public void testReportingStructureForJohnLennon() {
        // Get employee with ID "16a596ae-edd3-4847-99fe-c4518e82c86f"
        Employee employee = employeeService.read("16a596ae-edd3-4847-99fe-c4518e82c86f");
        assertNotNull(employee);

        // Test the ReportingStructure endpoint
        ResponseEntity<ReportingStructure> response = restTemplate.getForEntity(
                reportingStructureUrl, ReportingStructure.class, employee.getEmployeeId());

        ReportingStructure reportingStructure = response.getBody();
        assertNotNull(reportingStructure);
        assertEquals(employee.getEmployeeId(), reportingStructure.getEmployee().getEmployeeId());

        // John Lennon should have 4 reports
        assertEquals(4, reportingStructure.getNumberOfReports());
    }

    @Test
    public void testReportingStructureForPaulMcCartney() {
        // Get employee with ID "b7839309-3348-463b-a7e3-5de1c168beb3"
        Employee employee = employeeService.read("b7839309-3348-463b-a7e3-5de1c168beb3");
        assertNotNull(employee);

        // Test the ReportingStructure endpoint
        ResponseEntity<ReportingStructure> response = restTemplate.getForEntity(
                reportingStructureUrl, ReportingStructure.class, employee.getEmployeeId());

        ReportingStructure reportingStructure = response.getBody();
        assertNotNull(reportingStructure);
        assertEquals(employee.getEmployeeId(), reportingStructure.getEmployee().getEmployeeId());

        // Paul McCartney should have 0 reports
        assertEquals(0, reportingStructure.getNumberOfReports());
    }

    @Test
    public void testReportingStructureForRingoStarr() {
        // Get employee with ID "03aa1462-ffa9-4978-901b-7c001562cf6f"
        Employee employee = employeeService.read("03aa1462-ffa9-4978-901b-7c001562cf6f");
        assertNotNull(employee);

        // Test the ReportingStructure endpoint
        ResponseEntity<ReportingStructure> response = restTemplate.getForEntity(
                reportingStructureUrl, ReportingStructure.class, employee.getEmployeeId());

        ReportingStructure reportingStructure = response.getBody();
        assertNotNull(reportingStructure);
        assertEquals(employee.getEmployeeId(), reportingStructure.getEmployee().getEmployeeId());

        // Ringo Starr should have 2 reports
        assertEquals(2, reportingStructure.getNumberOfReports());
    }

    @Test
    public void testReportingStructureForPeteBest() {
        // Get employee with ID "62c1084e-6e34-4630-93fd-9153afb65309"
        Employee employee = employeeService.read("62c1084e-6e34-4630-93fd-9153afb65309");
        assertNotNull(employee);

        // Test the ReportingStructure endpoint
        ResponseEntity<ReportingStructure> response = restTemplate.getForEntity(
                reportingStructureUrl, ReportingStructure.class, employee.getEmployeeId());

        ReportingStructure reportingStructure = response.getBody();
        assertNotNull(reportingStructure);
        assertEquals(employee.getEmployeeId(), reportingStructure.getEmployee().getEmployeeId());

        // Pete Best should have 0 reports
        assertEquals(0, reportingStructure.getNumberOfReports());
    }

    @Test
    public void testReportingStructureForGeorgeHarrison() {
        // Get employee with ID "c0c2293d-16bd-4603-8e08-638a9d18b22c"
        Employee employee = employeeService.read("c0c2293d-16bd-4603-8e08-638a9d18b22c");
        assertNotNull(employee);

        // Test the ReportingStructure endpoint
        ResponseEntity<ReportingStructure> response = restTemplate.getForEntity(
                reportingStructureUrl, ReportingStructure.class, employee.getEmployeeId());

        ReportingStructure reportingStructure = response.getBody();
        assertNotNull(reportingStructure);
        assertEquals(employee.getEmployeeId(), reportingStructure.getEmployee().getEmployeeId());

        // George Harrison should have 0 reports
        assertEquals(0, reportingStructure.getNumberOfReports());
    }

    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }
}
