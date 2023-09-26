package inf.unideb.hu.exam.system.dao;

import inf.unideb.hu.exam.system.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnswerDao extends JpaRepository<Answer, UUID> {
}
