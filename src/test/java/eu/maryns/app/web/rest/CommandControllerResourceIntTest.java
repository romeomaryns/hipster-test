package eu.maryns.app.web.rest;

import eu.maryns.app.HipsterApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the CommandController REST controller.
 *
 * @see CommandControllerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HipsterApp.class)
public class CommandControllerResourceIntTest {

    private MockMvc restMockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        CommandControllerResource commandControllerResource = new CommandControllerResource();
        restMockMvc = MockMvcBuilders
            .standaloneSetup(commandControllerResource)
            .build();
    }

    /**
    * Test loadAccounts
    */
    @Test
    public void testLoadAccounts() throws Exception {
        restMockMvc.perform(get("/api/command-controller/load-accounts"))
            .andExpect(status().isOk());
    }
    /**
    * Test loadInstruments
    */
    @Test
    public void testLoadInstruments() throws Exception {
        restMockMvc.perform(get("/api/command-controller/load-instruments"))
            .andExpect(status().isOk());
    }

}
