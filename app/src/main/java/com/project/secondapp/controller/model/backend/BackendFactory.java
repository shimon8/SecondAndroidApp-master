package com.project.secondapp.controller.model.backend;


import com.project.secondapp.controller.model.datasource.Firebase_DBManager;

public class BackendFactory {
    private static Backend backend = null;

    public static Backend getBackend() {
        if (backend == null)
            backend = (Backend) new Firebase_DBManager();
        return backend;
    }
}
