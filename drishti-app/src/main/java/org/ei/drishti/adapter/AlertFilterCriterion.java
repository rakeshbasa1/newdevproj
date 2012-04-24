package org.ei.drishti.adapter;

import java.util.List;

import static java.util.Arrays.asList;

public enum AlertFilterCriterion {
    ANC("ANC", "ANC"), All("All", ""), BCG("BCG", "BCG"), HEP("Hepatitis", "HEP"), OPV("OPV", "OPV");

    private String display;
    private String visitCodePrefix;

    AlertFilterCriterion(String display, String visitCodePrefix) {
        this.display = display;
        this.visitCodePrefix = visitCodePrefix;
    }

    public String toString() {
        return display;
    }

    public String visitCodePrefix() {
        return visitCodePrefix;
    }

    public static List<AlertFilterCriterion> valuesInOrder() {
        return asList(All, BCG, HEP, OPV);
    }
}