package com.embabel.template.agent;

import com.embabel.agent.domain.io.UserInput;
import com.embabel.agent.testing.unit.FakeOperationContext;
import com.embabel.agent.testing.unit.FakePromptRunner;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class WriteAndReviewAgentTest {

    @Test
    void testWriteAndReviewAgent() {
        var context = FakeOperationContext.create();
        var promptRunner = (FakePromptRunner) context.promptRunner();
        context.expectResponse(new WriteAndReviewAgent.Story("One upon a time Sir Galahad . . "));

        var agent = new WriteAndReviewAgent(200, 400);
        WriteAndReviewAgent.Story craftStory = agent.craftStory(new UserInput("Tell me a story about a brave knight", Instant.now()), context);
        log.info("Crafted Story: {}", craftStory.text());
        var messages = promptRunner.getLlmInvocations().getFirst().getMessages();
        String messagesText = messages.toString();
        assertTrue(messagesText.contains("knight"), "Expected prompt to contain 'knight'");
    }

    @Test
    void testReview() {
        var agent = new WriteAndReviewAgent(200, 400);
        var userInput = new UserInput("Tell me a story about a brave knight", Instant.now());
        var story = new WriteAndReviewAgent.Story("Once upon a time, Sir Galahad...");
        var context = FakeOperationContext.create();
        context.expectResponse("A thrilling tale of bravery and adventure!");
        WriteAndReviewAgent.ReviewedStory reviewedStory = agent.reviewStory(userInput, story, context);
        log.info("Reviewed Story: {}", reviewedStory.review());
        var llmInvocation = context.getLlmInvocations().getFirst();
        var messages = llmInvocation.getMessages();
        String messagesText = messages.toString();
        assertTrue(messagesText.contains("knight"), "Expected prompt to contain 'knight'");
        assertTrue(messagesText.contains("review"), "Expected prompt to contain 'review'");
    }
}