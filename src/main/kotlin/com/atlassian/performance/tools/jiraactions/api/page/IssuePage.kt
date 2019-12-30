package com.atlassian.performance.tools.jiraactions.api.page

import com.atlassian.performance.tools.jiraactions.api.memories.Project
import com.atlassian.performance.tools.jiraactions.page.UserProfilePage
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions.or
import org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated
import java.lang.Exception
import java.time.Duration

class IssuePage(
    private val driver: WebDriver
) {
    private val reporterUserField = By.cssSelector("#reporter-val span")
    private val assigneeUserField = By.cssSelector("#assignee-val span")
    private val moreActionButton = By.id("opsbar-operations_more")
    private val archiveIssue = By.id("archive-issue")
    private val archiveIssueConf = By.id("archive-issue-submit")
    private val deleteIssue = By.id("delete-issue")
    private val deleteIssueConf = By.id("delete-issue-submit")
    private val projectNameInBreadcrumbs = By.id("project-name-val")
    private val summaryField = By.id("summary-val")

    fun waitForSummary(): IssuePage {
        val jiraErrors = JiraErrors(driver)
        driver.wait(
            timeout = Duration.ofSeconds(10),
            condition = or(
                visibilityOfElementLocated(summaryField),
                jiraErrors.anyCommonError()
            )
        )
        jiraErrors.assertNoErrors()
        return this
    }

    fun isEditable(): Boolean {
        return driver
            .findElements(By.id("edit-issue"))
            .firstOrNull()
            ?.getAttribute("href") != null
    }

    fun getReporter(): String? {
        return driver.findElements(reporterUserField).firstOrNull()?.getAttribute("rel")
    }

    fun archive(): Boolean {
        return try {
            driver.findElement(moreActionButton).click()
            driver.findElement(archiveIssue).click()
            driver.wait(Duration.ofSeconds(1), visibilityOfElementLocated(archiveIssueConf))
                .click()
            true
        } catch (ee: Exception) {
            false
        }
    }

    fun delete(): Boolean {
        return try {
            driver.findElement(moreActionButton).click()
            driver.findElement(deleteIssue).click()
            driver.wait(Duration.ofSeconds(1), visibilityOfElementLocated(deleteIssueConf))
                .click()
            true
        } catch (ee: Exception) {
            false
        }
    }

    fun getPossiblePriorities(): List<String>? {
        driver
            .findElements(By.cssSelector("#priority-val .drop-menu"))
            .firstOrNull()
            ?.click()
            ?: return null
        return driver
            .wait(Duration.ofSeconds(2), visibilityOfElementLocated(By.id("priority-suggestions")))
            .findElements(By.tagName("a"))
            .map { it.text }
    }

    fun getProject(): Project? {
        val projectNameElement = driver.findElement(projectNameInBreadcrumbs)
        val projectName = projectNameElement.text
        val projectKey = projectNameElement.getAttribute("href").split("/browse/").last()
        return Project(projectKey, projectName)
    }

    fun getAssignee(): String? {
        return driver.findElements(assigneeUserField).firstOrNull()?.getAttribute("rel")
    }

    internal fun visitReporterProfile(): UserProfilePage? {
        val reporter = getReporter() ?: return null
        return UserProfilePage(driver).navigateTo(reporter).waitForPageLoad()
    }

    fun getSummary(): String {
        return driver.findElement(By.id("summary-val")).text
    }

    fun getDescription(): String {
        return driver.findElement(By.cssSelector("#description-val .user-content-block")).text
    }

    fun getIssueId(): Long = driver.findElement(By.id("key-val")).getAttribute("rel").toLong()
    fun getIssueType(): String = driver.findElement(By.id("type-val")).text
}
