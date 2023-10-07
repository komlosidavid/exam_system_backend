
package inf.unideb.hu.exam.system.service.impl;

import inf.unideb.hu.exam.system.dao.AnswerDao;
import inf.unideb.hu.exam.system.dao.QuestionDao;
import inf.unideb.hu.exam.system.dao.TestDao;
import inf.unideb.hu.exam.system.dao.UserDao;
import inf.unideb.hu.exam.system.models.*;
import inf.unideb.hu.exam.system.models.enums.AnswerType;
import inf.unideb.hu.exam.system.models.enums.QuestionType;
import inf.unideb.hu.exam.system.request.CreateTestEntityRequest;
import inf.unideb.hu.exam.system.request.UpdateTestEntityRequest;
import inf.unideb.hu.exam.system.security.token.TokenService;
import inf.unideb.hu.exam.system.service.TestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Class for implementing service methods.
 */
@Service
@Transactional
@AllArgsConstructor
public class TestServiceImpl implements TestService {

    /**
     * Reference for {@link TestDao}.
     */
    private final TestDao repository;

    /**
     * Reference for {@link UserDao}.
     */
    private final UserDao userRepository;

    /**
     * Reference for {@link QuestionDao}.
     */
    private final QuestionDao questionRepository;

    /**
     * Reference for {@link AnswerDao}.
     */
    private final AnswerDao answerRepository;

    /**
     * Reference for {@link TokenService}.
     */
    private final TokenService jwtService;

    /**
     * Get all test entities from the database.
     * @return a {@link List} of {@link Test} entities.
     */
    @Override
    public Page<Test> getAllTests(
            HttpServletRequest request,
            String filter,
            Pageable pageable) {
        final String headers = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String token = headers.substring(7);
        final String username = jwtService.extractUsername(token);

        if (username != null) {
            var user = userRepository.findByUsername(username);
            assert user.isPresent();

            if (GetAllTestsFilter.valueOf(filter).equals(GetAllTestsFilter.ALL)) {
                return repository
                        .findByCreatorOrCollaborators(user.get(),
                                user.get(), pageable);
            }
            else if (GetAllTestsFilter.valueOf(filter).equals(GetAllTestsFilter.OWN)) {
                return repository
                        .findByCreator(user.get(), pageable);
            }
            else if (GetAllTestsFilter.valueOf(filter).equals(GetAllTestsFilter.COLLABORATING)) {
                return repository
                        .findByCollaborators(user.get(), pageable);
            }
        }

        return Page.empty();
    }

    /**
     * Function to create a {@link Test} entity.
     * @param request for creating {@link Test} entity.
     * @return a {@link Pair} class holding the data and the response message.
     */
    @Override
    public HttpStatus createTest(CreateTestEntityRequest request) {
        Optional<User> creatorOptional = userRepository.findById(request.getCreator());
        var collaborators = new ArrayList<User>();
        var students = new ArrayList<User>();

        // Check if the creator exists.
        if (creatorOptional.isEmpty()) {
            return HttpStatus.BAD_REQUEST;
        }

        // Check if all collaborators exist.
        Optional<User> collaboratorOptional;
        for (UUID collaborator : request.getCollaborators()) {
            collaboratorOptional = userRepository.findById(collaborator);
            if (collaboratorOptional.isEmpty()) {
                return HttpStatus.BAD_REQUEST;
            }
            else {
                collaborators.add(collaboratorOptional.get());
            }
        }

        // Check if all students exist.
        Optional<User> studentOptional;
        for (UUID student : request.getStudents()) {
            studentOptional = userRepository.findById(student);
            if (studentOptional.isEmpty()) {
                return HttpStatus.BAD_REQUEST;
            }
            else {
                students.add(studentOptional.get());
            }
        }

        var test = Test.builder()
                .subject(request.getSubject())
                .collaborators(collaborators)
                .opensAt(request.getOpensAt())
                .students(students)
                .creator(creatorOptional.get())
                .build();

        var questions = new ArrayList<Question>();
        request.getQuestions().forEach(questionRequest -> {
            var question = Question.builder()
                    .question(questionRequest.getQuestion())
                    .type(QuestionType.valueOf(questionRequest.getType()))
                    .points(questionRequest.getPoints())
                    .build();
            var answers = new ArrayList<Answer>();
            if (questionRequest.getAnswers() != null) {
                questionRequest.getAnswers().forEach(answerRequest -> {
                    var answer = Answer.builder()
                            .answer(answerRequest.getAnswer())
                            .question(question)
                            .type(AnswerType.valueOf(answerRequest.getType()))
                            .correct(answerRequest.isCorrect())
                            .build();
                    answers.add(answer);
                    answerRepository.saveAll(answers);
                    question.getAnswers().add(answer);
                });
            }
            test.getQuestions().add(question);
            questions.add(question);
            questionRepository.saveAll(questions);
        });

        repository.save(test);

        return HttpStatus.CREATED;
    }

    /**
     * Function to update a {@link Test} entity.
     * @param id      for identify the {@link Test} entity.
     * @param request for updating {@link Test} entity.
     * @return a {@link Pair} class holding the data and the response message.
     */
    @Override
    public Pair<Optional<Test>> updateTest(UUID id, UpdateTestEntityRequest request) {
        return null;
    }
}
