package org.ei.drishti.view;

import android.view.View;
import android.widget.ProgressBar;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.controller.AlertController;
import org.ei.drishti.domain.FetchStatus;
import org.ei.drishti.service.ActionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class UpdateAlertsTaskTest {
    @Mock
    private AlertController alertController;
    @Mock
    private ProgressBar progressBar;
    @Mock
    private ActionService actionService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldShowProgressBarsWhileFetchingAlerts() throws Exception {
        UpdateAlertsTask updateAlertsTask = new UpdateAlertsTask(null, actionService, alertController, progressBar);
        when(actionService.fetchNewActions()).thenReturn(FetchStatus.fetched);

        updateAlertsTask.updateFromServer();

        InOrder inOrder = inOrder(actionService, alertController, progressBar);
        inOrder.verify(progressBar).setVisibility(View.VISIBLE);
        inOrder.verify(actionService).fetchNewActions();
        inOrder.verify(alertController).refreshAlertsFromDB();
        inOrder.verify(progressBar).setVisibility(View.INVISIBLE);
    }


    @Test
    public void shouldNotUpdateDisplayIfNothingWasFetched() throws Exception {
        UpdateAlertsTask updateAlertsTask = new UpdateAlertsTask(null, actionService, alertController, progressBar);
        when(actionService.fetchNewActions()).thenReturn(FetchStatus.nothingFetched);

        updateAlertsTask.updateFromServer();

        verify(alertController, times(0)).refreshAlertsFromDB();
    }
}
