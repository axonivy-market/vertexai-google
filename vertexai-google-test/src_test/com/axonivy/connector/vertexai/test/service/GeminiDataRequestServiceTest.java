package com.axonivy.connector.vertexai.test.service;


import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.axonivy.connector.vertexai.entities.Conversation;
import com.axonivy.connector.vertexai.enums.*;
import com.axonivy.connector.vertexai.mock.DataMock;
import com.axonivy.connector.vertexai.mock.constants.VertexaiCommonConstants;
import com.axonivy.connector.vertexai.mock.context.MultiEnvironmentContextProvider;
import com.axonivy.connector.vertexai.mock.utils.VertexaiUtils;
import com.axonivy.connector.vertexai.service.GeminiDataRequestService;
import com.axonivy.connector.vertexai.utils.GeminiDataRequestServiceUtils;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;

import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;
import ch.ivyteam.ivy.environment.AppFixture;

@IvyProcessTest(enableWebServer = true)
@ExtendWith(MultiEnvironmentContextProvider.class)
public class GeminiDataRequestServiceTest {
	private GeminiDataRequestService geminiDataRequestService;
	MockedStatic<GeminiDataRequestServiceUtils> geminiDataRequestServiceMock;
	MockedStatic<ServiceAccountCredentials> mockedServiceAccountCredentialsStatic;
	MockedStatic<HttpClient> httpClientMockedStatic;

	@BeforeEach
	void beforeEach(ExtensionContext context, AppFixture fixture) {
		geminiDataRequestService = new GeminiDataRequestService();
		if (context.getDisplayName().equals(VertexaiCommonConstants.REAL_CALL_CONTEXT_DISPLAY_NAME)) {
			VertexaiUtils.setUpConfigForApiTest(fixture);
		} else {
			VertexaiUtils.setUpConfigForMockServer(fixture);
			geminiDataRequestServiceMock = Mockito.mockStatic(GeminiDataRequestServiceUtils.class);
			mockedServiceAccountCredentialsStatic = Mockito.mockStatic(ServiceAccountCredentials.class);
			httpClientMockedStatic = mockStatic(HttpClient.class);
		}

	}

	@AfterEach
	void afterEach(ExtensionContext context) {
		if (!context.getDisplayName().equals(VertexaiCommonConstants.REAL_CALL_CONTEXT_DISPLAY_NAME)) {
			geminiDataRequestServiceMock.close();
			mockedServiceAccountCredentialsStatic.close();
			httpClientMockedStatic.close();
		}
		geminiDataRequestService.cleanData();
	}

	@TestTemplate
	public void testSendRequestToGemini_SuccessResponse(ExtensionContext context)
			throws IOException, InterruptedException {
		boolean isRealTest = context.getDisplayName().equals(VertexaiCommonConstants.REAL_CALL_CONTEXT_DISPLAY_NAME);
		String message = "";
		if (!isRealTest) {
			mockAccessToken();
			mockHttpClient(200);
		}
		List<Conversation> result = geminiDataRequestService.sendRequestToGemini(message, Model.GEMINI);
		assertEquals(2, result.size());
		assertTrue(result.get(1).getText().contains("2"));
		assertEquals(Role.MODEL.getName(), result.get(1).getRole());
	}

	protected void mockAccessToken() throws IOException {
		String MOCK_TOKEN = "mockToken";

		// Mock the AccessToken
		AccessToken mockAccessToken = new AccessToken(MOCK_TOKEN, null);

		// Mock the GoogleCredentials and ServiceAccountCredentials
		GoogleCredentials mockGoogleCredentials = mock(GoogleCredentials.class);
		ServiceAccountCredentials mockServiceAccountCredentials = mock(ServiceAccountCredentials.class);
		FileInputStream mockFileInputStream = mock(FileInputStream.class);

		// Stub the behavior of FileInputStream
		geminiDataRequestServiceMock
				.when(() -> GeminiDataRequestServiceUtils.getInputStream(GeminiDataRequestService.VERTEX_KEY_FILE_PATH))
				.thenReturn(mockFileInputStream);
		// Use MockedStatic for the static method call to fromStream

		mockedServiceAccountCredentialsStatic.when(() -> ServiceAccountCredentials.fromStream(mockFileInputStream))
				.thenReturn(mockServiceAccountCredentials);

		// When createScoped is called, return the mocked GoogleCredentials
		when(mockServiceAccountCredentials.createScoped(List.of("https://www.googleapis.com/auth/cloud-platform")))
				.thenReturn(mockGoogleCredentials);

		// When refreshAccessToken is called, return the mocked AccessToken
		when(mockGoogleCredentials.refreshAccessToken()).thenReturn(mockAccessToken);
	}

	protected void mockHttpClient(int statusCode) {
		String mockResponseBody = DataMock.load("json/responseContent.json");

		// Mocking HttpClient
		HttpClient httpClient = mock(HttpClient.class);
		httpClientMockedStatic.when(HttpClient::newHttpClient).thenReturn(httpClient);

		// Mocking HttpResponse
		@SuppressWarnings("unchecked")
		HttpResponse<String> httpResponse = mock(HttpResponse.class);
		when(httpResponse.statusCode()).thenReturn(statusCode);
		when(httpResponse.body()).thenReturn(mockResponseBody);

		httpClientMockedStatic.when(() -> httpClient.send(ArgumentMatchers.any(), ArgumentMatchers.any()))
				.thenReturn(httpResponse);
	}
}
