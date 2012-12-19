package org.ei.drishti.domain;

import java.util.List;

import static java.util.Arrays.asList;
import static org.ei.drishti.domain.ReportIndicator.*;

public enum ReportsCategory {
    FP("Family Planning", asList(IUD, CONDOM, OCP, MALE_STERILIZATION, FEMALE_STERILIZATION)),
    VACCINE_PROGRAM_CHILD("Child Vaccination", asList(DPT, HEP, OPV, MEASLES, BCG)),
    MOTHER_CHILD_HEALTH("Mother Child Health", asList(EARLY_ANC_REGISTRATIONS, ANC_REGISTRATIONS, TT, LBW)),
    MOTHER_CHILD_DELIVERY("Mother Child Delivery", asList(LIVE_BIRTH, STILL_BIRTH, CHILD_MORTALITY, EARLY_ABORTIONS, LATE_ABORTIONS));

    private String description;
    private List<ReportIndicator> indicators;

    ReportsCategory(String description, List<ReportIndicator> indicators) {
        this.description = description;
        this.indicators = indicators;
    }

    public List<ReportIndicator> indicators() {
        return indicators;
    }

    public String description() {
        return description;
    }

    public String value() {
        return name();
    }
}