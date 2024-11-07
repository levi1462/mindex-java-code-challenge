package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;

public interface CompensationService {
    // CompensationService methods

    // Method to create new Compensation in mongodb
    Compensation create(Compensation compensation);

    // Method to read Compensation from mongodb by employeeId
    Compensation read(String id);
}
