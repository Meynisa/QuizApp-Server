package org.aprikot.presentation.routes.quiz_question

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.resources.*
import org.aprikot.domain.repository.QuizQuestionRepository
import org.aprikot.domain.util.onFailure
import org.aprikot.domain.util.onSuccess
import org.aprikot.presentation.util.respondWithError

fun Route.getQuizQuestionById(
    quizQuestionRepository: QuizQuestionRepository
){
  get<QuizQuestionRoutesPath.ById> { path ->
      quizQuestionRepository.getQuestionById(path.questionId)
          .onSuccess { question ->
              call.respond(
                  message = question,
                  status = HttpStatusCode.OK
              )
          }
          .onFailure { error ->
              respondWithError(error)
          }
  }
}