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

#### #Ausgewählt Armaturenbrett -> Schaffen ein neues Projekt

![](Images/schaffen-neu-Projekt-herein-vertexAi.jpg)

#### Kopie "Projiziert ID" von projiziert zu drängen zu Variablen Datei

#### Von Armaturenbrett, #Buchrolle Hügelland zu "#loslegen" und #ausgewählt "Erkunden und aktivieren APIs"

![](Images/#ausgewählt-und-aktivieren-apis.jpg)

#### #Auswählen "#AKTIVIEREN APIS UND BEDIENUNGEN"

![](Images/#ausgewählt-aktivieren-apis-und-Bedienungen.jpg)

#### Suche "Scheitel ai api" und aktivieren ihm (#Google Wolke wollen auffordern berechnen Konto) herein [Model Garten #Zwillinge-1.5 pro](https://Konsole.Wolke.google.com/Scheitel-ai/Verleger/google/Model-#gärtnern/gemini-1.5-pro-001)

#### Geh zurück zu der hauptsächlichen Seite von #Google Wolke und auswählen *_"IAM & Admin"_**

#### #Ausgewählt *_"IAM & Admin"_** -> *_"#Bespringen Verrechnet"_** -> *_"Schaffen Bedienung Konto"_**

![](Images/gehen-zu-Einrichtung-IAM-Admin.jpg)

#### Schaff ein Konto und gewähr Erlaubnis/Rolle für dieses Konto

#### Wähl aus das Konto jener einfach geschaffenen und #ausgewählt "Schlüssel" und auswählen "#ZUFÜGEN Schlüssel"

![](Images/bekommen-Schlüssel.jpg)

#### "FÜG ZU Schlüssel" -> "Schaffen neuen Schlüssel" -> "Json" -> #Google Wolke herunterladen automatisch einen #Berechtigungsnachweis json Datei.

#### Behalte diese Datei und legen den Pfad von der Datei zu `Variablen.vertexaiGemini.keyFilePath`

#### Wähl aus das modelName von: [Scheitel AI #Zwillinge Model Name](https://Konsole.Wolke.google.com/Scheitel-ai/Verleger/google/Model-#gärtnern/gemini-1.5-pro-001)

#### Wähl aus den Drehort von: [Scheitel AI Drehort/Region](https://Wolke.google.com/Berechnen/docs/Regionen-Zonen)

Fügen Sie die folgenden `Variables` zu Ihren `variables. yaml`:

```
@Variablen.yaml@
```

und die werte durch ihr vorgegebenes setup ersetzen.

> [!BEACHTE]
> Den variablen Pfad `vertexai-gemini` ist #umbenennen zu `vertexaiGemini` von 13.
