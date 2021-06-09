package org.kpi.mmsa.model;

import org.kpi.mmsa.mvc.Model;

public class Result {
    private Model model;
    private String log;

    public Result(Model model, String log) {
        this.model = model;
        this.log = log;
    }

    public Model getModel() {
        return model;
    }

    public String getLog() {
        return log;
    }
}
