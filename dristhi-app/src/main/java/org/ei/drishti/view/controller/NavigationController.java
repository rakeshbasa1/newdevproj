package org.ei.drishti.view.controller;

import android.app.Activity;
import android.content.Intent;

import org.ei.drishti.mamoni.elco.MamoniELigibleCoupleSmartRegisterActivity;
import org.ei.drishti.mamoni.pw.MamoniANCRegisterActivity;
import org.ei.drishti.view.activity.*;

import static org.ei.drishti.view.controller.ProfileNavigationController.*;

public class NavigationController {
    private Activity activity;
    private ANMController anmController;

    public NavigationController(Activity activity, ANMController anmController) {
        this.activity = activity;
        this.anmController = anmController;
    }

    public void startReports() {
        activity.startActivity(new Intent(activity, ReportsActivity.class));
    }

    public void startVideos() {
        activity.startActivity(new Intent(activity, VideosActivity.class));
    }

    public void startECSmartRegistry() {
//        activity.startActivity(new Intent(activity, NativeECSmartRegisterActivity.class));
//        activity.startActivity(new Intent(activity, NativePersonSmartRegisterActivity.class));
        activity.startActivity(new Intent(activity, MamoniELigibleCoupleSmartRegisterActivity.class));
    }

    public void startFPSmartRegistry() {
        activity.startActivity(new Intent(activity, NativeFPSmartRegisterActivity.class));
    }

    public void startANCSmartRegistry() {
        activity.startActivity(new Intent(activity, MamoniANCRegisterActivity.class));
    }

    public void startPNCSmartRegistry() {
        activity.startActivity(new Intent(activity, NativePNCSmartRegisterActivity.class));
    }

    public void startChildSmartRegistry() {
        activity.startActivity(new Intent(activity, NativeChildSmartRegisterActivity.class));
    }

    public String get() {
        return anmController.get();
    }

    public void goBack() {
        activity.finish();
    }

    public void startEC(String entityId) {
        navigateToECProfile(activity, entityId);
    }

    public void startANC(String entityId) {
        navigateToANCProfile(activity, entityId);
    }

    public void startPNC(String entityId) {
        navigateToPNCProfile(activity, entityId);
    }

    public void startChild(String entityId) {
        navigateToChildProfile(activity, entityId);
    }
}
