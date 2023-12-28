package com.qatros.logibug.core.data.network

import id.qatros.logibug.core.data.source.remote.response.member.DeleteMemberResponse
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
import com.qatros.logibug.core.data.response.achievement.AchievementResponse
import com.qatros.logibug.core.data.response.api_testing.ApiTestingResponse
import com.qatros.logibug.core.data.response.api_testing.ListDataJsonResponse
import com.qatros.logibug.core.data.response.api_testing.RunDataJsonResponse
import com.qatros.logibug.core.data.response.delete_account.DeleteUserResponse
import com.qatros.logibug.core.data.response.login.LoginResponse
import com.qatros.logibug.core.data.response.member.InviteMemberResponse
import com.qatros.logibug.core.data.response.member.ListAllMemberResponse
import com.qatros.logibug.core.data.response.notification.NotificationAllResponse
import com.qatros.logibug.core.data.response.profile.Profiles
import com.qatros.logibug.core.data.response.profile.UpdateProfileResponse
import com.qatros.logibug.core.data.response.project.*
import com.qatros.logibug.core.data.response.register.RegisterResponse
import com.qatros.logibug.core.data.response.report.ReportAutomationResponse
import com.qatros.logibug.core.data.response.report.ReportResponse
import com.qatros.logibug.core.data.response.result.*
import com.qatros.logibug.core.data.response.role.RoleResponse
import com.qatros.logibug.core.data.response.scenario.*
import com.qatros.logibug.core.data.response.test_case.*
import com.qatros.logibug.core.data.response.version.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("login")
    @Headers("Content-Type: application/json")
    suspend fun login(
        @Body login: LoginRequest
    ): Response<LoginResponse>

    @POST("users")
    suspend fun register(
        @Body register: RegisterRequest
    ): Response<RegisterResponse>

    @POST("projects")
    suspend fun createProject(
        @Header("Authorization") token: String,
        @Body createProject: CreateProjectRequest
    ): Response<CreateProjectResponse>

    @GET("projects")
    suspend fun getAllProject(
        @Header("Authorization") token: String
    ): Response<ListProjectResponse>

    @GET("projects")
    suspend fun getReportAllProject(
        @Header("Authorization") token: String
    ): Response<ListProjectResponse>

    @DELETE("projects/{id}")
    suspend fun deleteProject(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<DeleteProjectResponse>

    @GET("/api/v1/profiles")
    suspend fun getProfiles(
        @Header("Authorization") token: String
    ): Response<Profiles>

    @PUT("profiles")
    suspend fun addProfile(
        @Header("Authorization") token: String,
        @Body body: MultipartBody
    ): Response<UpdateProfileResponse>

    @GET("projects/{id}")
    suspend fun getProjectById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<GetProjectByIdResponse>

    @GET("versions")
    suspend fun getAllVersion(
        @Header("Authorization") token: String,
        @Query("project_id") projectId: Int
    ): Response<ListAllVersionResponse>

    @POST("versions")
    suspend fun createVersion(
        @Header("Authorization") token: String,
        @Body createVersion: CreateVersionRequest
    ): Response<CreateVersionResponse>

    @DELETE("versions/{id}")
    suspend fun deleteVersion(
        @Header("Authorization") token: String,
        @Path("id") versionId: Int
    ): Response<DeleteVersionResponse>

    @GET("versions/{id}")
    suspend fun getVersionById(
        @Header("Authorization") token: String,
        @Path("id") versionId: Int
    ): Response<GetVersionByIdResponse>

    @GET("notifications")
    suspend fun getAllNotifications(
        @Header("Authorization") token: String,
    ): Response<NotificationAllResponse>

    @PUT("versions/{id}")
    suspend fun updateVersion(
        @Header("Authorization") token: String,
        @Path("id") versionId: Int,
        @Body update: UpdateVersionRequest
    ): Response<UpdateVersionResponse>

    @POST("scenarios")
    suspend fun createScenario(
        @Header("Authorization") token: String,
        @Body createScenario: CreateScenarioRequest
    ): Response<CreateScenarioResponse>

    @PUT("projects/{id}")
    suspend fun updateProject(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body update: UpdateProjectRequest
    ): Response<UpdateProjectResponse>

    @GET("results")
    suspend fun getResult(
        @Header("Authorization") token: String,
        @Query("test_case_id") testCaseId: Int
    ): Response<DetailTestCaseResultResponse>

    @PUT("results/{id}")
    suspend fun editResult(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body body: MultipartBody
    ): Response<EditResultResponse>

    @POST("results")
    suspend fun addResult(
        @Header("Authorization") token: String,
        @Body body: MultipartBody
    ): Response<AddResultResponse>

    @DELETE("results/{id}")
    suspend fun deletedResults(
        @Header("Authorization") token: String,
        @Path("user_id") userid: Int,
    ): Response<DeletedResultsResponse>

    @GET("scenarios")
    suspend fun getAllScenario(
        @Header("Authorization") token: String,
        @Query("project_id") projectId: Int
    ): Response<ListScenarioResponse>

    @GET("scenarios/{id}")
    suspend fun getScenarioById(
        @Header("Authorization") token: String,
        @Path("id") scenarioId: Int
    ): Response<ScenarioByIdResponse>

    @PUT("scenarios/{id}")
    suspend fun updateScenario(
        @Header("Authorization") token: String,
        @Path("id") scenarioId: Int,
        @Body updateScenario: UpdateScenarioRequest
    ): Response<EditScenarioResponse>

    @DELETE("scenarios/{id}")
    suspend fun deleteScenario(
        @Header("Authorization") token: String,
        @Path("id") scenarioId: Int
    ): Response<DeleteScenarioResponse>

    @DELETE("members/{id}")
    suspend fun deleteMember(
        @Header("Authorization") token: String,
        @Path("id") memberId: Int
    ): Response<DeleteMemberResponse>

    @POST("test_cases")
    suspend fun createTestCase(
        @Header("Authorization") token: String,
        @Body createTestCase: CreateTestCaseRequest
    ): Response<CreateTestCaseResponse>

    @GET("test_cases")
    suspend fun getAllTestCase(
        @Header("Authorization") token: String,
        @Query("version_id") versionId: Int
    ): Response<GetAllTestCaseResponse>

    @GET("test_cases/{id}")
    suspend fun getTestCaseById(
        @Header("Authorization") token: String,
        @Path("id") testCaseId: Int
    ): Response<TestCaseByIdResponse>

    @GET("results/{id}")
    suspend fun getResultsById(
        @Header("Authorization") token: String,
        @Path("id") resultsId: Int
    ): Response<GetResultsByIdResponse>

    @PUT("test_cases/{id}")
    suspend fun updateTestCase(
        @Header("Authorization") token: String,
        @Path("id") testCaseId: Int,
        @Body updateTestCase: EditTestCaseRequest
    ): Response<EditTestCaseResponse>

    @DELETE("test_cases/{id}")
    suspend fun deleteTestCase(
        @Header("Authorization") token: String,
        @Path("id") testCaseId: Int
    ): Response<DeleteTestCaseResponse>

    @POST("members")
    suspend fun inviteMember(
        @Header("Authorization") token: String,
        @Body inviteMember: InviteMemberRequest
    ): Response<InviteMemberResponse>

    @GET("members")
    suspend fun getAllMember(
        @Header("Authorization") token: String,
        @Query("project_id") projectId: Int
    ): Response<ListAllMemberResponse>

    @GET("report/{id}")
    suspend fun getAllReport(
        @Header("Authorization") token: String,
        @Path("id") idProject: Int
    ): Response<ReportResponse>

    @GET("report/{id}")
    suspend fun getAllReportAutomation(
        @Header("Authorization") token: String,
        @Path("id") projectId: Int
    ): Response<ReportAutomationResponse>

    @GET("roles")
    suspend fun getRole(
        @Header("Authorization") token: String,
        @Query("project_id") projectId: Int
    ): Response<RoleResponse>

    @POST("versions/clone/{id}")
    suspend fun cloneVersion(
        @Header("Authorization") token: String,
        @Path("id") versionId: Int
    ): Response<CloneVersionResponse>

    @GET("test_cases")
    suspend fun filterTestCase(
        @Header("Authorization") token: String,
        @Query("version_id") versionId: Int,
        @Query("scenario_id") scenarioId: Int
    ): Response<FilterTestCaseResponse>

    @POST("automatic")
    suspend fun uploadFileJson(
        @Header("Authorization") token: String,
        @Body body: MultipartBody
    ): Response<ApiTestingResponse>

    @GET("automatic/{id}")
    suspend fun getListDataJson(
        @Header("Authorization") token: String,
        @Path("id") versionId: Int
    ): Response<ListDataJsonResponse>

    @GET("achieve")
    suspend fun getAchievement(
        @Header("Authorization") token: String,
        @Query("id") userId: Int
    ): Response<AchievementResponse>

    @GET("automatic/run/{versionId}/{id}")
    suspend fun runDataJson(
        @Header("Authorization") token: String,
        @Path("versionId") versionId: Int,
        @Path("id") dataId: Int
    ): Response<RunDataJsonResponse>

    @DELETE("users/{id}")
    suspend fun deleteUser(
        @Header("Authorization") token: String,
        @Path("id") idUser: Int
    ): Response<DeleteUserResponse>
}