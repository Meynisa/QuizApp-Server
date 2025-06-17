package org.aprikot.presentation.routes.quiz_topic

import io.ktor.resources.Resource

@Resource(path = "quiz/topics")
class QuizTopicRoutesPath {

    @Resource(path = "/{topicId}")
    data class ById(
        val parent: QuizTopicRoutesPath = QuizTopicRoutesPath(),
        val topicId: String
    )

    @Resource(path = "/batch")
    data class Batch(
        val parent: QuizTopicRoutesPath = QuizTopicRoutesPath()
    )

}