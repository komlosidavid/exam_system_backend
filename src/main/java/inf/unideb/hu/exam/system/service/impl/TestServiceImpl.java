
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
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Class for implementing service methods.
 */
@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestDao repository;
    private final UserDao userRepository;
    private final ModelMapper modelMapper;
    private final TokenService jwtService;

    /**
     * Get all test entities from the database.
     *
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
     *
     * @param request for creating {@link Test} entity.
     * @return a {@link Pair} class holding the data and the response message.
     */
    @Override
    public Pair<Optional<Test>> createTest(CreateTestEntityRequest request) {
        Optional<User> creatorOptional = userRepository.findById(request.getCreator());
        var collaborators = new HashSet<User>();
        var students = new HashSet<User>();

        // Check if the creator exists.
        if (creatorOptional.isEmpty()) {
            return new Pair<>(Optional.empty(),
                    "Creator was not found!");
        }

        // Check if all collaborators exist.
        Optional<User> collaboratorOptional;
        for (UUID collaborator : request.getCollaborators()) {
            collaboratorOptional = userRepository.findById(collaborator);
            if (collaboratorOptional.isEmpty()) {
                return new Pair<>(Optional.empty(),
                        "One of the collaborator was not found!");
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
                return new Pair<>(Optional.empty(),
                        "One of the students was not found!");
            }
            else {
                students.add(studentOptional.get());
            }
        }

        // If users were found create the new entity.
        var test = Test.builder()
                .subject(request.getSubject())
                .creator(creatorOptional.get())
                .collaborators(collaborators)
                .students(students)
                .build();

        creatorOptional.get().getOwnTests().add(test);

        var questions = new HashSet<Question>();
        var answers = new HashSet<Answer>();
        request.getQuestions().forEach(questionRequest -> {
             var question = Question.builder()
                    .question(questionRequest.getQuestion())
                    .type(QuestionType.valueOf(questionRequest.getType()))
                    .points(questionRequest.getPoints())
                    .build();

             questionRequest.getAnswers().forEach(answerRequest -> {
                 var answer = Answer.builder()
                         .answer(answerRequest.getAnswer())
                         .isCorrect(answerRequest.isCorrect())
                         .type(AnswerType.valueOf(answerRequest.getType()))
                         .question(question)
                         .build();
                 answers.add(answer);
             });

             question.setAnswers(answers);
             question.setTest(test);
             questions.add(question);
        });

        test.getQuestions().addAll(questions);

        repository.save(test);

        return new Pair<>(Optional.of(test), null);
    }

    /**
     * Function to update a {@link Test} entity.
     *
     * @param id      for identify the {@link Test} entity.
     * @param request for updating {@link Test} entity.
     * @return a {@link Pair} class holding the data and the response message.
     */
    @Override
    public Pair<Optional<Test>> updateTest(UUID id, UpdateTestEntityRequest request) {
        return null;
    }
}
