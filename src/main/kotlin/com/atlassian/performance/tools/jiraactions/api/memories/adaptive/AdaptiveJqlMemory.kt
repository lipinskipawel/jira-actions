package com.atlassian.performance.tools.jiraactions.api.memories.adaptive

import com.atlassian.performance.tools.jiraactions.api.SeededRandom
import com.atlassian.performance.tools.jiraactions.api.memories.JqlMemory
import com.atlassian.performance.tools.jiraactions.api.memories.JqlTypes
import com.atlassian.performance.tools.jiraactions.api.memories.adaptive.jql.JqlPrescription
import com.atlassian.performance.tools.jiraactions.api.memories.adaptive.jql.JqlPrescriptions
import com.atlassian.performance.tools.jiraactions.api.page.IssuePage
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class AdaptiveJqlMemory(
    private val random: SeededRandom
) : JqlMemory {

    private val logger: Logger = LogManager.getLogger(this::class.java)

    private val jqls = mutableListOf(
        "resolved is not empty order by description",
        "text ~ \"a*\" order by summary"
    )
    private val jqlPrescriptions = mutableSetOf(
        JqlPrescriptions.prioritiesInEnumeratedList(random),
        JqlPrescriptions.specifiedProject,
        JqlPrescriptions.specifiedAssignee,
        JqlPrescriptions.previousReporters,
        JqlPrescriptions.specifiedAssigneeInSpecifiedProject,
        JqlPrescriptions.filteredByGivenWord(random)
    )

    override fun observe(issuePage: IssuePage) {
        val bakedJql = jqlPrescriptions.asSequence()
            .map { BakedJql(it, it(issuePage)) }
            .filter { it.jql != null }
            .firstOrNull()

        bakedJql?.let {
            logger.debug("Rendered a new jql query: <<${it.jql!!}>>")
            jqls.add(it.jql)
            jqlPrescriptions.remove(it.jqlPrescription)
        }
    }

    override fun recall(): String? {
        return random.pick(jqls)
    }

    override fun recall(func: (String) -> Boolean): String? {
        return "empty jql"
    }

    override fun remember(memories: Collection<String>) {
        jqls.addAll(memories)
    }
}

data class BakedJql(
    val jqlPrescription: JqlPrescription,
    val jql: String?
)
