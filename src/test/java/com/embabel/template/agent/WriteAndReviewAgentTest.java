package com.embabel.template.agent;

import com.embabel.agent.domain.io.UserInput;
import com.embabel.agent.testing.unit.FakeOperationContext;
import com.embabel.agent.testing.unit.UnitTestUtils;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WriteAndReviewAgentTest {
    
    @Test
    void testWriteAndReviewAgent() {
        var agent = new WriteAndReviewAgent(200, 400);
        var llmCall = UnitTestUtils.captureLlmCall(() -> {
            agent.craftStory(new UserInput("Tell me a story about a brave knight", Instant.now()));
        });
        assertTrue(llmCall.getPrompt().contains("knight"), "Expected prompt to contain 'knight'");
        assertEquals(0.9, llmCall.getLlm().getTemperature(), 0.01,
                "Expected temperature to be 0.9: Higher for more creative output");
    }

    @Test
    void testReview() {
        var agent = new WriteAndReviewAgent(200, 400);
        var userInput = new UserInput("Tell me a story about a brave knight", Instant.now());
        var story = new Story("Once upon a time, Sir Galahad...");
        var context = FakeOperationContext.create();
        context.expectResponse("A thrilling tale of bravery and adventure!");
        agent.reviewStory(userInput, story, context);
        var llmInvocation = context.getLlmInvocations().getFirst();
        assertTrue(llmInvocation.getPrompt().contains("knight"), "Expected prompt to contain 'knight'");
        assertTrue(llmInvocation.getPrompt().contains("review"), "Expected prompt to contain 'review'");
    }

}