package com.transport.utils.factories;

import com.transport.controllers.QualificationController;
import com.transport.repositories.Qualification.IQualificationRepository;
import com.transport.repositories.Qualification.QualificationRepository;
import com.transport.services.qualification.IQualificationService;
import com.transport.services.qualification.QualificationService;

public class QualificationFactory {
    private static IQualificationRepository qualificationRepository;
    private static IQualificationService qualificationService;
    private static QualificationController qualificationController;

    public static IQualificationRepository getRepository() {
        if (qualificationRepository == null) {
            qualificationRepository = new QualificationRepository();
        }
        return qualificationRepository;
    }

    public static IQualificationService getService() {
        if (qualificationService == null) {
            qualificationService = new QualificationService(
                    getRepository(),
                    EmployeeFactory.getRepository()
            );
        }
        return qualificationService;
    }

    public static QualificationController getController() {
        if (qualificationController == null) {
            qualificationController = new QualificationController(getService());
        }
        return qualificationController;
    }
}
