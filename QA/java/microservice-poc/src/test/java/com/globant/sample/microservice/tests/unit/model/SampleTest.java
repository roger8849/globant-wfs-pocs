package com.globant.sample.microservice.tests.unit.model;

import com.globant.sample.microservice.Sample;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * SAMPLE REGULAR UNIT TEST
 *
 * @author Juan Krzemien
 */
@RunWith(MockitoJUnitRunner.class)
public class SampleTest {

    private static final String SOME_STRING = "Hi there!";

    @Mock
    Sample classUnderTest;

    @Before
    public void setUp() {
        assertNotNull("Mockito injections are not working", classUnderTest);
        reset(classUnderTest);
    }

    @Test
    public void reallyStupidTest() {
        when(classUnderTest.getContent()).thenReturn(SOME_STRING);
        assertEquals("Mockito does not work", classUnderTest.getContent(), SOME_STRING);
        verify(classUnderTest, times(1)).getContent();
        verifyNoMoreInteractions(classUnderTest);
    }

}
