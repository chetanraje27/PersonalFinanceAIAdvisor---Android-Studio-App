# PersonalFinanceAIAdvisor---Android-Studio-App
Personal Finance AI Advisor is an Android application that provides expert financial guidance using a hybrid AI approach. It leverages a local knowledge base for instant answers to common questions and integrates the Groq API (Llama 3.3) for complex financial advice, featuring a secure login system

# Personal Finance AI Advisor 💰🤖

Personal Finance AI Advisor is an Android application designed to help users manage their finances better by providing instant, AI-driven financial advice. The app uses a hybrid logic system: it first checks a local repository of financial best practices for common queries and falls back to the high-performance **Groq API** (Llama 3.3 model) for more complex, personalized questions.

## 🚀 Features

- **Hybrid AI Architecture**: 
  - **Local Advisor**: Instant responses for standard rules (e.g., 50/30/20 rule, emergency funds) without needing internet.
  - **Cloud AI**: Deep integration with Groq API for complex financial queries using `llama-3.3-70b-versatile`.
- **User Authentication**: Secure login screen with email/password validation and Google Sign-In stubs.
- **Modern UI**: Built with ConstraintLayout for a responsive and clean user experience.
- **Monetization Ready**: Integrated with **Google AdMob** for banner advertisements.
- **Asynchronous Processing**: Uses `ExecutorService` and `OkHttp` to ensure the UI remains smooth during API calls.

## 🛠️ Technologies Used

- **Language**: Java
- **IDE**: Android Studio
- **Networking**: [OkHttp](https://square.github.io/okhttp/)
- **JSON Parsing**: `org.json`
- **AI Model**: Groq API (Llama 3.3)
- **Monetization**: Google AdMob SDK
- **UI Components**: AndroidX, Material Design



## ⚙️ Setup Instructions

### 1. Prerequisites
- Android Studio Bumblebee or newer.
- A Groq API Key (Get it at [console.groq.com](https://console.groq.com/)).
- An AdMob App ID (optional for testing).

### 2. Configuration
Open `res/values/strings.xml` and add your API keys:

```xml
<string name="groq_api_key">YOUR_GROQ_API_KEY_HERE</string>
<string name="admob_banner_ad_unit_id">ca-app-pub-3940256099942544/6300978111</string>
