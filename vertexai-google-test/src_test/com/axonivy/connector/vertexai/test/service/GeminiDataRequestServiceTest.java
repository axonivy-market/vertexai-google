package com.axonivy.connector.vertexai.test.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
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
import com.axonivy.connector.vertexai.enums.*;
import com.axonivy.connector.vertexai.mock.DataMock;
import com.axonivy.connector.vertexai.mock.constants.VertexaiTestConstants;
import com.axonivy.connector.vertexai.mock.context.MultiEnvironmentContextProvider;
import com.axonivy.connector.vertexai.mock.utils.VertexaiTestUtils;
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

  private static File tempFile = null;

  @BeforeEach
  void beforeEach(ExtensionContext context, AppFixture fixture) {
    if (context.getDisplayName().equals(VertexaiTestConstants.REAL_CALL_CONTEXT_DISPLAY_NAME)) {
      VertexaiTestUtils.setUpConfigForApiTest(fixture);
      tempFile = createTempFile();
      fixture.var("vertexaiGemini.keyFilePath", tempFile.getAbsolutePath());
    } else {
      VertexaiTestUtils.setUpConfigForMockServer(fixture);
      geminiDataRequestServiceMock = Mockito.mockStatic(GeminiDataRequestServiceUtils.class);
      mockedServiceAccountCredentialsStatic = Mockito.mockStatic(ServiceAccountCredentials.class);
      httpClientMockedStatic = mockStatic(HttpClient.class);
    }
    geminiDataRequestService = new GeminiDataRequestService();
  }

  @AfterEach
  void afterEach(ExtensionContext context) {
    if (!context.getDisplayName().equals(VertexaiTestConstants.REAL_CALL_CONTEXT_DISPLAY_NAME)) {
      geminiDataRequestServiceMock.close();
      mockedServiceAccountCredentialsStatic.close();
      httpClientMockedStatic.close();
    } else {
      tempFile.deleteOnExit();
    }
    geminiDataRequestService.cleanData();
  }

  @TestTemplate
  public void testSendRequestToGemini_SuccessResponse(ExtensionContext context)
      throws IOException, InterruptedException {
    boolean isRealTest = context.getDisplayName().equals(VertexaiTestConstants.REAL_CALL_CONTEXT_DISPLAY_NAME);
    String message = "";
    if (!isRealTest) {
      mockAccessToken();
      mockHttpClient(200);
    }
    HttpResponse<String> result = geminiDataRequestService.sendRequest(message, Model.GEMINI);
    if (isRealTest) {
      assertEquals(result.statusCode(), 429);
    } else {
      assertEquals(result.statusCode(), 200);
    }
  }

  @TestTemplate
  public void testSendRequestToVertexGemini_SuccessResponse(ExtensionContext context)
      throws IOException, InterruptedException {
    boolean isRealTest = context.getDisplayName().equals(VertexaiTestConstants.REAL_CALL_CONTEXT_DISPLAY_NAME);
    String message = "";
    if (!isRealTest) {
      mockAccessToken();
      mockHttpClient(200);
    }
    HttpResponse<String> result = geminiDataRequestService.sendRequest(message, Model.VERTEXAI_GEMINI);
    if (isRealTest) {
      assertEquals(result.statusCode(), 403);
      assertTrue(
          result.body().contains("This API method requires billing to be enabled. Please enable billing on project"));
    } else {
      assertEquals(result.statusCode(), 200);
    }
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

  private File createTempFile() {
    try {
      File file = File.createTempFile("test", ".json");
      String vertexaiKeyContent = System.getProperty("vertexaiKeyContent");
      FileWriter writer = new FileWriter(file);
      writer.write(vertexaiKeyContent);
      writer.close();
      return file;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}

