package com.transport.utils.factories;

import com.transport.controllers.ClientController;
import com.transport.repositories.Client.ClientRepository;
import com.transport.repositories.Client.IClientRepository;
import com.transport.services.client.ClientService;
import com.transport.services.client.IClientService;

/**
 * Factory for Client-related dependencies
 */
public class ClientFactory {

    private static IClientRepository clientRepository;
    private static IClientService clientService;
    private static ClientController clientController;

    public static IClientRepository getRepository() {
        if (clientRepository == null) {
            clientRepository = new ClientRepository();
        }
        return clientRepository;
    }

    public static IClientService getService() {
        if (clientService == null) {
            clientService = new ClientService(getRepository(), CompanyFactory.getRepository());
        }
        return clientService;
    }

    public static ClientController getController() {
        if (clientController == null) {
            clientController = new ClientController(getService());
        }
        return clientController;
    }
}
