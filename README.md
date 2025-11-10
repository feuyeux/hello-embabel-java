# Hello Embabel in Java

# To run

Run the shell script to start Embabel under Spring Shell:

```bash
mvn spring-boot:run -s ../config/settings.xml
```

There is a single example agent, `WriteAndReviewAgent`.
It uses one LLM with a high temperature and creative persona to write a story based on your input,
then another LLM with a low temperature and different persona to review the story.

When the Embabel shell comes up, use the story agent like this:

```
x "Tell me a story about Tom and Jerry"
```
