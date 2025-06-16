package org.aprikot.presentation.routes.quiz_question

import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.delete
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import org.aprikot.domain.repository.QuizQuestionRepository
import org.aprikot.domain.util.onFailure
import org.aprikot.domain.util.onSuccess
import org.aprikot.presentation.util.respondWithError

fun Route.deleteQuizQuestionById(
    quizQuestionRepository: QuizQuestionRepository
){
   delete<QuizQuestionRoutesPath.ById> { path ->
       quizQuestionRepository.deleteQuestionById(path.questionId)
           .onSuccess {
               call.respond(HttpStatusCode.NoContent)
           }
           .onFailure { error ->
               respondWithError(error)
           }
   }
}