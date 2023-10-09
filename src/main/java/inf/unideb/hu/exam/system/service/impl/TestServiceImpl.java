
package inf.unideb.hu.exam.system.service.impl;

import inf.unideb.hu.exam.system.dao.AnswerDao;
import inf.unideb.hu.exam.system.dao.QuestionDao;
import inf.unideb.hu.exam.system.dao.TestDao;
import inf.unideb.hu.exam.system.dao.UserDao;
import inf.unideb.hu.exam.system.models.*;
import inf.unideb.hu.exam.system.models.enums.AnswerType;
import inf.unideb.hu.exam.system.models.enums.QuestionType;
import inf.unideb.hu.exam.system.request.CreateTestEntityRequest;
import inf.unideb.hu.exam.system.service.TestService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@Slf4j
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
     * Get all test entities from the database.
     * @param username of the {@link User}.
     * @param filter {@link GetAllTestsFilter} for the filter.
     * @param pageable for multiple entities.
     * @return a {@link Page} of {@link Test} entities.
     */
    @Override
    public Page<Test> getAllTests(
            String username,
            GetAllTestsFilter filter,
            Pageable pageable) {

        var user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            if (filter.equals(GetAllTestsFilter.ALL)) {
                log.info("Get all tests by filter: " + filter);
                return repository
                        .findByCreatorOrCollaborators(user.get(),
                                user.get(), pageable);
            }
            else if (filter.equals(GetAllTestsFilter.OWN)) {
                log.info("Get all tests by filter: " + filter);
                return repository
                        .findByCreator(user.get(), pageable);
            }
            else if (filter.equals(GetAllTestsFilter.COLLABORATING)) {
                log.info("Get all tests by filter: " + filter);
                return repository
                        .findByCollaborators(user.get(), pageable);
            }
        }

        log.error("User with username " + username + " was not found!" );
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
            log.error("User with id " + request.getCreator() + " was not found for creator!");
            return HttpStatus.BAD_REQUEST;
        }

        // Check if all collaborators exist.
        Optional<User> collaboratorOptional;
        for (UUID collaborator : request.getCollaborators()) {
            collaboratorOptional = userRepository.findById(collaborator);
            if (collaboratorOptional.isEmpty()) {
                log.error("User with id " + collaborator + " was not found for collaborator!");
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
                log.error("User with id " + student + " was not found for student!");
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

        var newTest = repository.save(test);

        collaborators.forEach(collaborator -> {
            collaborator.getCollaboratorTests().add(newTest);
        });

        students.forEach(student -> {
            student.getStudentTests().add(newTest);
        });

        log.info("Test was created with id " + newTest.getId());
        return HttpStatus.CREATED;
    }

    /**
     * Function to get all {@link Test} entities by subject property.
     * @param username of the {@link User}.
     * @param subject of the {@link Test}.
     * @return a {@link List} of {@link Test} entities.
     */
    @Override
    public List<Test> getAllTestsWithSubjectName(String username,
                                                 String subject) {
        log.info("Get all test with subject containing " + subject);
        return repository.findAllBySubjectContaining(subject.toLowerCase(), username);
    }

    /**
     * Function to get a {@link Test} entity by id.
     * @param id of the {@link Test}.
     * @return an {@link Optional} of {@link Test} entity.
     */
    @Override
    public Optional<Test> getTestById(UUID id) {
        return repository.findById(id);
    }
}
