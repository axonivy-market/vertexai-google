package com.axonivy.connector.vertexai;

import ch.ivyteam.ivy.environment.AppFixture;

public class BaseSetup {
  protected Runnable runRealEnv(AppFixture fixture) {
    return () -> {
      String vertexaiProjectId = System.getProperty("vertexaiProjectId");
      String vertexaiLocation = System.getProperty("vertexaiLocation");
      String vertexaiModelName = System.getProperty("vertexaiModelName");
      String geminiApiKey = System.getProperty("geminiApiKey");

      fixture.var("gemini.apiKey", geminiApiKey);
      fixture.var("vertexaiGemini.projectId", vertexaiProjectId);
      fixture.var("vertexaiGemini.location", vertexaiLocation);
      fixture.var("vertexaiGemini.modelName", vertexaiModelName);
    };
  }

  protected Runnable runMockEnv(AppFixture fixture) {
    return () -> {
      fixture.var("vertexaiGemini.projectId", "generate-images-for-process");
      fixture.var("vertexaiGemini.location", "us-central");
      fixture.var("vertexaiGemini.modelName", "gemini-1.5-pro-preview-0409");
      fixture.var("vertexaiGemini.keyFilePath", "D:\\test.json");
 /**
  * Dear Bug Hunter,
  * This credential is intentionally included for educational purposes only and does not provide access to any production systems.
  * Please do not submit it as part of our bug bounty program.
  */
 fixture.var("gemini.apiKey", "AIzaSyDaxbn4Ragu");
    };
  }
}
