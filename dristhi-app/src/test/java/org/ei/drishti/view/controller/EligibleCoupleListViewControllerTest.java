package org.ei.drishti.view.controller;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.service.CommCareClientService;
import org.ei.drishti.view.contract.EC;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class EligibleCoupleListViewControllerTest {
    @Mock
    private AllEligibleCouples allEligibleCouples;
    @Mock
    private Context context;
    @Mock
    private CommCareClientService commCareClientService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void shouldSortANCsByName() throws Exception {
        when(allEligibleCouples.all()).thenReturn(asList(new EligibleCouple("EC Case 2", "woman B", "Husband B", "EC Number 2", "Bherya", "Bherya SC", new HashMap<String, String>()),
                new EligibleCouple("EC Case 3", "Woman C", "Husband C", "EC Number 3", "Bherya", "Bherya SC", new HashMap<String, String>()), new EligibleCouple("EC Case 1", "Woman A", "Husband A", "EC Number 1", "Bherya", "Bherya SC", new HashMap<String, String>())));

        EligibleCoupleListViewController controller = new EligibleCoupleListViewController(allEligibleCouples, context, commCareClientService);
        List<EC> ecs = new Gson().fromJson(controller.get(), new TypeToken<List<EC>>() { }.getType());

        assertEquals("Woman A", ecs.get(0).wifeName());
        assertEquals("woman B", ecs.get(1).wifeName());
        assertEquals("Woman C", ecs.get(2).wifeName());
    }
}