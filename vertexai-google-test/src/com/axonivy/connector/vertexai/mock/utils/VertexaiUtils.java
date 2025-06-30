package com.axonivy.connector.vertexai.mock.utils;

import ch.ivyteam.ivy.environment.AppFixture;

public class VertexaiUtils {

	public static void setUpConfigForApiTest(AppFixture fixture) {
		String geminiApiKey = System.getProperty("geminiApiKey");
		fixture.var("gemini.apiKey", geminiApiKey);
	}

	public static void setUpConfigForMockServer(AppFixture fixture) {
		fixture.var("vertexai-gemini.projectId", "generate-images-for-process");
		fixture.var("vertexai-gemini.location", "us-central");
		fixture.var("vertexai-gemini.modelName", "gemini-1.5-pro-preview-0409");
		fixture.var("vertexai-gemini.keyFilePath", "D:\\test.json");
		fixture.var("gemini.apiKey", "AIzaSyDaxbn4Ragu");
	}
}
