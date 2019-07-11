package com.atlassian.performance.tools.jiraactions.api.memories.adaptive

import com.atlassian.performance.tools.jiraactions.api.SeededRandom
import com.atlassian.performance.tools.jiraactions.api.memories.JqlTypes
import com.atlassian.performance.tools.jiraactions.api.page.IssuePage
import junit.framework.Assert.assertEquals
import org.junit.Test

class AdaptiveJqlMemoryTest {

    private val testedClass = AdaptiveJqlMemory(SeededRandom(1L))

    @Test
    fun enumAPI() {
        //Given:
        testedClass.observe(IssuePage(WebDriverMock()))

        //When:
        val recall = testedClass.recall(JqlTypes.AStar)

        //Then:
        assertEquals("a*", recall)
    }

    @Test
    fun filterAPI() {
        //Given:
        testedClass.observe(IssuePage(WebDriverMock()))

        //When:
        val recall = testedClass.recall { v -> v == "" }

        //Then:
        assertEquals("a*", recall)
    }

    @Test
    fun oldAPI() {
        //Given:
        testedClass.observe(IssuePage(WebDriverMock()))

        //When
        val recall = testedClass.recall()

        //Then:
        assertEquals("resolved is not empty order by description", recall)
    }
}
