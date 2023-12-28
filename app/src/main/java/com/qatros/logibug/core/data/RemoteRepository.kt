package com.qatros.logibug.core.data

import com.qatros.logibug.core.data.network.ApiService
import com.qatros.logibug.core.data.request.login.LoginRequest
import com.qatros.logibug.core.data.request.member.InviteMemberRequest
import com.qatros.logibug.core.data.request.project.CreateProjectRequest
import com.qatros.logibug.core.data.request.project.UpdateProjectRequest
import com.qatros.logibug.core.data.request.register.RegisterRequest
import com.qatros.logibug.core.data.request.scenario.CreateScenarioRequest
import com.qatros.logibug.core.data.request.scenario.UpdateScenarioRequest
import com.qatros.logibug.core.data.request.test_case.CreateTestCaseRequest
import com.qatros.logibug.core.data.request.test_case.EditTestCaseRequest
import com.qatros.logibug.core.data.request.version.CreateVersionRequest
import com.qatros.logibug.core.data.request.version.UpdateVersionRequest
import okhttp3.MultipartBody
import javax.inject.Inject

class RemoteRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun login(
        email: String,
        password: String
    ) = apiService.login(
        LoginRequest(
            email,
            password
        )
    )

    suspend fun register(
        name: String,
        email: String,
        password: String,
        passwordConfirmation: String
    ) = apiService.register(
        RegisterRequest(
            name,
            email,
            password,
            passwordConfirmation
        )
    )


    suspend fun getAllProject(
        token: String
    ) = apiService.getAllProject(
        "Bearer $token"
    )

    suspend fun getReportAllProject(
        token: String
    ) = apiService.getReportAllProject(
        "Bearer $token"
    )

    suspend fun deleteProject(
        token: String,
        idProject: Int
    ) = apiService.deleteProject(
        "Bearer $token",
        idProject
    )

    suspend fun getProfiles(
        token: String
    ) = apiService.getProfiles(
        "Bearer $token"
    )

    suspend fun updateProfile(
        token: String,
        imageProfile: MultipartBody
    ) = apiService.addProfile(
        token,
        imageProfile
    )

    suspend fun getProjectById(
        token: String,
        idProject: Int
    ) = apiService.getProjectById(
        "Bearer $token",
        idProject
    )

    suspend fun updateProject(
        token: String,
        idProject: Int,
        name: String,
        typeTest: String,
        platform: String
    ) = apiService.updateProject(
        "Bearer $token",
        idProject,
        UpdateProjectRequest(
            name,
            typeTest,
            platform
        )
    )

    suspend fun addResult(
        token: String,
        imgUrl: MultipartBody
    ) = apiService.addResult(
        token,
        imgUrl
    )

    suspend fun deletedResults(
        token: String,
        userid: Int
    ) = apiService.deletedResults(
        token,
        userid
    )

    suspend fun editResult(
        token: String,
        idResult: Int,
        imgUrl: MultipartBody
    ) = apiService.editResult(
        token,
        idResult,
        imgUrl
    )

    suspend fun createProject(
        token: String,
        name: String,
        typeTest: String,
        platform: String
    ) = apiService.createProject(
        "Bearer $token",
        CreateProjectRequest(
            name,
            typeTest,
            platform

        )
    )

    suspend fun getAllVersion(
        token: String,
        projectId: Int
    ) = apiService.getAllVersion(
        "Bearer $token",
        projectId
    )

    suspend fun getAllNotifications(
        token: String
    )= apiService.getAllNotifications(
        "Bearer $token"
    )

    suspend fun getResult(
        token: String,
        testCaseId: Int
    ) = apiService.getResult(
        "Bearer $token",
        testCaseId
    )

    suspend fun createVersion(
        token: String,
        name: String,
        projectId: Int
    ) = apiService.createVersion(
        "Bearer $token",
        CreateVersionRequest(
            name,
            projectId
        )
    )

    suspend fun deleteVersion(
        token: String,
        versionId: Int
    ) = apiService.deleteVersion(
        "Bearer $token",
        versionId
    )

    suspend fun getVersionById(
        token: String,
        versionId: Int
    ) = apiService.getVersionById(
        "Bearer $token",
        versionId
    )

    suspend fun updateVersion(
        token: String,
        versionId: Int,
        name: String
    ) = apiService.updateVersion(
        "Bearer $token",
        versionId,
        UpdateVersionRequest(
            name
        )
    )

    suspend fun createScenario(
        token: String,
        projectId: Int,
        scenarioName: String
    ) = apiService.createScenario(
        "Bearer $token",
        CreateScenarioRequest(
            projectId,
            scenarioName
        )
    )

    suspend fun getAllScenario(
        token: String,
        projectId: Int
    ) = apiService.getAllScenario(
        "Bearer $token",
        projectId
    )

    suspend fun getScenarioById(
        token: String,
        scenarioId: Int
    ) = apiService.getScenarioById(
        "Bearer $token",
        scenarioId
    )

    suspend fun updateScenario(
        token: String,
        scenarioId: Int,
        name: String
    ) = apiService.updateScenario(
        "Bearer $token",
        scenarioId,
        UpdateScenarioRequest(
            name
        )
    )

    suspend fun deleteScenario(
        token: String,
        scenarioId: Int
    ) = apiService.deleteScenario(
        "Bearer $token",
        scenarioId
    )

    suspend fun deleteMember(
        token: String,
        memberId: Int
    )= apiService.deleteMember(
        "Bearer $token",
        memberId
    )

    suspend fun createTestCase(
        token: String,
        testCaseCategory: String,
        scenarioId: Int,
        versionId: Int,
        preCondition: String,
        testCase: String,
        testStep: String,
        expectation: String
    ) = apiService.createTestCase(
        "Bearer $token",
        CreateTestCaseRequest(
            testCaseCategory,
            scenarioId,
            versionId,
            preCondition,
            testCase,
            testStep,
            expectation
        )
    )

    suspend fun getAllTestCase(
        token: String,
        versionId: Int
    ) = apiService.getAllTestCase(
        "Bearer $token",
        versionId
    )

    suspend fun getTestCaseById(
        token: String,
        testCaseId: Int
    ) = apiService.getTestCaseById(
        "Bearer $token", testCaseId
    )

    suspend fun getResultsById(
        token: String,
        testCaseId: Int
    ) = apiService.getResultsById(
        token, testCaseId
    )

    suspend fun updateTestCase(
        token: String,
        testCaseId: Int,
        testCategory: String,
        preCondition: String,
        testCaseName: String,
        testStep: String,
        expectation: String
    ) = apiService.updateTestCase(
        "Bearer $token",
        testCaseId,
        EditTestCaseRequest(
            testCategory,
            preCondition,
            testCaseName,
            testStep,
            expectation
        )
    )

    suspend fun deleteTestCase(
        token: String,
        testCaseId: Int
    ) = apiService.deleteTestCase(
        "Bearer $token",
        testCaseId
    )

    suspend fun inviteMember(
        token: String,
        projectId: Int,
        email: String,
        role: String
    ) = apiService.inviteMember(
        "Bearer $token",
        InviteMemberRequest(
            projectId,
            email,
            role
        )
    )

    suspend fun getAllMember(
        token: String,
        projectId: Int
    ) = apiService.getAllMember(
        "Bearer $token",
        projectId
    )

    suspend fun getAllReportManual(
        token: String,
        idProject: Int
    ) = apiService.getAllReport(
        token, idProject
    )

    suspend fun getAllReportAutomation(
        token: String,
    projectId: Int
    ) = apiService.getAllReportAutomation(
        token, projectId
    )

    suspend fun getRole(
        token: String,
        projectId: Int
    ) = apiService.getRole(
        token, projectId
    )

    suspend fun cloneVersion(
        token: String,
        versionId: Int
    ) = apiService.cloneVersion(
        token, versionId
    )

    suspend fun filterTestCase(
        token: String,
        versionId: Int,
        scenarioId: Int
    ) = apiService.filterTestCase(
        token, versionId, scenarioId
    )

    suspend fun uploadFileJson(
        token: String,
        fileBody: MultipartBody
    ) = apiService.uploadFileJson(
        "Bearer $token",
        fileBody
    )

    suspend fun getListDataJson(
        token: String,
        versionId: Int
    ) = apiService.getListDataJson(
        "Bearer $token",
        versionId
    )

    suspend fun getAchievement(
        token: String,
        userId : Int
    ) = apiService.getAchievement(
        token, userId
    )

    suspend fun runDataJson(
        token: String,
        versionId: Int,
        dataId: Int
    ) = apiService.runDataJson(
        token, versionId, dataId
    )

    suspend fun deleteUser(
        token: String,
        idUser : Int
    ) = apiService.deleteUser(
        token,
        idUser
    )
}