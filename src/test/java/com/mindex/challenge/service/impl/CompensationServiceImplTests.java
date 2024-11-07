package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTests {

        private String compensationUrl;
        private String compensationIdUrl;

        @Autowired
        private EmployeeService employeeService;

        @LocalServerPort
        private int port;

        @Autowired
        private TestRestTemplate restTemplate;

        @Before
        public void setup() {
                compensationUrl = "http://localhost:" + port + "/compensation";
                compensationIdUrl = "http://localhost:" + port + "/compensation/{employeeId}";
        }

        @Test
        public void testCreateAndReadCompensationForJohnLennon() {
                Employee employeeJohn = employeeService.read("16a596ae-edd3-4847-99fe-c4518e82c86f");
                assertNotNull(employeeJohn);

                // Create a Compensation for John Lennon
                Compensation compensation = new Compensation(employeeJohn, 100000, "2024-01-01");

                // Test hitting REST endpoint to create compensation
                Compensation createdCompensation = restTemplate
                                .postForEntity(compensationUrl, compensation, Compensation.class)
                                .getBody();
                assertNotNull(createdCompensation);
                assertCompensationEquivalence(compensation, createdCompensation);

                // Test hitting REST endpoint to read newly created compensation
                ResponseEntity<Compensation> response = restTemplate.getForEntity(compensationIdUrl, Compensation.class,
                                employeeJohn.getEmployeeId());
                Compensation readCompensation = response.getBody();
                assertNotNull(readCompensation);
                assertCompensationEquivalence(createdCompensation, readCompensation);
        }

        @Test
        public void testCreateAndReadCompensationForPaulMcCartney() {
                Employee employeePaul = employeeService.read("b7839309-3348-463b-a7e3-5de1c168beb3");
                assertNotNull(employeePaul);

                // Create a Compensation for Paul McCartney
                Compensation compensation = new Compensation(employeePaul, 85000, "2024-02-01");

                // Test hitting REST endpoint to create compensation
                Compensation createdCompensation = restTemplate
                                .postForEntity(compensationUrl, compensation, Compensation.class)
                                .getBody();
                assertNotNull(createdCompensation);
                assertCompensationEquivalence(compensation, createdCompensation);

                // Test hitting REST endpoint to read newly created compensation
                ResponseEntity<Compensation> response = restTemplate.getForEntity(compensationIdUrl, Compensation.class,
                                employeePaul.getEmployeeId());
                Compensation readCompensation = response.getBody();
                assertNotNull(readCompensation);
                assertCompensationEquivalence(createdCompensation, readCompensation);
        }

        @Test
        public void testCreateAndReadCompensationForRingoStarr() {
                Employee employeeRingo = employeeService.read("03aa1462-ffa9-4978-901b-7c001562cf6f");
                assertNotNull(employeeRingo);

                // Create a Compensation for Ringo Starr
                Compensation compensation = new Compensation(employeeRingo, 95000, "2024-03-01");

                // Test hitting REST endpoint to create compensation
                Compensation createdCompensation = restTemplate
                                .postForEntity(compensationUrl, compensation, Compensation.class)
                                .getBody();
                assertNotNull(createdCompensation);
                assertCompensationEquivalence(compensation, createdCompensation);

                // Test hitting REST endpoint to read newly created compensation
                ResponseEntity<Compensation> response = restTemplate.getForEntity(compensationIdUrl, Compensation.class,
                                employeeRingo.getEmployeeId());
                Compensation readCompensation = response.getBody();
                assertNotNull(readCompensation);
                assertCompensationEquivalence(createdCompensation, readCompensation);
        }

        @Test
        public void testCreateAndReadCompensationForPeteBest() {
                Employee employeePete = employeeService.read("62c1084e-6e34-4630-93fd-9153afb65309");
                assertNotNull(employeePete);

                // Create a Compensation for Pete Best
                Compensation compensation = new Compensation(employeePete, 78000, "2024-04-01");

                // Test hitting REST endpoint to create compensation
                Compensation createdCompensation = restTemplate
                                .postForEntity(compensationUrl, compensation, Compensation.class)
                                .getBody();
                assertNotNull(createdCompensation);
                assertCompensationEquivalence(compensation, createdCompensation);

                // Test hitting REST endpoint to read newly created compensation
                ResponseEntity<Compensation> response = restTemplate.getForEntity(compensationIdUrl, Compensation.class,
                                employeePete.getEmployeeId());
                Compensation readCompensation = response.getBody();
                assertNotNull(readCompensation);
                assertCompensationEquivalence(createdCompensation, readCompensation);
        }

        @Test
        public void testCreateAndReadCompensationForGeorgeHarrison() {
                Employee employeeGeorge = employeeService.read("c0c2293d-16bd-4603-8e08-638a9d18b22c");
                assertNotNull(employeeGeorge);

                // Create a Compensation for George Harrison
                Compensation compensation = new Compensation(employeeGeorge, 91000, "2024-05-01");

                // Test hitting REST endpoint to create compensation
                Compensation createdCompensation = restTemplate
                                .postForEntity(compensationUrl, compensation, Compensation.class)
                                .getBody();
                assertNotNull(createdCompensation);
                assertCompensationEquivalence(compensation, createdCompensation);

                // Test hitting REST endpoint to read newly created compensation
                ResponseEntity<Compensation> response = restTemplate.getForEntity(compensationIdUrl, Compensation.class,
                                employeeGeorge.getEmployeeId());
                Compensation readCompensation = response.getBody();
                assertNotNull(readCompensation);
                assertCompensationEquivalence(createdCompensation, readCompensation);
        }

        private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
                // Verify the two passed compensations are equivalent
                assertEquals(expected.getEmployee().getEmployeeId(), actual.getEmployee().getEmployeeId());
                assertEquals(expected.getSalary(), actual.getSalary(), 0);
                assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
        }
}
