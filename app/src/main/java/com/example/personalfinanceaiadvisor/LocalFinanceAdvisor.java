package com.example.personalfinanceaiadvisor;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Provides basic, hardcoded answers for common finance questions.
 * This acts as a fallback or a way to handle simple queries without
 * making a network call to the Gemini API.
 */
public class LocalFinanceAdvisor {

    private static final Map<String, String> QA_PAIRS = new HashMap<>();

    static {
        // Initialize the map with basic question/answer pairs
        // NOTE: All questions are converted to lowercase for simple matching.

        // Question 1: Budgeting Rule
        QA_PAIRS.put("what is the 50/30/20 rule",
                "The 50/30/20 rule is a simple budgeting guideline: 50% of your income goes to Needs (rent, food, bills), 30% goes to Wants (entertainment, dining out), and 20% goes to Savings and Debt Repayment (investments, loans).");

        // Question 2: Emergency Fund
        QA_PAIRS.put("how much should my emergency fund be",
                "A good emergency fund should cover 3 to 6 months of your essential living expenses. It should be kept in a high-yield savings account so it's easily accessible but still earning a little interest.");

        // Question 3: Credit Score
        QA_PAIRS.put("how can i improve my credit score",
                "To improve your credit score, focus on these key areas: 1) Pay bills on time (payment history is crucial), 2) Reduce debt (especially credit card balances), and 3) Keep old accounts open to maintain a long credit history.");

        // Question 4: Monthly Savings Target (NEW)
        QA_PAIRS.put("how much to save monthly",
                "A common financial goal is to save at least 20% of your net income each month. This aligns well with the 50/30/20 budgeting rule, which dedicates 20% for savings and debt repayment.");

        // Question 5: Simple Investing Definition (NEW)
        QA_PAIRS.put("what is investing",
                "Investing is the act of allocating resources, usually money, in the expectation of generating an income or profit. This could be buying stocks, bonds, or real estate.");
    }

    /**
     * Checks if the given user query has a predefined local answer.
     * @param query The user's input question.
     * @return The hardcoded answer if found, or null otherwise.
     */
    public static String getLocalAnswer(String query) {
        if (query == null) {
            return null;
        }
        // Normalize the query to lowercase and trim spaces for consistent matching
        String normalizedQuery = query.toLowerCase(Locale.ROOT).trim();

        for (Map.Entry<String, String> entry : QA_PAIRS.entrySet()) {
            // Use contains() for a more flexible match than equals()
            if (normalizedQuery.contains(entry.getKey())) {
                return "Local Advisor: " + entry.getValue();
            }
        }
        return null;
    }
}