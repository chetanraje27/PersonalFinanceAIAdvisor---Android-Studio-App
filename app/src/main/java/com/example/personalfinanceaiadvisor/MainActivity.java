package com.example.personalfinanceaiadvisor;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etQuery;
    private Button btnAsk;
    private TextView tvResponse;

    private OkHttpClient httpClient;
    private ExecutorService executor;

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    // Groq API endpoint
    private static final String GROQ_API_URL = "https://api.groq.com/openai/v1/chat/completions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etQuery = findViewById(R.id.etQuery);
        btnAsk = findViewById(R.id.btnAsk);
        tvResponse = findViewById(R.id.tvResponse);

        httpClient = new OkHttpClient();
        executor = Executors.newSingleThreadExecutor();

        btnAsk.setOnClickListener(v -> {
            String question = etQuery.getText() != null ? etQuery.getText().toString().trim() : "";
            if (TextUtils.isEmpty(question)) {
                etQuery.setError("Enter a question");
                return;
            }

            String startTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            tvResponse.setText("Thinking... (Query started at " + startTime + ")");

            // 1. Check for local, hardcoded answer first
            String localAnswer = LocalFinanceAdvisor.getLocalAnswer(question);
            if (localAnswer != null) {
                tvResponse.setText(localAnswer);
            } else {
                // 2. If no local answer, call Groq API
                callGroqAPI(question);
            }
        });
    }

    private void callGroqAPI(String prompt) {
        String apiKey = getString(R.string.groq_api_key);
        if (apiKey == null || apiKey.equals("YOUR_GROQ_API_KEY_HERE")) {
            runOnUiThread(() -> tvResponse.setText("Groq API key missing. Get one from https://console.groq.com/keys"));
            return;
        }

        executor.execute(() -> {
            try {
                // Build JSON request body for Groq
                JSONObject body = new JSONObject();

                // Use llama-3.3-70b-versatile - fast and capable model
                // Other options: mixtral-8x7b-32768, gemma2-9b-it, llama-3.1-70b-versatile
                body.put("model", "llama-3.3-70b-versatile");

                // Create messages array
                JSONArray messages = new JSONArray();

                // System message to set context
                JSONObject systemMsg = new JSONObject();
                systemMsg.put("role", "system");
                systemMsg.put("content", "You are a helpful personal finance advisor. Provide clear, practical financial advice.");
                messages.put(systemMsg);

                // User message
                JSONObject userMsg = new JSONObject();
                userMsg.put("role", "user");
                userMsg.put("content", prompt);
                messages.put(userMsg);

                body.put("messages", messages);
                body.put("temperature", 0.7);
                body.put("max_tokens", 1024);

                RequestBody requestBody = RequestBody.create(body.toString(), JSON);
                Request request = new Request.Builder()
                        .url(GROQ_API_URL)
                        .post(requestBody)
                        .addHeader("Authorization", "Bearer " + apiKey)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = httpClient.newCall(request).execute();

                if (!response.isSuccessful()) {
                    final String errorBody = response.body() != null ? response.body().string() : "No response body";
                    final String msg = "API Request Failed."
                            + "\nHTTP Code: " + response.code()
                            + "\nReason: " + response.message()
                            + "\nBody: " + errorBody.substring(0, Math.min(errorBody.length(), 300)) + "...";
                    runOnUiThread(() -> tvResponse.setText(msg));
                    response.close();
                    return;
                }

                String respStr = response.body() != null ? response.body().string() : "";
                response.close();

                // Parse Groq response (OpenAI-compatible format)
                JSONObject json = new JSONObject(respStr);
                JSONArray choices = json.optJSONArray("choices");
                String text = "No response";

                if (choices != null && choices.length() > 0) {
                    JSONObject choice = choices.getJSONObject(0);
                    JSONObject message = choice.optJSONObject("message");
                    if (message != null) {
                        text = message.optString("content", "No content");
                    }
                }

                final String finalText = text.trim();
                runOnUiThread(() -> {
                    String endTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                    tvResponse.setText("Response received at " + endTime + ":\n\n" + finalText);
                });

            } catch (Exception e) {
                final String msg = "Network Error: " + e.getMessage();
                runOnUiThread(() -> tvResponse.setText(msg));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdownNow();
    }
}