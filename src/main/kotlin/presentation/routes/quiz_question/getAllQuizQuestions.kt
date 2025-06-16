package org.aprikot.presentation.routes.quiz_question

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.aprikot.domain.repository.QuizQuestionRepository
import org.aprikot.domain.util.onFailure
import org.aprikot.domain.util.onSuccess
import org.aprikot.presentation.util.respondWithError
import io.ktor.server.resources.*

fun Route.getAllQuizQuestions(
    quizQuestionRepository: QuizQuestionRepository
){
   get<QuizQuestionRoutesPath> { path ->
       quizQuestionRepository.getAllQuestions(path.topicCode, path.limit)
           .onSuccess { questions ->
               call.respond(
                   message = questions,
                   status = HttpStatusCode.OK
               )
           }
           .onFailure { error ->
               respondWithError(error)
           }
   }
}