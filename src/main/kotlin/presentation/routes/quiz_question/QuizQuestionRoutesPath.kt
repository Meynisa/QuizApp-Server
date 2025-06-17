package org.aprikot.presentation.routes.quiz_question

import io.ktor.resources.Resource

@Resource(path = "quiz/questions")
class QuizQuestionRoutesPath(
    val topicCode: Int? = null,
    val limit: Int? = null
) {

    @Resource(path = "/{questionId}")
    data class ById(
        val parent: QuizQuestionRoutesPath = QuizQuestionRoutesPath(),
        val questionId: String
    )

    @Resource(path = "/batch")
    data class Batch(
        val parent: QuizQuestionRoutesPath = QuizQuestionRoutesPath()
    )
}