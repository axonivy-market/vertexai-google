Vertex AI Google

Vertex AI ist eine Plattform von Google Cloud, die speziell für die Entwicklung und Bereitstellung von maschinellen Lernmodellen entwickelt wurde. Es bietet eine Reihe von Dienstleistungen und Werkzeugen, um den gesamten Lebenszyklus von maschinellen Lernprojekten zu unterstützen, von der Datenverarbeitung und Vorbereitung bis zur Modellentwicklung, Bereitstellung und Management in der Produktion. 

Für unseren Steckverbinder haben wir uns auf das Gemini-Modell konzentriert. Gemini ist ein KI-Modell, optimiert für multimodale Aufgaben, insbesondere **verarbeitende visuelle und textuelle Eingaben*. Es ermöglicht Funktionalitäten wie
- visuelles Verständnis
- Klassifizierung
- Zusammenfassung
- und Texterzeugung basierend auf Bildern.

oder Demonstranten

### Use Case: Texterzeugung auf der Grundlage von Bildern

![](bilder/chat-with-gemini.jpg)

### Wechseln zwischen den beiden Modellen mit einem Dropdown-Menü

![](bilder/auswahl-modell-zu-chat.jpg) 

Hinweis: Für unsere einfache "Cat-Use-Case" macht es keinen Unterschied mit der Vertex-AI-Gemini-API oder der Gemini-API direkt.

- Verwenden Sie **Vertex AI Gemini API**, wenn Sie lieber das Cloud-basierte Google AI-Universum (BigDataQuery, ModelTraining und - Bereitstellung, Monitoring und Protokollierung) verwenden möchten.
- Ja. Wenn Sie auf der Suche nach einer eher flexiblen, kostengünstigen und einfachen Verwendung API als die **Gemini-API** direkt verwenden.

- Setup

### Gemini

oder Gehen Sie zu Gemini: [Gemini Online](https://aistudio.google.com/app/apikey) und erstellen Sie einen API-Schlüssel

![](Bilder/create-new-API-key-for-gemini.jpg)

oder Erhalten Sie den API-Schlüssel

![](bilder/generiert-gemini-key.jpg)

### VertexAI

oder Melden Sie sich an [Vertex Online](https://console.cloud.google.com/) mit Konto

#### Select Dashboard -> Create a new project

![](images/create-new-project-in-vertexAi.jpg)

#### Copy "Project ID" from project to push to variables file

#### From Dashboard, scroll down to "Getting Started" and select "Explore and enable APIs"

![](images/select-and-enable-apis.jpg)

#### Select "ENABLE APIS AND SERVICES"

![](images/select-enable-apis-and-services.jpg)

#### Search "vertex ai api" and enable it (Google Cloud will request billing account) in [Model Garden Gemini-1.5 pro](https://console.cloud.google.com/vertex-ai/publishers/google/model-garden/gemini-1.5-pro-001)

#### Go back to the main page of Google cloud and select **_"IAM & Admin"_**

#### Select **_"IAM & Admin"_** -> **_"Service Accounts"_** -> **_"Create Service Account"_**

![](images/go-to-setup-IAM-Admin.jpg)

#### Create an account and grant permission/role for this account

#### Select the account that just created and select "Key" and choose "ADD key"

![](images/get-Key.jpg)

#### "ADD Key" -> "Create new Key" -> "Json" -> Google Cloud automatically download a credential json file.

#### Keep this file and put the path of the file to `Variables.vertexaiGemini.keyFilePath`

#### Select the modelName from: [Vertex AI Gemini Model name](https://console.cloud.google.com/vertex-ai/publishers/google/model-garden/gemini-1.5-pro-001)

#### Select the Location from: [Vertex AI Location/Region](https://cloud.google.com/compute/docs/regions-zones)

Fügen Sie die folgenden `Variables` zu Ihren `variables. yaml`:

```
@variables.yaml@
```

und die werte durch ihr vorgegebenes setup ersetzen.

> [!NOTE]
> The variable path `vertexai-gemini` is renamed to `vertexaiGemini` from 13.
