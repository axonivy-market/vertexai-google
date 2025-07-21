package com.axonivy.connector.vertexai.mock.utils;

import ch.ivyteam.ivy.environment.AppFixture;

public class VertexaiTestUtils {

  public static void setUpConfigForApiTest(AppFixture fixture) {
    String vertexaiProjectId = System.getProperty("vertexaiProjectId");
    String vertexaiLocation = System.getProperty("vertexaiLocation");
    String vertexaiModelName = System.getProperty("vertexaiModelName");
    String vertexaiKeyFilePath = System.getProperty("vertexaiKeyFilePath");
    String geminiApiKey = System.getProperty("geminiApiKey");

    fixture.var("gemini.apiKey", geminiApiKey);
    fixture.var("vertexai-gemini.projectId", vertexaiProjectId);
    fixture.var("vertexai-gemini.location", vertexaiLocation);
    fixture.var("vertexai-gemini.modelName", vertexaiModelName);
    fixture.var("vertexai-gemini.keyFilePath", vertexaiKeyFilePath);
  }

  public static void setUpConfigForMockServer(AppFixture fixture) {
    fixture.var("vertexai-gemini.projectId", "generate-images-for-process");
    fixture.var("vertexai-gemini.location", "us-central");
    fixture.var("vertexai-gemini.modelName", "gemini-1.5-pro-preview-0409");
    fixture.var("vertexai-gemini.keyFilePath", "D:\\test.json");
 /**
  * Dear Bug Hunter,
  * This credential is intentionally included for educational purposes only and does not provide access to any production systems.
  * Please do not submit it as part of our bug bounty program.
  */
 fixture.var("gemini.apiKey", "AIzaSyDaxbn4Ragu");
  }

}
